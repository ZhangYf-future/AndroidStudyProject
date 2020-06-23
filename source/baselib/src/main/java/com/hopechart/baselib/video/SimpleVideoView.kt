package com.hopechart.baselib.video

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.media.AudioManager
import android.os.CountDownTimer
import android.os.Handler
import android.text.TextUtils
import android.util.AttributeSet
import android.view.*
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import com.hopechart.baselib.R
import com.hopechart.baselib.databinding.LayoutPlayerControllerBinding
import com.hopechart.baselib.utils.DateUtils
import com.hopechart.baselib.utils.Logs
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import tv.danmaku.ijk.media.player.IjkTimedText


/**
 *@time 2020/6/11
 *@user 张一凡
 *@description
 *@introduction
 */

// TODO: 2020/6/11 此页面后期需要能够设置可传入的控制播放View，以提高扩展性 
class SimpleVideoView : FrameLayout, IMediaPlayer.OnPreparedListener,
    IMediaPlayer.OnVideoSizeChangedListener, IMediaPlayer.OnCompletionListener,
    IMediaPlayer.OnErrorListener, IMediaPlayer.OnInfoListener,
    IMediaPlayer.OnBufferingUpdateListener, IMediaPlayer.OnSeekCompleteListener,
    IMediaPlayer.OnTimedTextListener, SurfaceHolder.Callback, View.OnClickListener {

    //播放状态
    companion object {
        //出错
        const val STATE_ERROR = -1

        //空闲
        const val STATE_IDLE = 0

        //加载中
        const val STATE_PREPARING = 1

        //加载完成
        const val STATE_PREPARED = 2

        //播放中
        const val STATE_PLAYING = 3

        //暂停
        const val STATE_PAUSED = 4

        //停止
        const val STATE_STOP = 6

        //后台播放完成
        const val STATE_PLAYBACK_COMPLETED = 5

        //播放完成
        const val STATE_PLAY_COMPLETE = 7

        //视频缓冲中的状态
        const val VIDEO_BUFFERING_START = 701//正在缓冲
        const val VIDEO_BUFFERING_END = 702 //缓冲结束

        //间隔500毫秒发送一次时间
        private const val RANGE_TIME = 500
    }

    //是否是直播，默认不是
    private var mIsLive = false

    //播放地址
    private var mVideoPath: String? = null

    //当前的播放状态，默认为空闲状态
    private var mCurrentState = STATE_IDLE

    //这里使用surfaceView做播放时显示内容的View
    private val mSurfaceView by lazy {
        val view = SurfaceView(context)
        view.holder.addCallback(this)
        val params =
            ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        view.layoutParams = params
        view
    }

    //播放器
    private var mMediaPlayer: IjkMediaPlayer? = null

    //控制部分的View
    private val mControllerViewBinding by lazy {
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_player_controller, null, false)
        DataBindingUtil.bind<LayoutPlayerControllerBinding>(view)
    }

    //handler发送消息
    private val mHandler = Handler()

    //消息循环线程
    private val mPlayInfoRunnable by lazy {
        ChangePlayTimeRunnable()
    }

    //记录是否需要读取播放器信息,默认需要
    private var mNeedReadPlayerInfo = true

    //动画执行器
    private var mAnimator: ValueAnimator? = null

    //全屏点击事件
    private var mClickFullScreenListener: (() -> Unit)? = null

    //发送心跳事件回调
    private var mPlayIntervalListener: (() -> Unit)? = null

    //需要重新加载数据的回调
    private var mNeedReloadListener: (() -> Unit)? = null

    //播放状态监听的接口
    private var mPlayStatusChangeListener: ((Int) -> Unit)? = null

    //点击播放器返回键的接口
    private var mPlayerBackClickListener: (() -> Unit)? = null

    //发送心跳的时间间隔，需要等待30秒发一次心跳
    private var mPlayIntervalTime = System.currentTimeMillis()

    //倒计时管理，当用户两秒内没有操作的话则调用此倒计时隐藏控制器View
    private val mCountDown by lazy {
        object : CountDownTimer(2000, 2000) {
            override fun onFinish() {
                //结束时调用隐藏的方法
                hiddenController()
            }

            override fun onTick(millisUntilFinished: Long) {

            }

        }
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    //初始化页面
    private fun init() {
        this.addView(mSurfaceView)
        this.addView(mControllerViewBinding?.root)
        //控制部分View的点击事件
        mControllerViewBinding?.clController?.setOnClickListener(this)
        //播放/暂停按钮点击事件
        mControllerViewBinding?.ivPlayPause?.setOnClickListener(this)
        //全屏点击事件
        mControllerViewBinding?.ivFullScreen?.setOnClickListener {
            mClickFullScreenListener?.invoke()
        }
        //返回键点击事件
        mControllerViewBinding?.ivPlayerBack?.setOnClickListener {
            mPlayerBackClickListener?.invoke()
        }
        //播放器View点击事件
        mSurfaceView.setOnClickListener {
            showController()
        }
        //进度条事件
        mControllerViewBinding?.playProgress?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mMediaPlayer?.let {
                    it.seekTo(seekBar?.progress?.toLong() ?: it.currentPosition)
                }
            }

        })
    }


    //开始播放
    private fun openVideo() {
        if (TextUtils.isEmpty(mVideoPath))
            return
        val am = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
        //每次都创建新的播放器
        createPlayer()
        //设置播放器监听
        mMediaPlayer?.let {
            //缓冲监听
            it.setOnPreparedListener(this)
            //video尺寸变化监听
            it.setOnVideoSizeChangedListener(this)
            it.setOnCompletionListener(this)
            it.setOnErrorListener(this)
            it.setOnInfoListener(this)
            it.setOnBufferingUpdateListener(this)
            it.setOnSeekCompleteListener(this)
            it.setOnTimedTextListener(this)
            //设置数据源
            it.dataSource = mVideoPath
            //绑定到Surface
            it.setDisplay(mSurfaceView.holder)
            //准备播放
            setLoadingState()
            it.prepareAsync()
        }

    }

    //创建ijkMediaPlayer
    private fun createPlayer() {
        //创建播放器
        mMediaPlayer = IjkMediaPlayer()
        //设置Debug模式下打印日志
        IjkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG)
        //设置参数
        mMediaPlayer?.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 0)
        //不使用openSLES
        mMediaPlayer?.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 0)
        mMediaPlayer?.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1)
        mMediaPlayer?.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0)
        mMediaPlayer?.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", 0)
        mMediaPlayer?.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48)
    }


    //设置播放路径并立即开始播放
    fun setVideoPath(path: String, playNow: Boolean) {
        //停止之前播放的视频
        mMediaPlayer?.let {
            it.stop()
            it.release()
            setPausePlayState()
        }
        this.mVideoPath = path
        setIdleState()
        if (playNow)
            openVideo()
    }

    //设置直播
    fun setLive() {
        mIsLive = true
        //如果是直播，则进度条和时间不显示
        mControllerViewBinding?.playProgress?.visibility = View.GONE
        mControllerViewBinding?.tvTime?.visibility = View.GONE
    }

    //设置视频
    fun setVideo() {
        mIsLive = false
        //如果是视频，则所有按钮都显示
        mControllerViewBinding?.playProgress?.visibility = View.VISIBLE
        mControllerViewBinding?.tvTime?.visibility = View.VISIBLE
    }

    //设置当前是全屏状态
    fun setFullScreen() {
        mControllerViewBinding?.let {
            it.isFullScreen = true
        }
    }

    //设置当前不是全屏状态
    fun setNoFullScreen() {
        mControllerViewBinding?.let {
            it.isFullScreen = false
        }
    }

    //设置点击全屏的事件
    fun setClickFullScreenListener(clickFullScreen: () -> Unit) {
        this.mClickFullScreenListener = clickFullScreen
    }

    //设置发送心跳事件
    fun setPlayIntervalListener(listener: () -> Unit) {
        this.mPlayIntervalListener = listener
    }

    //设置需要重新加载数据事件
    fun setNeedReloadListener(listener: () -> Unit) {
        this.mNeedReloadListener = listener
    }

    //设置视频暂停
    fun setPause() {
        mMediaPlayer?.let {
            it.pause()
            setPausePlayState()
        }
    }


    //设置状态改变的接口
    fun setPlayStatusChangeListener(listener: (Int) -> Unit) {
        this.mPlayStatusChangeListener = listener
    }

    //设置点击播放器返回键的接口
    fun setPlayBackClickListener(listener: () -> Unit) {
        this.mPlayerBackClickListener = listener
    }


    //设置准备中的状态
    private fun setIdleState() {
        mCurrentState = STATE_IDLE
        //播放按钮不可点击
        mControllerViewBinding?.ivPlayPause?.isEnabled = false
        //进度条不可点击
        mControllerViewBinding?.playLoading?.isEnabled = false
        //加载进度条不显示
        mControllerViewBinding?.playLoading?.visibility = View.GONE
        //播放按钮的图标为可播放
        mControllerViewBinding?.ivPlayPause?.setImageResource(R.drawable.ic_video_play)
        //播放按钮显示
        mControllerViewBinding?.ivPlayPause?.visibility = View.VISIBLE
        //通知状态改变
        mPlayStatusChangeListener?.invoke(mCurrentState)
    }

    //设置加载中的状态
    private fun setLoadingState() {
        mCurrentState = STATE_PREPARING
        //播放按钮和进度条均不可点击
        mControllerViewBinding?.ivPlayPause?.isEnabled = false
        mControllerViewBinding?.playLoading?.isEnabled = false
        //加载进度条显示
        mControllerViewBinding?.playLoading?.visibility = View.VISIBLE
        //播放按钮的图标为可播放
        mControllerViewBinding?.ivPlayPause?.setImageResource(R.drawable.ic_video_play)
        //播放按钮不显示
        mControllerViewBinding?.ivPlayPause?.visibility = View.GONE
        //通知状态改变
        mPlayStatusChangeListener?.invoke(mCurrentState)
    }

    //设置加载完成的状态
    private fun setLoadedState() {
        mCurrentState = STATE_PREPARED
        //播放按钮和进度条均可点击
        mControllerViewBinding?.ivPlayPause?.isEnabled = true
        mControllerViewBinding?.playLoading?.isEnabled = true
        //加载进度条不显示
        mControllerViewBinding?.playLoading?.visibility = View.GONE
        //播放按钮的图标为可播放
        mControllerViewBinding?.ivPlayPause?.setImageResource(R.drawable.ic_video_play)
        //播放按钮显示
        mControllerViewBinding?.ivPlayPause?.visibility = View.VISIBLE
        //通知状态改变
        mPlayStatusChangeListener?.invoke(mCurrentState)
    }

    //设置开始播放的状态
    private fun setStartPlayState() {
        mCurrentState = STATE_PLAYING
        //播放按钮和进度条均可点击
        mControllerViewBinding?.ivPlayPause?.isEnabled = true
        mControllerViewBinding?.playLoading?.isEnabled = true
        //加载中进度条不显示
        mControllerViewBinding?.playLoading?.visibility = View.GONE
        //播放按钮的图标为可暂停
        mControllerViewBinding?.ivPlayPause?.setImageResource(R.drawable.ic_video_pause)
        //播放按钮显示
        mControllerViewBinding?.ivPlayPause?.visibility = View.VISIBLE
        //启动消息循环
        mNeedReadPlayerInfo = true
        mHandler.post(mPlayInfoRunnable)
        //开始播放之后则开始倒计时
        mCountDown.start()
        //开始播放的时候，判断时间是否超过100秒，如果100秒没有发送心跳，则调用回调接口通知页面重新请求数据
        mNeedReloadListener?.let { listener ->
            if (System.currentTimeMillis() - mPlayIntervalTime >= 100 * 1000) {
                listener.invoke()
                return
            }
        }
        //通知状态改变
        mPlayStatusChangeListener?.invoke(mCurrentState)
    }

    //设置暂停播放的状态
    private fun setPausePlayState() {
        mCurrentState = STATE_PAUSED
        //播放按钮和进度条均可点击
        mControllerViewBinding?.ivPlayPause?.isEnabled = true
        mControllerViewBinding?.playLoading?.isEnabled = true
        //加载进度条不显示
        mControllerViewBinding?.playLoading?.visibility = View.GONE
        //播放按钮的图标为可播放
        mControllerViewBinding?.ivPlayPause?.setImageResource(R.drawable.ic_video_play)
        //待播放按钮显示
        mControllerViewBinding?.ivPlayPause?.visibility = View.VISIBLE
        //暂停消息循环
        mHandler.removeCallbacksAndMessages(null)
        mNeedReadPlayerInfo = false
        //显示操作按钮
        showController()
        //停止倒计时
        mCountDown.cancel()
        //通知状态改变
        mPlayStatusChangeListener?.invoke(mCurrentState)
    }

    //设置停止状态
    private fun setStopPlayState() {
        mCurrentState = STATE_STOP
        //播放按钮和进度条均可点击
        mControllerViewBinding?.ivPlayPause?.isEnabled = true
        mControllerViewBinding?.playLoading?.isEnabled = true
        //加载进度条不显示
        mControllerViewBinding?.playLoading?.visibility = View.GONE
        //播放按钮的图标为可播放
        mControllerViewBinding?.ivPlayPause?.setImageResource(R.drawable.ic_video_play)
        //播放按钮显示
        mControllerViewBinding?.ivPlayPause?.visibility = View.VISIBLE
        //停止消息循环
        mHandler.removeCallbacksAndMessages(null)
        mNeedReadPlayerInfo = false
        //通知状态改变
        mPlayStatusChangeListener?.invoke(mCurrentState)
    }

    //设置出错状态
    private fun setErrorPlayState() {
        mCurrentState = STATE_ERROR
        //播放按钮和进度条均可点击
        mControllerViewBinding?.ivPlayPause?.isEnabled = true
        mControllerViewBinding?.playLoading?.isEnabled = true
        //加载进度条不显示
        mControllerViewBinding?.playLoading?.visibility = View.GONE
        //播放按钮的图标为可播放
        mControllerViewBinding?.ivPlayPause?.setImageResource(R.drawable.ic_video_play)
        //播放按钮显示
        mControllerViewBinding?.ivPlayPause?.visibility = View.VISIBLE
        //停止消息循环
        mHandler.removeCallbacksAndMessages(null)
        mNeedReadPlayerInfo = false
        //通知状态改变
        mPlayStatusChangeListener?.invoke(mCurrentState)
    }

    //设置视频缓冲的状态
    private fun setBufferingState(buffering: Boolean) {
        mControllerViewBinding?.ivPlayPause?.visibility = if (buffering) View.GONE else View.VISIBLE
        mControllerViewBinding?.playLoading?.visibility = if (buffering) View.VISIBLE else View.GONE
        if (buffering) {
            mCurrentState = VIDEO_BUFFERING_START
            showController()
        } else {
            mCurrentState = VIDEO_BUFFERING_END
            //加载完成则开启倒计时
            mCountDown.start()

        }
        //通知状态改变
        mPlayStatusChangeListener?.invoke(mCurrentState)
    }

    //设置视频播放完成的状态
    private fun setPlayCompleteState() {
        mCurrentState = STATE_PLAY_COMPLETE
        mControllerViewBinding?.ivPlayPause?.setImageResource(R.drawable.ic_video_play)
        mMediaPlayer?.let {
            //进度条设置到开始状态
            it.seekTo(0)
            initPlayTime()
            mControllerViewBinding?.playProgress?.progress = 0
        }
        //停止消息循环
        mHandler.removeCallbacksAndMessages(null)
        mNeedReadPlayerInfo = false
        //播放完成后显示控制器部分
        showController()
        //通知状态改变
        mPlayStatusChangeListener?.invoke(mCurrentState)
    }

    //设置播放时间
    private fun setPlayTime() {
        mMediaPlayer?.let {
            val playTime = getFormatTotalTime(it.currentPosition, it.duration)
            val videoTime = getFormatTotalTime(it.duration, it.duration)
            mControllerViewBinding?.tvTime?.text = "${playTime}/${videoTime}"
        }
    }

    //初始化播放时间
    private fun initPlayTime() {
        mMediaPlayer?.let {
            mControllerViewBinding?.playProgress?.max = it.duration.toInt()
            setPlayTime()
        }
    }

    //获取视频中的时间长度
    private fun getFormatTotalTime(time: Long, timeReference: Long) =
        when (DateUtils.getTimeLength(timeReference)) {
            3 -> DateUtils.getTimeHour(time) + ":" + DateUtils.getTimeMinute(time) + ":" + DateUtils.getTimeSecond(
                time
            )
            2 -> DateUtils.getTimeMinute(time) + ":" + DateUtils.getTimeSecond(time)
            else -> DateUtils.getTimeSecond(time).toString()
        }

    override fun onPrepared(player: IMediaPlayer?) {
        Logs.e("onPrepared...")
        //准备完成
        setLoadedState()
        //设置播放时间
        initPlayTime()
        //开始播放
        player?.start()
        //设置开始播放状态
        setStartPlayState()
    }

    override fun onVideoSizeChanged(p0: IMediaPlayer?, p1: Int, p2: Int, p3: Int, p4: Int) {
        Logs.e("onVideoSizeChanged...")
    }

    override fun onCompletion(p0: IMediaPlayer?) {
        Logs.e("onCompletion...")
        //播放完成后的回调
        setPlayCompleteState()
    }

    override fun onError(p0: IMediaPlayer?, p1: Int, p2: Int): Boolean {
        Logs.e("播放出错:$p1,$p2")
        //设置出错状态
        setErrorPlayState()
        return true
    }

    override fun onInfo(p0: IMediaPlayer?, bufferState: Int, p2: Int): Boolean {
        Logs.e("onInfo...$bufferState,$p2")
        //设置是否为读取缓存状态
        setBufferingState(bufferState == VIDEO_BUFFERING_START)
        return true
    }

    override fun onBufferingUpdate(player: IMediaPlayer?, p1: Int) {
        //每次更新之后计算时间
        player?.let {
            val bufferTime = it.duration * (p1 / 100.toFloat())
            mControllerViewBinding?.playProgress?.secondaryProgress = bufferTime.toInt()
        }
    }

    override fun onSeekComplete(p0: IMediaPlayer?) {
        Logs.e("onSeekComplete...")
    }

    override fun onTimedText(p0: IMediaPlayer?, p1: IjkTimedText?) {
        Logs.e("onTimedText...")
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        Logs.e("surfaceChanged")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        Logs.e("surfaceDestroyed")
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        if (holder != null) {
            mMediaPlayer?.setDisplay(holder)
        }
    }

    //点击事件
    override fun onClick(v: View?) {
        //点击按钮重置倒计时
        mCountDown.cancel()
        mCountDown.start()

        when (v?.id) {
            R.id.iv_play_pause -> {
                //判断当前的控制器部分是否隐藏
                val isGone = mControllerViewBinding?.clController?.visibility
                isGone?.let {
                    if (it != View.GONE) {
                        val alpha = mControllerViewBinding?.clController?.alpha
                        if (alpha != null) {
                            if (alpha != 1.toFloat()) {
                                //如果没显示先显示出来
                                showController()
                                return
                            }
                        }
                    }
                }

                mMediaPlayer?.let {
                    if (mCurrentState == STATE_ERROR) {
                        openVideo()
                        return
                    }
                    if (it.isPlaying) {
                        it.pause()
                        setPausePlayState()
                    } else {
                        if (mCurrentState == STATE_STOP) {
                            openVideo()
                            return
                        }

                        it.start()
                        setStartPlayState()
                    }
                }
            }
            R.id.cl_controller -> {
                val isGone = mControllerViewBinding?.clController?.visibility
                isGone?.let {
                    if (it != View.GONE) {
                        val alpha = mControllerViewBinding?.clController?.alpha
                        if (alpha != null) {
                            if (alpha == 1.toFloat()) {
                                hiddenController()
                            } else {
                                showController()
                            }
                            return
                        }
                    }
                }
                hiddenController()
            }
        }
    }


    //控制器部分隐藏的动画，默认立即执行动画，但是也有可能用户操作之后等待1秒钟执行动画
    private fun hiddenController(delay: Long = 0) {
        //如果当前是加载中或者暂停状态，则不能隐藏操作狂
        mMediaPlayer?.let {
            if (!it.isPlaying)
                return
        }

        if (mCurrentState == VIDEO_BUFFERING_START) {
            return
        }

        val alpha = mControllerViewBinding?.clController?.alpha ?: 0.toFloat()
        mAnimator?.cancel()
        mAnimator = ObjectAnimator.ofFloat(alpha, 0.toFloat())
        mAnimator?.let {
            it.duration = 200
            it.interpolator = LinearInterpolator()
            it.addUpdateListener { valueAnimator ->
                val value = valueAnimator.animatedValue as Float
                //设置透明度
                mControllerViewBinding?.clController?.alpha = value
                if (value < 0.1) {
                    mControllerViewBinding?.clController?.visibility = View.GONE
                }
            }
            it.startDelay = delay
            it.start()
        }

    }

    //控制器部分显示
    private fun showController() {
        mAnimator?.cancel()
        mControllerViewBinding?.clController?.visibility = View.VISIBLE
        val alpha = mControllerViewBinding?.clController?.alpha ?: 0.toFloat()
        mAnimator = ObjectAnimator.ofFloat(alpha, 1.toFloat())
        mAnimator?.let {
            it.duration = 200
            it.interpolator = LinearInterpolator()
            it.addUpdateListener { valueAnimator ->
                val value = valueAnimator.animatedValue as Float
                //设置透明度
                mControllerViewBinding?.clController?.alpha = value
                if (value == 1.toFloat()) {
                    mCountDown.start()
                }
            }
            it.start()
            //显示的时候经过两秒隐藏
            mCountDown.start()
        }
    }

    //页面退出时需要调用此方法销毁资源
    fun destroy() {
        //完成播放
        mMediaPlayer?.let {
            it.release()
        }
        //完成消息轮播
        mHandler.removeCallbacksAndMessages(null)
        mNeedReadPlayerInfo = false
        //完成倒计时
        mCountDown.cancel()
    }

    //判断当前是否是在播放中
    fun isPlaying(): Boolean {
        mMediaPlayer?.let {
            return it.isPlaying
        }

        return false
    }

    //设置显示标题栏
    fun showTitle() {
        mControllerViewBinding?.showTitle = true
        mControllerViewBinding?.executePendingBindings()
    }

    //设置隐藏标题栏
    fun hiddenTitle() {
        mControllerViewBinding?.showTitle = false
        mControllerViewBinding?.executePendingBindings()
    }

    //设置标题栏文字
    fun setTitle(title: String) {
        mControllerViewBinding?.title = title
        mControllerViewBinding?.executePendingBindings()
    }

    //内部类，用于实现消息循环，读取播放信息
    inner class ChangePlayTimeRunnable : Runnable {
        override fun run() {
            if (mNeedReadPlayerInfo) {
                mMediaPlayer?.let {
                    if (mPlayIntervalListener != null) {
                        if (System.currentTimeMillis() - mPlayIntervalTime >= 30 * 1000) {
                            mPlayIntervalTime = System.currentTimeMillis()
                            mPlayIntervalListener?.invoke()
                        }
                    }
                    mHandler.postDelayed(this, RANGE_TIME.toLong())
                    setPlayTime()
                    mControllerViewBinding?.playProgress?.progress = it.currentPosition.toInt()
                }
            }
        }
    }
}



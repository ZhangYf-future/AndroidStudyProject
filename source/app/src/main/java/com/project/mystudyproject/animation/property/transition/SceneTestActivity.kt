package com.project.mystudyproject.animation.property.transition

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.transition.*
import com.hopechart.baselib.ui.BaseActivity
import com.hopechart.baselib.utils.Logs
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivitySceneTestBinding

class SceneTestActivity : BaseActivity<ActivitySceneTestBinding>() {

    private val mSceneRoot by lazy {
        mBinding.sceneLayout
    }

    //第一个场景信息
    private val mSceneALayout by lazy {
        Scene.getSceneForLayout(mSceneRoot, R.layout.layout_scene_test_a, this)
    }

    //第二个场景信息
    private val mSceneBLayout by lazy {
        Scene.getSceneForLayout(mSceneRoot, R.layout.layout_scene_test_b, this)
    }

    //第三个场景信息
    private val mSceneCLayout by lazy {
        Scene.getSceneForLayout(mSceneRoot,R.layout.layout_scene_test_c,this)
    }

    //创建一个过渡类型
    private val mTransition by lazy {
        Fade().apply {
            duration = 2000
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_scene_test

    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {

            R.id.btn_change_first_layout -> changeFirstLayoutWithScene()

            R.id.btn_change_second_layout -> changeSecondLayoutWithScene()

            R.id.btn_create_scene_with_code -> createSceneWithCode()

            R.id.btn_create_scene_function -> createSceneFunction()

            R.id.btn_use_transition_with_resource -> useTransitionWithResource()

            R.id.btn_remove_transition_target -> removeTransitionTarget()

            R.id.btn_add_transition_target -> addTransitionTarget()

            R.id.btn_use_transition_set_change_layout -> useTransitionSetChangeLayout()

            R.id.btn_use_transition_in_current_layout -> useTransitionInCurrentLayout()

            R.id.btn_listenering_transition_life -> listeneringTransition()

            R.id.btn_use_custom_transition -> useCustomTransition()

            R.id.btn_show_first_layout_with_custom_transition -> showFirstLayoutWithCustomTransition()
        }
    }

    //使用场景切换第一个布局
    private fun changeFirstLayoutWithScene() {
        //创建过渡
        val transition = Fade()
        transition.duration = 3000
        TransitionManager.go(mSceneALayout, transition)
    }

    //使用场景切换布局
    private fun changeSecondLayoutWithScene() {
        //创建过渡
        val transition = ChangeBounds()
        //应用过渡
        //TransitionManager.go(sceneALayout)
        TransitionManager.go(mSceneBLayout, transition)
    }

    //通过代码指定场景
    private fun createSceneWithCode() {
        //创建一个ImageView并设置图片
        val imageView = ImageView(this)
        imageView.setImageResource(R.mipmap.ic_launcher)
        //创建场景，指定要添加的View以及将要添加到的ViewGroup
        val scene = Scene(mBinding.sceneLayout, imageView)
        //执行操作
        TransitionManager.go(scene)
    }

    //创建场景操作
    private fun createSceneFunction() {
        mSceneBLayout.setEnterAction {
            //当第二个视图进入的时候，隐藏第二个按钮
            mBinding.btnChangeSecondLayout.animate().alpha(0f).start()
        }

        mSceneBLayout.setExitAction {
            //当第二个视图进入的时候，显示第二个按钮
            mBinding.btnChangeSecondLayout.animate().alpha(1f).start()
        }

        //显示第二个场景
        changeSecondLayoutWithScene()
    }

    //使用在资源文件中定义的过渡
    private fun useTransitionWithResource() {
        //加载过渡
        val fade = TransitionInflater.from(this).inflateTransition(R.transition.test_fade)
        //应用到场景中
        TransitionManager.go(mSceneBLayout, fade)
    }

    //移除不想应用过渡效果的视图
    private fun removeTransitionTarget() {
        //创建过渡类型
//        val transition = Fade()
//        transition.duration = 2000

        //首先将根布局加入到tag中
        //mTransition.addTarget(R.id.cl_content)
        //然后将不想使用动画效果的布局移除出去
        Logs.e("添加的tag:${mTransition.targetIds}")
        mTransition.removeTarget(R.id.tv_2)
        mTransition.removeTarget(R.id.tv_3)
        TransitionManager.go(mSceneCLayout, mTransition)
    }

    //添加想要使用过渡效果的视图
    private fun addTransitionTarget() {
        //创建过渡类型
        val transition = Fade()
        transition.duration = 2000

        mTransition.addTarget(R.id.tv_3)
        TransitionManager.go(mSceneCLayout, transition)
    }

    //使用过度集更改布局
    private fun useTransitionSetChangeLayout() {
        val transitionSet =
            TransitionInflater.from(this).inflateTransition(R.transition.test_transition_set)
        transitionSet.duration = 1000
        TransitionManager.go(mSceneBLayout, transitionSet)
    }

    //在当前布局添加或者移除view时使用过渡
    private fun useTransitionInCurrentLayout(){
        val transition = ChangeBounds()
        val imageView = ImageView(this)
        imageView.setImageResource(R.mipmap.ic_launcher)
        TransitionManager.beginDelayedTransition(mBinding.sceneLayout,transition)
        mBinding.sceneLayout.addView(imageView)
    }

    //监听过渡的生命周期
    private fun listeneringTransition(){
        val transition = Fade()
        transition.duration = 3000
        transition.addListener(object : Transition.TransitionListener{
            override fun onTransitionStart(transition: Transition) {
                Logs.e("过渡开始")
            }

            override fun onTransitionEnd(transition: Transition) {
                Logs.e("过渡结束")
            }

            override fun onTransitionCancel(transition: Transition) {
                Logs.e("过渡取消")
            }

            override fun onTransitionPause(transition: Transition) {
                Logs.e("过渡暂停")
            }

            override fun onTransitionResume(transition: Transition) {
                Logs.e("过渡重现")
            }

        })
        transition.addListener(object : TransitionListenerAdapter(){
            override fun onTransitionEnd(transition: Transition) {
                super.onTransitionEnd(transition)
                Logs.e("adapter: 过渡结束")
            }
        })

        TransitionManager.go(mSceneBLayout,transition)
    }


    private val customTransition by lazy {
        CustomTransition().apply {
            duration = 3000
        }
    }
    //使用自定义过渡
    private fun useCustomTransition(){
        TransitionManager.go(mSceneBLayout,customTransition)
    }

    //使用自定义动画显示第一个布局
    private fun showFirstLayoutWithCustomTransition(){
        val transition = ChangeScroll().apply { duration = 3000
        }
        transition.addListener(object : TransitionListenerAdapter(){
            override fun onTransitionEnd(transition: Transition) {
                super.onTransitionEnd(transition)
                Logs.e("动画结束")
                transition.removeListener(this)
            }
        })
        TransitionManager.go(mSceneALayout,customTransition)

    }
}
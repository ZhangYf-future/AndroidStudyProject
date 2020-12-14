package com.project.mystudyproject.animation.property.transition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.transition.*
import com.hopechart.baselib.ui.BaseActivity
import com.hopechart.baselib.utils.Logs
import com.project.mystudyproject.R
import com.project.mystudyproject.databinding.ActivitySceneTestBinding
import com.project.mystudyproject.databinding.LayoutSceneTestABinding
import com.project.mystudyproject.databinding.LayoutSceneTestBBinding

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
        }
    }

    //使用场景切换第一个布局
    private fun changeFirstLayoutWithScene() {
        //创建过渡
        val transition = Fade()
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
        val scene = Scene(mBinding.sceneLayout,imageView)
        //执行操作
        TransitionManager.go(scene)
    }

    //创建场景操作
    private fun createSceneFunction(){
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
    private fun useTransitionWithResource(){
        //加载过渡
        val fade = TransitionInflater.from(this).inflateTransition(R.transition.test_fade)
        //应用到场景中
        TransitionManager.go(mSceneBLayout,fade)
    }

    //移除不想应用过渡效果的视图
    private fun removeTransitionTarget(){
        //创建过渡类型
        val transition = AutoTransition()
        transition.duration = 2000


        //开始过渡

        transition.addTarget(mSceneBLayout.sceneRoot.findViewById<View>(R.id.image_view))
    }

    //添加想要使用过渡效果的视图
    private fun addTransitionTarget(){
        //创建过渡类型
        val transition = AutoTransition()
        transition.duration = 2000

        //开始过渡
        TransitionManager.go(mSceneBLayout,transition)
        transition.addTarget(mSceneBLayout.sceneRoot.findViewById<View>(R.id.image_view))
    }
}
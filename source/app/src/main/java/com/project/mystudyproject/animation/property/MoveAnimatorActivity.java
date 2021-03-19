package com.project.mystudyproject.animation.property;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.PathInterpolator;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.RecyclerView;

import com.hopechart.baselib.ui.BaseActivity;
import com.hopechart.baselib.utils.Logs;
import com.project.mystudyproject.R;
import com.project.mystudyproject.databinding.ActivityMoveAnimatorBinding;
import org.jetbrains.annotations.NotNull;

/**
 * 使用动画移动视图
 */
public class MoveAnimatorActivity extends BaseActivity<ActivityMoveAnimatorBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_move_animator;
    }

    @Override
    protected void initUI() {
        super.initUI();
        //创建动画用于修改位置
//        SpringAnimation animation1 = new SpringAnimation(mBinding.circleView2,DynamicAnimation.TRANSLATION_X);
//        SpringForce force1 = new SpringForce();
//        force1.setDampingRatio(0.5f);
//        force1.setStiffness(500);
//        animation1.setSpring(force1);
//
//        SpringAnimation animation2 = new SpringAnimation(mBinding.circleView2, DynamicAnimation.TRANSLATION_Y);
//        SpringForce force2 = new SpringForce();
//        force2.setDampingRatio(0.5f);
//        force2.setStiffness(500);
//        animation2.setSpring(force2);
//
//        SpringAnimation animation3 = new SpringAnimation(mBinding.circleView3,DynamicAnimation.TRANSLATION_X);
//        SpringForce force3 = new SpringForce();
//        force3.setDampingRatio(0.5f);
//        force3.setStiffness(500);
//        animation3.setSpring(force3);
//
//        SpringAnimation animation4 = new SpringAnimation(mBinding.circleView3,DynamicAnimation.TRANSLATION_Y);
//        SpringForce force4 = new SpringForce();
//        force4.setDampingRatio(0.5f);
//        force4.setStiffness(500);
//        animation4.setSpring(force4);
//
//        mBinding.circleView.setMoveXListener(aFloat -> {
//            animation1.animateToFinalPosition(aFloat);
//            return null;
//        });
//
//        mBinding.circleView.setMoveYListener(translationY ->{
//            animation2.animateToFinalPosition(translationY);
//            return null;
//        });
//
//        mBinding.circleView2.setMoveXListener(translationX -> {
//            animation3.animateToFinalPosition(translationX);
//            return null;
//        });
//
//        mBinding.circleView2.setMoveYListener(translationY -> {
//            animation4.animateToFinalPosition(translationY);
//            return null;
//        });
    }

    @Override
    public void doClick(@NotNull View view) {
        super.doClick(view);
        switch (view.getId()) {
            case R.id.btn_move_in_x:
                moveInX();
                break;
            case R.id.btn_move_in_y:
                moveInY();
                break;
            case R.id.btn_move_together:
                moveTogether();
                break;
            case R.id.btn_use_path_interpolator_move_view:
                moveViewWithPathInterpolator();
                break;
            case R.id.btn_use_object_animator_to_set_path:
                useObjectAnimatorWithPath();
                break;
            case R.id.btn_use_path_interpolator_create_fall_anim:
                createPathInterpolatorWithPath();
                break;
            case R.id.btn_create_fling_animation:
                createFlingAnimation();
                break;
            case R.id.btn_create_simple_spring_animation:
                createSimpleSpringAnimation();
                break;
            case R.id.btn_create_spring_animation_register_listener:
                createSpringAnimationWithListener();
                break;
            case R.id.btn_create_spring_animation_set_spring_force:
                createSpringAnimationWithSpringForce();
                break;
        }
    }

    //横向移动View
    private void moveInX() {
        ObjectAnimator animator =
                ObjectAnimator.ofFloat(mBinding.tvMovedView, "translationX", 200);
        animator.setDuration(300);
        animator.start();
    }

    //纵向移动View
    private void moveInY() {
        ObjectAnimator animator =
                ObjectAnimator.ofFloat(mBinding.tvMovedView, "translationY", 200);
        animator.setDuration(300);
        animator.start();
    }

    //同时移动View
    private void moveTogether() {
        AnimatorSet set = new AnimatorSet();

        //横向移动
        float end = mBinding.tvMovedView.getTranslationX() + 200;
        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(mBinding.tvMovedView, "translationX", 0f, 200f, 0f);
        xAnimator.setInterpolator(input -> {
            Logs.e("接收到的参数:" + input);
            return input * input * -2f + 2 * input;
        });
        xAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //Logs.e("动画执行的当前值:"+animation.getAnimatedValue());
            }
        });
        xAnimator.setDuration(1000);

        //纵向移动
        ObjectAnimator yAnimator = ObjectAnimator.ofFloat(mBinding.tvMovedView, "translationY", 200f);
        yAnimator.setDuration(1000);


        set.play(xAnimator).with(yAnimator);
        set.start();
    }

    //使用pathInterpolator移动视图
    private void moveViewWithPathInterpolator() {
        float endX = mBinding.tvMovedView.getTranslationX() > 200 ? mBinding.tvMovedView.getTranslationX() - 200 : mBinding.tvMovedView.getTranslationX() + 200;
        //设置动画
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mBinding.tvMovedView, "translationX", endX);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            PathInterpolator pathInterpolator = new PathInterpolator(1f, 0f);
            animatorX.setInterpolator(pathInterpolator);
        }
        animatorX.setDuration(1000);
        animatorX.start();
    }

    //使用ObjectAnimator实现动画路径
    private void useObjectAnimatorWithPath() {
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Path path = new Path();
            path.arcTo(0, mBinding.tvMovedView.getTop(), 500, mBinding.tvMovedView.getTop() + 500f, -90, 180, true);
            ObjectAnimator animator = ObjectAnimator.ofFloat(mBinding.tvMovedView, View.X, View.Y, path);
            animator.setDuration(2000);
            animator.start();
        }
    }

    //使用Path创建PathInterpolator

    /**
     * PathMeasure pathMeasure = new PathMeasure(path,false);
     * Logs.e("length:"+pathMeasure.getLength());
     * float[] pos = new float[2];
     * float[] tan = new float[2];
     * pathMeasure.getPosTan(0,pos,tan);
     * for(float point: pos){
     * Logs.e("点的坐标为："+point);
     * }
     */
    private void createPathInterpolatorWithPath() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Path path = new Path();
            //移动到(0,0)点，必须从这里开始
            path.moveTo(0f, 0f);
            //绘制第一个坠落的路径，是一个1/4的椭圆，注意椭圆所在的矩形范围
            path.arcTo(new RectF(-0.5f, 0f, 0.5f, 2f), -90, 90);
            //绘制第一个弹起的路径，是一个1/2的椭圆
            path.arcTo(new RectF(0.5f, 0.5f, 0.8f, 1.5f), -180, 180);
            //不再弹起，直接连线到结束
            path.lineTo(1f, 1f);
            //设置PathInterpolator
            PathInterpolator pathInterpolator = new PathInterpolator(path);

            //横轴做普通动画
            ObjectAnimator animatorX = ObjectAnimator.ofFloat(mBinding.circleView, "translationX", 0f, 500f);
            animatorX.setDuration(2000);

            //纵轴根据指定的path做动画
            ObjectAnimator animatorY = ObjectAnimator.ofFloat(mBinding.circleView, "translationY", 0f, 500f);
            //将pathInterpolator设置给纵轴方向上的运动
            animatorY.setInterpolator(pathInterpolator);
            animatorY.setDuration(2000);

            //动画集合
            AnimatorSet set = new AnimatorSet();
            set.play(animatorX).with(animatorY);
            set.start();
        }
    }


    //创建一个简单的投掷动画
    private void createFlingAnimation(){
        //首先通过一个动画创建View移动的动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(mBinding.circleView,"translationX",100f);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator anim) {
                super.onAnimationEnd(anim);
                //结束时创建投掷动画缓慢减速
                //创建投掷动画实例
                FlingAnimation animation = new FlingAnimation(mBinding.circleView, DynamicAnimation.TRANSLATION_X);
                //设置速度
                animation.setStartVelocity(170.0f);
                //设置动画的最小值
                animation.setMinValue(50f);
                //设置动画的最大值
                animation.setMaxValue(200f);
                //设置摩擦力
                animation.setFriction(2.0f);
                animation.addUpdateListener((animation1, value, velocity) -> Logs.e("动画执行中:"+ value));
                //开始执行
                animation.start();
            }
        });
        animator.start();
    }

    //创建弹性动画
    private void createSimpleSpringAnimation(){
        //直接通过构造函数指定finalPosition
        //SpringAnimation springAnimation = new SpringAnimation(mBinding.circleView,DynamicAnimation.TRANSLATION_X,500f);
        SpringAnimation springAnimation = new SpringAnimation(mBinding.circleView,DynamicAnimation.TRANSLATION_X);
        //通过SpringForce指定finalPosition
        SpringForce force = new SpringForce();
        force.setFinalPosition(500f);
        springAnimation.setSpring(force);
        springAnimation.start();
            //使用属性动画实现类似于上面的弹性动画的效果
//        ObjectAnimator animator = ObjectAnimator.ofFloat(mBinding.circleView,"translationX",0f,500f);
//        animator.setInterpolator(new OvershootInterpolator());
//        animator.start();
    }

    //创建弹性动画并注册监听器
    private void createSpringAnimationWithListener(){
        //对第一个View设置横向和纵向移动的动画
        SpringAnimation animation1X = new SpringAnimation(mBinding.circleView,DynamicAnimation.TRANSLATION_X,500);
        SpringAnimation animation1Y = new SpringAnimation(mBinding.circleView,DynamicAnimation.TRANSLATION_Y,300);

        //对第二个View设置横向和纵向移动的动画
        SpringAnimation animation2X = new SpringAnimation(mBinding.circleView2,DynamicAnimation.TRANSLATION_X);
        SpringAnimation animation2Y = new SpringAnimation(mBinding.circleView2,DynamicAnimation.TRANSLATION_Y);

        //添加动画监听器,当动画1中的值改变后修改动画2中的值
        animation1X.addUpdateListener((animation, value, velocity) -> animation2X.animateToFinalPosition(value));
        animation1Y.addUpdateListener((animation,value,velocity) -> animation2Y.animateToFinalPosition(value));
        animation1X.addEndListener((animation,canceled,value,velocity) -> Logs.e("动画1执行结束"));
        animation2X.addEndListener((animation,canceled,value,velocity) -> Logs.e("动画2执行结束"));


        animation1X.setStartValue(300f);
        animation1Y.setStartValue(-300f);

        //设置范围
        animation1X.setMinValue(100f);
        animation1X.setMaxValue(1000f);

        //设置起始速度
        animation1X.setStartVelocity(1000f);
        animation1Y.setStartVelocity(-1000f);

        animation1X.start();
        animation1Y.start();
    }

    //为弹性动画设置阻尼比
    private void createSpringAnimationWithSpringForce(){
        SpringAnimation springAnimation = new SpringAnimation(mBinding.circleView2,DynamicAnimation.TRANSLATION_X);
        //创建SpringForce对象
        SpringForce force = new SpringForce(500f);
        //设置阻尼比
        force.setDampingRatio(0f);
        //设置刚度
        force.setStiffness(20);
        //为动画设置SpringForce
        springAnimation.setSpring(force);

        //设置动画的起始位置
        springAnimation.setStartValue(0f);
        //开始播放动画
        springAnimation.start();

        //经过1秒后终止动画
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if(springAnimation.canSkipToEnd()){
//                      springAnimation.skipToEnd();
//                }
                springAnimation.cancel();

            }
        },1000);
    }
}
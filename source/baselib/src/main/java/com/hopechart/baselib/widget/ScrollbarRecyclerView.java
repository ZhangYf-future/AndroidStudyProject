package com.hopechart.baselib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hopechart.baselib.R;
import com.hopechart.baselib.utils.PxUtils;

/**
 * Description:  自定义带滚动条的recyclerView
 *
 * @author lincq
 * @date 2019/6/12
 */
public class ScrollbarRecyclerView extends RecyclerView {
    private int barLength;
    private int barWidth;
    private int barMargin;
    private int barBgColor;
    private int barColor;
    private int barPositionWidth;
    private int barLeft;

    private Paint paintBg;
    private Paint paint;

    private RectF rectFBg;
    private RectF rectF;

    private int position;

    public ScrollbarRecyclerView(Context context) {
        super(context);
    }

    public ScrollbarRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScrollbarRecyclerView);
        barLength = (int) ta.getDimension(R.styleable.ScrollbarRecyclerView_bar_length,PxUtils.dp2px(38));
        barWidth = (int) ta.getDimension(R.styleable.ScrollbarRecyclerView_bar_width,PxUtils.dp2px(4));
        barMargin = (int) ta.getDimension(R.styleable.ScrollbarRecyclerView_bar_margin,PxUtils.dp2px(11));
        barBgColor = ta.getColor(R.styleable.ScrollbarRecyclerView_bar_bg_color,context.getResources().getColor(R.color.gray_d8));
        barColor = ta.getColor(R.styleable.ScrollbarRecyclerView_bar_color,context.getResources().getColor(R.color.color_f54949));
        ta.recycle();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(barColor);

        paintBg = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBg.setStyle(Paint.Style.FILL);
        paintBg.setColor(barBgColor);

        rectFBg = new RectF();
        rectF = new RectF();
        barPositionWidth = barLength/2;
        barLeft = PxUtils.getScreenWidth(context)/2 - barLength/2;

        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int temp = ((GridLayoutManager)getLayoutManager()).findFirstVisibleItemPosition();
                if (position != temp){
                    position = temp;
                    invalidate();
                }

            }
        });

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (canvas == null){
            return;
        }
        if (getAdapter() != null && getAdapter().getItemCount()<=10){
            return;
        }
        rectFBg.set(barLeft,getMeasuredHeight()-barWidth,barLeft+barLength,getMeasuredHeight());
        canvas.drawRoundRect(rectFBg,barWidth/2,barWidth/2,paintBg);
        int left ;
        if (getAdapter()!= null){
            left = barLeft+barLength/2*position/2/(((getAdapter().getItemCount()-10)+1)/2);
        }else {
            left = barLeft;
        }

        rectF.set(left,getMeasuredHeight()-barWidth,left + barLength/2,getMeasuredHeight());
        canvas.drawRoundRect(rectF,barWidth/2,barWidth/2,paint);

    }


}

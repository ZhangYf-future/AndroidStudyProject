package com.hopechart.baselib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.hopechart.baselib.R;
import com.hopechart.baselib.utils.DensityUtils;
import com.hopechart.baselib.utils.Logs;

import org.jetbrains.annotations.NotNull;

/**
 * description：
 * author：yiwen
 * date：2019/3/29 上午 10:22
 * remark： 注意不要在RecyclerView的item中使用此View，缓存会造成部分数据显示异常
 */
public class TwoStyleTextView extends AppCompatTextView {
    //开始位置
    private int mStartPosition = -1;
    //开始位置字符
    private String mStartPositionStr;
    //结束位置
    private int mEndPosition = -1;
    //结束位置字符
    private String mEndPositionStr;
    //是否包含开始字段
    private boolean mIncludeStartStr = false;
    //是否包含结束字段
    private boolean mIncludeEndStr = false;
    //另一种字体颜色
    private int mOtherColor;
    //另一种字体大小
    private int mOtherTextSize;
    //另一种文字字体，这里主要是加粗/取消加粗显示
    private int mTypeStyle = Typeface.NORMAL;
    //是否设置点击事件
    private boolean isClickable;

    //点击的接口
    private ClickOtherTextListener mClickOtherTextListener;

    //另一种文字的展示方式
    private SpannableString mSpan;
    private ForegroundColorSpan mColorSpan;
    private ClickableSpan mClickableSpan;
    private AbsoluteSizeSpan mSizeSpan;
    private StyleSpan mStyleSpan;

    public TwoStyleTextView(Context context) {
        this(context, null);
    }

    public TwoStyleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TwoStyleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    //获取用户定义的数据
    private void init(AttributeSet attr) {
        TypedArray array = getContext().obtainStyledAttributes(attr, R.styleable.TwoStyleTextView);
        this.mStartPosition = array.getInt(R.styleable.TwoStyleTextView_start_position, -1);
        this.mStartPositionStr = array.getString(R.styleable.TwoStyleTextView_start_position_str);
        this.mEndPosition = array.getInt(R.styleable.TwoStyleTextView_end_position, -1);
        this.mEndPositionStr = array.getString(R.styleable.TwoStyleTextView_end_position_str);
        this.mOtherColor = array.getColor(R.styleable.TwoStyleTextView_other_text_color, getCurrentTextColor());
        this.mOtherTextSize = array.getDimensionPixelSize(R.styleable.TwoStyleTextView_other_text_size, (int) getTextSize());
        this.mTypeStyle = array.getInt(R.styleable.TwoStyleTextView_other_text_style, Typeface.NORMAL);
        this.mIncludeStartStr = array.getBoolean(R.styleable.TwoStyleTextView_include_start_str, false);
        this.mIncludeEndStr = array.getBoolean(R.styleable.TwoStyleTextView_include_end_str, false);
        this.isClickable = array.getBoolean(R.styleable.TwoStyleTextView_is_clickable, false);
        array.recycle();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        initSpan(text == null ? null : text.toString());
        if(mSpan != null) {
            super.setText(mSpan, BufferType.SPANNABLE);
        }else{
            super.setText(text, type);
        }
    }

    //设置Span
    private void initSpan(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (!TextUtils.isEmpty(mStartPositionStr)) {
            //如果包含指定的开始字符
            if (withStrPosition(mStartPositionStr, text) >= 0) {
                if (mIncludeStartStr) {
                    mStartPosition = withStrPosition(mStartPositionStr, text);
                } else {
                    mStartPosition = withStrPosition(mStartPositionStr, text) + 1;
                }
            }
        }
        if (!TextUtils.isEmpty(mEndPositionStr)) {
            //如果包含指定的结束字符串
            if (mIncludeEndStr) {
                mEndPosition = withStrPosition(mEndPositionStr, text) + 1;
            } else {
                mEndPosition = withStrPosition(mEndPositionStr, text);
            }
        }
        //限制最大为字符串的长度
        if (mEndPosition > text.length()) {
            mEndPosition = text.length();
        }
        mSpan = null;
        if (mStartPosition > -1 && mEndPosition > -1 && mEndPosition >= mStartPosition) {
            mSpan = new SpannableString(text);
            //设置颜色
            mColorSpan = new ForegroundColorSpan(mOtherColor);
            mSpan.setSpan(mColorSpan, mStartPosition, mEndPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置字体大小
            mSizeSpan = new AbsoluteSizeSpan(mOtherTextSize, false);
            mSpan.setSpan(mSizeSpan, mStartPosition, mEndPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置字体样式
            mStyleSpan = new StyleSpan(mTypeStyle);
            mSpan.setSpan(mStyleSpan, mStartPosition, mEndPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

    }


    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    //根据字符获取对应的位置
    private int withStrPosition(String str, String text) {
        if (TextUtils.isEmpty(str))
            return -1;
        else {
            int strPosition = text.indexOf(str);
            if (strPosition != -1)
                return strPosition;
            //从第一个字符开始判断
            char[] chars = str.toCharArray();
            for (char c : chars) {
                int position = text.indexOf(c);
                if (position != -1)
                    return position;
            }
        }
        return -1;
    }

    public interface ClickOtherTextListener {
        void clickOtherText(String clickStr);
    }

    //在当前的基础上给文本设置点击事件，无法直接设置到上面setMovementMethod(LinkMovementMethod.getInstance())会导致文本不显示
    public void setClickOther(ClickOtherTextListener listener) {
        if (mSpan == null)
            return;
        mClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                if (listener != null)
                    listener.clickOtherText(getText().toString().substring(mStartPosition, mEndPosition));

            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                //super.updateDrawState(ds);
                ds.setAntiAlias(true);
                ds.setColor(getCurrentTextColor());
                ds.setUnderlineText(false);
            }
        };
        this.setHighlightColor(getResources().getColor(R.color.transparent));
        this.setMovementMethod(LinkMovementMethod.getInstance());
        mSpan.setSpan(mClickableSpan, mStartPosition, mEndPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(mSpan);
    }
}
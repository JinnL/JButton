package com.jinn.jbutton.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.Gravity;


/**
 * Description: 自定义按钮
 * author: tonglj
 * creat date : 2018/12/7.
 * email: tonglj@hundsun.com
 */

public class JButton extends AppCompatButton {


    private int mCornerRadius = 0;// 圆角
    private int mStrokeWidth = 0;// 边界宽度
    private int mSolidColor_n;// 普通填充色
    private int mSolidColor_p;// 按下填充色
    private int mStrokeColor_n;// 普通边框颜色
    private int mStrokeColor_p;// 按下边框颜色
    private int mTextColor_n;// 普通填充色
    private int mTextColor_p;// 按下填充色
    private int enabledColor;//禁用时填充色
    private int mTextColor_e;//禁用时文字颜色
    private int mSolidColor_start;//渐变背景开始颜色
    private int mSolidColor_end;//渐变背景结束颜色

    private int mGradientDirection;//渐变方向
    /**
     * 是否显示涟漪效果
     */
    private boolean rippleEffect;


    /**
     * 默认背景
     */
    private GradientDrawable mDrawableNormal;


    private Context mContext;



    public JButton(Context context) {
        this(context, null);
    }

    public JButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context.getApplicationContext();
        initView(context, attrs);
    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (mDrawableNormal != null) {
            setSolidBackground();
            invalidate();
        }
    }


    /**
     * 设置Enabled颜色
     *
     * @param enabledColor 禁用状态颜色
     */
    public void setEnabledColor(@ColorRes int enabledColor) {
        this.enabledColor = enabledColor;
        invalidate();
    }

    private void initView(Context context, AttributeSet attrs) {
        setGravity(Gravity.CENTER);

        if (null != attrs) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.JButton);

            enabledColor = a.getColor(R.styleable.JButton_jinn_color_e, Color.parseColor("#CCCCCC"));
            rippleEffect = a.getBoolean(R.styleable.JButton_jinn_ripple_effect, false);
            mStrokeColor_n = a.getColor(R.styleable.JButton_jinn_storke_color_n, mSolidColor_n);
            mGradientDirection = a.getInt(R.styleable.JButton_jinn_gradientDirection,0);
            mSolidColor_start = a.getColor(R.styleable.JButton_jinn_startColor, -1);
            mSolidColor_end = a.getColor(R.styleable.JButton_jinn_endColor, mSolidColor_start);
            mStrokeColor_p = a.getColor(R.styleable.JButton_jinn_storke_color_p, mSolidColor_p);
            mCornerRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, a.getDimension(R.styleable.JButton_jinn_corners,
                    0), getResources().getDisplayMetrics());
            mStrokeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, a.getDimension(R.styleable.JButton_jinn_storke,
                    0), getResources().getDisplayMetrics());
            mTextColor_n = a.getColor(R.styleable.JButton_jinn_textcolor_n, getCurrentTextColor());
            mTextColor_p = a.getColor(R.styleable.JButton_jinn_textcolor_p, mTextColor_n);
            mTextColor_e = a.getColor(R.styleable.JButton_jinn_textcolor_e, mTextColor_p);
            mSolidColor_n = a.getColor(R.styleable.JButton_jinn_color_n, Color.parseColor("#000000"));
            if (mSolidColor_start != -1) {
                mSolidColor_n = mSolidColor_start;
            }
            mSolidColor_p = a.getColor(R.styleable.JButton_jinn_color_p, mSolidColor_n);
            a.recycle();
            setTextCompat();
            setSolidBackground();

        }

    }

    /**
     * 设置背景颜色
     */
    private void setSolidBackground() {
        StateListDrawable background = new StateListDrawable();
        if (mSolidColor_start != -1) {
            mDrawableNormal = createDrawable(mSolidColor_start, mSolidColor_end, mCornerRadius, mStrokeColor_n, mStrokeWidth);
        } else {
            mDrawableNormal = createDrawable(mSolidColor_n, mCornerRadius, mStrokeColor_n, mStrokeWidth);
        }
        /*按下背景*/
        GradientDrawable mDrawablePressed = createDrawable(mSolidColor_p, mCornerRadius, mStrokeColor_p, mStrokeWidth);
        /*禁用背景*/
        GradientDrawable mDrawableEnabled = createDrawable(enabledColor, mCornerRadius, android.R.color.transparent, 0);
        if (!isEnabled()) {
            background.addState(StateSet.NOTHING, mDrawableEnabled);
        } else {
            background.addState(new int[]{android.R.attr.state_pressed}, mDrawablePressed);
            background.addState(StateSet.WILD_CARD, mDrawableNormal);
        }
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH || !rippleEffect) {
            setBackgroundCompat(background);
        } else {//绘制波纹效果
            TypedArray array = mContext.obtainStyledAttributes(null, new int[]{android.R.attr.colorControlHighlight});
            int color = array.getColor(0, 0);
            array.recycle();
            RippleDrawable bonderDrawable = new RippleDrawable(ColorStateList.valueOf(color), null, mDrawableNormal);
            LayerDrawable ld = new LayerDrawable(new Drawable[]{background, bonderDrawable});
            setBackgroundCompat(ld);
        }
    }


    /**
     * 设置文字颜色
     */
    private void setTextCompat() {
        int statePressed = android.R.attr.state_pressed;
        int stateFocesed = android.R.attr.state_focused;
        int stateEnabled = android.R.attr.state_enabled;
        int stateWindow = android.R.attr.state_window_focused;
        //        int[][] state = {{statePressed, stateEnabled}, {stateEnabled}, {stateFocesed, stateEnabled}, {stateFocesed},  {}};
        int[][] state = {{statePressed, stateEnabled}, {stateEnabled}, {stateFocesed, stateEnabled}, {stateFocesed}, {stateWindow}, {}};

        int color_n = mTextColor_n;
        int color_p = mTextColor_p;
        int color_e = mTextColor_e;
        int[] color = {color_p, color_n, color_p, color_n, color_e, color_n};
        ColorStateList colorStateList = new ColorStateList(state, color);
        setTextColor(colorStateList);
    }

    /**
     * 含边框
     *
     * @param color        颜色
     * @param cornerRadius 圆角
     * @param strokeColor  边框颜色
     * @param strokeWidth  边框宽度
     * @return 背景
     */
    private GradientDrawable createDrawable(int color, int cornerRadius, int strokeColor, int strokeWidth) {
        final GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setStroke(strokeWidth, strokeColor);
        drawable.setCornerRadius(cornerRadius);
        return drawable;

    }

    /**
     * 含边框
     *
     * @param startColor   颜色
     * @param endColor     颜色
     * @param cornerRadius 圆角
     * @param strokeColor  边框颜色
     * @param strokeWidth  边框宽度
     * @return 背景
     */
    private GradientDrawable createDrawable(int startColor, int endColor, int cornerRadius, int strokeColor, int strokeWidth) {
        final GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN || startColor == endColor) {//API 16以上才有渐变
            drawable.setColor(startColor);
        } else {
            drawable.setColors(new int[]{startColor, endColor});
            GradientDrawable.Orientation[] enums = {GradientDrawable.Orientation.LEFT_RIGHT,GradientDrawable.Orientation.TOP_BOTTOM,GradientDrawable.Orientation.TL_BR,GradientDrawable.Orientation.BL_TR};
            drawable.setOrientation(enums[mGradientDirection]);
        }
        drawable.setStroke(strokeWidth, strokeColor);
        drawable.setCornerRadius(cornerRadius);
        return drawable;

    }


    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    private void setBackgroundCompat(@Nullable Drawable drawable) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(drawable);
        } else {
            setBackground(drawable);
        }
    }


//    public void setIconLeft(@DrawableRes int icon) {
//        setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
//    }


}

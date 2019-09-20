package com.jinn.jbutton.weight;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * desc:
 * author:tonglj
 * Create date: 2019/9/20
 */
public class JIconButton extends JButton{
    public JIconButton(Context context) {
        super(context);
    }
    public JIconButton(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public JIconButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //All these logic should happen before onDraw()
        Drawable[] drawables = getCompoundDrawables();
        Drawable drawableLeft = drawables[0],
                drawableTop = drawables[1],
                drawableRight = drawables[2],
                drawableBottom = drawables[3];

        String text = getText().toString();
        float textWidth = getPaint().measureText(text, 0, text.length());
        double textHeight = getLineHeight() * getLineCount();

        int totalDrawablePaddingH = 0;
        int totalDrawablePaddingV = 0;
        int totalDrawableWidth = 0;
        int totalDrawableHeight = 0;
        float totalWidth;
        float totalHeight;
        int paddingH=0;
        int paddingV=0;

        if(drawableLeft != null||drawableRight != null){
            if (drawableLeft != null) {
                totalDrawableWidth += drawableLeft.getIntrinsicWidth();
                totalDrawablePaddingH += getCompoundDrawablePadding();//drawablePadding
            }
            if (drawableRight != null) {
                totalDrawableWidth += drawableRight.getIntrinsicWidth();
                totalDrawablePaddingH += getCompoundDrawablePadding();
            }
            totalWidth = textWidth + totalDrawableWidth + totalDrawablePaddingH;
            paddingH = (int) (getWidth() - totalWidth) / 2;
        }

        if(drawableTop != null||drawableBottom != null){
            // measure height
            if (drawableTop != null) {
                totalDrawableHeight += drawableTop.getIntrinsicHeight();
                totalDrawablePaddingV += getCompoundDrawablePadding();
            }
            if (drawableBottom != null) {
                totalDrawableHeight += drawableBottom.getIntrinsicHeight();
                totalDrawablePaddingV += getCompoundDrawablePadding();
            }
            totalHeight = (float) (textHeight + totalDrawableHeight + totalDrawablePaddingV);
            paddingV = (int) (getHeight() - totalHeight) / 2;
        }

        // reset padding.
        setPadding(paddingH, paddingV, paddingH, paddingV);
    }
}

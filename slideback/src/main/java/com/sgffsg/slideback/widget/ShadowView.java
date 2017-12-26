package com.sgffsg.slideback.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.FloatRange;
import android.view.View;

/**
 * 阴影效果
 * Created by sgffsg on 17/11/2.
 */

public class ShadowView extends View{

    private LinearGradient mLinearGradient;
    private Paint mPaint;
    private Paint narrowPaint;
    private RectF mRectF;
    private boolean isShowWideShadow=false;
    private boolean isShowNarrowShadow=true;

    private float mAlphaPercent = -1;
    private float templeft;

    public ShadowView(Context context) {
        super(context);
        mPaint = new Paint();
        narrowPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mAlphaPercent >= 0) {
            mRectF = new RectF();
            if (isShowWideShadow){
                mPaint.setAlpha((int) (mAlphaPercent * 255));
                mRectF.set(0, 0, getWidth(), getHeight());
                canvas.drawRect(mRectF, mPaint);
            }
            if (isShowNarrowShadow){
                if (isShowWideShadow){
                    templeft=getWidth()-getWidth()/50f;
                    narrowPaint.setAlpha(80);
                }else {
                    narrowPaint.setAlpha((int) (mAlphaPercent * 255));
                }
                // 绘制渐变阴影
                if (mLinearGradient == null) {
                    int[] colors = {Color.parseColor("#0A000000"), Color.parseColor("#66000000"), Color.parseColor("#aa000000")};
                    // 我设置着色器开始的位置为（0，0），结束位置为（getWidth(), 0）表示我的着色器要给整个View在水平方向上渲染
                    mLinearGradient = new LinearGradient(templeft, 0, getWidth(), 0, colors, null, Shader.TileMode.REPEAT);
                    narrowPaint.setShader(mLinearGradient);
                }
                mRectF.set(templeft, 0, getWidth(), getHeight());
                canvas.drawRect(mRectF, narrowPaint);
            }
        }
    }

    public void redraw(@FloatRange(from = 0.0,
            to = 1.0) float alphaPercent) {
        mAlphaPercent = alphaPercent;
        invalidate();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mLinearGradient = null;
    }

    /**
     * 设置阴影样式
     * @param showNarrow 窄阴影
     * @param showWide 宽阴影
     */
    public void showShadow(boolean showNarrow,boolean showWide){
        this.isShowNarrowShadow=showNarrow;
        this.isShowWideShadow=showWide;
    }
}

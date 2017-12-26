package com.sgffsg.slideback;

import android.support.annotation.FloatRange;

/**
 * Created by Oubowu on 2016/9/22 23:37.
 * change by sgffsg on 2017/12/3
 */
public class SlideConfig {

    public static final int SHADOW_STYLE_NOMAL=0;//一般阴影
    public static final int SHADOW_STYLE_WECHAT=1;//微信阴影
    public static final int SHADOW_STYLE_SNOW_BALL=2;//雪球阴影
    public static final int SHADOW_STYLE_BYTE_JUMP=3;//头条阴影

    @FloatRange(from = 0.0,
            to = 1.0)
    private float mEdgePercent;

    @FloatRange(from = 0.0,
            to = 1.0)
    private float mSlideOutPercent;

    //阴影样式
    private int shadowStyle;

    private SlideConfig() {
    }

    public float getmEdgePercent() {
        return mEdgePercent;
    }

    public float getmSlideOutPercent() {
        return mSlideOutPercent;
    }

    public int getShadowStyle() {
        return shadowStyle;
    }

    public SlideConfig(Builder builder) {
        mEdgePercent = builder.edgePercent;
        mSlideOutPercent = builder.slideOutPercent;
        shadowStyle=builder.shadowStyle;
    }

    public static class Builder {

        @FloatRange(from = 0.0,
                to = 1.0)
        private float edgePercent = 0.4f;
        @FloatRange(from = 0.0,
                to = 1.0)
        private float slideOutPercent = 0.1f;

        private int shadowStyle;
        public Builder() {
        }

        public SlideConfig create() {
            return new SlideConfig(this);
        }


        public Builder edgePercent(@FloatRange(from = 0.0,
                to = 1.0) float edgePercent) {
            this.edgePercent = edgePercent;
            return this;
        }

        public Builder slideOutPercent(@FloatRange(from = 0.0,
                to = 1.0) float slideOutPercent) {
            this.slideOutPercent = slideOutPercent;
            return this;
        }

        public Builder shadowStyle(int shadowStyle) {
            this.shadowStyle = shadowStyle;
            return this;
        }
    }

}

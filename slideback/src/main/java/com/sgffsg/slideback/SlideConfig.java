package com.sgffsg.slideback;

import android.support.annotation.FloatRange;

/**
 * Created by Oubowu on 2016/9/22 23:37.
 * change by sgffsg on 2017/12/3
 */
public class SlideConfig {

    @FloatRange(from = 0.0,
            to = 1.0)
    private float mEdgePercent;

    @FloatRange(from = 0.0,
            to = 1.0)
    private float mSlideOutPercent;
    //是否显示成微信样式的滑动返回
    private boolean mIsWeChatStyle;

    private SlideConfig() {
    }

    public float getmEdgePercent() {
        return mEdgePercent;
    }

    public float getmSlideOutPercent() {
        return mSlideOutPercent;
    }

    public boolean ismIsWeChatStyle() {
        return mIsWeChatStyle;
    }

    public SlideConfig(Builder builder) {
        mEdgePercent = builder.edgePercent;
        mSlideOutPercent = builder.slideOutPercent;
        mIsWeChatStyle=builder.isWeChatStyle;
    }

    public static class Builder {

        @FloatRange(from = 0.0,
                to = 1.0)
        private float edgePercent = 0.4f;
        @FloatRange(from = 0.0,
                to = 1.0)
        private float slideOutPercent = 0.1f;

        private boolean isWeChatStyle;
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

        public Builder isWeChatStyle(boolean isWeChatStyle) {
            this.isWeChatStyle = isWeChatStyle;
            return this;
        }

    }

}

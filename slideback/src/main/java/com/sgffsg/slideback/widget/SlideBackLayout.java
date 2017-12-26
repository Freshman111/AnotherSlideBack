package com.sgffsg.slideback.widget;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sgffsg.slideback.SlideConfig;
import com.sgffsg.slideback.listener.OnSlideCallBack;

/**
 * SlideBack库核心类，大部分功能在此实现
 * Created by Oubowu on 2016/9/22 0022 15:24.
 * Changed by sgffsg on 17/11/2.
 */

public class SlideBackLayout extends FrameLayout{

    private static final String TAG="SlideBackLayout";
    private static final int MIN_FLING_VELOCITY = 400;
    private View mContentView;
    private View mPreContentView;
    private PreviousPageView mCacheView;

    private ViewDragHelper mDragHelper;
    private int mScreenWidth;
    private float mDownX;
    private float mDownY;

    private float mSlideOutRange;
    @FloatRange(from = 0.0, to = 1.0)
    private float mEdgeRangePercent = 0.1f;//滑动触发区域的比例
    @FloatRange(from = 0.0, to = 1.0)
    private float mSlideOutRangePercent = 0.48f;//松手时决定返回还是留在当前页面
    private boolean mIsWeChatStyle;

    //滑动速度的阀值
    private float slideOutVelocity = 2000f;
    private boolean mCloseFlagForWindowFocus;
    private boolean mCloseFlagForDetached;
    private boolean mEnableTouchEvent;
    //前一个页面是否可移动
    private boolean canPrePageMove=false;
    private boolean mIsFirstAttachToWindow;
    private ShadowView shadowView;
    private OnSlideCallBack callBack;
    private boolean mFirstLayout = true;
    private int mCurrentLeft;
    private float mCurrentAlpha=0.5f;
    private float mMinSlidDistantX;
    //阴影样式
    private int shadowStyle;

    /**
     * How far the panel is offset from its closed position.
     * range [0, 1] where 0 = closed, 1 = open.
     */
    float mSlideOffset;
    private float mEdgeRange;

    public SlideBackLayout(@NonNull Context context, View contentView, View preContentView, SlideConfig config, OnSlideCallBack callBack) {
        super(context);
        this.mContentView=contentView;
        this.mPreContentView=preContentView;
        this.callBack=callBack;
        setWillNotDraw(false);
        initConfig(config);
    }

    /**
     * 做一些初始化配置操作
     */
    private void initConfig(SlideConfig config){
        if (config != null) {
            mSlideOutRangePercent=config.getmSlideOutPercent();
            mEdgeRangePercent=config.getmEdgePercent();
            shadowStyle=config.getShadowStyle();
            if (shadowStyle==SlideConfig.SHADOW_STYLE_WECHAT){
                mIsWeChatStyle=true;
            }
        }

        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        final float density = getResources().getDisplayMetrics().density;
        final float minVel = MIN_FLING_VELOCITY * density;

        SlideCallBack slideLeftCallback=new SlideCallBack();
        mDragHelper = ViewDragHelper.create(this, 0.5f, slideLeftCallback);
        // 最小拖动速度
        mDragHelper.setMinVelocity(minVel);
//        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        mMinSlidDistantX = mScreenWidth / 10.0f;
        mCacheView=new PreviousPageView(getContext());
        mCacheView.setVisibility(INVISIBLE);
        addView(mCacheView);

        shadowView = new ShadowView(getContext());
        shadowView.setVisibility(INVISIBLE);
        if (shadowStyle==SlideConfig.SHADOW_STYLE_SNOW_BALL){
            shadowView.showShadow(false,true);
            addView(shadowView,mScreenWidth, LayoutParams.MATCH_PARENT);
        }else if (shadowStyle==SlideConfig.SHADOW_STYLE_BYTE_JUMP){
            shadowView.showShadow(true,true);
            addView(shadowView,mScreenWidth , LayoutParams.MATCH_PARENT);
        }else {
            shadowView.showShadow(true,false);
            addView(shadowView,mScreenWidth / 28, LayoutParams.MATCH_PARENT);
        }
        addView(mContentView);

        mEdgeRange = mScreenWidth * mEdgeRangePercent;

        mSlideOutRange = mScreenWidth * mSlideOutRangePercent;
//        mContentView.findViewById(android.R.id.content).setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // 屏蔽上个内容页的点击事件
//                }
//            });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 优化侧滑的逻辑，不要一有稍微的滑动就被ViewDragHelper拦截掉了
                final float x = event.getX();
                final float y = event.getY();
                final float adx = Math.abs(x - mDownX);
                final float ady = Math.abs(y - mDownY);
                if (adx<mMinSlidDistantX){
                    return false;
                }
                final int slop = mDragHelper.getTouchSlop();
                if (adx > slop && ady > adx) {
                    mDragHelper.cancel();
                    return false;
                }
                break;
        }

        boolean res=mDragHelper.shouldInterceptTouchEvent(event);
        return res;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!mEnableTouchEvent) {
//            Log.d(TAG,"onTouchEvent-----first"+super.onTouchEvent(event));
            return super.onTouchEvent(event);
        }
        if (mCloseFlagForDetached || mCloseFlagForWindowFocus) {
//            Log.d(TAG,"onTouchEvent-----直接返回");
            // 针对快速滑动的时候，页面关闭的时候移除上个页面的时候，布局重新调整，这时候我们把contentView设为invisible，
            // 但是还是可以响应DragHelper的处理，所以这里根据页面关闭的标志位不给处理事件了
            // Log.e("TAG", mTestName + "都要死了，还处理什么触摸事件！！");
            return super.onTouchEvent(event);
        }
        mDragHelper.processTouchEvent(event);
//        Log.d(TAG,"onTouchEvent-----second"+true);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        layoutChildren(left, top, right, bottom, false /* no force left gravity */);
    }

    void layoutChildren(int left, int top, int right, int bottom, boolean forceLeftGravity) {
        final boolean isLayoutRtl = isLayoutRtlSupport();
        if (isLayoutRtl) {
            mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);
        } else {
            mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        }
        final int count = getChildCount();

        final int parentLeft = getPaddingLeft();
        final int parentTop = getPaddingTop();
        int xStart = parentLeft;
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();

                final int childWidth = child.getMeasuredWidth();
                final int childHeight = child.getMeasuredHeight();

                int childLeft;
                int childTop;

                if (child==mContentView){
                    childLeft = xStart+mCurrentLeft;
                    childTop = parentTop + lp.topMargin;
                }else if (child==shadowView){
                    childLeft = xStart+mCurrentLeft - childWidth;
                    childTop = parentTop + lp.topMargin;
                }else {
                    if (mIsWeChatStyle){
                        float percent = (xStart+mCurrentLeft) * 1.0f / mScreenWidth;
                        childLeft= (int) (-mScreenWidth/4+percent/2*mScreenWidth/2);
                    }else {
                        childLeft = xStart;
                    }
                    childTop = parentTop + lp.topMargin;
                }
                child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);

            }
        }
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     * 滑动回调
     */
    class SlideCallBack extends ViewDragHelper.Callback{

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child==mContentView;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return Math.max(Math.min(mScreenWidth, left), 0);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mScreenWidth;
        }

        //松手时的回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (releasedChild==mContentView){
                if (xvel > slideOutVelocity) {
                    mDragHelper.settleCapturedViewAt(mScreenWidth, 0);
                    invalidate();
                    return;
                }
                if (mContentView.getLeft() < mSlideOutRange) {
                    mDragHelper.settleCapturedViewAt(0, 0);
                } else {
                    mDragHelper.settleCapturedViewAt(mScreenWidth, 0);
                }
                invalidate();
            }

        }

        @Override
        public void onViewDragStateChanged(int state) {
            switch (state){
                case ViewDragHelper.STATE_IDLE:
                    if (mContentView.getLeft()==0){
                        callBack.onStart();
                    } else if (mContentView.getLeft()==mScreenWidth) {
                        if (mCacheView.getVisibility()==INVISIBLE){
//                            mCacheView.setBackground(mPreDecorViewDrawable);
                            mCacheView.cacheView(mPreContentView);
                            mCacheView.setVisibility(VISIBLE);
                            // Log.e("TAG", mTestName + ": 这里再绘制一次是因为在屏幕旋转的模式下，remove了preContentView后布局会重新调整");
                            mCloseFlagForWindowFocus = true;
                            mCloseFlagForDetached = true;
                            // Log.e("TAG", mTestName + ": 滑动到尽头了这个界面要死了，把preContentView给回上个Activity");
                            // 这里setTag是因为下面的回调会把它移除出当前页面，这时候会触发它的onDetachedFromWindow事件，
                            // 而它的onDetachedFromWindow实际上是来处理屏幕旋转的，所以设置个tag给它，让它知道是当前界面移除它的，并不是屏幕旋转导致的
                            mPreContentView.setTag("notScreenOrientationChange");
                            callBack.onClose(true);
                            mPreContentView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                                    mCacheView.setBackground(mPreDecorViewDrawable);
                                    mCacheView.cacheView(mPreContentView);
                                }
                            }, 10);
                        }else {
                            mCloseFlagForWindowFocus = true;
                            mCloseFlagForDetached = true;
                            callBack.onClose(true);
                        }
                    }
                    break;
                default:
                    break;
            }

        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            addPreContentView();
            if (shadowView.getVisibility()!=VISIBLE){
                shadowView.setVisibility(VISIBLE);
            }
            mCurrentLeft+=dx;
            final float percent = left * 1.0f / mScreenWidth;
            final int newStart = left;
            final int paddingStart =  getPaddingLeft();
            final LayoutParams lp = (LayoutParams) changedView.getLayoutParams();
            final int lpMargin = lp.leftMargin;
            final int startBound = paddingStart + lpMargin;

//            mPreContentView.setX(-mScreenWidth / 2 + percent * (mScreenWidth / 2));
            if (mIsWeChatStyle){
                mPreContentView.layout((int)(-mScreenWidth / 4 + percent /2 * (mScreenWidth / 2)),top,(int)(3*mScreenWidth / 4 + percent * (mScreenWidth / 2)),top+mPreContentView.getHeight());
            }
            mSlideOffset = (float) (newStart - startBound) / mScreenWidth;
//            shadowView.setX(left - shadowView.getWidth());
            shadowView.redraw(1-percent);
            shadowView.layout(left - shadowView.getWidth(),top,left,top+shadowView.getHeight());
        }
    }

    private void addPreContentView() {
        if (mPreContentView.getParent() != SlideBackLayout.this) {
            // Log.e("TAG", mTestName + ": 我要把上个页面的内容页加到我这里啦！");
            mPreContentView.setTag("notScreenOrientationChange");
            ((ViewGroup) mPreContentView.getParent()).removeView(mPreContentView);
            SlideBackLayout.this.addView(mPreContentView, 0);
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus){
            mEnableTouchEvent=true;
            // 当前页面
            if (!mIsFirstAttachToWindow) {
                mIsFirstAttachToWindow = true;
                // Log.e("TAG", mTestName + ": 第一次窗口取得焦点");
            }
        }else {
            // 1.跳转到另外一个Activity，例如也是需要滑动的，这时候就需要取当前Activity的contentView，所以这里把preContentView给回上个Activity
            if (mCloseFlagForWindowFocus) {
                mCloseFlagForWindowFocus = false;
                // Log.e("TAG", mTestName + ": onWindowFocusChanged前已经调了关闭");
            } else {
                // Log.e("TAG", mTestName + ": 跳转到另外一个Activity，取这个Activity的contentView前把preContentView给回上个Activity");
                callBack.onClose(false);
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mFirstLayout=true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mEnableTouchEvent = false;
        if (mCloseFlagForDetached) {
            mCloseFlagForDetached = false;
            // Log.e("TAG", mTestName + ": onDetachedFromWindow(): " + "已经调了关闭");
        } else {
            if (getTag() != null && getTag().equals("notScreenOrientationChange")) {
                // 说明是手动删的不关旋转屏幕的事，所以不处理
                // Log.e("TAG", mTestName + ":说明是手动删的不关旋转屏幕的事，所以不处理");
                setTag(null);
            } else {
                // Log.e("TAG", mTestName + ":屏幕旋转了，重建界面: 把preContentView给回上个Activity");
                callBack.onClose(false);
            }
        }
        mFirstLayout = true;
    }

    boolean isLayoutRtlSupport() {
        return ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
    }

}

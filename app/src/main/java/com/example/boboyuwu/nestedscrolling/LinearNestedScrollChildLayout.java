package com.example.boboyuwu.nestedscrolling;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by wubo on 2017/6/6.
 */

public class LinearNestedScrollChildLayout extends LinearLayout implements NestedScrollingChild{

    private NestedScrollingChildHelper mNestedScrollingChildHelper;

    private int [] offset=new int[2];
    private int [] consume=new int[2];
    private float mStartY;

    public LinearNestedScrollChildLayout(Context context) {
        this(context,null);
    }

    public LinearNestedScrollChildLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mNestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mNestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mNestedScrollingChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mNestedScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mNestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed,dyConsumed,dxUnconsumed,dyUnconsumed,offset);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return mNestedScrollingChildHelper.dispatchNestedPreScroll(dx,dy,consume,offset);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mNestedScrollingChildHelper.dispatchNestedFling(velocityX,velocityY,consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mNestedScrollingChildHelper.dispatchNestedPreFling(velocityX,velocityY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int size = MeasureSpec.getSize(heightMeasureSpec);
        int h = MeasureSpec.makeMeasureSpec(size, MeasureSpec.UNSPECIFIED);
        super.onMeasure(widthMeasureSpec, h);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();

                int diffY= (int) (mStartY-y);
                if(startNestedScroll(View.SCROLL_AXIS_VERTICAL)&&dispatchNestedPreScroll(0,diffY,consume,offset)){

                    Log.e("wwwconsume",consume[1]+"");
                }else{
                    scrollBy(0,diffY);
                }
                Log.e("wwww",diffY+"     startY:"+mStartY+"   y:"+y+"  top:"+getTop());

                mStartY=y;


                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    @Override
    public void scrollTo(@Px int x, @Px int y) {
        Log.e("wwww","y=="+y+"  getMeasureH"+getMeasuredHeight());
        if(y<0){
            y=0;
        }
        super.scrollTo(x, y);
    }
}

package com.example.boboyuwu.nestedscrolling;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by wubo on 2017/6/6.
 */

public class LinearNestedScrollParentLayout extends LinearLayout implements NestedScrollingParent{


    private NestedScrollingParentHelper mNestedScrollingParentHelper;
    private View mChildAt0;

    public LinearNestedScrollParentLayout(Context context) {
        this(context,null);
    }

    public LinearNestedScrollParentLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private int mMeasuredHeight;

    private void init() {

        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        post(new Runnable() {


            @Override
            public void run() {
                int childCount = getChildCount();
                View childAt0 = getChildAt(0);
                mMeasuredHeight = childAt0.getMeasuredHeight();
            }
        });

       /* mChildAt0 = getChildAt(0);
        Log.e("www","child0Height:"+mChildAt0.getMeasuredHeight());*/

    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.e("wwww","onStartNestedScroll");
        return child instanceof NestedScrollingChild && nestedScrollAxes==View.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child,target,nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        mNestedScrollingParentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

        //这里消费一部分
        if(hasHide(dy) || hasShow(dy)){
            scrollBy(0,dy);
            consumed[1]=dy;
        }
    }

    private boolean hasShow(int dy) {
        if(dy<0){
            return true;
        }
        return false;
    }

    public boolean hasHide(int dy){
        if(mMeasuredHeight==0)
            return false;
        return dy>0;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return 0;
    }

    @Override
    public void scrollTo(@Px int x, @Px int y) {

        if(y<0){
            y=0;
        }
        if(y>mMeasuredHeight && mMeasuredHeight!=0){
            y=mMeasuredHeight;
        }
        super.scrollTo(x, y);
    }
}

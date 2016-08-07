package com.ogettoweb.dealers.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private Context context;

    public DividerItemDecoration(Context context, AttributeSet attrs) {
        final TypedArray a = context
                .obtainStyledAttributes(attrs, new int[]{android.R.attr.listDivider});
        mDivider = a.getDrawable(0);
        a.recycle();
        this.context = context;
    }

    public DividerItemDecoration(Context context, AttributeSet attrs, boolean showFirstDivider, boolean showLastDivider) {
        this(context, attrs);
    }

    public DividerItemDecoration(Context context, int resId) {
        mDivider = ContextCompat.getDrawable(context, resId);
    }

    public DividerItemDecoration(Context context, int resId, boolean showFirstDivider, boolean showLastDivider) {
        this(context, resId);
    }

    public DividerItemDecoration(Drawable divider) {
        mDivider = divider;
    }

    public DividerItemDecoration(Drawable divider, boolean showFirstDivider, boolean showLastDivider) {
        this(divider);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mDivider == null) {
            super.onDrawOver(c, parent, state);
            return;
        }

        // Initialization needed to avoid compiler warning
        int left, right, top, bottom, size;
        int childCount = parent.getChildCount();

        size = mDivider.getIntrinsicHeight();
        left = parent.getPaddingLeft();
        right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 1; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            top = child.getTop() - params.topMargin - size;
            bottom = top + size;
            mDivider.setBounds(left, top - 1, right, bottom);
            mDivider.setColorFilter(ContextCompat.getColor(context, android.R.color.darker_gray), PorterDuff.Mode.DST_ATOP);
            mDivider.draw(c);
        }

    }

}
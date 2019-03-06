package com.joinz.taskmanager.features.productivity;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joinz.taskmanager.R;

import java.util.Arrays;
import java.util.List;

public class DaysOfWeekView extends ViewGroup {
    private int daysTextSize;
    private List<String> days = Arrays.asList("su", "mo", "tu", "we", "th", "fr", "tu");
    private int width = 0;

    public DaysOfWeekView(Context context) {
        super(context);
        init(context);
    }

    public DaysOfWeekView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DaysOfWeekView, 0, 0);
        try {
            int resourceId = a.getResourceId(R.styleable.DaysOfWeekView_days, 0);
            if (resourceId != 0) {
                this.days = Arrays.asList(a.getResources().getStringArray(resourceId));
            }
            this.daysTextSize = a.getDimensionPixelSize(R.styleable.DaysOfWeekView_daysTextSize, 12);
        } finally {
            a.recycle();
        }
        init(context);

    }

    private void init(Context context) {
        for (String day : days) {
            TextView tv = new TextView(context);
            tv.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            tv.setText(day);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, daysTextSize);
            tv.setGravity(TEXT_ALIGNMENT_VIEW_END);
            addView(tv);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);

        int height = 0;
        if (getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                View childView = getChildAt(i);
                childView.measure(
                        MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.AT_MOST),
                        MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.AT_MOST)
                );
                height = Math.max(height, childView.getMeasuredHeight());
            }
        }
        width = measuredWidth;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int space = width / getChildCount();

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.layout(i * space + getPaddingLeft(), getPaddingTop(), i * space + space - getPaddingRight(), b - getPaddingBottom());
        }
    }
}

package com.joinz.taskmanager.features.productivity;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class DaysOfWeekView extends ViewGroup {
    private final List<String> days = Arrays.asList("mo", "tu", "we", "th", "fr", "tu", "su");
    private int width = 0;

    public DaysOfWeekView(Context context) {
        super(context);
        init(context);
    }

    public DaysOfWeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        for (String day : days) {
            TextView tv = new TextView(context);
            tv.setText(day);
            tv.setTextColor(Color.BLACK);
            tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
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

        setMeasuredDimension(measuredWidth, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int space = width / getChildCount();

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.layout(i * space, t, i * space + space, b);
        }
    }
}

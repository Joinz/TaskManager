package com.joinz.taskmanager.features.productivity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.joinz.taskmanager.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GraphView extends View {
    private List<Integer> tasksDonePerDay;
    private final int graphColor;
    private final int pointBorderColor;
    private final int pointBackgroundColor;
    private Paint paintGraph;
    private Paint paintPoint;
    private Path graphPath;
    private int measuredWidth = 0;
    private int measuredHeight = 0;
    private List<Integer> x;
    private List<Integer> y;

    public GraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.tasksDonePerDay = Arrays.asList(0, 0, 0, 0, 0, 0, 0);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GraphView, 0, 0);
        try {
            graphColor = a.getColor(R.styleable.GraphView_graphColor, Color.BLUE);
            pointBorderColor = a.getColor(R.styleable.GraphView_pointBorderColor, Color.BLACK);
            pointBackgroundColor = a.getColor(R.styleable.GraphView_pointBackgroundColor, Color.CYAN);
        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {
        paintGraph = new Paint();
        paintGraph.setColor(graphColor);
        paintPoint = new Paint();
        paintPoint.setStrokeWidth(3);
        graphPath = new Path();
        x = new ArrayList<>();
        y = new ArrayList<>();
    }

    public void initList(List<Integer> tasksDone) {
            tasksDonePerDay = tasksDone;
            invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Integer max = Collections.max(tasksDonePerDay);

        int widthDivision = (measuredWidth - getPaddingLeft() - getPaddingRight()) / (tasksDonePerDay.size() - 1);
        int heightDivision = measuredHeight - getPaddingTop() - getPaddingBottom();
        if (max > 0) {
            heightDivision = heightDivision / max;
        }

        graphPath.reset();

        //Drawing and fill background shape
        //Drawing vertical lines
        graphPath.moveTo(getPaddingLeft(), measuredHeight - getPaddingBottom());
        for(int i = 0; i < tasksDonePerDay.size(); i++) {
            Integer tasksDoneToday = tasksDonePerDay.get(i);
            x.add(i, (i) * widthDivision + getPaddingLeft());
            y.add(i, measuredHeight - (tasksDoneToday * heightDivision) - getPaddingBottom());
            graphPath.lineTo(x.get(i), y.get(i));

            //Drawing vertical lines
            paintPoint.setColor(pointBackgroundColor);
            canvas.drawLine(x.get(i), measuredHeight - getPaddingBottom(), x.get(i), y.get(i), paintPoint);
        }
        graphPath.lineTo(measuredWidth - getPaddingRight(), measuredHeight - getPaddingBottom());
        graphPath.lineTo(getPaddingLeft(), measuredHeight - getPaddingBottom());
        canvas.drawPath(graphPath, paintGraph);

        //with bold top path line
        for(int i = 0; i < tasksDonePerDay.size(); i++ ) {
            if(i != 0) {
                canvas.drawLine(x.get(i-1), y.get(i-1), x.get(i), y.get(i), paintPoint);
            } else {
                canvas.drawLine(getPaddingLeft(), measuredHeight, x.get(i), y.get(i), paintPoint);
            }
        }


        //drawing circles at top points
        for (int i = 0; i < tasksDonePerDay.size(); i++) {
            paintPoint.setStyle(Paint.Style.FILL);
            paintPoint.setColor(pointBackgroundColor);
            canvas.drawCircle(x.get(i), y.get(i), 10, paintPoint);
            paintPoint.setStyle(Paint.Style.STROKE);
            paintPoint.setColor(pointBorderColor);
            canvas.drawCircle(x.get(i), y.get(i), 10, paintPoint);
        }
    }
}

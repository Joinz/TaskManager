package com.joinz.taskmanager.features.productivity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joinz.taskmanager.R;
import com.joinz.taskmanager.db.PersistantStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ProductivityFragment extends Fragment {
    public static final String TASKS_DONE = "tasksDone";
    private TextView tvTasksDone;

    public ProductivityFragment() {
        // Required empty public constructor
    }

    public static ProductivityFragment newInstance() {
        return new ProductivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_productivity, container, false);
//        GraphView graphView = new GraphView(inflater.getContext());
//        graphView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        DaysOfWeekView daysOfWeekView = new DaysOfWeekView(inflater.getContext());
//        daysOfWeekView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        root.addView(graphView);
//        root.addView(daysOfWeekView);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTasksDone = view.findViewById(R.id.tvTasksDone);
        setTasksDone();
    }

    public void setTasksDone() {
        if (getActivity() != null) {
            PersistantStorage.init(getActivity());
            int value = PersistantStorage.getProperty(TASKS_DONE);
            String taskDoneText = String.valueOf(value) + " " + getString(R.string.tv_tasks_done);
            tvTasksDone.setText(taskDoneText);
        }
    }

    public static class GraphView extends View {
        private final List<Integer> tasksDonePerDay = Arrays.asList(2, 6, 2, 11, 7, 3, 0);
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
            paintPoint.setStyle(Paint.Style.FILL_AND_STROKE);
            paintPoint.setStrokeWidth(5);
            paintPoint.setColor(pointBorderColor);
            graphPath = new Path();
            x = new ArrayList<>();
            y = new ArrayList<>();
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

            int widthDivision = measuredWidth / tasksDonePerDay.size();
            int heightDivision = measuredHeight / max;

            graphPath.reset();

            graphPath.moveTo(0, measuredHeight);
            for(int i = 0; i < tasksDonePerDay.size(); i++) {
                Integer tasksDoneToday = tasksDonePerDay.get(i);
                x.add(i, (i + 1) * widthDivision);
                y.add(i, measuredHeight - (tasksDoneToday * heightDivision));
                graphPath.lineTo(x.get(i), y.get(i));
            }
            graphPath.lineTo(measuredWidth, measuredHeight);
            graphPath.lineTo(0, measuredHeight);

            canvas.drawPath(graphPath, paintGraph);
            for (int i = 0; i < tasksDonePerDay.size(); i++) {
                canvas.drawCircle(x.get(i), y.get(i), 10, paintPoint);
            }
        }
    }
}
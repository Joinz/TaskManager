package com.joinz.taskmanager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class NewTaskFragment extends Fragment {

    public static final String TAG = "NewTaskFragment";
    private EditText etTaskName;
    private ImageButton ibNewItem;
    private TextView tvPriority;
    private TextView tvDot;
    private int priority = 0;

    public static NewTaskFragment newInstance() {
        return new NewTaskFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        addSpannable();
        setImageButtonListener();
        tvPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PriorityDialogFragment priorityDialogFragment = PriorityDialogFragment.newInstance();
                priorityDialogFragment.show(getChildFragmentManager(), PriorityDialogFragment.TAG);
            }
        });

    }

    private void initViews(View view) {
        etTaskName = view.findViewById(R.id.etTaskName);
        ibNewItem = view.findViewById(R.id.ibNewItem);
        tvPriority = view.findViewById(R.id.tvPriority);
        tvDot = view.findViewById(R.id.tvDot);
        ibNewItem.setEnabled(false);
    }

    private void addSpannable() {
        Spannable spannableText = new SpannableString(tvDot.getText().toString());
        spannableText.setSpan(new ForegroundColorSpan(Task.getColor(priority)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvDot.setText(spannableText);
    }

    private void setImageButtonListener() {
        ibNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = etTaskName.getText().toString();
                Task task = new Task(taskName, priority);

                AppDatabase db = App.getInstance().getDatabase();
                db.taskDao().insert(task);
                getActivity().finish();
                Toast.makeText(getContext(), "Задача " + task.name + " добавлена", Toast.LENGTH_SHORT).show();
            }
        });

        etTaskName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                ibNewItem.setEnabled(!TextUtils.isEmpty(editable));
            }
        });
    }

    public void onPriorityChosen(int priority) {
        this.priority = priority;
        addSpannable();
        Toast.makeText(getContext(), "Выбран приоритет: " + priority, Toast.LENGTH_SHORT).show();
    }
}

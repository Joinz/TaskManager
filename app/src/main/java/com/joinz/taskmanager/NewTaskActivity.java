package com.joinz.taskmanager;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class NewTaskActivity extends AppCompatActivity {

    public static final String NEW_TASK_KEY = "NEW_TASK_KEY";
    private EditText etTaskName;
    private ImageButton ibNewItem;
    private TextView tvDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        initViews();
        addSpannable();
        setImageButtonListener();
    }

    private void initViews() {
        etTaskName = findViewById(R.id.etTaskName);
        ibNewItem = findViewById(R.id.ibNewItem);
        tvDot = findViewById(R.id.tvDot);
    }

    private void addSpannable() {
        Spannable spannableText = new SpannableString("•");
        spannableText.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvDot.setText(spannableText);
    }

    private void setImageButtonListener() {
        ibNewItem.setEnabled(false);

        ibNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                String taskName = etTaskName.getText().toString();
                Task value = new Task(taskName, 0);
                data.putExtra(NEW_TASK_KEY, value);
                NewTaskActivity.this.setResult(AppCompatActivity.RESULT_OK, data);
                NewTaskActivity.this.finish();
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
}

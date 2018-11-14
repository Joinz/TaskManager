package com.joinz.taskmanager;

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
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText etTaskName;
    private ImageButton ibNewItem;
    private TextView tvDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTaskName = findViewById(R.id.etTaskName);
        ibNewItem = findViewById(R.id.ibNewItem);
        tvDot = findViewById(R.id.tvDot);


        Spannable spannableText = new SpannableString("•");
        spannableText.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvDot.setText(spannableText);

        ibNewItem.setEnabled(false);
        ibNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "User clicker", Toast.LENGTH_SHORT);
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

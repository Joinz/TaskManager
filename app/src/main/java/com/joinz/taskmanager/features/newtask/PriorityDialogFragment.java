package com.joinz.taskmanager.features.newtask;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.joinz.taskmanager.R;

public class PriorityDialogFragment extends DialogFragment {

    public static final String TAG = "PriorityDialogFragment";
    @Nullable
    private PriorityDialogListener priorityDialogListener;

    public static PriorityDialogFragment newInstance() {
        return new PriorityDialogFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            if (context instanceof PriorityDialogListener) {
                priorityDialogListener = ((PriorityDialogListener) context);
            } else {
                throw new UnsupportedOperationException("Activity must be instanceof PriorityDialogListener");
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_priority, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button btn1 = view.findViewById(R.id.btnPriority1);
        Button btn2 = view.findViewById(R.id.btnPriority2);
        Button btn3 = view.findViewById(R.id.btnPriority3);
        Button btn4 = view.findViewById(R.id.btnPriority4);
        Button btnClose = view.findViewById(R.id.btnClose);

        btnListener(btn1, 1);
        btnListener(btn2, 2);
        btnListener(btn3, 3);
        btnListener(btn4, 4);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().cancel();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        priorityDialogListener = null;
    }

    public void btnListener(Button button, final int priority) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (priorityDialogListener != null) {
                    priorityDialogListener.onPriorityChosen(priority);
                }
                getDialog().cancel();
            }
        });
    }
}

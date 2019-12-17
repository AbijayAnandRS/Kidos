package com.example.kids.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.example.kids.R;

import androidx.annotation.NonNull;

public class ProgressDialog extends Dialog {
    public ProgressDialog(@NonNull Context context, String message) {
        super(context);
        setContentView(R.layout.dialog_progress_bar);
        setCanceledOnTouchOutside(false);
        ((TextView) findViewById(R.id.tv_progress_bar)).setText(message);
    }
}

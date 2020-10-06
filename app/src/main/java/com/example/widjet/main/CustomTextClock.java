package com.example.widjet.main;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class CustomTextClock extends TextView {

    public CustomTextClock(Context context) {
        super(context);
    }

    public CustomTextClock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextClock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomTextClock(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}

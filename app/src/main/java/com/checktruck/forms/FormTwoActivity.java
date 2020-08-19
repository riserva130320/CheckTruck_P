package com.checktruck.forms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.checktruck.R;

public class FormTwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_two);

        findViewById(R.id.button5).setOnClickListener(v -> {
            finish();
        });
    }
}
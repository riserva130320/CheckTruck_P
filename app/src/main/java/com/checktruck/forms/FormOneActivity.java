package com.checktruck.forms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.checktruck.R;

public class FormOneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_one);

        findViewById(R.id.button2).setOnClickListener(v -> {
            startActivity(new Intent(this, FormTwoActivity.class));
        });
    }
}
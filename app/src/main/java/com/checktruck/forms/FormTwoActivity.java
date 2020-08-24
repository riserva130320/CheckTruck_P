package com.checktruck.forms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import com.checktruck.R;

public class FormTwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_two);

        findViewById(R.id.form_two_back_btn).setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.form_two_next_btn).setOnClickListener(v -> {
            startActivity(new Intent(this, FormThreeActivity.class));
        });

        SeekBar dcd = (SeekBar)findViewById(R.id.seekBar2);
        dcd.setProgress(100);
    }
}
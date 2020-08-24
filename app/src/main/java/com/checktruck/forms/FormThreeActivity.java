package com.checktruck.forms;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.checktruck.R;

public class FormThreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_form_three);

        findViewById(R.id.form_three_back_btn).setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.form_three_next_btn).setOnClickListener(v -> {
            startActivity(new Intent(this, FormTractorActivity.class));
        });
    }
}

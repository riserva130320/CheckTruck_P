package com.checktruck.forms;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.checktruck.R;

public class FormTractorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_form_tractor);

        findViewById(R.id.form_tractot_back_btn).setOnClickListener(v -> {
            finish();
        });

    }
}

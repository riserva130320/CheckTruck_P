package com.checktruck.forms;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.SeekBar;

import com.checktruck.R;

public class FormTwoActivity extends AppCompatActivity {

    private RadioButton siRealViolRbtn, noRealViolRbtn;
    private ImageButton photRealViolImgBtn;
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

        initAsig();
        initListener();
    }

    private void initAsig() {

        siRealViolRbtn= (RadioButton)findViewById(R.id.form_si_sealViolRbtn);
        noRealViolRbtn= (RadioButton)findViewById(R.id.form_no_sealViolRbtn);
        photRealViolImgBtn = (ImageButton) findViewById(R.id.form_sealViolImg);

    }

    private void initListener() {
        siRealViolRbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                photRealViolImgBtn.setVisibility(View.VISIBLE);
            }
        });

        noRealViolRbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                photRealViolImgBtn.setVisibility(View.INVISIBLE);
            }
        });
    }
}
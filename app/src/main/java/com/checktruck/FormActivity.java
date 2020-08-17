package com.checktruck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.URL;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        getSupportActionBar().hide();

        findViewById(R.id.button).setOnClickListener(v -> {
            startActivity(new Intent(this, FormOneActivity.class));
        });
    }

}

package com.checktruck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        getSupportActionBar().hide();
    }
}

package com.checktruck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.URL;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
    }
    public  void SendData(String URL){
        URL url=null;
        String linea;
        String response;
        StringBuilder result;
    }
}

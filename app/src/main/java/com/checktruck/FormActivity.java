package com.checktruck;

import android.content.Intent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.checktruck.forms.FormOneActivity;
import com.checktruck.forms.FormThreeActivity;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.IndicatorType;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;
import com.warkiz.widget.TickMarkType;

public class FormActivity extends AppCompatActivity {

    private static final String TAG = "---!!>>";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        getSupportActionBar().hide();

        findViewById(R.id.button).setOnClickListener(v -> {
            startActivity(new Intent(this, FormOneActivity.class));
        });

        //IndicatorSeekBar seekBar = (IndicatorSeekBar)findViewById(R.id.seekBar);


        /*seekBar1.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                Log.i(TAG, String.valueOf(seekParams.seekBar));
                Log.i(TAG, String.valueOf(seekParams.progress));
                Log.i(TAG, String.valueOf(seekParams.progressFloat));
                Log.i(TAG, String.valueOf(seekParams.fromUser));
                //when tick count > 0
                Log.i(TAG, String.valueOf(seekParams.thumbPosition));
                Log.i(TAG, seekParams.tickText);
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

                Toast.makeText(FormActivity.this, String.valueOf(seekBar.getProgressFloat()) ,Toast.LENGTH_SHORT).show();
            }
        });*/
    }

}

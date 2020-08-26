package com.checktruck;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.checktruck.database.DBManager;

import java.io.File;

public class SplashActivity extends AppCompatActivity {

    private boolean dbError; //Si no se pudo crear la base de datos, para avisar al usuario
    private boolean needFix;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_page);

        File dir = new File(AppDelegate.DataPath+"/");
        if(!dir.exists()) {
            dir.mkdir();
        }

        dbError = false;
        needFix = false;
        DBManager dbMng = new DBManager(getBaseContext());
        try {
            dbMng.createDataBase();
        } catch(Exception e) {
            Log.i("ver", "Problemas al crear las bases de datos. "+e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppDelegate.actActivity = this;
        //Se les da un tiempo a las bases de datos para que copien correctamente sus datos.
        verifyDataBase();
    }

    private void verifyDataBase() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized(this) {
                        wait(3000);
                    }
                } catch(InterruptedException e) {
                }

                //Si hay error al crear la base de datos ya no se puede hacer lo siguiente.
                SharedPreferences prefs = getApplicationContext().getSharedPreferences("GcmDemoApp",
                        Context.MODE_PRIVATE);

                SplashWait();
            }
        };
        thread.start();
    }

    public void SplashWait()
    {

        Thread thread=  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        wait(2000);
                    }
                }
                catch(InterruptedException ex){
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences spref = getSharedPreferences("WeHoopSystems", Context.MODE_PRIVATE);
                        String logAux = spref.getString("userNombre", "logout");
                        int logAuxId = spref.getInt("usrid", 0);


                        if(logAux.equals("logout") || logAuxId == 0) {
                            Intent init = new Intent(SplashActivity.this, LoginActivity.class);
                            init.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(init);
                        } else {
                            Intent log = new Intent(SplashActivity.this, FormActivity.class);
                            log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(log);
                        }
                    }
                });
            }
        };
        thread.start();
    }

}

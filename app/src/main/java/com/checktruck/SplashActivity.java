package com.checktruck;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.checktruck.database.DBManager;

import java.io.File;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_page);
        //Obtenemos el directorio y checamos si existe en caso de no existir lo creamos.
        File dir = new File(AppDelegate.DataPath+"/");
        if(!dir.exists()) {
            dir.mkdir();
        }
        DBManager dbMng = new DBManager(getBaseContext());
        try {
            dbMng.createDataBase();
        } catch(Exception e) {
            Log.i("ver", "Problemas al crear las bases de datos. "+e.getMessage());
        }
    }
}

package com.checktruck.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;
import android.util.Log;

import com.checktruck.AppDelegate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {

    private static String _DBPath = AppDelegate.DBPath;
    private static String _DBName = "checktruck.db";

    private SQLiteDatabase _DBPtr;
    private final Context _DBCtxt;

    public DBManager(Context context) {
        super(context, _DBName, null, 1);
        this._DBCtxt = context;
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if (!dbExist) {
            //Anteriormente se utilizaba esto de android 8 (o 7) en adelante causa problemas
            //this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                //throw new Error("Error copiando Base de Datos");
                File tmp = new File(_DBPath + _DBName);
                tmp.delete();
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase dbPtr = null;

        try {
            String dbPath = _DBPath + _DBName;
            dbPtr = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            Log.i("Error","-->>> err " + e.getMessage());
        }

        if (dbPtr != null)
            dbPtr.close();

        return dbPtr != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        File dbDir = new File(AppDelegate.DBPath);
        if(!dbDir.exists()) {
            dbDir.mkdir();
        }
        dbDir = null;

        InputStream inStrm = _DBCtxt.getAssets().open(_DBName);
        String outFN = _DBPath + _DBName;
        OutputStream outStrm = new FileOutputStream(outFN);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inStrm.read(buffer)) > 0) {
            outStrm.write(buffer, 0, length);
        }

        inStrm.close();
        inStrm = null;
        outStrm.flush();
        outStrm.close();
        outStrm = null;
    }

    public void open() throws SQLException {
        try {
            createDataBase();
        } catch (IOException e) {
            //throw new Error("No se pudo crear la base de datos");
            File tmp = new File(_DBPath + _DBName);
            tmp.delete();
        }

        String dbPath = _DBPath + _DBName;
        _DBPtr = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (_DBPtr != null)
            _DBPtr.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}

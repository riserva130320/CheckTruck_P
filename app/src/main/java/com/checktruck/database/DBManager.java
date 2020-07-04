package com.checktruck.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.checktruck.AppDelegate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {

    private static String _DBPath = AppDelegate.DBPath;
    private static String _DBName = "colaboramx.sqlite";
    private SQLiteDatabase _DBPtr;
    private final Context _DBCtxt;


    private final static String DB_PROFILES_COL0 = "id";
    private final static String DB_PROFILES_COL1 = "id_prof";
    private final static String DB_PROFILES_COL2 = "id_user";
    private final static String DB_PROFILES_COL3 = "name";
    private final static String DB_PROFILES_COL4 = "parent";
    private final static String DB_PROFILES_COL5 = "sequence";

    private final static String DB_PROFSERV_COL0 = "id";
    private final static String DB_PROFSERV_COL1 = "id_prof";
    private final static String DB_PROFSERV_COL2 = "id_serv";

    private final static String DB_PROFPRES_COL0 = "id";
    private final static String DB_PROFPRES_COL1 = "id_prof";
    private final static String DB_PROFPRES_COL2 = "id_pres";


    private final static String[] dbProfilesCols = new String[]{DB_PROFILES_COL0,
            DB_PROFILES_COL1, DB_PROFILES_COL2, DB_PROFILES_COL3, DB_PROFILES_COL4, DB_PROFILES_COL5};

    private final static String[] dbProfServCols = new String[]{DB_PROFSERV_COL0,
            DB_PROFSERV_COL1, DB_PROFSERV_COL2};

    private final static String[] dbProfPresCols = new String[]{DB_PROFPRES_COL0,
            DB_PROFPRES_COL1, DB_PROFPRES_COL2};

    public DBManager(Context context) {
        super(context, _DBName, null, 1);
        this._DBCtxt = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copiando Base de Datos");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase dbPtr = null;

        try {
            String dbPath = _DBPath + _DBName;
            dbPtr = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            Log.i("--->>>","Error: "+e.getMessage());
        }

        if (dbPtr != null)
            dbPtr.close();

        return dbPtr != null ? true : false;
    }


    private void copyDataBase() throws IOException {
        InputStream inStrm = _DBCtxt.getAssets().open(_DBName);
        String outFN = _DBPath + _DBName;
        OutputStream outStrm = new FileOutputStream(outFN);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inStrm.read(buffer)) > 0) {
            outStrm.write(buffer, 0, length);
        }

        outStrm.flush();
        outStrm.close();
        inStrm.close();
    }

    public void open() throws SQLException {
        try {
            createDataBase();
        } catch (IOException e) {
            throw new Error("No se pudo crear la base de datos");
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

    public void addProfileInfo(JSONArray pdata, String idUser) {
        int r;
        int c;

        //Elimina todos los registros que tenÃ­a este usuario anteriormente
        _DBPtr.delete("Profiles", DB_PROFILES_COL2+"="+idUser, null);

        //Agrega todos los elementos que acaba de descargar en un solo query por tabla
        String query = "INSERT INTO Profiles (id, id_prof, id_user, name, parent, sequence) VALUES";
        String squery = "INSERT OR REPLACE INTO ProfServ (id,id_prof,id_serv) VALUES";
        String pquery = "INSERT OR REPLACE INTO ProfPres (id,id_prof,id_pres) VALUES";

        ArrayList<DBProfServ> wserv = new ArrayList<DBProfServ>();
        ArrayList<DBProfPres> wpres = new ArrayList<DBProfPres>();

        try {
            int limit = 0;
            int limit2 = 0;
            for (c = 0; c < pdata.length(); c++) {
                if(limit > 300) {
                    limit = 0;
                    query = query.substring(0, query.length()-1);
                    _DBPtr.execSQL(query);
                    query = "INSERT INTO Profiles (id, id_prof, id_user, name, parent, sequence) VALUES";
                }
                JSONObject jobj = pdata.getJSONObject(c);
                //Query para perfiles
                query += " ('" + idUser + jobj.getInt("id_prof") + "', '" +
                        jobj.getInt("id_prof") + "', '" +
                        idUser + "', '" +
                        jobj.getString("name") + "', '" +
                        jobj.getInt("parent")+"', '" +
                        jobj.getInt("sequence") + "'),";
                //Query para servicios
                JSONArray arr = jobj.getJSONArray("serv");
                for (r = 0; r < arr.length(); r++) {
                    if(limit2 > 300) {
                        limit2 = 0;
                        squery = squery.substring(0, squery.length()-1);
                        _DBPtr.execSQL(squery);
                        squery = "INSERT OR REPLACE INTO ProfServ (id,id_prof,id_serv) VALUES";
                    }
                    squery += " ('" + jobj.getInt("id_prof") + arr.getInt(r) + "', '" +
                            jobj.getInt("id_prof") + "', '" +
                            arr.getInt(r) + "'),";
                    DBProfServ ps = new DBProfServ();
                    ps.IdProf = jobj.getInt("id_prof");
                    ps.IdServ = arr.getInt(r);
                    wserv.add(ps);
                    limit2++;
                }
                limit++;
            }

            //Quita la Ãºltima coma para evitar errores en el query
            query = query.substring(0, query.length()-1);
            squery = squery.substring(0, squery.length()-1);
            _DBPtr.execSQL(query);
            _DBPtr.execSQL(squery);

            limit = 0;
            for (c = 0; c < pdata.length(); c++) {
                JSONObject jobj = pdata.getJSONObject(c);

                //Query para presentaciones
                JSONArray arr = jobj.getJSONArray("pres");
                for(r=0; r<arr.length(); r++) {
                    pquery+=" ('"+jobj.getInt("id_prof")+arr.getInt(r)+"', '" +
                            jobj.getInt("id_prof")+"', '" +
                            arr.getInt(r)+"'),";
                    DBProfPres pp = new DBProfPres();
                    pp.IdProf = jobj.getInt("id_prof");
                    pp.IdPres = arr.getInt(r);
                    wpres.add(pp);

                    limit++;
                    //No se pueden insertar mas de cierto nÃºmero de registros a la vez, limit sirve para
                    //ir agregando bloques de 300 por 300
                    if(limit > 300) {
                        limit = 0;
                        //Quita la Ãºltima coma para evitar errores en el query
                        pquery = pquery.substring(0, pquery.length()-1);
                        _DBPtr.execSQL(pquery);
                        //Reinicia el query
                        pquery = "INSERT OR REPLACE INTO ProfPres (id,id_prof,id_pres) VALUES";
                    }
                }
            }
            //Si limit es mayor a 0 es que aun hay registros que agregar
            //Quita la Ãºltima coma para evitar errores en el query
            pquery = pquery.substring(0, pquery.length()-1);
            _DBPtr.execSQL(pquery);
        } catch(Exception e) {
            Log.i("ver","Error: "+e.getMessage());
        }

        deleteUserProfileFrom(wserv, wpres);
    }

    private void deleteUserProfileFrom(ArrayList<DBProfServ> wserv, ArrayList<DBProfPres> wpres) {
        //Elimina los registros que ya no se utilizan (los que ya no se encuentran en las listas
        //que se acaban de descargarm wserv y wpres)

        Cursor res = _DBPtr.query("ProfServ", dbProfServCols, null, null, null, null, DB_PROFSERV_COL0);
        boolean find;
        if ((res != null) && res.moveToFirst()) {
            do {
                DBProfServ ps = new DBProfServ();
                ps.IdProf = res.getInt(res.getColumnIndex(DB_PROFSERV_COL1));
                ps.IdServ = res.getInt(res.getColumnIndex(DB_PROFSERV_COL2));
                find = false;
                for (int c = 0; c < wserv.size(); c++) {
                    if ((ps.IdProf == wserv.get(c).IdProf) && (ps.IdServ == wserv.get(c).IdServ)) {
                        find = true;
                        break;
                    }
                }
                if (!find) {
                    _DBPtr.delete("ProfServ",
                            DB_PROFSERV_COL0 + "=" + res.getInt(res.getColumnIndex(DB_PROFSERV_COL0)), null);
                }
            } while (res.moveToNext());
        }
        res.close();
        res = _DBPtr.query("ProfPres", dbProfPresCols, null, null, null, null, DB_PROFPRES_COL0);
        if ((res != null) && res.moveToFirst()) {
            do {
                DBProfPres pp = new DBProfPres();
                pp.IdProf = res.getInt(res.getColumnIndex(DB_PROFSERV_COL1));
                pp.IdPres = res.getInt(res.getColumnIndex(DB_PROFPRES_COL2));
                find = false;
                for (int c = 0; c < wpres.size(); c++) {
                    if ((pp.IdProf == wpres.get(c).IdProf) && (pp.IdPres == wpres.get(c).IdPres)) {
                        find = true;
                        break;
                    }
                }
                if (!find) {
                    _DBPtr.delete("ProfPres",
                            DB_PROFPRES_COL0 + "=" + res.getInt(res.getColumnIndex(DB_PROFPRES_COL0)), null);
                }
            } while (res.moveToNext());
        }
        res.close();
    }

    /* ***********************************
     * Querys
     * *********************************** */
}

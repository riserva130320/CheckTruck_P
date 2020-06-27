package com.checktruck;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import android.view.View;
import android.view.inputmethod.InputMethodManager;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class AppDelegate {




    public static String WebPath = "http://www.wehoopsystems.com/hoop.com.wehoopsystems.com/checkTruck/public/services/ws/";

    public static String VersionApp = "2.1[23]";


    public static String CorpId = "1";
    public static LoginActivity rootAct = null; //Apunta al login, sirve para regresarse a esa
    //actividad cuando se sale del app

    public static boolean tabletDevice;
    public static Activity actActivity;
    public static boolean downloadData;
    public static boolean downloadingPres;
    public static boolean cancelDownload;
    public static boolean flagError;
    public static boolean flagSync;
    public static boolean flagform;
    public static boolean checAllbtn;
    public static boolean checKeyBoard;
    private static int BUFFER_SIZE = 1024;

    public static int spinerSelect=0;
    public static int idstates=0;

    //formulario
    public static List<String> listaStates;
    public static List<String> listaCities;
    public static List<String> listaVertical;
    public static List<String> listaCharge;
    public static List<String> listaProfa;
    public static List<String> listaPymes;
    public static Hashtable<String,Integer> pymesDic;
    public static Hashtable<String,Integer> profilesDic;
    public static Hashtable<String,Integer> stateDic;
    public static Hashtable<String,Integer> citiesDic;
    public static  int selectStates=0;
    public static  int selectCities=0;
    public static  String selectVertical="";
    public static  int selectChange=0;
    public static  int selectPerf=0;
    public static  int selectPyme=0;
    public static Boolean selectPymeBol=false;

    public static String stateSelect="";


/*
  public static String Err01 = "Ha ocurrido un error inesperado, por favor revisa tu conexión a " +
                                  "internet e intentalo nuevamente.";
  public static String Err02 = "Es necesario que ingrese su correo electrónico registrado en " +
                                   "Samaya";
  public static String Err03 = "Es necesario ingresar alguna respuesta";

  public static String Err04 = "No tienes conexión a internet";

  public static String Err05 = "Tienes conexión a internet";

  public static  String LogLab1 = "​Gracias por usar el App Samaya. Para ingresar a ver los contenidos " +
               "presiona en el botón que dice VER PRESENTACIONES. Ahí selecciona el Servicio que " +
               "deseas consultar y después selecciona la Presentación que deseas consultar. Una vez " +
               "ahí veras los contenidos. Selecciona el que desees.";
  public static String LogLab2 = "Para poder acceder al App es necesario saber que estás registrado " +
               "en Samaya. Necesitarás verificar tus datos registrados. Dentro de " +
               "los campos que se ven aquí coloca: \n" +
               "1. Tu EMAIL registrado en Samaya. \n" +
               "2. Tu IDENTIFICADOR. Este es el numero que te asignaron cuando te registraron " +
               "en Samaya. \n" +
               "3. Tu TELÉFONO. Este es el que tienes registrado en Samaya.";
  public static String LogLab3 = "<html><head><style type=\"text/css\">body{color: #000; background-color: #fff;}</style>" +
               "<meta charset=utf-8></head><body><div style=text-align:justify>" +
               "<center><h2>Ayuda</h2></center><br>" +
               "<p align=\"justify\">Bienvenido a Samaya. Esta es un App en la que " +
               "podr&aacute;s recibir el material de ventas de tu empresa el cual se " +
               "guardar&aacute;n aqu&iacute; para que puedas consultarlo cuando necesites. " +
               "Para poder acceder al App es necesario saber que est&aacute;s registrado en " +
               "Samaya. Necesitar&aacute;s verificar tus datos. Dentro de los campos que ven " +
               "aqu&iacute; coloca:</p><br>" +
               "<p align=\"justify\">  1. Tu EMAIL registrado en Samaya.</p>" +
               "<p align=\"justify\">  2. Tu Identificador. Este es el numero que te asignaron " +
               "cuando te registraron en Samaya.</p>" +
               "<p align=\"justify\">  3. Tu TEL&Eacute;FONO. Este es el que tienes registrado en " +
               "Samaya.</p><br>" +
               "<p align=\"justify\">Si alguno de estos datos no lo conoces o es incorrecto y " +
               "no te puedes conectar, favor de contactarnos dando click en Soporte t&eacute;cnico o " +
               "al correo hola@samaya.mx</p>" +
               "</div></body></html>";
  public static String LogLab4 = "Si alguno de estos datos no lo conoces o es incorrecto y no " +
               "te puedes conectar, favor de contactarnos dando click en Soporte " +
               "técnico o al correo hola@samaya.mx";*/

    //Para mostrar los Push


    /* ****************************************
     * Cola de descargas
     * **************************************** */


    /* ****************************************
     * *** Manejo de archivos
     * **************************************** */

    public static void writeToFile(String sfile, String data) {
        FileOutputStream outStrm;
        try {
            outStrm = actActivity.openFileOutput(sfile, Context.MODE_PRIVATE);
            outStrm.write(data.getBytes());
            outStrm.close();
      /*OutputStreamWriter outputStreamWriter = new OutputStreamWriter
          (actActivity.openFileOutput(sfile, Context.MODE_PRIVATE));
      outputStreamWriter.write(data);
      outputStreamWriter.close();*/
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static String readFromFile(String sfile) {
        String ret = "";

        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(sfile));

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public static void copyFile(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public static void copyDirectory(File sourceLocation , File targetLocation)
            throws IOException {

        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists() && !targetLocation.mkdirs()) {
                throw new IOException("Cannot create dir " + targetLocation.getAbsolutePath());
            }

            String[] children = sourceLocation.list();
            for (int i=0; i<children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {

            //Revisa que el directorio destino exista
            File directory = targetLocation.getParentFile();
            if (directory != null && !directory.exists() && !directory.mkdirs()) {
                throw new IOException("Cannot create dir " + directory.getAbsolutePath());
            }

            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);

            //Copia los datos del archivo de source a target
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }

    public static void zipFiles(String[] files, String zipFile) throws IOException {
        BufferedInputStream origin = null;
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
        try {
            byte data[] = new byte[BUFFER_SIZE];

            for (int i = 0; i < files.length; i++) {
                FileInputStream fi = new FileInputStream(files[i]);
                origin = new BufferedInputStream(fi, BUFFER_SIZE);
                try {
                    ZipEntry entry = new ZipEntry(files[i].substring(files[i].lastIndexOf("/") + 1));
                    out.putNextEntry(entry);
                    int count;
                    while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                        out.write(data, 0, count);
                    }
                }
                finally {
                    origin.close();
                }
            }
        }
        finally {
            out.close();
        }
    }









    /* ****************************************
     * *** Herramientas generales
     * **************************************** */
  /*public static void showStaticMessage(final Activity act, final String title, final String msg) {
    act.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setMessage(msg)
            .setTitle(title)
            .setCancelable(false)
            .setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int id) {}
                });
        AlertDialog alert = builder.create();
        alert.show();
      }
    });
  }*/

    public final static boolean validateEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static String getDeviceName() {
        String manufac = Build.MANUFACTURER;
        String model = Build.MODEL;
        if(model.startsWith(manufac)) {
            model = Character.toUpperCase(model.charAt(0)) + model.substring(1);
        } else {
            model = Character.toUpperCase(manufac.charAt(0)) + manufac.substring(1) + "_" + model;
        }

        model.trim();
        model = model.replace(" ", "_");
        return  model;
    }

    public static long daysToDate(String compDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        long daysDiff = -1;

        try {
            Date sdate = sdf.parse(compDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdate);

            long msDiff = Calendar.getInstance().getTimeInMillis() - cal.getTimeInMillis();
            daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);

            Log.i("ver",">>>>>Dias: "+daysDiff);
        } catch (ParseException e) {
            Log.i("ver", ">>>Error: " + e.getMessage());
        }
        return  daysDiff;
    }

    public static void hideKeyboard(Activity act) {
        InputMethodManager inputMethodManager = (InputMethodManager)act.
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager != null ) {
            View localView = act.getCurrentFocus();
            if(localView != null && localView.getWindowToken() != null ) {
                IBinder windowToken = localView.getWindowToken();
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
            }
        }
    }
}

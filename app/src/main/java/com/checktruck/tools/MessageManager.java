package com.checktruck.tools;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
public class MessageManager {
    //Errores Avisos
    public static String Err01 = "Ha ocurrido un error inesperado, por favor revisa tu conexión a " +
            "internet e intentalo nuevamente.";
    public static String Err02 = "Es necesario que ingrese su correo electrónico registrado en " +
            "Samaya";
    public static String Err03 = "Es necesario ingresar alguna respuesta";

    public static String Err04 = "No tienes conexión a internet";

    public static String Err05 = "Tienes conexión a internet";

    public static String Err06 = "Se inicio la descarga de todas las presentaciones";

    public static String Err07 = "Debe iniciar sesion para capturar clientes";

    public static String Err08 = "Debe iniciar sesion para enviar notificacion.";

    public static String Err09 = "Debe iniciar sesion para utilizar el cotizador.";

    public static String Err10 = "Debe iniciar sesion para capturar inventario.";

    public static String Err11 ="Debe iniciar sesion para enviar fotos.";

    public static String Err12 = "Debe iniciar sesion para usar esta función.";

    public static String Err13 = "Debe escribir un correo valido";

    public static String Err14 = "Ingresaste caracteres no validos en alguno de los campos";

    public static String Err15= "Se envio una contraseña temporal al correo: ";

    public static String Err16= "Se ha cancelado la descarga, es posible que ";

    public static String Err17= "los datos no estén actualizados.";

    public static String Err18= "Todo dispositivo seleccionado ha sido desconectado, ahora puedes ingresar con tu ";

    public static String Err19= "cuenta en este dispositivo.";

    public static String Err20=  "Se ha enviado un NIP de validación a tu correo, ";

    public static String Err21=  "ingrésalo en el campo correspondiente.";

    public static String Err22=  "No se pudo abrir este documento, el archivo puede estar corrupto. Avisanos en soporte " +
            "técnico (presionando el botón ? en la barra superior de la pantalla anterior) para " +
            "revisar el problema, gracias.";

    public static String Err23= "Es necesario llenar todos los campos."  ;

    public static String Err24= "Tu notificacion ha sido enviada.";

    public static String Err25="Alguno de los campos  tiene datos invalidos";

    public static String Err26= "No fue posible obtener la localización geográfica";

    public static String Err27= "No se obtuvo respuesta de la cámara.";

    public static String Err28="Es necesario ingresar un nombre de imagen y " +
            "un comentario";
    public static String Err29="Su información ha sido enviada.";

    public static String Err31 = "Para usar esta herramienta debes acceder con tú cuenta. ";
    public static String Err32 = "La presentacion no se puede compartir,es necesario actualizarla, en cuanto este disponible su actualizacion se mostrara nuevamente ";
    public static String Err33 ="Ocurrió un error al conectar. verifique su conexión a internet y vuelva a intentar.";

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
            "técnico o al correo hola@samaya.mx";

    private ButtonClickedListener _listener;

    public interface ButtonClickedListener {
        public void onAcceptCicked();
        public void onCancelClicked();
        public void onCustomClicked(String btn);
    }

    public MessageManager() {
        _listener = null;
    }

    public static void showStaticMessage(final Activity act, final String title, final String msg) {
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
    }

    public void showSingleMessage(final Activity act, final String title, final String msg) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(act);
                builder.setMessage(msg)
                        .setTitle(title)
                        .setCancelable(false)
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if(_listener != null) {
                                            _listener.onAcceptCicked();
                                        }
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void showDoubleMessage(final Activity act, final String title, final String msg) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(act);
                builder.setMessage(msg)
                        .setTitle(title)
                        .setCancelable(false)
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if(_listener != null) {
                                            _listener.onAcceptCicked();
                                        }
                                    }
                                })
                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(_listener != null) {
                                            _listener.onCancelClicked();
                                        }
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void showCustomMessage(final Activity act, final String title, final String msg,
                                  final String btn1, final String btn2) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(act);
                builder.setMessage(msg)
                        .setTitle(title)
                        .setPositiveButton(btn1,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(_listener != null) {
                                            _listener.onCustomClicked(btn1);
                                        }
                                    }
                                })
                        .setNegativeButton(btn2,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(_listener != null) {
                                            _listener.onCustomClicked(btn2);
                                        }
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void showCustomMessage(final Activity act, final String title, final String msg,
                                  final String btn1, final String btn2, final String btn3) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(act);
                builder.setMessage(msg)
                        .setTitle(title)
                        .setPositiveButton(btn1,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(_listener != null) {
                                            _listener.onCustomClicked(btn1);
                                        }
                                    }
                                })
                        .setNeutralButton(btn2,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(_listener != null) {
                                            _listener.onCustomClicked(btn2);
                                        }
                                    }
                                })
                        .setNegativeButton(btn3,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(_listener != null) {
                                            _listener.onCustomClicked(btn3);
                                        }
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void setOnButtonClickedListener(ButtonClickedListener listener) {
        _listener = listener;
    }
}

package com.checktruck;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import com.checktruck.database.DBManager;
import com.checktruck.tools.MessageManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    Button btnlogin;
    EditText user, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        user = (EditText) findViewById(R.id.txtlogin_user);
        password = (EditText) findViewById(R.id.txtlogin_pass);

        btnlogin = (Button) findViewById(R.id.btnlogin);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // sendData("http://www.wehoopsystems.com/hoop.com.wehoopsystems.com/Soporte/Servicios/UsuarioPrueba.php");
                loginClicked();
            }
        });
    }


    public void loginClicked() {
        executeLogin();

    }

    public void executeLogin() {
        NewLoginTask log = new NewLoginTask();
        log.startLogin();
    }

    public class NewLoginTask{
        private String data;

        public NewLoginTask(){

        }

        public void startLogin(){
           /* LoginActivity.this.runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.login_view_proccessLay).setVisibility(View.VISIBLE);
                        }
                    });*/


           String  id = ((EditText) findViewById(R.id.txtlogin_user)).getText().toString();

            if (id.isEmpty())
                id = ((EditText) findViewById(R.id.txtlogin_user)).getHint().toString();
            id= id.trim().replace(" ","");
            String pass = ((EditText) findViewById(R.id.txtlogin_pass)).getText().toString();
            pass = pass.trim().replace(" ","");
            String devId = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);

            RequestParams loginValues = new RequestParams();
            loginValues.put("corpId",AppDelegate.CorpId);
            loginValues.put("userName",id);
            loginValues.put("password",pass);


            String spost =AppDelegate.WebPath + "loginUser.php";
            AsyncHttpClient webClient =  new AsyncHttpClient();

            webClient.setTimeout(180000);
            webClient.post(spost, loginValues, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String s, Throwable throwable) {
                    Log.i("ver", "Error en respuesta web");
                    postExecute(false);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String s) {
                    data =s;
                    postExecute(true);
                }
            });
        }

        private void postExecute(boolean success){
            SharedPreferences spref = getSharedPreferences("WeHoopSystems",
                    Context.MODE_PRIVATE);

           /* LoginActivity.this.runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.login_view_proccessLay).setVisibility(View.INVISIBLE);

                    });*/
            if (success) {

                try {
                    final JSONObject jobj = new JSONObject(data);

                    if (jobj.getString("Error").isEmpty()) {
                        SharedPreferences.Editor editor = spref.edit();
                        jobj.getJSONObject("Response").get("id").toString();

                        //Se registran los datos de login
                        editor.putInt("checktruck_usrid", Integer.parseInt(  jobj.getJSONObject("Response").get("id").toString()));

                        editor.putString("checktruck_nombre",  jobj.getJSONObject("Response").get("name").toString());

                        Log.i("ver",">>>>>>Login: "+String.valueOf(  jobj.getJSONObject("Response").get("id").toString()));
                        editor.putInt("samaya_offid", Integer.parseInt(          jobj.getJSONObject("Response").get("id").toString()));
                        editor.putString("checktruck_Name", "CheckTruck");
                        editor.putString("samaya_usrName", ((EditText) findViewById(R.id.txtlogin_user)).getText().toString());
                        editor.commit();


                        DBManager dbMng = new DBManager(LoginActivity.this.getBaseContext());
                        dbMng.open();
                        //dbMng.addProfileInfo(jobj.getJSONArray("profiles"), jobj.getString("id"));
                        dbMng.close();

                        UserLoged();
                    } else {
                        MessageManager.showStaticMessage(LoginActivity.this, "Error", jobj.getString("Error"));

                    }

                    //Comprobar la conexión a internet
                  /*  ConnectionDetector connect = new ConnectionDetector(AppDelegate.actActivity);
                    if(connect.isConnectingToInternet()){
                       // AppDelegate.showStaticMessage(LoginActivity.this, "Aviso", AppDelegate.Err05);
                    }*/
                }catch (Exception e) {
                    MessageManager.showStaticMessage(LoginActivity.this, "Aviso", MessageManager.Err04);
                }
            }else {MessageManager.showStaticMessage(LoginActivity.this, "Error", MessageManager.Err04);
        }
        }
    }

  /*  private  void sendData(String URL){
        StringRequest stringRequest= new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    Intent intent= new Intent(getApplicationContext(),FormActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this,"Usuario o Contraseña Incorrecto",Toast.LENGTH_SHORT).show();                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> parameters= new HashMap<String, String>();
               parameters.put("id",user.getText().toString());
               parameters.put("pass",password.getText().toString());

                return parameters;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/
  public void UserLoged(){
      Intent next = new Intent(LoginActivity.this,   FormActivity.class);
      startActivity(next);
  }
}

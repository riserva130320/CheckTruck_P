package com.checktruck.tools.connection;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

import com.checktruck.AppDelegate;
import com.checktruck.tools.MessageManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Connection extends AppCompatActivity {
    private static String urlLogin = "loginUser.php";
    public static class loginUser{
        private loginUserListener _listener;

        public interface loginUserListener{
            void loginUserSuccess();
            void loginUserFail(String type, String error);
        }

        public void startLogin(String userName, String pass){
            String url = AppDelegate.WebPath + urlLogin;
            AsyncHttpClient webClient = new AsyncHttpClient();
            webClient.setTimeout(120000);

            RequestParams loginValues = new RequestParams();
            loginValues.put("corpId", AppDelegate.CorpId);
            loginValues.put("userName", userName);
            loginValues.put("password", pass);

            webClient.post(url, loginValues, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    fail();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    success(statusCode, responseString);
                }
            });
        }

        private void success(int statusCode, String responseString) {
            try {
                JSONObject jobj = new JSONObject(responseString);
                if (jobj.getString("Error").isEmpty()) {
                    jobj = jobj.getJSONObject("Response");
                    SharedPreferences spref = AppDelegate.actActivity.getSharedPreferences("WeHoopSystems",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = spref.edit();
                    //Se registran los datos de login
                    editor.putInt("usrid", jobj.getInt("id"));
                    editor.putString("nombre",  jobj.getString("name"));
                    editor.putString("lastName",  jobj.getString("last_name"));
                    editor.putString("userNombre",  jobj.getString("user_name"));
                    editor.putInt("profId",  jobj.getInt("profile_id"));
                    editor.commit();
                    if (_listener != null){
                        _listener.loginUserSuccess();
                    }
                }else {
                    if (_listener != null){
                        _listener.loginUserFail("Error", jobj.getString("Error"));
                    }
                    //MessageManager.showStaticMessage(LoginActivity.this, "Error", jobj.getString("Error"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                fail();
            }
        }

        public void fail(){
            if (_listener != null){
                _listener.loginUserFail("Error", MessageManager.Err33);
            }
        }

        public void setloginUserListener(loginUserListener listener) {
            _listener = listener;
        }
    }
}


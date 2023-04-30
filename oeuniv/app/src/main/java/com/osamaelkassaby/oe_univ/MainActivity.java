package com.osamaelkassaby.oe_univ;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Connection connection;
    private String URL = "http://10.10.1.64/oe_univ/login/";
    private Sqlite_user_info info = new Sqlite_user_info(MainActivity.this);
    private String useranme , password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // check if database found
        Cursor cursor = info.redData();
        if(cursor.getCount() > 0){
            Intent intent = new Intent(MainActivity.this , Profile.class);
            startActivity(intent);
            finish();
        }





       // Login Button
        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edit_username = findViewById(R.id.emailEdit);
                EditText edit_password = findViewById(R.id.password);
                useranme = edit_username.getText().toString();
                password = edit_password.getText().toString();
                if(useranme.isEmpty() || password.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Inputs is empty")
                            .setIcon(R.drawable.ic_baseline_error_24)
                            .setMessage("Inputs Can't be Empty ");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    login(useranme , password);

                }
            }
        });


    }

    public void login(String username, String password){
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        info.user(object.getInt("studentID") , username , password , object.getString("token"));
                        Intent intent = new Intent(MainActivity.this , Profile.class);
                        startActivity(intent);
                        finish();
                    }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this , e.toString() , Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Error")
                        .setIcon(R.drawable.ic_baseline_error_24)
                        .setMessage("Error In connection "+ error.toString());
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("login" , "f");
                data.put("username" , username);
                data.put("password" , password);

                return data;
            }
       };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
}
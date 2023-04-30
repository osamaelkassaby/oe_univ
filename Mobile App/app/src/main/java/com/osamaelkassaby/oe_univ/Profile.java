package com.osamaelkassaby.oe_univ;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AsyncCache;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    private   Sqlite_user_info info = new Sqlite_user_info(Profile.this);
    private   String URL = "http://10.10.1.64/oe_univ/info/";
    private   String URL_courese = "http://10.10.1.64/oe_univ/info/courses/";
    private   String URL_notification = "http://10.10.1.64/oe_univ/notification/";
    private   TextView TvId ;
    private   TextView TvName;
    private   TextView TvGPA ;
    private   TextView TvLevel ;
    private   TextView TvTPH;
    private   TextView courses_count;
    private   String name ;
    private   String id ;
    private   String token;
    private   String mail ;
    private   RecyclerView recyclerView;
    private   Customadapter customAdapter;
    private   ArrayList<String> courses;
    private   ArrayList<String> course_id;
    private   Thread thread;
    private   String encodeImageString;
    private int NotificationID ;
    private int imageNotificationID ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
         TvId = findViewById(R.id.tvID);
         TvName = findViewById(R.id.tvNameEdit);
         TvGPA = findViewById(R.id.tvGPA);
         TvLevel = findViewById(R.id.tvLEVEL);
         TvTPH = findViewById(R.id.tvTPH);
         courses = new ArrayList<>();
         course_id = new ArrayList<>();
         courses_count = findViewById(R.id.courses_count);


        Button editprofile = findViewById(R.id.editProfile);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this , EditProfile.class);
                intent.putExtra("id" , id );
                intent.putExtra("token" , token);
                startActivity(intent);
            }
        });

         info(4221147 , "2244");

        courses("2244" , 4221147);

        courses_count("2244" , 4221147);

        Cursor cursor = info.redData();
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                id = cursor.getString(0);
                token = cursor.getString(3);
            }
        }


       notification_REQUEST();
    }


    public void  info(int id , String token){
       StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               try {
                   JSONArray array = new JSONArray(response);
                   for (int i = 0; i < array.length(); i++) {
                       JSONObject object = array.getJSONObject(i);
                   //    Toast.makeText(getApplicationContext() , object.getString("id").toString() , Toast.LENGTH_LONG).show();
                       TvId.setText(object.getString("id").toString());
                       TvName.setText(object.getString("firstname") + " " + object.getString("lastname"));
                       TvGPA.setText( object.getString("gpa"));
                       TvLevel.setText(object.getString("level"));
                       TvTPH.setText(object.getString("TPH"));
                       ImageView view = findViewById(R.id.EditImage);
                       Glide.with(Profile.this).load(object.getString("img")).into(view);

                   }
               }catch (Exception e){
                   Toast.makeText(getApplicationContext(), e.toString() , Toast.LENGTH_LONG).show();
               }



           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(Profile.this , error.toString() , Toast.LENGTH_LONG).show();
           }
       }){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               Map<String , String> data = new HashMap<>();
               data.put("info","");
               data.put("id", String.valueOf(id));
               data.put("token" , token);

               return data;

           }
       };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    public void courses(String token , int id){
        StringRequest request = new StringRequest(Request.Method.POST, URL_courese, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                 //       Toast.makeText(getApplicationContext() , object.getString("course_name").toString() , Toast.LENGTH_LONG).show();
                   //     courses.add(object.getString("course_name"));
                        courses.add(object.getString("course_name"));
                        course_id.add(object.getString("course_id"));

                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString() , Toast.LENGTH_LONG).show();
                }
                recyclerView = findViewById(R.id.recyclerView2);
                customAdapter = new Customadapter(Profile.this , courses , course_id);
                recyclerView.setAdapter(customAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(Profile.this));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Profile.this , error.toString() , Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String , String> getParams() throws AuthFailureError{
                Map<String , String> data = new HashMap<>();
                data.put("info" , "");
                data.put("id" , String.valueOf(id));
                data.put("token" , token);

                return data;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    public void courses_count(String token , int id){
        StringRequest request = new StringRequest(Request.Method.POST, URL_courese, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        //       Toast.makeText(getApplicationContext() , object.getString("course_name").toString() , Toast.LENGTH_LONG).show();
                        //     courses.add(object.getString("course_name"));
                        courses_count.setText("Courses " +object.getString("COUNT(course_id)"));

                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString() , Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Profile.this , error.toString() , Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String , String> getParams() throws AuthFailureError{
                Map<String , String> data = new HashMap<>();
                data.put("count" , "");
                data.put("id" , String.valueOf(id));
                data.put("token" , token);

                return data;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void notification_REQUEST(){
        thread = new Thread(new Runnable() {
            int notificationId;
            int notificationId_2;
            String title;
            String message;
            String url;
            @Override
            public void run() {

                while (true){
                    try {
                        Thread.sleep(1000*20);


                        StringRequest request = new StringRequest(Request.Method.POST, URL_notification, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray array = new JSONArray(response);
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object = array.getJSONObject(i);
                                         //      Toast.makeText(getApplicationContext() , object.getString("course_name").toString() , Toast.LENGTH_LONG).show();
                                        //       courses.add(object.getString("course_name"));

                                                imageNotificationID = object.getInt("id");
                                                title = object.getString("title");
                                                message =  object.getString("message");
                                                url = object.getString("url");

                                    }

                                    notificationId= imageNotificationID;
                                    if(notificationId >notificationId_2 ){
                                        showImagebitmap_notification(title , message, url , imageNotificationID);
                                        notificationId_2 = notificationId;
                                    //    Toast.makeText(getApplicationContext(),NotificationID , Toast.LENGTH_LONG).show();
                                    }else {
                                        notificationId_2 = notificationId;
                                    }

                                }catch (Exception e){
                                   // Toast.makeText(getApplicationContext(), e.toString() , Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            protected Map<String , String> getParams() throws AuthFailureError {
                                Map<String, String> data = new HashMap<>();
                                data.put("notification", "");

                                return data;
                            }

                        };
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(request);




                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
        thread.start();
    }
    @SuppressLint("StaticFieldLeak")
    private void showImagebitmap_notification(String tile , String msg , String url , int imageNotificationID){
        new AsyncTask<String, Void , Bitmap>(){

            @Override
            protected Bitmap doInBackground(String... strings) {
                InputStream inputStream;
                try {
                    java.net.URL url = new URL(strings[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    inputStream = connection.getInputStream();
                    return BitmapFactory.decodeStream(inputStream);
                }catch (Exception ex){
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {

                    notification(tile , msg , bitmap);


            }
        }.execute(url);
    }
    private void encodeBitmapImage(Bitmap bitmap)
    {

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    private void notification(String title , String msg , Bitmap bitmap){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("notification oe_univ" , "notification oe_univ" , NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(Profile.this , "notification oe_univ");
        builder.setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(R.drawable.ic_baseline_circle_notifications_24)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .setAutoCancel(true);
        NotificationManagerCompat compat = NotificationManagerCompat.from(Profile.this);
        compat.notify(1,builder.build());

    }

}
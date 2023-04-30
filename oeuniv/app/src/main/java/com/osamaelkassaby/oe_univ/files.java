package com.osamaelkassaby.oe_univ;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class files extends AppCompatActivity {

  private   String course_id ;
  private  String course_name ;
  private  String done;

  private    RecyclerView recyclerView;
  private    Customadapter_files customadapter_files;
  private ArrayList<String> fileName;
  private ArrayList<String> url;
  private ArrayList<String> fileType;
  private String URL_FILES = "http://10.10.1.64/oe_univ/info/files/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);

        fileName = new ArrayList<>();
        url = new ArrayList<>();
        fileType = new ArrayList<>();
        course_id = getIntent().getStringExtra("course_id");
        course_name = getIntent().getStringExtra("course_name");

        TextView courseName = findViewById(R.id.courseName);
        TextView courseId   = findViewById(R.id.courseID);

        courseName.setText(course_name);
        courseId.setText(course_id);




        try {
            done = getIntent().getStringExtra("done");
            if(done.isEmpty()){
                AlertDialog.Builder builder = new AlertDialog.Builder(files.this);
                builder.setTitle("Download")
                        .setIcon(R.drawable.ic_baseline_error_24)
                        .setMessage("Download Fiald");
                AlertDialog dialog = builder.create();
                dialog.show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(files.this);
                builder.setTitle("Download")
                        .setIcon(R.drawable.sucsess)
                        .setMessage("Download Start");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }catch (Exception e){

        }
        try {
            files(Integer.parseInt(course_id));

        }catch (Exception e){

        }
    }

    public void files(int course_id){
        StringRequest request = new StringRequest(Request.Method.POST, URL_FILES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        fileName.add(object.getString("file_name"));
                        url.add(object.getString("path"));
                        fileType.add(object.getString("file_type"));
                    }
                }catch (Exception e){
                    Toast.makeText(files.this , e.toString() , Toast.LENGTH_LONG).show();
                }

                recyclerView = findViewById(R.id.recyclerView2);
                customadapter_files = new Customadapter_files(files.this , fileName , url , fileType);
                recyclerView.setAdapter(customadapter_files);
                recyclerView.setLayoutManager(new LinearLayoutManager(files.this));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("files" , "f");
                data.put("id" , String.valueOf(course_id));
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
}
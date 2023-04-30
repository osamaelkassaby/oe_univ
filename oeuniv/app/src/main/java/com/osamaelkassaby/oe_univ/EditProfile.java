package com.osamaelkassaby.oe_univ;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
    private ImageView img;
    private  Bitmap bitmap;
    private  String encodeImageString;
    private  String URL = "http://10.10.1.64/oe_univ/edit.php";
    private EditText email;
    private EditText phone;
    private EditText address;
    private TextView name;
    private TextView birth;
    private TextView gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        String id ,token;
        id = getIntent().getStringExtra("id");
        token = getIntent().getStringExtra("token");
        getDate(token , id);



        img = findViewById(R.id.EditImage);
        Button edit = findViewById(R.id.btnEditImage);
        email = findViewById(R.id.emailEdit);
        phone = findViewById(R.id.phoneEdit);
        address = findViewById(R.id.AddressEdit);
        name = findViewById(R.id.tvNameEdit);
        birth = findViewById(R.id.tvBirthEdit);
        gender = findViewById(R.id.tvGenderEdit);




        Button update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UplaodImage(encodeImageString , id , token , email.getText().toString() , phone.getText().toString() , address.getText().toString());
            }
        });



        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(EditProfile.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {

                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"choess image ") , 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                Toast.makeText(EditProfile.this , "Acsess Denided" , Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1){
            Uri uri = data.getData();
            try {
                InputStream  inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            }catch (Exception ex){
                Toast.makeText(EditProfile.this , ex.toString() , Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(EditProfile.this , "Error" , Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }







    private void getDate(String token , String id){

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        name.setText(object.getString("firstname") + object.getString("lastname"));
                        birth.setText(object.getString("birth"));
                        gender.setText(object.getString("gender"));
                        email.setText(object.getString("username"));
                        phone.setText(object.getString("phone"));
                        address.setText(object.getString("address"));
                        Glide.with(getApplicationContext()).load(object.getString("img")).into(img);

                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString() , Toast.LENGTH_LONG).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.toString());
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






    private void UplaodImage(String bitmap ,String id ,  String token , String email , String phone , String address  ){
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("done")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
                    builder.setTitle("update")
                            .setIcon(R.drawable.sucsess)
                            .setMessage("data updated sucsessfuly");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
                    builder.setTitle("update ")
                            .setIcon(R.drawable.ic_baseline_error_24)
                            .setMessage(" Faild to update ");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
                builder.setTitle("update ")
                        .setIcon(R.drawable.ic_baseline_error_24)
                        .setMessage(" Faild to update ");
                AlertDialog dialog = builder.create();
                dialog.show();            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("edit", "");
                data.put("img", bitmap);
                data.put("token", token);
                data.put("id", id);
                data.put("address", address);
                data.put("phone", phone);
                data.put("email", email);
                return data;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

}
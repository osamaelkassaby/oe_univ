package com.osamaelkassaby.oe_univ;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

public class Download extends AppCompatActivity {

    private static final int PERMISSION_STORAGE_CODE = 1000;
    String URL = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        URL = getIntent().getStringExtra("path");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                AlertDialog.Builder builder = new AlertDialog.Builder(Download.this);
                builder.setTitle("PERMISSION")
                        .setIcon(R.drawable.ic_baseline_error_24)
                        .setMessage("PERMISSION DENIED");
                AlertDialog dialog = builder.create();
                dialog.show();
                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission , PERMISSION_STORAGE_CODE);
            }else {
                startDownload(URL);
            }
        }else {
            startDownload(URL);

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (PERMISSION_STORAGE_CODE) {
            case PERMISSION_STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownload(URL);

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Download.this);
                    builder.setTitle("PERMISSION")
                            .setIcon(R.drawable.ic_baseline_error_24)
                            .setMessage("PERMISSION_DENIED");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        }
    }

    public void startDownload(String url){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download");
        request.setDescription("Download ...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, ""+System.currentTimeMillis());
        DownloadManager manager =  (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
        Intent intent = new Intent(Download.this,files.class);
        intent.putExtra("done" , "1");
        startActivity(intent);
    }


}
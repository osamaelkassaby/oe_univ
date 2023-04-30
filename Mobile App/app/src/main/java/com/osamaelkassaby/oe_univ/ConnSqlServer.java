package com.osamaelkassaby.oe_univ;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;

import kotlin.Suppress;

public class ConnSqlServer {
    Connection connection;
    @SuppressLint("NewApi")
    public Connection connClass() {
        String ip = "10.10.1.40", port = "1433", dbname = "oe", username = "osama", password = "P@$$word";

        String connectUrl = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectUrl = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databasename=" + dbname + ";user=" + username + ";" + "password=" + password + ";";
            connection = DriverManager.getConnection(connectUrl);

        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }

        return connection;
    }
}

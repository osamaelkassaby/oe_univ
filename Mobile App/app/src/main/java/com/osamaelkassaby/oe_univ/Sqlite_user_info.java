package com.osamaelkassaby.oe_univ;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Sqlite_user_info extends SQLiteOpenHelper {
     private final  Context context;
     private static final String DATABASE_MAME = "note.db";
     private static final int DATABASE_VERSION = 1;

     public  static final String TABLE_NAME = "user";
     public  static final String COULMN_id       = "id";
     public  static final String COULMN_username = "username";
     public  static final String COULMN_password = "password";
     public static final  String COULMN_token    = "token";
    public Sqlite_user_info(@Nullable Context context) {
        super(context , DATABASE_MAME , null , DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                "("+ COULMN_id + " int , " + COULMN_username + " TEXT , "+ COULMN_password + " TEXT , " + COULMN_token + " TEXT );" ;
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void user(int id , String username , String password , String token ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COULMN_id, id);
        cv.put(COULMN_username, username);
        cv.put(COULMN_password, password);
        cv.put(COULMN_token, token);

        long result = db.insert(TABLE_NAME , null , cv);
        if(result == -1){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(R.drawable.ic_baseline_error_24)
                    .setTitle(" ")
                    .setMessage("failed database");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(R.drawable.sucsess)

                    .setView(R.layout.sucsess);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
    Cursor redData(){
        String query = "SELECT * FROM " +TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }
}

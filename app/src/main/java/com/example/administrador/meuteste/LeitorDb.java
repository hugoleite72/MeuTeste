package com.example.administrador.meuteste;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrador on 29/08/2017.
 */

public class LeitorDb extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "meuteste.db";
    public LeitorDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS CLIENTES(COD_CLIENTE INTEGER PRIMARY KEY AUTOINCREMENT,NOME TEXT,CPFCGC TEXT)");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// This database is only a cache for online data, so its upgrade policy is
// to simply to discard the data and start over
        db.execSQL("DROP TABLE CLIENTES");
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    //njknjk
}
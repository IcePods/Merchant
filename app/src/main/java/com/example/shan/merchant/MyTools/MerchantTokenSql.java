package com.example.shan.merchant.MyTools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sweet on 2018/6/3.
 */

public class MerchantTokenSql extends SQLiteOpenHelper {
    public static String name = "MerchantTokenSql.db";
    public static Integer version =1;

    public MerchantTokenSql(Context context) {
        super(context, name, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table merchant(id integer primary key autoincrement,merchantaccount varchar(100),merchantpassword varchar(100),merchanttoken varchar(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

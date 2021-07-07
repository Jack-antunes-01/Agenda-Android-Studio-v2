package com.example.agenda.ui.sqliteDb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbGateway {
    private static com.example.agenda.ui.sqliteDb.DbGateway gw;
    private SQLiteDatabase db;

    public DbGateway(Context context){
        com.example.agenda.ui.sqliteDb.DbHelper helper = new com.example.agenda.ui.sqliteDb.DbHelper(context);
        db = helper.getWritableDatabase();
    }

    public static com.example.agenda.ui.sqliteDb.DbGateway getInstance(Context context){
        if(gw == null){
            gw = new com.example.agenda.ui.sqliteDb.DbGateway(context);
        }
        return gw;
    }

    public SQLiteDatabase getDatabase(){
        return this.db;
    }
}

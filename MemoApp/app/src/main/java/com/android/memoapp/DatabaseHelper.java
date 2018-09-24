package com.android.memoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String Tag="DatabaseHelper";

    private static final String TABLE_NAME="memo_table";
    private static final String COL1="ID";
    private static final String COL2="title";
    private static final String COL3="content";


    public DatabaseHelper(Context context) {
        super(context,TABLE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
          String createTable="CREATE TABLE "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,"+COL2+","+COL3+"TEXT)";
          db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           db.execSQL("DROP IF TABLE EXISTS "+ TABLE_NAME);
           onCreate(db);
    }

    public boolean addData(String title,String content){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL2,title);
//        contentValues.put(COL3,content);

        long result=db.insert(TABLE_NAME,null,contentValues);

        if(result==-1){
            return false;
        }else{
            return true;
        }

    }

    public Cursor getData(){
        SQLiteDatabase db=this.getWritableDatabase();
        String query= "SELECT * FROM "+ TABLE_NAME;
        Cursor data=db.rawQuery(query,null);
        return data;
    }
}

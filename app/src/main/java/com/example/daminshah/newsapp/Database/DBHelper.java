package com.example.daminshah.newsapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by daminshah on 7/25/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=3;
    private static final String DATABASE_NAME="news.db";
    private static final String TAG="dbhelper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String queryString="CREATE TABLE " + Contract.TABLE_ARTICLES.TABLE_NAME+ " ("+
                Contract.TABLE_ARTICLES._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE+" TEXT NOT NULL, "+
                Contract.TABLE_ARTICLES.COLUMN_NAME_AUTHOR+" TEXT NOT NULL, "+
                Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION+" TEXT NOT NULL, "+
                Contract.TABLE_ARTICLES.COLUMN_NAME_NEWS_URL+" TEXT NOT NULL, "+
                Contract.TABLE_ARTICLES.COLUMN_NAME_IMAGE_URL+" TEXT NOT NULL, "+
                Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED+" DATE "+
                "); ";

        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);


        //Executed the query for Creating all the Relevant Column names
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

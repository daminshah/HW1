package com.example.daminshah.newsapp.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.daminshah.newsapp.Model.NewsItem;

import java.util.ArrayList;

import static com.example.daminshah.newsapp.Database.Contract.TABLE_ARTICLES.COLUMN_NAME_AUTHOR;
import static com.example.daminshah.newsapp.Database.Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION;
import static com.example.daminshah.newsapp.Database.Contract.TABLE_ARTICLES.COLUMN_NAME_IMAGE_URL;
import static com.example.daminshah.newsapp.Database.Contract.TABLE_ARTICLES.COLUMN_NAME_NEWS_URL;
import static com.example.daminshah.newsapp.Database.Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED;
import static com.example.daminshah.newsapp.Database.Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE;
import static com.example.daminshah.newsapp.Database.Contract.TABLE_ARTICLES.TABLE_NAME;

/**
 * Created by daminshah on 7/25/17.
 */

public class DBUtils {

    //Fetch all the items for the cursor to use
        public static Cursor getAllitems(SQLiteDatabase db){
            Cursor cursor= db.query(
                    Contract.TABLE_ARTICLES.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED + " DESC");
            return cursor;
        }

        //Inserts everything into the arraylist from the database
        public static void  insertnews(SQLiteDatabase db, ArrayList<NewsItem> newsItems) {

            //Deletes everything from database and populates it with new records .
            deleteNewsitem(db);
            db.beginTransaction();
            try {
                for (NewsItem newsItem : newsItems) {
                    ContentValues cv = new ContentValues();
                    cv.put(COLUMN_NAME_TITLE, newsItem.getTitle());
                    cv.put(COLUMN_NAME_AUTHOR, newsItem.getAuthor());
                    cv.put(COLUMN_NAME_DESCRIPTION, newsItem.getDescription());
                    cv.put(COLUMN_NAME_NEWS_URL, newsItem.getUrl());
                    cv.put(COLUMN_NAME_IMAGE_URL, newsItem.getUrlToimage());
                    cv.put(COLUMN_NAME_PUBLISHED, newsItem.getPublish());
                    db.insert(TABLE_NAME, null, cv);
                }
                db.setTransactionSuccessful();

            } finally {
                db.endTransaction();
                db.close();
            }
        }
            private static void deleteNewsitem(SQLiteDatabase db) {
                db.delete(Contract.TABLE_ARTICLES.TABLE_NAME, null, null);
            }

        }






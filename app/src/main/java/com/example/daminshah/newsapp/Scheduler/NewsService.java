package com.example.daminshah.newsapp.Scheduler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.daminshah.newsapp.Database.DBHelper;
import com.example.daminshah.newsapp.Database.DBUtils;
import com.example.daminshah.newsapp.Model.NewsItem;
import com.example.daminshah.newsapp.NetworkUtils;
import com.example.daminshah.newsapp.OpenNewsJsonUtils;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by daminshah on 7/26/17.
 */

public class NewsService extends com.firebase.jobdispatcher.JobService {
  private static final String TAG="NewsService";

   //JobService updates the database with the latest data
    @Override
    public boolean onStartJob(com.firebase.jobdispatcher.JobParameters params) {
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {

                updateDatabase(NewsService.this);
                return null;
            }
        }.execute();

        Log.d(TAG,"NewsItem Updated");
        return false;
    }


    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        return false;
    }


    public static void updateDatabase(Context context) {
        try {
            URL newsURL = NetworkUtils.buildURL();

            String results = NetworkUtils.getResponseFromHttpUrl(newsURL);

                ArrayList<NewsItem> newsoutput = OpenNewsJsonUtils.getSimpleNewsStringsFromJson(context, results);
                SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
                DBUtils.insertnews(db, newsoutput);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
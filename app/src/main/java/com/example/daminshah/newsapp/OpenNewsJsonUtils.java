package com.example.daminshah.newsapp;

import android.content.Context;

import com.example.daminshah.newsapp.Model.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by daminshah on 6/26/17.
 */

public class OpenNewsJsonUtils {


    public static ArrayList<NewsItem> getSimpleNewsStringsFromJson(Context context, String NewsJsonStr) throws JSONException
    {

        final String NEWS_ARTICLES="articles";
        final String NEWS_AUTHOR="author";
        final String NEWS_TITLE="title";
        final String NEWS_DESCRIPTION="description";
        final String NEWS_URL="url";
       final String NEWS_URLTOIMAGE="urlToImage";
        final String NEWS_PUBLISHEDAT="publishedAt";
        final String NEWS_STATUS="status";

      //  String[] parsedNewsData=null;

        ArrayList<NewsItem> parsedNewsData=new ArrayList<>();

        JSONObject newsJson=new JSONObject(NewsJsonStr);

            if (newsJson.has(NEWS_STATUS))
            {
                String status=newsJson.getString("status");

                switch (status) {


                    case "ok":
                        break;

                    default:
                        /* Server probably down */
                        return null;
                }
            }

        JSONArray newsArray=newsJson.getJSONArray(NEWS_ARTICLES);
        //parsedNewsData=new String[newsArray.length()];

        for (int i=0;i<newsArray.length();i++)

        {

            String author;
            String title;
            String description;
            String url;
            String urlToimage;
            String publish;

            JSONObject news=newsArray.getJSONObject(i);

            author=news.getString(NEWS_AUTHOR);
            title=news.getString(NEWS_TITLE);
            description=news.getString(NEWS_DESCRIPTION);
            url=news.getString(NEWS_URL);
            urlToimage=news.getString(NEWS_URLTOIMAGE);
           publish=news.getString(NEWS_PUBLISHEDAT);
            NewsItem item=new NewsItem(author,title,description,url,urlToimage,publish);
            parsedNewsData.add(item);

         //   parsedNewsData[i]=author +"-"+ title + "-" +description+ "-" +url+ "-" +urlToimage+ "-" + publish;


        }

        return parsedNewsData;


    }
}

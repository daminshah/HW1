    package com.example.daminshah.newsapp;


    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.net.Uri;
    import android.os.Bundle;
    import android.preference.PreferenceManager;
    import android.support.v4.app.LoaderManager;
    import android.support.v4.app.LoaderManager.LoaderCallbacks;
    import android.support.v4.content.AsyncTaskLoader;
    import android.support.v4.content.Loader;
    import android.support.v7.app.AppCompatActivity;
    import android.support.v7.widget.LinearLayoutManager;
    import android.support.v7.widget.RecyclerView;
    import android.util.Log;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.ProgressBar;
    import android.widget.TextView;

    import com.example.daminshah.newsapp.Database.Contract;
    import com.example.daminshah.newsapp.Database.DBHelper;
    import com.example.daminshah.newsapp.Database.DBUtils;
    import com.example.daminshah.newsapp.Model.NewsItem;
    import com.example.daminshah.newsapp.Scheduler.SCUtils;

    import java.net.URL;
    import java.util.ArrayList;

    public class MainActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<Void>> , NewsAdapter.ItemClickListener {
        static final String TAG = "mainactivity";



        private ProgressBar loadingindicator;

        private TextView mErrormessagedisplay;

        private RecyclerView recyclerView;

        //Declare Variables for adapter ,cursor and database

        private  NewsAdapter adapter;
        private Cursor cursor;
        private SQLiteDatabase db;


        private static final int NEWS_LOADER = 1;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            //Displays what's in the database onCreate()

            db=new DBHelper(this).getReadableDatabase();
            cursor=DBUtils.getAllitems(db);
            adapter=new NewsAdapter(cursor,this);



            loadingindicator=(ProgressBar)findViewById(R.id.pb_loading_indicator);

            mErrormessagedisplay=(TextView)findViewById(R.id.error_message_display);

            recyclerView=(RecyclerView)findViewById(R.id.rv_news);


            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            SCUtils.scheduleRefresh(this);

            //Set the RecyclerView Adapter
           recyclerView.setAdapter(adapter);




            loadonfirstrun();


        }


        private void loadonfirstrun()
        {
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            boolean data  = defaultSharedPreferences.getBoolean("data", true);
            if (data) {
              //  LoaderManager loaderManager=getLoaderManager().restartLoader(NEWS_LOADER,null,this).forceLoad();

                LoaderManager loaderManager=getSupportLoaderManager();
                loaderManager.restartLoader(NEWS_LOADER,null,this).forceLoad();
                SharedPreferences.Editor editor = defaultSharedPreferences.edit();
                editor.putBoolean("data", false);
                editor.commit();
            }
        }



        private void showErrorMessage(){


            mErrormessagedisplay.setVisibility(View.VISIBLE);

        }


        @Override protected void onStart() {
            super.onStart();
            //Initialize the scheduler
            SCUtils.scheduleRefresh(this);
        }



        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.main,menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int itemselected=item.getItemId();
            if(itemselected==R.id.action_search)
            {
                //Restart the LoaderManager
                LoaderManager loaderManager=getSupportLoaderManager();
                loaderManager.restartLoader(NEWS_LOADER,null,this).forceLoad();

            }
            else{

                showErrorMessage();
            }

            return super.onOptionsItemSelected(item);
        }

        //Implemented the methods for AsyncTask Loader and replaced with AsyncTask
        @Override
        public Loader<ArrayList<Void>> onCreateLoader(int id, Bundle args) {
            return new AsyncTaskLoader<ArrayList<Void>>(this) {

                ArrayList<NewsItem> newsoutput  = null;


                @Override
                public void onStartLoading() {
                    super.onStartLoading();


                         loadingindicator.setVisibility(View.VISIBLE);

                    }

                @Override
                public ArrayList<Void> loadInBackground() {

                    URL searchurl = NetworkUtils.buildURL();

                    try {
                        String results = NetworkUtils.getResponseFromHttpUrl(searchurl);

                        newsoutput=OpenNewsJsonUtils.getSimpleNewsStringsFromJson(MainActivity.this,results);

                        db=new DBHelper(this.getContext()).getWritableDatabase();

                        DBUtils.insertnews(db,newsoutput);

                    }
                        catch (Exception e)
                        {
                        e.printStackTrace();
                        }

                    return null;
                  }


        };
        }

        //onloadfinished gets information from the database and sets the adapter which is then used in recyclerview
        @Override
        public void onLoadFinished(Loader<ArrayList<Void>> loader, ArrayList<Void> data) {

            loadingindicator.setVisibility(View.INVISIBLE);



            db = new DBHelper(MainActivity.this).getReadableDatabase();
            cursor = DBUtils.getAllitems(db);
            adapter = new NewsAdapter(cursor, this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }


        @Override
        public void onLoaderReset(android.support.v4.content.Loader<ArrayList<Void>> loader) {

        }



        //Used to open a new intent when clicked on the particular newsitem
        @Override
        public void onItemClick(Cursor cursor, int clickedItemIndex) {
            cursor.moveToPosition(clickedItemIndex);
            String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_NEWS_URL));
            Log.d(TAG, String.format("Url %s", url));
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }

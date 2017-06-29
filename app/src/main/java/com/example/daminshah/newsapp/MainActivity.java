    package com.example.daminshah.newsapp;

    import android.content.Intent;
    import android.net.Uri;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.support.v7.app.AppCompatActivity;
    import android.support.v7.widget.LinearLayoutManager;
    import android.support.v7.widget.RecyclerView;
    import android.util.Log;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.ProgressBar;
    import android.widget.TextView;

    import com.example.daminshah.newsapp.Model.NewsItem;

    import java.net.URL;
    import java.util.ArrayList;

    public class MainActivity extends AppCompatActivity {
        static final String TAG = "mainactivity";

        private TextView mTextView;

        private ProgressBar loadingindicator;

        private TextView mErrormessagedisplay;

        private RecyclerView recyclerView;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


         //   mTextView=(TextView)findViewById(R.id.url_display);

            loadingindicator=(ProgressBar)findViewById(R.id.pb_loading_indicator);

            mErrormessagedisplay=(TextView)findViewById(R.id.error_message_display);

            recyclerView=(RecyclerView)findViewById(R.id.rv_news);


            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        }

//        private void showData()
//        {
//            mTextView.setVisibility(View.VISIBLE);
//
//            mErrormessagedisplay.setVisibility(View.INVISIBLE);
//        }

        private void search(){
           // URL newsurl=NetworkUtils.buildURL();
           // new FetchNews().execute();


            mErrormessagedisplay.setVisibility(View.INVISIBLE);
//            mTextView.setVisibility(View.VISIBLE);
            // mErrormessagedisplay.setVisibility(View.INVISIBLE);


        }

        private void showErrorMessage(){
          //  mTextView.setVisibility(View.INVISIBLE);

            mErrormessagedisplay.setVisibility(View.VISIBLE);

        }


        public class FetchNews extends AsyncTask<String,Void,ArrayList<NewsItem>>{


            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                Log.d(TAG,"Status"+loadingindicator.getProgress());
                loadingindicator.setVisibility(View.VISIBLE);
              //  mTextView.setVisibility(View.VISIBLE);

            }

            @Override
            protected ArrayList<NewsItem> doInBackground(String... params) {

                ArrayList<NewsItem> simpleJSONweatherdData=null;
                URL searchurl=NetworkUtils.buildURL();
                String results=null;
                try {
                    results=NetworkUtils.getResponseFromHttpUrl(searchurl);
                   // String[] simpleJSONweatherData=OpenNewsJsonUtils.getSimpleNewsStringsFromJson(MainActivity.this,results);
                        simpleJSONweatherdData=OpenNewsJsonUtils.getSimpleNewsStringsFromJson(MainActivity.this,results);
                    return simpleJSONweatherdData;
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(final ArrayList<NewsItem> s) {

                loadingindicator.setVisibility(View.INVISIBLE);

                if (s!=null && !s.equals("")) {
                    //showData();

                    NewsAdapter adapter=new NewsAdapter(s, new NewsAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(int clickedItemIndex) {
                            String url = s.get(clickedItemIndex).getUrl();
                            Log.d(TAG, String.format("Url %s", url));
                            openWebPage(url);
                        }
                    });
                   // mTextView.setText(s + "\n\n\n");
                 //   mTextView.setText("");
//                    for(NewsItem news: s)
//                    {
//                        mTextView.append(("TITLE:"+news.getTitle())+"\n"+ "DESCRIPTION:"+news.getDescription()+"\n"+"URL:"+news.getUrl()+
//                                "\n"+"DATE:"+news.getPublish()+
//
//
//                                "\n-------------------------------------------------\n\n");
//                    }


                    recyclerView.setAdapter(adapter);
                }
                else {
                    showErrorMessage();
                }
            }
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
               // mTextView.setText("");

                new FetchNews().execute();



                search();
                return true;
            }
            else{
                showErrorMessage();
            }

            return super.onOptionsItemSelected(item);
        }


        public void openWebPage(String url) {
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

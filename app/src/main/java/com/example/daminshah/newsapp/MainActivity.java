    package com.example.daminshah.newsapp;

    import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

    public class MainActivity extends AppCompatActivity {

        private TextView mTextView;

        private ProgressBar loadingindicator;

        private TextView mErrormessagedisplay;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            mTextView=(TextView)findViewById(R.id.url_display);

            loadingindicator=(ProgressBar)findViewById(R.id.pb_loading_indicator);

            mErrormessagedisplay=(TextView)findViewById(R.id.error_message_display);

        }

//        private void showData()
//        {
//            mTextView.setVisibility(View.VISIBLE);
//
//            mErrormessagedisplay.setVisibility(View.INVISIBLE);
//        }

        private void search(){
            URL newsurl=NetworkUtils.buildURL();
            new FetchNews().execute(newsurl);


            mErrormessagedisplay.setVisibility(View.INVISIBLE);
            mTextView.setVisibility(View.VISIBLE);
            // mErrormessagedisplay.setVisibility(View.INVISIBLE);


        }

        private void showErrorMessage(){
            mTextView.setVisibility(View.INVISIBLE);

            mErrormessagedisplay.setVisibility(View.VISIBLE);

        }


        public class FetchNews extends AsyncTask<URL,Void,String>{


            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                loadingindicator.setVisibility(View.VISIBLE);
              //  mTextView.setVisibility(View.VISIBLE);

            }

            @Override
            protected String doInBackground(URL... params) {

                URL searchurl=params[0];
                String results=null;
                try {
                    results=NetworkUtils.getResponseFromHttpUrl(searchurl);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                return results;
            }

            @Override
            protected void onPostExecute(String s) {

                loadingindicator.setVisibility(View.INVISIBLE);

                if (s!=null && !s.equals("")) {
                    //showData();


                    mTextView.setText(s + "\n\n\n");
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



                search();
                return true;
            }
            else{
                showErrorMessage();
            }

            return super.onOptionsItemSelected(item);
        }
    }

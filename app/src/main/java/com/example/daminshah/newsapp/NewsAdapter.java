package com.example.daminshah.newsapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daminshah.newsapp.Database.Contract;
import com.example.daminshah.newsapp.Model.NewsItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.daminshah.newsapp.R.layout.item;

/**
 * Created by daminshah on 6/26/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder> {

    private ArrayList<NewsItem> data;
    ItemClickListener listener;
    private Cursor cursor;
    private Context context;
    private final String TAG = "NewsAdapter";
    private int debug =10;

    public NewsAdapter(Cursor cursor, ItemClickListener listener) {

       // this.data=data;
        this.listener=listener;
        this.cursor=cursor;
    }

    //Interface for ItemclickListener
    public interface ItemClickListener {
        void onItemClick(Cursor cursor,int clickedItemIndex);
    }



    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

         context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        boolean shouldAttach=false;

        View view=inflater.inflate(item,parent,shouldAttach);
        ItemHolder  holder=new ItemHolder(view);
        return holder;


    }

    //Make a call to bind method

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {

        holder.bind(holder,position);

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    } //Items to display

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        //Fields required to be displayed
        TextView title;
        TextView description;
        TextView time;
         ImageView newsImageView;
        String desc;
        String url;
        String published;
        String articlename;
        long id;


        public ItemHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.news_title);
            description=(TextView)itemView.findViewById(R.id.news_desc);
            time=(TextView)itemView.findViewById(R.id.news_date);
            newsImageView=(ImageView) itemView.findViewById(R.id.image_view);
            itemView.setOnClickListener(this);
        }

        //Sets the TextViews and ImageView ,which gets the information from Database that is stored in Cursor
        public void bind(ItemHolder holder,int position){
           // debug++;
            //String d = String.valueOf(debug);
            cursor.moveToPosition(position);
            id=cursor.getLong(cursor.getColumnIndex(Contract.TABLE_ARTICLES._ID));
            articlename=cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE));
            desc=cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION));
            url=cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_NEWS_URL));
            published=cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED));

            String imageUrl=cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_IMAGE_URL));

//            NewsItem item=data.get(position);
//            title.setText("Description:"+item.getTitle());
//            description.setText(item.getDescription());
//            time.setText(item.getPublish());

            Log.d(TAG,"URL-----"+imageUrl);
            title.setText(articlename);
            description.setText(desc);
        //    Log.d(TAG,"Debug is:"+d);
            time.setText(published);


            //Loads the imageUrl into the ImageView and displays it using Picasso.
                Picasso.with(context).load(imageUrl).into(newsImageView);



        }


        //Gives the position and cursor of the item which was clicked
        @Override
        public void onClick(View v) {

            int pos=getAdapterPosition();
            listener.onItemClick(cursor,pos);
        }
    }
}

package com.example.daminshah.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daminshah.newsapp.Model.NewsItem;

import java.util.ArrayList;

/**
 * Created by daminshah on 6/26/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder> {

    private ArrayList<NewsItem> data;
    ItemClickListener listener;

    public NewsAdapter(ArrayList<NewsItem> data, ItemClickListener listener)
    {
        this.data=data;
        this.listener=listener;
    }

    public interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        boolean shouldAttach=false;

        View view=inflater.inflate(R.layout.item,parent,shouldAttach);
        ItemHolder  holder=new ItemHolder(view);
        return holder;


    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {

        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView description;
        TextView time;

        public ItemHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.news_title);
            description=(TextView)itemView.findViewById(R.id.news_desc);
            time=(TextView)itemView.findViewById(R.id.news_date);
            itemView.setOnClickListener(this);
        }

        public void bind(int position){
            NewsItem item=data.get(position);
            title.setText("Description:"+item.getTitle());
            description.setText(item.getDescription());
            time.setText(item.getPublish());
        }


        @Override
        public void onClick(View v) {

            int pos=getAdapterPosition();
            listener.onItemClick(pos);
        }
    }
}

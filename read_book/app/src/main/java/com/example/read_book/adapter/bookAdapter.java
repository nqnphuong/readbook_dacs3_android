package com.example.read_book.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.read_book.R;
import com.example.read_book.detail_book_screen;
import com.example.read_book.loadmore;
import com.example.read_book.model.Book;
import com.example.read_book.update_information_user_screen;

import java.util.List;


/**
 * Created by reale on 10/10/2017.
 */

public class bookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM=0,VIEW_TYPE_LOADING=1;
    loadmore loadMore;
    boolean isLoading;
    Activity activity;
    List<Book> items;
    int visibleThreshold=5;
    int lastVisibleItem,totalItemCount;

    public bookAdapter(RecyclerView recyclerView, Activity activity, List<Book> items) {
        this.activity = activity;
        this.items = items;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalItemCount <= (lastVisibleItem+visibleThreshold))
                {
                    if(loadMore != null)
                        loadMore.onLoadMore();
                    isLoading = true;
                }

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    public void setLoadMore(loadmore loadMore) {
        this.loadMore = loadMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM)
        {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_book, parent, false);
            return new ItemViewHolder(view);
        }
        else if(viewType == VIEW_TYPE_LOADING)
        {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_loading,parent,false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof  ItemViewHolder)
        {
            Book item = items.get(position);
            ItemViewHolder viewHolder = (ItemViewHolder) holder;

            viewHolder.txt_name_book.setText(items.get(position).getBookName());
            viewHolder.txt_author_book.setText(items.get(position).getBookAuthor());
            Glide.with(activity).load(items.get(position).getBookImage()).into(viewHolder.img_book_book);
            viewHolder.layout_book_itembook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, detail_book_screen.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("id_book",item.getId_book());
                    bundle.putSerializable("bookImage",item.getBookImage());
                    bundle.putSerializable("bookName",item.getBookName());
                    bundle.putSerializable("bookAuthor",item.getBookAuthor());
                    bundle.putSerializable("bookDescription",item.getBookDescription());
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                }
            });

        }
        else if(holder instanceof LoadingViewHolder)
        {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder)holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setLoaded() {
        isLoading = false;
    }


    class LoadingViewHolder extends RecyclerView.ViewHolder
    {

        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progressBar);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        public TextView txt_name_book,txt_author_book;
        public ImageView img_book_book;
        public LinearLayout layout_book_itembook;
        public ItemViewHolder(View itemView) {
            super(itemView);
            txt_name_book = (TextView)itemView.findViewById(R.id.txt_name_book);
            txt_author_book = (TextView)itemView.findViewById(R.id.txt_author_book);
            img_book_book = (ImageView)itemView.findViewById(R.id.img_book_book);
            layout_book_itembook = (LinearLayout) itemView.findViewById(R.id.layout_book_itembook);
        }
    }

}


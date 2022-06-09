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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.read_book.R;
import com.example.read_book.detail_book_screen;
import com.example.read_book.detail_createbook;
import com.example.read_book.loadmore;
import com.example.read_book.model.Book;

import java.util.List;

public class bookAdapter_createbook extends RecyclerView.Adapter<bookAdapter_createbook.ItemViewHolder3>{

    Context context;
    List<Book> book;

    public bookAdapter_createbook(Context context, List<Book> book) {
        this.book = book;
        this.context = context;
    }

    @Override
    public ItemViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_createbook, parent, false);
        return new ItemViewHolder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder3 holder, int position) {
        Book item = book.get(position);
        bookAdapter_createbook.ItemViewHolder3 viewHolder = (bookAdapter_createbook.ItemViewHolder3) holder;
        viewHolder.txt_name_book.setText(book.get(position).getBookName());
        viewHolder.txt_author_book.setText(book.get(position).getBookAuthor());
        Integer numchapter = book.get(position).getBookNumbook();
        viewHolder.txt_numchapter.setText(numchapter.toString());
        Glide.with(context).load(book.get(position).getBookImage()).into(viewHolder.img_book_book);
        viewHolder.layout_book_itembook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, detail_createbook.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("id_book",item.getId_book());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (book != null){
            return book.size();
        }
        return 0;
    }




    public class ItemViewHolder3 extends RecyclerView.ViewHolder{

        public TextView txt_name_book,txt_author_book,txt_numchapter;
        public ImageView img_book_book;
        public LinearLayout layout_book_itembook;
        public ItemViewHolder3(View itemView) {
            super(itemView);
            txt_name_book = (TextView)itemView.findViewById(R.id.txt_name_book);
            txt_author_book = (TextView)itemView.findViewById(R.id.txt_author_book);
            img_book_book = (ImageView)itemView.findViewById(R.id.img_book_book);
            txt_numchapter = (TextView)itemView.findViewById(R.id.txt_numchapter);
            layout_book_itembook = (LinearLayout) itemView.findViewById(R.id.layout_book_itembook);
        }
    }

}

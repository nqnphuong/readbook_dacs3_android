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
import com.example.read_book.loadmore;
import com.example.read_book.model.Book;

import java.util.List;

public class bookAdapter2 extends RecyclerView.Adapter<bookAdapter2.ItemViewHolder2>{

    Context context;
    List<Book> book;

    public bookAdapter2(Context context, List<Book> book) {
        this.book = book;
        this.context = context;
    }

    @Override
    public ItemViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new ItemViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder2 holder, int position) {
        Book item = book.get(position);
        bookAdapter2.ItemViewHolder2 viewHolder = (bookAdapter2.ItemViewHolder2) holder;
        viewHolder.txt_name_book.setText(book.get(position).getBookName());
        viewHolder.txt_author_book.setText(book.get(position).getBookAuthor());
        Glide.with(context).load(book.get(position).getBookImage()).into(viewHolder.img_book_book);
        viewHolder.layout_book_itembook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, detail_book_screen.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("id_book",item.getId_book());
                bundle.putSerializable("bookImage",item.getBookImage());
                bundle.putSerializable("bookName",item.getBookName());
                bundle.putSerializable("bookAuthor",item.getBookAuthor());
                bundle.putSerializable("bookDescription",item.getBookDescription());
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




    public class ItemViewHolder2 extends RecyclerView.ViewHolder{

        public TextView txt_name_book,txt_author_book;
        public ImageView img_book_book;
        public LinearLayout layout_book_itembook;
        public ItemViewHolder2(View itemView) {
            super(itemView);
            txt_name_book = (TextView)itemView.findViewById(R.id.txt_name_book);
            txt_author_book = (TextView)itemView.findViewById(R.id.txt_author_book);
            img_book_book = (ImageView)itemView.findViewById(R.id.img_book_book);
            layout_book_itembook = (LinearLayout) itemView.findViewById(R.id.layout_book_itembook);
        }
    }

}

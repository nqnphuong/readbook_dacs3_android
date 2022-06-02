package com.example.read_book.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.read_book.R;
import com.example.read_book.adapter.bookAdapter;
import com.example.read_book.book_for_category_screen;
import com.example.read_book.model.Category;

import java.io.Serializable;
import java.util.List;

public class categoryfindAdapter extends RecyclerView.Adapter<categoryfindAdapter.category_ViewHolder> {

    private final Context context;
    private List<Category> mListCategory;

    public categoryfindAdapter(Context mContext) {
        this.context = mContext;
    }

    public void setData(List<Category> list){
        this.mListCategory = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public category_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_find,parent,false);
        return new category_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull category_ViewHolder holder, int position) {
        Category category_model = mListCategory.get(position);
        if (category_model == null){
            return;
        }
        holder.txt_category_find.setText(category_model.getCategoryName());
        holder.layout_category_find_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickgotoDetail(category_model);
            }
        });
    }

    private void onClickgotoDetail(Category category) {
        Intent intent = new Intent(context, book_for_category_screen.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("name_category", category.getCategoryName());
        bundle.putSerializable("id_category", category.getId_category());
//        System.out.println(category.getCategoryName());
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (mListCategory != null){
            return mListCategory.size();
        }
        return 0;
    }

    public class category_ViewHolder extends RecyclerView.ViewHolder{
        private final TextView txt_category_find;
        private final LinearLayout layout_category_find_item;

        public category_ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_category_find = itemView.findViewById(R.id.txt_category_find);
            layout_category_find_item = itemView.findViewById(R.id.layout_category_find_item);

        }
    }

}
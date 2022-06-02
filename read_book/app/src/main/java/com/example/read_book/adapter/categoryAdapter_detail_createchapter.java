package com.example.read_book.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.read_book.R;
import com.example.read_book.api.api_category_book;
import com.example.read_book.book_for_category_screen;
import com.example.read_book.detail_createbook;
import com.example.read_book.model.Category;
import com.example.read_book.model.Category_book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class categoryAdapter_detail_createchapter extends RecyclerView.Adapter<categoryAdapter_detail_createchapter.category_ViewHolder> {

    private final Context context;
    private List<Category> mListCategory;
    private boolean status = false;

    public categoryAdapter_detail_createchapter(Context mContext) {
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
        if (category_model.getCategory_choose_color() == 1){
            holder.txt_category_find.setTextColor(0xFFAE8B76);
        } else {
            holder.txt_category_find.setTextColor(Color.BLACK);
        }
        System.out.println(category_model.getCategory_choose_color());
        holder.layout_category_find_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (-5338250 == holder.txt_category_find.getTextColors().getDefaultColor()){
                    status = true;
                } else {
                    status = false;
                }
                if (status == true){
                    System.out.println("Dang bat");
                    api_category_book.api_cate_bo.delete_category_book(category_model.getId_category(), category_model.getId_book())
                            .enqueue(new Callback<ArrayList>() {
                                @Override
                                public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                                    holder.txt_category_find.setTextColor(Color.BLACK);
                                    notifyDataSetChanged();
                                    Intent intent = new Intent(context,detail_createbook.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("id_book",category_model.getId_book());
                                    intent.putExtras(bundle);
                                    context.startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<ArrayList> call, Throwable t) {
                                    System.out.println(t.toString());
                                }
                            });


                }
                if (status == false) {
                    System.out.println("Khong bat");
                    api_category_book.api_cate_bo.category_book_create(category_model.getId_category(), category_model.getId_book())
                            .enqueue(new Callback<Category_book>() {
                                @Override
                                public void onResponse(Call<Category_book> call, Response<Category_book> response) {
                                    holder.txt_category_find.setTextColor(0xFFAE8B76);
                                    notifyDataSetChanged();
                                    Intent intent = new Intent(context,detail_createbook.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("id_book",category_model.getId_book());
                                    intent.putExtras(bundle);
                                    context.startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<Category_book> call, Throwable t) {
                                    System.out.println(t.toString());
                                }
                            });
                }
            }
        });
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
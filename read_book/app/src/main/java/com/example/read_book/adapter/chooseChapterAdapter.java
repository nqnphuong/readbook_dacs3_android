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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.read_book.R;
import com.example.read_book.detail_book_screen;
import com.example.read_book.model.Book;
import com.example.read_book.model.Chapter;
import com.example.read_book.read_book_chapter_screen;

import java.util.List;

public class chooseChapterAdapter extends RecyclerView.Adapter<chooseChapterAdapter.chapter_ViewHolder>{

    private List<Chapter> chapter_model;
    private Context context_model;

    public chooseChapterAdapter() {
    }

    public void setData(Context context, List<Chapter> chapter_model){
        this.context_model = context;
        this.chapter_model = chapter_model;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public chooseChapterAdapter.chapter_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_chapter, parent, false);
        return new chapter_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull chooseChapterAdapter.chapter_ViewHolder holder, int position) {
        final Chapter chapter = chapter_model.get(position);
        if (chapter == null){
            return;
        }
        holder.txt_title_choose_chapter.setText(chapter.getChapterTitle());
        holder.layout_choose_chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context_model, read_book_chapter_screen.class);
                Bundle bundle = new Bundle();
//                    public Chapter(Integer id_chapter, Integer id_book, Integer chapterNumberLove, String chapterTitle, String chapterContent) {
                bundle.putSerializable("id_chapter", chapter.getId_chapter());
                bundle.putSerializable("id_book_chapter", chapter.getId_book());
                bundle.putSerializable("chapterTitle", chapter.getChapterTitle());
                bundle.putSerializable("chapterContent", chapter.getChapterContent());
                intent.putExtras(bundle);
                context_model.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (chapter_model != null){
            return chapter_model.size();
        }
        return 0;
    }

    public class chapter_ViewHolder extends RecyclerView.ViewHolder{
        private final TextView txt_title_choose_chapter;
        private final LinearLayout layout_choose_chapter;
        public chapter_ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_choose_chapter = itemView.findViewById(R.id.layout_choose_chapter);
            txt_title_choose_chapter =itemView.findViewById(R.id.txt_title_choose_chapter);
        }
    }
}

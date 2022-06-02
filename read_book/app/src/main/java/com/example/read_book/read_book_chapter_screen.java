package com.example.read_book;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.read_book.adapter.bookAdapter;
import com.example.read_book.api.api_chapter;
import com.example.read_book.model.Book;
import com.example.read_book.model.Chapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class read_book_chapter_screen extends AppCompatActivity {
    private TextView txt_titleChapter_chapter, txt_contentChapter_chapter;
    private List<Chapter> mchapter;
    private ImageView btn_back_read_book_chapter, btn_next_read_book_chapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book_chapter_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        TypedArray styledAttributes = getTheme().obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        Drawable back= getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(back);

        txt_contentChapter_chapter = (TextView) findViewById(R.id.txt_contentChapter_chapter);
        txt_titleChapter_chapter = (TextView) findViewById(R.id.txt_titleChapter_chapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            System.out.println("Hello null rồi");
            Integer id_book = detail_book_screen.id_book;
            api_chapter.api_ch.show_all_chapter().enqueue(new Callback<List<Chapter>>() {
                @Override
                public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                    mchapter = response.body();
                    for (Chapter ch : mchapter)  {
                        if (ch.getId_book() == id_book){
                            txt_contentChapter_chapter.setText(ch.getChapterContent());
                            txt_titleChapter_chapter.setText(ch.getChapterTitle());
                            break;
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<Chapter>> call, Throwable t) {
                    System.out.println(t.toString());
                }
            });
        } else {
            Integer id_chapter = (Integer) bundle.get("id_chapter");
            txt_contentChapter_chapter.setText((String) bundle.get("chapterContent"));
            txt_titleChapter_chapter.setText((String) bundle.get("chapterTitle"));
        }

        btn_back_read_book_chapter = (ImageView) findViewById(R.id.btn_next_read_book_chapter);
        btn_back_read_book_chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
        btn_back_read_book_chapter = (ImageView) findViewById(R.id.btn_back_read_book_chapter);
        btn_back_read_book_chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.update_infor:
                Intent intent = new Intent(read_book_chapter_screen.this, update_information_user_screen.class);
                startActivity(intent);
            case R.id.create_book:
            case R.id.logout:
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
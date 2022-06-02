package com.example.read_book;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.read_book.api.api_book;
import com.example.read_book.api.api_chapter;
import com.example.read_book.model.Chapter;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class create_chapter_screen extends AppCompatActivity {
    private EditText txt_title_chapter_createchapter, txt_content_chapter_createchapter;
    private Button btn_delete_chapter, btn_save_chapter;
    private AlertDialog mDialog;
    public static int id_book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_chapter_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        TypedArray styledAttributes = getTheme().obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        Drawable back= getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(back);

        txt_title_chapter_createchapter = (EditText) findViewById(R.id.txt_title_chapter_createchapter);
        txt_content_chapter_createchapter = (EditText) findViewById(R.id.txt_content_chapter_createchapter);
        btn_delete_chapter = (Button) findViewById(R.id.btn_delete_chapter);
        btn_save_chapter = (Button) findViewById(R.id.btn_save_chapter);
        txt_title_chapter_createchapter.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        int id_chapter;
        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            id_chapter = -1;
        } else {
            id_chapter = (Integer)bundle.get("id_chapter");
            id_book = detail_createbook.id_book;
        }
        id_book = detail_createbook.id_book;
        if (id_chapter == -1){
            // create chapter
            btn_save_chapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = txt_title_chapter_createchapter.getText().toString();
                    String content = txt_content_chapter_createchapter.getText().toString();

                    api_chapter.api_ch.create_chapter(id_book, content, 0, title)
                            .enqueue(new Callback<Chapter>() {
                                @Override
                                public void onResponse(Call<Chapter> call, Response<Chapter> response) {
                                    Toast.makeText(getApplicationContext(),"Create chapter successfully" , Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<Chapter> call, Throwable t) {
                                    System.out.println(t.toString());
                                }
                            });
                }
            });
        } else {
            api_chapter.api_ch.read_by_id(id_chapter).enqueue(new Callback<List<Chapter>>() {
                @Override
                public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                    for (Chapter ch : response.body()){
                        String content = ch.getChapterContent();
                        String title = ch.getChapterTitle();
                        txt_content_chapter_createchapter.setText(content);
                        txt_title_chapter_createchapter.setText(title);
                        btn_save_chapter.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                api_chapter.api_ch.update_chapter(id_chapter, txt_content_chapter_createchapter.getText().toString(), txt_title_chapter_createchapter.getText().toString())
                                        .enqueue(new Callback<List<Chapter>>() {
                                            @Override
                                            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                                                Toast.makeText(getApplicationContext(),"Update chapter successfully" , Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFailure(Call<List<Chapter>> call, Throwable t) {
                                                System.out.println(t.toString());
                                            }
                                        });
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<List<Chapter>> call, Throwable t) {
                    System.out.println(t.toString());
                }
            });


        }


        btn_delete_chapter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Create AlertDialog here
                AlertDialog.Builder builder = new AlertDialog.Builder(create_chapter_screen.this);
                builder.setMessage("Are you sure delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Use mListRowPosition for clicked list row...
                                api_chapter.api_ch.delete_chapter(id_chapter).enqueue(new Callback<List<Chapter>>() {
                                    @Override
                                    public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                                        Toast.makeText(getApplicationContext(),"Delete chapter successfully" , Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<List<Chapter>> call, Throwable t) {
                                        System.out.println(t.toString());
                                        Toast.makeText(getApplicationContext(),"Save chapter successfully" , Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(create_chapter_screen.this, detail_createbook.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.dismiss();
                            }
                        });
                // Create the AlertDialog object
                mDialog = builder.create();
                mDialog.show();
            }
        });

    }


    private void getInforChapter(){

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(create_chapter_screen.this, detail_createbook.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
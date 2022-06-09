package com.example.read_book;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.read_book.api.api_book;
import com.example.read_book.model.Book;
import com.example.read_book.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detail_createbook_screeen extends AppCompatActivity {
    private Button btn_create_book;
    private EditText txt_description_book_detailcreatebook, txt_title_book_detailcreatebook;
    private LinearLayout layout_bookcover_createbook;
    private ImageView img_book_book;

    private static final int MY_REQUEST_CODE = 10;
    public static final String TAG = update_information_user_screen.class.getName();
    private Uri mUri;
    private ProgressDialog mProgressDialog;

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result){
                    Log.e(TAG, "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if (data == null){
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            img_book_book.setImageBitmap(bitmap);
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_createbook_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        TypedArray styledAttributes = getTheme().obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        Drawable back= getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(back);

        mProgressDialog = new ProgressDialog(detail_createbook_screeen.this);
        mProgressDialog.setMessage("Please wait ...");

        layout_bookcover_createbook = (LinearLayout) findViewById(R.id.layout_bookcover_createbook);
        img_book_book = (ImageView) findViewById(R.id.img_book_book);
        txt_title_book_detailcreatebook = (EditText) findViewById(R.id.txt_title_book_detailcreatebook);
        txt_description_book_detailcreatebook = (EditText) findViewById(R.id.txt_description_book_detailcreatebook);
        txt_description_book_detailcreatebook.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        btn_create_book = (Button) findViewById(R.id.btn_create_book);

//
        btn_create_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.show();
                String title = txt_title_book_detailcreatebook.getText().toString();
                String des = txt_description_book_detailcreatebook.getText().toString();
                Integer id_user = login_screen.id_user;

                RequestBody requestBodytitle = RequestBody.create(MediaType.parse("multipart/form-data"),title);
                RequestBody requestBodydes = RequestBody.create(MediaType.parse("multipart/form-data"),des);

                String strRealPath = RealPathUtil.getRealPath(detail_createbook_screeen.this,mUri);
                Log.e("Trang thai duong dan", strRealPath);
                File file = new File(strRealPath);
                RequestBody imgava = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part imgMultipartBody = MultipartBody.Part.createFormData("bookImage",file.getName(),imgava);

                api_book.api_bo.create_book(requestBodytitle, imgMultipartBody, id_user, requestBodydes)
                        .enqueue(new Callback<Book>() {
                    @Override
                    public void onResponse(Call<Book> call, Response<Book> response) {
                        Toast.makeText(getApplicationContext(),"Create book successfully" , Toast.LENGTH_SHORT).show();
                        mProgressDialog.cancel();
                        Intent intent = new Intent(detail_createbook_screeen.this, create_book.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Book> call, Throwable t) {
                        System.out.println(t.toString());
                        mProgressDialog.cancel();
                    }
                });
            }
        });

        img_book_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLickRequestPermission();
            }
        });

    }

    private void onCLickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        } else {
            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, create_book.class);
                startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
package com.example.read_book;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.read_book.api.api_user;
import com.example.read_book.model.User;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.transform.Result;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class update_information_user_screen extends AppCompatActivity {

    private EditText txt_pass_infor_update, txt_name_infor_update, txt_dob_infor_update, txt_phonenum_infor_update, txt_description_infor_update;
    private TextView txt_email_infor_update;
    private Button btn_update_update_infor_us;
    private de.hdodenhof.circleimageview.CircleImageView img_avatar_infor_update;

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
                            img_avatar_infor_update.setImageBitmap(bitmap);
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
            }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_information_user_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        TypedArray styledAttributes = getTheme().obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        Drawable back= getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(back);


        txt_email_infor_update = (TextView) findViewById(R.id.txt_email_infor_update);
        txt_pass_infor_update = (EditText) findViewById(R.id.txt_pass_infor_update);
        txt_name_infor_update = (EditText) findViewById(R.id.txt_name_infor_update);
        txt_dob_infor_update = (EditText) findViewById(R.id.txt_dob_infor_update);
        txt_phonenum_infor_update = (EditText) findViewById(R.id.txt_phonenum_infor_update);
        txt_description_infor_update = (EditText) findViewById(R.id.txt_description_infor_update);
        txt_description_infor_update.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        img_avatar_infor_update = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.img_avatar_infor_update);

        Integer id_user = login_screen.id_user;
        System.out.println(id_user);
        show_infor_user(id_user);

        img_avatar_infor_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLickRequestPermission();
            }
        });


        mProgressDialog = new ProgressDialog(update_information_user_screen.this);
        mProgressDialog.setMessage("Please wait ...");
        btn_update_update_infor_us = (Button) findViewById(R.id.btn_update_update_infor_us);
        btn_update_update_infor_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.show();
                String test_dob = txt_dob_infor_update.getText().toString();
                char c[] = test_dob.toCharArray();
                if (c.length !=10 ){
                    Toast.makeText(getApplicationContext(),"Wrong date of birth format" , Toast.LENGTH_SHORT).show();
                }
                if (c[2] != '/' || c[5] != '/'){
                    Toast.makeText(getApplicationContext(),"Wrong date of birth format" , Toast.LENGTH_SHORT).show();
                } else {
                    String email = txt_email_infor_update.getText().toString().trim();
                    String pass = txt_pass_infor_update.getText().toString().trim();
                    String name = txt_name_infor_update.getText().toString().trim();
                    String dob = txt_dob_infor_update.getText().toString().trim();
                    String phonenum = txt_phonenum_infor_update.getText().toString().trim();
                    String description = txt_description_infor_update.getText().toString().trim();
                    String strRealPath = RealPathUtil.getRealPath(update_information_user_screen.this,mUri);
                    Log.e("Trang thai duong dan", strRealPath);
                    File file = new File(strRealPath);
                    RequestBody imgava = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part imgMultipartBody = MultipartBody.Part.createFormData("userImage1",file.getName(),imgava);
                    RequestBody requestBodyemail = RequestBody.create(MediaType.parse("multipart/form-data"),email);
                    RequestBody requestBodypass = RequestBody.create(MediaType.parse("multipart/form-data"),pass);
                    RequestBody requestBodyname = RequestBody.create(MediaType.parse("multipart/form-data"),name);
                    RequestBody requestBodydob = RequestBody.create(MediaType.parse("multipart/form-data"),dob);
                    RequestBody requestBodyphonenum = RequestBody.create(MediaType.parse("multipart/form-data"),phonenum);
                    RequestBody requestBodydescription = RequestBody.create(MediaType.parse("multipart/form-data"),description);

                    api_user.api_us.update_user(id_user, requestBodyemail, requestBodypass, requestBodyname, requestBodydob, imgMultipartBody,requestBodyphonenum, requestBodydescription)
                            .enqueue(new Callback<List<User>>() {
                                @Override
                                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                                    Toast.makeText(getApplicationContext(),"Update information successfully" , Toast.LENGTH_SHORT).show();
                                    mProgressDialog.cancel();
                                    show_infor_user(id_user);
                                }

                                @Override
                                public void onFailure(Call<List<User>> call, Throwable t) {
                                    System.out.println(t.toString());
                                    mProgressDialog.cancel();
                                }
                            });

                }

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

    public void show_infor_user(Integer id_user){

        api_user.api_us.user_readByID(id_user).enqueue(new Callback<List<User>>() {
        @Override
        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
            assert response.body() != null;
            for (User user : response.body()){
                System.out.println(user);
                Glide.with(update_information_user_screen.this).load(user.getUserImage1()).into(img_avatar_infor_update);
                txt_email_infor_update.setText(user.getUserEmail());
                txt_pass_infor_update.setText(user.getUserPassword());
                txt_name_infor_update.setText(user.getUserFirstname());
                txt_dob_infor_update.setText(user.getUserDayofbirth());
                txt_phonenum_infor_update.setText(user.getUserPhone());
                txt_description_infor_update.setText(user.getUserDescription());
            }
        }

        @Override
        public void onFailure(Call<List<User>> call, Throwable t) {
            System.out.println(t.toString());
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
                Intent intent = new Intent(this, update_information_user_screen.class);
                startActivity(intent);
            case R.id.create_book:
            case R.id.logout:
        }

        return super.onOptionsItemSelected(item);
    }
}
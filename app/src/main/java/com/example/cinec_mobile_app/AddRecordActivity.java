package com.example.cinec_mobile_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddRecordActivity extends AppCompatActivity {

    private ImageView mImageView;
    private EditText title;
    private EditText description;
    private Button btnAddNewBlog;
    private Databasehelper databasehelper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog);

        mImageView = findViewById(R.id.personImage);
        title = findViewById(R.id.blogTitle);
        description = findViewById(R.id.blogDescription);
        btnAddNewBlog = findViewById(R.id.addNewBlogButton);

        context = this;
        databasehelper = new Databasehelper(context);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                startActivityForResult(Intent.createChooser(intent,"Pick an image"),1);
            }
        });



        btnAddNewBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  mImageView = (ImageView) findViewById(R.id.personImage);
                String userTitle = title.getText().toString();
                String userDesc = description.getText().toString();
                long blogCreateTime = System.currentTimeMillis();

                BlogModal blog = new BlogModal(userTitle,userDesc,blogCreateTime);
             if (!(userTitle.isEmpty() || userDesc.isEmpty())) {
                 long val = databasehelper.addNewBlog(blog);
                 if (val > 0){
                     Toast.makeText(AddRecordActivity.this,"Blog created successfully", Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(context,HomeActivity.class));
                 }else {
                     Toast.makeText(AddRecordActivity.this,"Blog creating error occur", Toast.LENGTH_SHORT).show();
                 }
             }else{
                 Toast.makeText(AddRecordActivity.this,"Please check the fields", Toast.LENGTH_SHORT).show();
             }


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK &&  requestCode ==1){
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                mImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
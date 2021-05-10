package com.example.cinec_mobile_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditBlog extends AppCompatActivity {

    private EditText edtTitle;
    private EditText edtDescription;
    private Button edtBtnBlog;
    private Databasehelper databasehelper;
    private Context context;
    private Long dateBlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_blog);

        context = this;
        databasehelper = new Databasehelper(context);

        edtTitle = findViewById(R.id.editBlogTitle);
        edtDescription = findViewById(R.id.editBlogDescription);
        edtBtnBlog = findViewById(R.id.editNewBlogButton);

        final String id = getIntent().getStringExtra("id");
       BlogModal blgModal =  databasehelper.getSingleBlog(Integer.parseInt(id));
       edtTitle.setText(blgModal.getTitle());
       edtDescription.setText(blgModal.getDescription());

       edtBtnBlog.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String titleEdt = edtTitle.getText().toString();
               String desEdt = edtDescription.getText().toString();
               dateBlog = System.currentTimeMillis();

               BlogModal bgModal = new BlogModal(Integer.parseInt(id),titleEdt,desEdt,dateBlog);
               int stats = databasehelper.updateSingleBlog(bgModal);
               if (stats > 0){
                   startActivity(new Intent(context,HomeActivity.class));
               }
           }
       });


    }
}
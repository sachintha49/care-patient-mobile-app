package com.example.cinec_mobile_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ListView listViewBlog;
    private Button btnAddBlog;
    Databasehelper databasehelper;
    private List<BlogModal> allBlogs;
    Context context;
    private BlogAdapter blogAdapter;
    EditText edtTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home3);
        context = this;
        databasehelper = new Databasehelper(context);

        edtTxt = findViewById(R.id.searchFilter);
        listViewBlog = findViewById(R.id.viewBlogList);
        btnAddBlog = findViewById(R.id.btnAddNewBlog);
        allBlogs = new ArrayList<>();

        allBlogs = databasehelper.getAllBlog(); // getting all blogs detail into allBlogs arrayList
        blogAdapter = new BlogAdapter(context, R.layout.single_blog, allBlogs);
        listViewBlog.setAdapter(blogAdapter);

        edtTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txt = edtTxt.getText().toString();
                if (txt.length() > 0) {
                    List<BlogModal> tempList = new ArrayList<>();
                    for (BlogModal g : allBlogs){
                        if (g.getTitle().startsWith(txt)){
                            tempList.add(g);
                            blogAdapter = new BlogAdapter(context, R.layout.single_blog, tempList);
                            listViewBlog.setAdapter(blogAdapter);
                        }else{
                            System.out.println("samana nehe");
                        }
                    }
                }else {
                    blogAdapter = new BlogAdapter(context, R.layout.single_blog, allBlogs);
                    listViewBlog.setAdapter(blogAdapter);
                }

                /*if (txt.length() > 0){
                    for (int i = 0; i < allBlogs.size(); i++) {
                        if (allBlogs.get(i).getTitle().startsWith(txt)){
                            arrayBlogTemp.add(allBlogs.get(i));
                        }
                        listViewBlog.setAdapter(new BlogAdapter(context,R.layout.single_blog,arrayBlogTemp));
                    }
                    // listViewBlog.setAdapter(blogAdapter);
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnAddBlog.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,AddRecordActivity.class));
            }
        });

        listViewBlog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                final BlogModal blogModal = allBlogs.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Blog title "+blogModal.getTitle());
                builder.setMessage(blogModal.getDescription());

                /*builder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });*/
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databasehelper.deleteBlog(blogModal.getId());
                        startActivity(new Intent(context,HomeActivity.class));
                    }
                });
                builder.setNeutralButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(context,EditBlog.class);
                        intent.putExtra("id",String.valueOf(blogModal.getId()));
                        startActivity(intent);

                    }
                });
                builder.show();
            }
        });
    }
}
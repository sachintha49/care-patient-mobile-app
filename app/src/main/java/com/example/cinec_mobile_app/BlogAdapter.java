package com.example.cinec_mobile_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class BlogAdapter extends ArrayAdapter<BlogModal> {

    private Context context;
    private int resource;
    List<BlogModal> blogs;

    public BlogAdapter(Context context, int resource, List<BlogModal> blogs) {
        super(context, resource, blogs);
        this.context = context;
        this.resource = resource;
        this.blogs = blogs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(resource,parent,false);

        TextView title = row.findViewById(R.id.title);
        TextView description = row.findViewById(R.id.description);

        BlogModal blogModal = blogs.get(position);
        title.setText(blogModal.getTitle());
        description.setText(blogModal.getDescription());

        return row;
    }
}

package com.example.cinec_mobile_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import java.util.ArrayList;
import java.util.List;


public class Databasehelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "register.db";
    public static final int VERSION = 3;

    //Registration table here
    public static final String TABLE_NAME = "registeruser";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "username";
    public static final String COL_3 = "password";

    //Blog table here
    public static final String TABLE_BLOG = "blogTable";
    public static final String BLOG_ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String BLOG_DATE = "blogDate";


    public Databasehelper(Context context) {

        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE blogTable (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, description TEXT NOT NULL, blogDate TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOG);
        onCreate(sqLiteDatabase);
    }

    public long addUser(String user, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user);
        contentValues.put("password", password);
        long res = db.insert("registeruser", null, contentValues);
        db.close();
        return res;
    }

    public boolean checkUser(String username, String password) {
        String[] columns = {COL_1};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }


    public long addNewBlog(BlogModal blogModal) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("title", blogModal.getTitle());
        contentValues.put("description", blogModal.getDescription());
        System.out.println("desctionasdfffffffffffffffff" + blogModal.getDescription());
        contentValues.put("blogDate", blogModal.getBlogDate());

        //save to table
        long resp = sqLiteDatabase.insert("blogTable", null, contentValues);
        sqLiteDatabase.close();
        return resp;

    }

    /*select all blogs from the database*/
    public List<BlogModal> getAllBlog() {

        List<BlogModal> blogList = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_BLOG;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                BlogModal oneBlog = new BlogModal();

                oneBlog.setId(cursor.getInt(0));
                oneBlog.setTitle(cursor.getString(1));
                oneBlog.setDescription(cursor.getString(2));
                oneBlog.setBlogDate(cursor.getLong(3));

                blogList.add(oneBlog);
                System.out.println(blogList);
            } while (cursor.moveToNext());
        }
        return blogList;
    }

public void deleteBlog(int id){
        SQLiteDatabase db = getWritableDatabase();
    db.delete(TABLE_BLOG, "id =?", new String[]{String.valueOf(id)});
    db.close();
}

public BlogModal getSingleBlog(int id){
    SQLiteDatabase sqLiteDatabase = getWritableDatabase();

    Cursor cursor = sqLiteDatabase.query(TABLE_BLOG, new String[]{BLOG_ID, TITLE, DESCRIPTION, BLOG_DATE},
            BLOG_ID + "= ?", new String[]{String.valueOf(id)},
            null, null, null);

    BlogModal blogModal;
    if (cursor != null) {
        cursor.moveToFirst();
        blogModal  = new BlogModal(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getLong(3)
        );
        return blogModal;
    }
    return null;
}

     public int updateSingleBlog(BlogModal blogModal){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

         contentValues.put("id", blogModal.getId());
         contentValues.put("title", blogModal.getTitle());
         contentValues.put("description", blogModal.getDescription());
         contentValues.put("blogDate", blogModal.getBlogDate());

         int status = db.update(TABLE_BLOG,contentValues, BLOG_ID +" =?",
                 new String[]{String.valueOf(blogModal.getId())});
         db.close();
         return status;
     }

}

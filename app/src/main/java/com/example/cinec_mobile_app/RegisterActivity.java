package com.example.cinec_mobile_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    Databasehelper db;
    EditText mTextUsername;
    EditText mTextPassword;
    EditText getTextCnfPassword;
    Button mButtonRegister;
    TextView mTextViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new Databasehelper(this);
        mTextUsername = (EditText)findViewById(R.id.edittext_username);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        getTextCnfPassword = (EditText)findViewById(R.id.edittext_cnf_password);
        mButtonRegister = (Button)findViewById(R.id.button_register);
        mTextViewLogin = (TextView)findViewById(R.id.textview_register);

        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent loginIntent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(loginIntent);
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                String cnf_pwd = getTextCnfPassword.getText().toString().trim();

                if (user.isEmpty()){
                    Toast.makeText(RegisterActivity.this,"User name is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (user.length() < 8){
                    Toast.makeText(RegisterActivity.this,"Please enter more than 8 characters for username ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(user.matches("[A-Za-z0-9.]+"))) {
                    Toast.makeText(RegisterActivity.this,"Please enter only characters and numbers", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pwd.isEmpty()){
                    Toast.makeText(RegisterActivity.this,"Password is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pwd.length() < 8){
                    Toast.makeText(RegisterActivity.this,"Characters should be more than 8", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (true){
                    if (pwd.equals(cnf_pwd)){

                        long val = db.addUser(user,pwd);
                        if (val > 0){
                            Toast.makeText(RegisterActivity.this,"You are registered", Toast.LENGTH_SHORT).show();
                            Intent moveToLogin = new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(moveToLogin);
                        } else {
                            Toast.makeText(RegisterActivity.this,"Registration error", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(RegisterActivity.this,"Password is not match", Toast.LENGTH_SHORT).show();
                    }
                }else {


                }

            }
        });





    }
}
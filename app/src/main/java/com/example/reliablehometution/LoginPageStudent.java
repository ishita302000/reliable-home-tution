package com.example.reliablehometution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginPageStudent extends AppCompatActivity {

    private TextView signUp_LoginPageStudent;
    private TextView login_with_phoneno_LoginPageStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page_student);

        signUp_LoginPageStudent = findViewById(R.id.slogan_name_student);
       login_with_phoneno_LoginPageStudent = findViewById(R.id.phnno_log_student);


        signUp_LoginPageStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(LoginPageStudent.this,SignUpStudent.class));
            }
        });

        login_with_phoneno_LoginPageStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(LoginPageStudent.this,LoginStudentPhone.class));
            }
        });
    }
}
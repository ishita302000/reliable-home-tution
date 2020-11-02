package com.example.reliablehometution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginPageTeacher extends AppCompatActivity {

    private TextView signUp_LoginPageTeacher;
    private TextView login_with_phoneno_LoginPageTeacher;
    private Button loginnext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page_teacher);

        signUp_LoginPageTeacher = findViewById(R.id.slogan_name_teacher);
        login_with_phoneno_LoginPageTeacher = findViewById(R.id.phnno_log_teacher);


        signUp_LoginPageTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(LoginPageTeacher.this,SignUpTeacher.class));
            }
        });

        login_with_phoneno_LoginPageTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(LoginPageTeacher.this,LoginTeacherPhone.class));
            }
        });
        loginnext = findViewById(R.id.login_teacher);
        loginnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPageTeacher.this , form_teacher.class));
            }
        });


    }

}
package com.example.reliablehometution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginPageParent extends AppCompatActivity {

    private TextView signUp_LoginPageParent;
    private TextView login_with_phoneno_LoginPageParent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page_parent);

        signUp_LoginPageParent = findViewById(R.id.slogan_name_parent);
        login_with_phoneno_LoginPageParent = findViewById(R.id.phnno_log_parent);

        signUp_LoginPageParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(LoginPageParent.this,SignUpParent.class));
            }
        });

        login_with_phoneno_LoginPageParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(LoginPageParent.this,LoginParentPhone.class));
            }
        });
    }
}
package com.example.reliablehometution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class LOGOUTforParent extends AppCompatActivity {

    private Button log_out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l_o_g_o_u_tfor_parent);

        log_out = findViewById(R.id.button_logout);

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity( new Intent(LOGOUTforParent.this,MainActivity.class));
                finish();
            }
        });
    }
}
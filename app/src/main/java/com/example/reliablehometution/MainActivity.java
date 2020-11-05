package com.example.reliablehometution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private ImageView teacherimage;
    private ImageView studentimage;
    private ImageView parentimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teacherimage = (ImageView) findViewById(R.id.teacherImage);
        studentimage = (ImageView) findViewById(R.id.childrenImage);
        parentimage = (ImageView) findViewById(R.id.parentImage);

        teacherimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent_teacher = new Intent(MainActivity.this, LoginPageTeacher.class);
                startActivity(intent_teacher);

            }
        });

        studentimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent_student = new Intent(MainActivity.this, LoginPageStudent.class);
                startActivity(intent_student);
            }
        });

        parentimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent_parent = new Intent(MainActivity.this, LoginPageParent.class);
                startActivity(intent_parent);
            }
        });
    }
}
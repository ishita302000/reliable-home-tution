package com.example.reliablehometution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView teacherimage;
    private ImageView studentimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teacherimage = (ImageView) findViewById(R.id.teacherImage);
        studentimage = (ImageView) findViewById(R.id.childrenImage);
        teacherimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_teacher = new Intent(MainActivity.this, LoginPageTeacher.class);
                startActivity(intent_teacher);

            }
        });

        studentimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_student = new Intent(MainActivity.this, LoginPageStudent.class);
                startActivity(intent_student);
            }
        });
    }
}
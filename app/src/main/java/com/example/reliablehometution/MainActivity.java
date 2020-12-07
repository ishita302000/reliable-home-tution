package com.example.reliablehometution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private MaterialCardView teacherCardView;
    private MaterialCardView studentCardView;
    private MaterialCardView parentCardView;
    
    private Animation animation1;
    private Animation animation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teacherCardView = findViewById(R.id.teacherCardView);
        studentCardView = findViewById(R.id.studentCardView);
        parentCardView = findViewById(R.id.parentCardView);

        animation1 = AnimationUtils.loadAnimation(this,R.anim.from_left);
        teacherCardView.setAnimation(animation1);

        animation2 = AnimationUtils.loadAnimation(this,R.anim.from_right);
        studentCardView.setAnimation(animation2);

        animation1 = AnimationUtils.loadAnimation(this,R.anim.from_left);
        parentCardView.setAnimation(animation1);

        teacherCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent_teacher = new Intent(MainActivity.this, LoginPageTeacher.class);
                startActivity(intent_teacher);

            }
        });

        studentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent_student = new Intent(MainActivity.this, LoginPageStudent.class);
                startActivity(intent_student);
            }
        });

        parentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent_parent = new Intent(MainActivity.this, LoginPageParent.class);
                startActivity(intent_parent);
            }
        });
    }
}
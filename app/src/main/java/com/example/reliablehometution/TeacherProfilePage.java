package com.example.reliablehometution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TeacherProfilePage extends AppCompatActivity {

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile_page);

        drawerLayout=findViewById(R.id.drawerlayout_teacher);
        navigationView = findViewById(R.id.navView_teacher);
        TextView name = findViewById(R.id.t_name);
        TextView age = findViewById(R.id.t_age);

        View nav_header = navigationView.getHeaderView(0);
         TextView nav_name_t = (TextView)nav_header.findViewById(R.id.nav_name_t);

        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user=fAuth.getCurrentUser();
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DocumentReference docref = db.collection("PERSONAL_DETAILS_TEACHER").document(user.getUid());
        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
              if(task.isSuccessful())
              {
                  DocumentSnapshot document = task.getResult();
                  if(document.exists())
                  {
                      age.setText(document.getString("age"));
                  }
              }
              else
              {
                  Log.d("info" , "get failed with" , task.getException());
              }
            }
        });
        DocumentReference docRef = db.collection("TEACHER").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        name.setText(document.getString("name"));
                        nav_name_t.setText(document.getString("name"));
                        //Log.d("info", "No such document");
                    }
                }
                else
                {
                    Log.d("info", "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
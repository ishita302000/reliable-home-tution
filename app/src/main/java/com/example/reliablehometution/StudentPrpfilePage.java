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
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentPrpfilePage extends AppCompatActivity {

   private ActionBarDrawerToggle toggle;
   private DrawerLayout drawerLayout;
   private NavigationView navigationView;
   private FirebaseFirestore db;
   private FirebaseUser user;
   private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_prpfile_page);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navView);

        TextView age = (TextView)findViewById(R.id.age);

        TextView blood_group = (TextView)findViewById(R.id.bloodgroup);
        TextView class1 = (TextView)findViewById(R.id.class1);
       TextView name = (TextView) findViewById(R.id.name);

         navigationView  = findViewById(R.id.navView);
         View nav_header = navigationView.getHeaderView(0);
        TextView nav_name = (TextView) nav_header.findViewById(R.id.nav_name);

        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DocumentReference docRef = db.collection("STUDENT_PERSONAL_DETAILS").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                       // Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        age.setText(document.getString("age"));

                        blood_group.setText(document.getString("blood group"));
                        class1.setText(document.getString("class"));

                        //Log.d("info", "No such document");
                    }
                } else {
                    Log.d("info", "get failed with ", task.getException());
                }
            }
        });
        DocumentReference docRef1 = db.collection("STUDENT").document(user.getUid());
        docRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                       name.setText(document.getString("name"));
                       nav_name.setText(document.getString("name"));
                        //Log.d("info", "No such document");
                    }
                } else {
                    //Toast.makeText(StudentPrpfilePage.this, "get failed with ",Toast.LENGTH_SHORT).show();
                    Log.d("info", "get failed with ", task.getException());
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
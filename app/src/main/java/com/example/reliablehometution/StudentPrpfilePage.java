package com.example.reliablehometution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
        TextView address = (TextView)findViewById(R.id.address);
        TextView age = (TextView)findViewById(R.id.age);
        TextView birth = (TextView)findViewById(R.id.birth);
        TextView blood_group = (TextView)findViewById(R.id.bloodgroup);
        TextView class1 = (TextView)findViewById(R.id.class1);
        TextView gender = (TextView)findViewById(R.id.gender);
        TextView percentage = (TextView)findViewById(R.id.percentage);
        TextView school_board = (TextView)findViewById(R.id.school_board);
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
                        address.setText(document.getString("address"));
                        age.setText(document.getString("age"));
                        birth.setText(document.getString("birth"));
                        blood_group.setText(document.getString("blood group"));
                        class1.setText(document.getString("class"));
                        gender.setText(document.getString("gender"));
                        percentage.setText(document.getString("percentage"));
                        school_board.setText(document.getString("school board"));
                        //Log.d("info", "No such document");
                    }
                } else {
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
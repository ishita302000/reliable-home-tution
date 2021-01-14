package com.example.reliablehometution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentPrpfilePage extends AppCompatActivity {

   private ActionBarDrawerToggle toggle;
   private DrawerLayout drawerLayout;
   private NavigationView navigationView;
   private FirebaseFirestore db;
   private FirebaseUser user;
   private FirebaseAuth fAuth;
   private StorageReference mStoragereference;
   private  String name_of_logined_user;
   private String filename;
   private List<StudentTeacherTimetable> arraylist = new ArrayList<>();
   private StudentTimetableAdapterView studentTimetableAdapterView;
  private int checker = 0;

    public StudentPrpfilePage() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile_page);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navView);

        TextView age = (TextView)findViewById(R.id.age);

        TextView blood_group = (TextView)findViewById(R.id.bloodgroup);
        TextView class1 = (TextView)findViewById(R.id.class1);
       TextView name = (TextView) findViewById(R.id.name);


        View nav_header = navigationView.getHeaderView(0);
        TextView nav_name = (TextView) nav_header.findViewById(R.id.nav_name);
        ImageView navigation_profile = (ImageView) nav_header.findViewById(R.id.imageView10);

        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);


        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.feedback :
                        startActivity(new Intent(StudentPrpfilePage.this,feedbacks.class));
                        break;
                    case R.id.logout :
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(StudentPrpfilePage.this,LoginPageStudent.class));
                        finish();
                        break;
                    case R.id.contact :
                        startActivity(new Intent(StudentPrpfilePage.this,contacts.class));
                        break;
                    case R.id.profile :
                        startActivity(new Intent(StudentPrpfilePage.this,StudentDetails.class));
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
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
                         name_of_logined_user = document.getString("name");
                       name.setText(name_of_logined_user);
                      //filena me = "STUDENT/"+"+-"+document.getString("name");
                        mStoragereference = FirebaseStorage.getInstance().getReference().child("STUDENT/+-"+document.getString("name"));
                        try {
                            final File file = File.createTempFile("image", "jpg");
                            mStoragereference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                    ((ImageView)findViewById(R.id.imageView_profile)).setImageBitmap(bitmap);
                                    navigation_profile.setImageBitmap(bitmap);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                       nav_name.setText(document.getString("name"));
                        //Log.d("info", "No such document");
                    }
                }
                else {
                    //Toast.makeText(StudentPrpfilePage.this, "get failed with ",Toast.LENGTH_SHORT).show();
                    Log.d("info", "get failed with ", task.getException());
                }
            }
        });


        db.collection("STUDENT_TEACHER_TIMETABLE").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> b=document.getData();
                        Object c = b.get("student");
                        String k = (String) c;
                       if(k.equals(nav_name.getText() ))
                           arraylist.add(new StudentTeacherTimetable((String)b.get("teacher")));
                        //Toast.makeText(other.this,"size"+(String)c,Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(StudentPrpfilePage.this,"size"+arraylist.size(),Toast.LENGTH_SHORT).show();
                    RecyclerView recyclerView_timetable = findViewById(R.id.recycler_view_timetable);
                    studentTimetableAdapterView = new StudentTimetableAdapterView(arraylist,StudentPrpfilePage.this);
                    RecyclerView.LayoutManager mLayoutManager =
                            new LinearLayoutManager(StudentPrpfilePage.this);
                    recyclerView_timetable.setLayoutManager(mLayoutManager);
                    recyclerView_timetable.setItemAnimator(new DefaultItemAnimator());
                    recyclerView_timetable.addItemDecoration(new DividerItemDecoration(StudentPrpfilePage.this, LinearLayoutManager.VERTICAL));
                    recyclerView_timetable.setAdapter(studentTimetableAdapterView);
                }
                else {
                    Toast.makeText(StudentPrpfilePage.this,"failed to retrive data",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(StudentPrpfilePage.this,StudentPrpfilePage.class));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
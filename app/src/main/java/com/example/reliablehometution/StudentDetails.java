package com.example.reliablehometution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class StudentDetails extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth fAuth;
    private TextView address;
    private TextView age;
    private TextView schoolboard;
    private TextView shoolname;
    private TextView percentage;
    private TextView gender;
    private StorageReference mStoragereference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        address = findViewById(R.id.address);
        age = findViewById(R.id.age);
        schoolboard = findViewById(R.id.schoolboard);
        shoolname = findViewById(R.id.shoolname);
        percentage =  findViewById(R.id.percentage);
        gender = findViewById(R.id.gender);

        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        DocumentReference docRef = db.collection("STUDENT_PERSONAL_DETAILS").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        age.setText(document.getString("age"));

                        address.setText(document.getString("address"));
                        schoolboard.setText(document.getString("school board"));
                        shoolname.setText(document.getString("school name"));
                        percentage.setText(document.getString("percentage"));
                        gender.setText(document.getString("gender"));

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

                        //name.setText(document.getString("name"));
                        //filena me = "STUDENT/"+"+-"+document.getString("name");
                        mStoragereference = FirebaseStorage.getInstance().getReference().child("STUDENT/+-"+document.getString("name"));
                        try {
                            final File file = File.createTempFile("image", "jpg");
                            mStoragereference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                    ((ImageView)findViewById(R.id.profile)).setImageBitmap(bitmap);
                                   // navigation_profile.setImageBitmap(bitmap);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                       // nav_name.setText(document.getString("name"));
                        //Log.d("info", "No such document");
                    }
                }
                else {
                    //Toast.makeText(StudentPrpfilePage.this, "get failed with ",Toast.LENGTH_SHORT).show();
                    Log.d("info", "get failed with ", task.getException());
                }
            }
        });
    }
}
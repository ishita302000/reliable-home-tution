package com.example.reliablehometution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class feedbacks extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth fAuth;
    private EditText feedbacks;
    private Button submit;
    private TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbacks);

        feedbacks = findViewById(R.id.feedbackR);
        submit = findViewById(R.id.submit);
        name = findViewById(R.id.name);

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference docRef1 = db.collection("STUDENT").document(user.getUid());
                docRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                                name.setText(document.getString("name"));

                                //Log.d("info", "No such document");
                            }
                        }
                        else {
                            //Toast.makeText(StudentPrpfilePage.this, "get failed with ",Toast.LENGTH_SHORT).show();
                            Log.d("info", "get failed with ", task.getException());
                        }
                    }
                });

                DocumentReference documentReference = db.collection("FEEDBACK").document(user.getUid()).collection("USERFEEDBACK").document();
                Map<String, Object> user = new HashMap<>();
                user.put("feedback", feedbacks.getText().toString());

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(feedbacks.this, "added successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(feedbacks.this,StudentPrpfilePage.class));
                    }

                });
            }


        });


    }
}
package com.example.reliablehometution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginPageStudent extends AppCompatActivity {

    private TextView signUp_LoginPageStudent;
    private TextView login_with_phoneno_LoginPageStudent;
    private EditText email_LoginPageTeacher;
    private EditText password_LoginPageteacher;
    private Button login;
    private ProgressBar progressBar;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page_student);

        signUp_LoginPageStudent = findViewById(R.id.slogan_name_student);
       login_with_phoneno_LoginPageStudent = findViewById(R.id.phnno_log_student);
       email_LoginPageTeacher = findViewById(R.id.email_log_student);
       password_LoginPageteacher = findViewById(R.id.password_log_student);
       login = findViewById(R.id.login_student);
       progressBar = findViewById(R.id.progressBar_student_login);
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), LoginPageTeacher.class));
            finish();
        }
        signUp_LoginPageStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(LoginPageStudent.this,SignUpStudent.class));
            }
        });

        login_with_phoneno_LoginPageStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(LoginPageStudent.this,LoginStudentPhone.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_login=email_LoginPageTeacher.getText().toString().trim();
                String password_login=password_LoginPageteacher.getText().toString().trim();
                if(TextUtils.isEmpty(email_login)) {
                    email_LoginPageTeacher.setError("email is required");
                    return;
                }
                if(TextUtils.isEmpty(password_login)) {
                    password_LoginPageteacher.setError("password is required");
                    //startActivity( new Intent(LoginPageStudent.this,StudentPrpfilePage.class));
                    return;
                }
                if(password_login.length()<6)
                {
                    password_LoginPageteacher.setError("the password must be more than 6 charaters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                login.setEnabled(false);
                fAuth.signInWithEmailAndPassword(email_login,password_login).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Toast.makeText(LoginTeacher.this,"Loggid in successfully",Toast.LENGTH_SHORT).show();
                            DocumentReference documentReference = fstore.collection("STUDENT").document(fAuth.getCurrentUser().getUid());
                            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        Toast.makeText(LoginPageStudent.this,"Loggid in successfully",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        login.setEnabled(true);
                                        DocumentReference documentReference1 = fstore.collection("STUDENT_PERSONAL_DETAILS").document(fAuth.getCurrentUser().getUid());
                                        documentReference1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                          @Override
                                                                                          public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                              if(documentSnapshot.exists()){
                                                                                                  startActivity(new Intent(getApplicationContext(),StudentPrpfilePage.class));
                                                                                              }
                                                                                              else{
                                                                                                  startActivity(new Intent(getApplicationContext(),form_student.class));
                                                                                              }
                                                                                          }
                                                                                      });
                                       // startActivity(new Intent(getApplicationContext(),StudentPrpfilePage.class));
                                    }else{
                                        Toast.makeText(LoginPageStudent.this,"invalid Id and password",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        login.setEnabled(true);
                                    }
                                }
                            });

                            //startActivity(new Intent(getApplicationContext(),form_teacher.class));
                        }else{
                            Toast.makeText(LoginPageStudent.this,"Error !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            login.setEnabled(true);
                        }
                    }
                });
            }
        });
    }
    public void logout(){
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        logout();

    }
}
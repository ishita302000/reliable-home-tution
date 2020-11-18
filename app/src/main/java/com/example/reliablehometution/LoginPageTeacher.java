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

public class LoginPageTeacher extends AppCompatActivity {

    private TextView signUp_LoginPageTeacher;
    private TextView login_with_phoneno_LoginPageTeacher;
    private Button loginnext;
    private EditText email_LoginPageTeacher;
    private EditText password_LoginPageteacher;
    private Button login_btn_LoginPageteacher;
    private ProgressBar progressBar;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page_teacher);

        signUp_LoginPageTeacher = findViewById(R.id.slogan_name_teacher);
        login_with_phoneno_LoginPageTeacher = findViewById(R.id.phnno_log_teacher);
        email_LoginPageTeacher = findViewById(R.id.email_log_teacher);
        password_LoginPageteacher = findViewById(R.id.password_log_teacher);
        login_btn_LoginPageteacher = findViewById(R.id.login_teacher);
        progressBar = findViewById(R.id.progressBar_teacher_login);
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), LoginPageTeacher.class));
            finish();
        }

        login_btn_LoginPageteacher.setOnClickListener(new View.OnClickListener() {
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

                    return;
                }
                if(password_login.length()<6)
                {
                    password_LoginPageteacher.setError("the password must be more than 6 charaters");

                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                login_btn_LoginPageteacher.setEnabled(false);
                fAuth.signInWithEmailAndPassword(email_login,password_login).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Toast.makeText(LoginTeacher.this,"Loggid in successfully",Toast.LENGTH_SHORT).show();
                            DocumentReference documentReference = fstore.collection("TEACHER").document(fAuth.getCurrentUser().getUid());
                            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        Toast.makeText(LoginPageTeacher.this,"Loggid in successfully",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        login_btn_LoginPageteacher.setEnabled(true);
                                        startActivity(new Intent(getApplicationContext(),form_teacher.class));
                                    }else{
                                        Toast.makeText(LoginPageTeacher.this,"invalid Id and password",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        login_btn_LoginPageteacher.setEnabled(true);
                                    }
                                }
                            });

                            //startActivity(new Intent(getApplicationContext(),form_teacher.class));
                        }else{
                            Toast.makeText(LoginPageTeacher.this,"Error !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            login_btn_LoginPageteacher.setEnabled(true);
                        }
                    }
                });
            }
        });


        signUp_LoginPageTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(LoginPageTeacher.this,SignUpTeacher.class));
            }
        });

        login_with_phoneno_LoginPageTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(LoginPageTeacher.this,LoginTeacherPhone.class));
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
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

public class LoginPageParent extends AppCompatActivity {

    private TextView signUp_LoginPageParent;
    private TextView login_with_phoneno_LoginPageParent;
    private Button loginnext;
    private EditText email_LoginPageParent;
    private EditText password_LoginPageParent;
    private Button login_btn_LoginPageParent;
    private ProgressBar progressBar;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page_parent);

        signUp_LoginPageParent = findViewById(R.id.slogan_name_parent);
        login_with_phoneno_LoginPageParent = findViewById(R.id.phnno_log_parent);
        email_LoginPageParent = findViewById(R.id.email_log_parent);
        password_LoginPageParent = findViewById(R.id.password_log_parent);
        login_btn_LoginPageParent = findViewById(R.id.login_parent);
        progressBar = findViewById(R.id.progressBar_parent_login);
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        signUp_LoginPageParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(LoginPageParent.this,SignUpParent.class));
            }
        });

        login_with_phoneno_LoginPageParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(LoginPageParent.this,LoginParentPhone.class));
            }
        });
        login_btn_LoginPageParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_login=email_LoginPageParent.getText().toString().trim();
                String password_login=password_LoginPageParent.getText().toString().trim();
                if(TextUtils.isEmpty(email_login)) {
                    email_LoginPageParent.setError("username is required");

                    return;
                }
                if(TextUtils.isEmpty(password_login)) {
                    password_LoginPageParent.setError("password is required");
                    return;
                }
                if(password_login.length()<6)
                {
                    password_LoginPageParent.setError("the password must be more than 6 charaters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                login_btn_LoginPageParent.setEnabled(false);
                fAuth.signInWithEmailAndPassword(email_login,password_login).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            DocumentReference documentReference = fstore.collection("PARENT").document(fAuth.getCurrentUser().getUid());
                            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        Toast.makeText(LoginPageParent.this,"Loggid in successfully",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),personal_details_parent.class));
                                    }else{
                                        Toast.makeText(LoginPageParent.this,"invalid Id and password",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        login_btn_LoginPageParent.setEnabled(true);
                                    }
                                }
                            });
                            //Toast.makeText(LoginParent.this,"Loggid in successfully",Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(getApplicationContext(),form_teacher.class));
                        }else{
                            Toast.makeText(LoginPageParent.this,"Error !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            login_btn_LoginPageParent.setEnabled(true);
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
        logout();
        super.onBackPressed();


    }
}
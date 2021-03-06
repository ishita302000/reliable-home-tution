package com.example.reliablehometution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SignUpTeacher extends AppCompatActivity {
    TextInputEditText teacher_regName, teacher_regEmail, teacher_Phone, teacher_regpassword;
    Button teacher_regbtn, teacher_regtoLoginBtn;
    private FirebaseAuth fAuth;
    private FirebaseAuth fAuth3;
    private FirebaseFirestore fstore;
    private FirebaseUser fUser;
    private FirebaseUser fUser3;
    private String userId_techer;
    private String userId_techer3;
    private String userId_techer1;
    private EditText enterotp;
    private  Button enter_otp;
    private ProgressBar progressBar;
    String verificationid;
    Boolean verificationInprogress = false;
    int u = 0;
    String userId1;
    String name_t;
    String email_t;
    String phpne_t;
    private FirebaseAuth fAuth2;
    PhoneAuthProvider.ForceResendingToken token;
    FirebaseAuth fAuth1=FirebaseAuth.getInstance();
    FirebaseFirestore fstore1=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_teacher);

        teacher_regName = findViewById(R.id.signn_fullname_teacher);
        //teacher_regUsserName = findViewById(R.id.signn_username_teacher);
        teacher_regEmail = findViewById(R.id.sign_email_teacher);
        teacher_Phone = findViewById(R.id.sign_phn_teacher);
        teacher_regpassword = findViewById(R.id.sign_password_teacher);

        teacher_regbtn = findViewById(R.id.signup_teacher);
        teacher_regtoLoginBtn = findViewById(R.id.Login_text_sigin_teacher);

        enterotp = findViewById(R.id.sign_otp_teacher);
        enter_otp=findViewById(R.id.signup_teacher_phoneNO);

        progressBar = findViewById(R.id.progressBar_teacher_signup);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        fAuth2=FirebaseAuth.getInstance();
        fAuth3=FirebaseAuth.getInstance();
        fUser=fAuth2.getCurrentUser();
        fUser3=fAuth3.getCurrentUser();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), SignUpTeacher.class));
            finish();
        }

        teacher_regtoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpTeacher.this,LoginPageTeacher.class));
            }
        });

        teacher_regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (u == 0) {

                    final String teacher_name = teacher_regName.getEditableText().toString();
                    final String teacher_password = teacher_regpassword.getEditableText().toString();
                    // final String  teacher_username = teacher_regUsserName.getEditableText().toString();
                    final String teacher_email = teacher_regEmail.getEditableText().toString();
                    final String teacher_phoneNo = teacher_Phone.getEditableText().toString();

                    if((TextUtils.isEmpty(teacher_name))) {
                        teacher_regName.setError("name is required");
                        return;
                    }
                    if((TextUtils.isEmpty(teacher_password))) {
                        teacher_regpassword.setError("password is required");
                        return;
                    }
                    if(teacher_password.length()<6)
                    {
                        teacher_regpassword.setError("the password must be more than 6 charaters");
                        return;
                    }
                    if((TextUtils.isEmpty(teacher_email))) {
                        teacher_regEmail.setError("email is required");
                        return;
                    }

                    if((TextUtils.isEmpty(teacher_phoneNo))) {
                        teacher_Phone.setError("phone is required");
                        return;
                    }
                    if(teacher_phoneNo.length()<10) {
                        teacher_Phone.setError("the no must be of 10 character");
                        return;
                    }


                    name_t=teacher_name;
                    email_t= teacher_email;
                    phpne_t=teacher_phoneNo;

                    teacher_regbtn.setEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);
                    teacher_regtoLoginBtn.setVisibility(View.GONE);

                    //UserHelperClass_teacher helperClass_teacher = new UserHelperClass_teacher(teacher_name, teacher_email, teacher_phoneNo, teacher_password);
                    fAuth.createUserWithEmailAndPassword(teacher_email, teacher_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Toast.makeText(Signup_teacher.this, "user created", Toast.LENGTH_SHORT).show();
                                //putting other data like name ,email etc into the fire base collection name users
                                userId_techer = fAuth.getCurrentUser().getUid();

                                DocumentReference documentReference = fstore.collection("TEACHER").document(userId_techer);
                                Map<String, Object> user = new HashMap<>();
                                user.put("name", teacher_name);
                                user.put("mail", teacher_email);
                                user.put("phoneNo", teacher_phoneNo);

                                u=1;

                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //Log.i("info", "on success:user  profile is created" + userId_techer);
                                        //. Log.i("info","on success:user  profile is created"+userId);
                                        FirebaseAuth.getInstance().signOut();

                                        String phonrnumber = "+91" + teacher_phoneNo;
                                        requestOTP(phonrnumber);
                                    }

                                });
                                //startActivity(new Intent(getApplicationContext(),LoginTeacher.class));
                                //finish();
                            } else {
                                Toast.makeText(SignUpTeacher.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                // mProgressBar2.setVisibility(View.GONE);u=
                                teacher_regbtn.setEnabled(true);
                                progressBar.setVisibility(View.GONE);
                                teacher_regtoLoginBtn.setVisibility(View.VISIBLE);
                                u = 0;
                            }
                        }


                    });
                } else {
                    teacher_regbtn.setEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);
                    String userOTP = enterotp.getText().toString().trim();
                    if (!userOTP.isEmpty() && userOTP.length() == 6) {
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, userOTP);
                        verifyAuth(credential);
                        u = 0;
                        abc();
                        // verificationInprogress=false;
                    } else {
                        teacher_Phone.setError("valid otp is required");
                        teacher_regbtn.setEnabled(true);
                    }
                }

            }
        });
    }

    public void requestOTP(String phonNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phonNo, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationid = s;
                //setContentView(R.layout.otp_layout);
                token = forceResendingToken;
                verificationInprogress = true;
                enterotp.setVisibility(View.VISIBLE);
                // enter_otp.setVisibility(View.VISIBLE);
                teacher_regbtn.setEnabled(true);
                teacher_regName.setVisibility(View.GONE);
                teacher_regEmail.setVisibility(View.GONE);
                teacher_Phone.setVisibility(View.GONE);
                teacher_regpassword.setVisibility(View.GONE);
                teacher_regbtn.setEnabled(true);

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(SignUpTeacher.this, "Cannot create account" + e.getMessage(), Toast.LENGTH_SHORT).show();

                fAuth2.signInWithEmailAndPassword(teacher_regEmail .getEditableText().toString(),teacher_regpassword.getEditableText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Toast.makeText(Signup_teacher.this,"Loggid in successfully",Toast.LENGTH_SHORT).show();

                            userId_techer1 = fAuth2.getCurrentUser().getUid();
                            FirebaseFirestore.getInstance().collection("TEACHER").document(userId_techer1).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Toast.makeText(Signup_teacher.this,"Deleted Successfuly",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignUpTeacher.this,"Error",Toast.LENGTH_SHORT).show();
                                }
                            });
                            fUser=fAuth2.getCurrentUser();
                            fUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUpTeacher.this,"Signup again after sometimes.",Toast.LENGTH_SHORT).show();
                                        FirebaseAuth.getInstance().signOut();
                                    }
                                    else{
                                        //Toast.makeText(Signup_teacher.this,"noDeleted not",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(SignUpTeacher.this,"Error !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            //progressBar.setVisibility(View.GONE);
                            //teacher_login.setEnabled(false);
                        }
                    }
                });


            }
        });

    }

    private void verifyAuth(PhoneAuthCredential credential) {
        fAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpTeacher.this, "registration is sucessful", Toast.LENGTH_SHORT).show();
                    userId1 = fAuth1.getCurrentUser().getUid();
                    DocumentReference documentReference1 = fstore1.collection("TEACHER").document(userId1);
                    Map<String, Object> user1 = new HashMap<>();
                    user1.put("name", name_t);
                    user1.put("E-mail", email_t);
                    user1.put("PhoneNo", phpne_t);
                    documentReference1.set(user1).addOnSuccessListener(new OnSuccessListener<Void>(){

                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i("info","on success:user  profile is created"+userId1);
                        }
                    });
                } else {
                    Toast.makeText(SignUpTeacher.this, "registration failed", Toast.LENGTH_SHORT).show();
                    // teacher_regbtn.setEnabled(true);


                    fAuth3.signInWithEmailAndPassword(teacher_regEmail .getEditableText().toString(),teacher_regpassword.getEditableText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                // Toast.makeText(Signup_teacher.this,"Loggid in successfully",Toast.LENGTH_SHORT).show();

                                userId_techer3 = fAuth3.getCurrentUser().getUid();
                                FirebaseFirestore.getInstance().collection("TEACHER").document(userId_techer3).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Toast.makeText(Signup_teacher.this,"Deleted Successfuly",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //Toast.makeText(Signup_teacher.this,"Error",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                fUser3=fAuth3.getCurrentUser();
                                fUser3.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            // Toast.makeText(Signup_teacher.this,"noDeleted",Toast.LENGTH_SHORT).show();
                                            FirebaseAuth.getInstance().signOut();
                                        }
                                        else{
                                            //Toast.makeText(Signup_teacher.this,"noDeleted not",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                // startActivity(new Intent(getApplicationContext(),LoginTeacher.class));
                            }else{
                                Toast.makeText(SignUpTeacher.this,"Error !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                //progressBar.setVisibility(View.GONE);
                                //teacher_login.setEnabled(false);
                            }
                        }
                    });

                }
            }
        });

    }

    public void abc() {
        startActivity(new Intent(this,MainActivity.class));
    }
    public void logout(){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(SignUpTeacher.this,"authentication failed", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        logout();

    }
}


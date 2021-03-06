package com.example.reliablehometution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class personal_details_student extends AppCompatActivity {

    private TextInputEditText age_s;
    private AutoCompleteTextView gender_s;
    private TextInputEditText birth_s;
    private TextInputEditText schoolname_s;
    private TextInputEditText schoolboard_s;
    private TextInputEditText class_s;
    private TextInputEditText percentage_s;
    private TextInputEditText address_s;
    private TextInputEditText blood_s;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    private String userId_techer;
    private Button btn_student_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_personal_details_student);
        age_s = findViewById(R.id.age_student);
        gender_s = findViewById(R.id.Gender_student);
        birth_s = findViewById(R.id.birth_student);
        schoolname_s = findViewById(R.id.schoolname_student);
        schoolboard_s = findViewById(R.id.Board_student);
        class_s = findViewById(R.id.class_student);
        percentage_s = findViewById(R.id.percentage_student);
        address_s = findViewById(R.id.address_student);
        blood_s = findViewById(R.id.Blood_student);
        btn_student_next = findViewById(R.id.persnal_teacher_button);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        String[] genderItems = new String[]{"Male","Female","Other"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>( personal_details_student.this,R.layout.gender_item,genderItems);
        gender_s.setAdapter(genderAdapter);
        birth_s.setFocusable(false);
        birth_s.setClickable(true);
        birth_s.setLongClickable(false);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select Your Date Of Birth");
        MaterialDatePicker materialDatePicker = builder.build();

        birth_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(),"Date Picker");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                birth_s.setText(materialDatePicker.getHeaderText());
            }
        });


        btn_student_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String age = age_s.getText().toString().trim();
                String gender = gender_s.getText().toString().trim();
                String birth = birth_s.getText().toString().trim();
                String school_name = schoolname_s.getText().toString().trim();
                String school_board = schoolboard_s.getText().toString().trim();
                String classs = class_s.getText().toString().trim();
                String percentage = percentage_s.getText().toString().trim();
                String address = address_s.getText().toString().trim();
                String blood_group = blood_s.getText().toString().trim();
                userId_techer = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("STUDENT_PERSONAL_DETAILS").document(userId_techer);
                Map<String, Object> user = new HashMap<>();
                user.put("age", age);
                user.put("gender", gender);
                user.put("birth", birth);
                user.put("school name", school_name);
                user.put("school board", school_board);
                user.put("class", classs);
                user.put("percentage ", percentage);
                user.put("address", address);
                user.put("blood group", blood_group);

                if ((TextUtils.isEmpty(gender))) {
                    gender_s.setError("gender is required");
                    return;
                }
                if ((TextUtils.isEmpty(age))) {
                    age_s.setError("age is required");
                    return;
                }
                if ((TextUtils.isEmpty(school_name))) {
                    schoolname_s.setError("schoolname is required");
                    return;
                }
                if ((TextUtils.isEmpty(birth))) {
                    birth_s.setError("birth date is required");
                    return;
                }
                if ((TextUtils.isEmpty(address))) {
                    address_s.setError("address is required");
                    return;
                }
                if ((TextUtils.isEmpty(school_board))) {
                    schoolboard_s.setError("school board is required");
                    return;
                }
                if ((TextUtils.isEmpty(percentage))) {
                    percentage_s.setError("address is required");
                    return;
                }
                if ((TextUtils.isEmpty(blood_group))) {
                    blood_s.setError("blood group is required");
                    return;
                }
                if ((TextUtils.isEmpty(classs))) {
                    class_s.setError("address is required");
                    return;
                }
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("info", "details added successfully" + userId_techer);
                        //. Log.i("info","on success:user  profile is created"+userId);
                        //   Intent i = new Intent(uploadphoto_student.this , professional_teacher.class);
                        // startActivity(i);
                        Toast.makeText(personal_details_student.this, "ThankYou for filling the form ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(personal_details_student.this, uploadFileactivity.class));
                    }
                });


            }
        });
    }
}
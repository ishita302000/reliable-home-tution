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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class personal_details_teacher extends AppCompatActivity {

    private TextInputEditText name_t;
    private TextInputEditText age_t;
    private AutoCompleteTextView gender_t;
    private AutoCompleteTextView martial_t;
    private TextInputEditText email_t;
    private TextInputEditText birth_t;
    private TextInputEditText contact_t;
    private TextInputEditText address_t;
    Button btn_next;

    Button btn_save;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    private String userId_techer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details_teacher);
        //logOut = findViewById(R.id.button);
        age_t = (TextInputEditText)findViewById(R.id.age_teacher);
        gender_t = (AutoCompleteTextView)findViewById(R.id.Gender_teacher);
        martial_t = (AutoCompleteTextView)findViewById(R.id.martial_teacher);
        // email_t = (TextInputEditText)findViewById(R.id.Email_teacher);
        birth_t = (TextInputEditText)findViewById(R.id.Birth_teacher);
        //contact_t = (TextInputEditText)findViewById(R.id.contact_teacher);
        address_t = (TextInputEditText)findViewById(R.id.address_teacher);
       // reff = FirebaseDatabase.getInstance().getReference().child("users");
        btn_save = (Button)findViewById(R.id.persnal_teacher_button);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        String[] genderItems = new String[]{"Male","Female","Other"};
        String[] maritalItems = new String[]{"Married","Single","Divorced","Widow"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>( personal_details_teacher.this,R.layout.gender_item,genderItems);
        ArrayAdapter<String> maritalAdapter = new ArrayAdapter<>( personal_details_teacher.this,R.layout.gender_item,maritalItems);
        gender_t.setAdapter(genderAdapter);
        martial_t.setAdapter(maritalAdapter);
        birth_t.setFocusable(false);
        birth_t.setClickable(true);
        birth_t.setLongClickable(false);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select Your Date Of Birth");
        MaterialDatePicker materialDatePicker = builder.build();

        birth_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(),"Date Picker");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                birth_t.setText(materialDatePicker.getHeaderText());
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  /* int aget = Integer.parseInt(age_t.getText().toString().trim());
                   Long  birtht =Long.parseLong(age_t.getText().toString().trim());
                   Long contactt = Long.parseLong(contact_t.getText().toString().trim());*/
                String ageT=age_t.getText().toString().trim();
                String genderT=gender_t.getText().toString().trim();
                String materialT=martial_t.getText().toString().trim();
                String birthT= birth_t.getText().toString().trim();
                String addressT= address_t.getText().toString().trim();
                userId_techer = fAuth.getCurrentUser().getUid();
                if((TextUtils.isEmpty(genderT))) {
                    gender_t.setError("gender is required");
                    return;
                }
                if((TextUtils.isEmpty(ageT))) {
                    age_t.setError("age is required");
                    return;
                }
                if((TextUtils.isEmpty(materialT))) {
                    martial_t.setError("martial is required");
                    return;
                }
                if((TextUtils.isEmpty(birthT))) {
                    birth_t.setError("birth date is required");
                    return;
                }
                if((TextUtils.isEmpty(addressT))) {
                    address_t.setError("address is required");
                    return;
                }
                DocumentReference documentReference = fstore.collection("PERSONAL_DETAILS_TEACHER").document(userId_techer);
                Map<String, Object> user = new HashMap<>();
                user.put("age", ageT);
                user.put("gender", genderT);
                user.put("martial status", materialT);
                user.put("date of birth", birthT);
                user.put("address", addressT);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("info", "details added successfully" + userId_techer);
                        //. Log.i("info","on success:user  profile is created"+userId);
                        Intent i = new Intent(personal_details_teacher.this , professional_teacher.class);
                        startActivity(i);
                    }
                });
            }
        });
    }
}
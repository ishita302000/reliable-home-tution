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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class personal_details_parent extends AppCompatActivity {

    private TextInputEditText age_p;
    private AutoCompleteTextView gender_p;
    private TextInputEditText occupation_p;
    private AutoCompleteTextView graduation_p;
    private TextInputEditText birth_p;
    private AutoCompleteTextView post_graduation__p;
    private TextInputEditText address_p;
    private TextInputEditText allergy_p;
    MaterialButton btn_save;
   // DatabaseReference reff;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    private String userId_techer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details_parent);

       // age_p = (TextInputEditText)findViewById(R.id.age_p);
        gender_p = (AutoCompleteTextView) findViewById(R.id.GenderEditText);
        occupation_p=(TextInputEditText)findViewById(R.id.ocuppationEditText);
        graduation_p=(AutoCompleteTextView)findViewById(R.id.graduationEditText);
        birth_p = (TextInputEditText)findViewById(R.id.birthTextView);
        post_graduation__p=(AutoCompleteTextView) findViewById(R.id.pgEditText);
        allergy_p=(TextInputEditText)findViewById(R.id.allergyEditText) ;
        address_p = (TextInputEditText)findViewById(R.id.adressEditText);
        //reff = FirebaseDatabase.getInstance().getReference().child("users");
        btn_save = (MaterialButton)findViewById(R.id.nextButton);


        String[] genderItems = new String[]{"Male","Female","Other"};
        String[] graduationItems = new String[]{"B.tech","B.sc","B.C.A","B.Arch","other"};
        String[] postGraduationItems = new String[]{"M.tech","M.sc","M.C.A","M.Arch","other"};

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>( personal_details_parent.this,R.layout.gender_item,genderItems);
        ArrayAdapter<String> graduationAdapter = new ArrayAdapter<>( personal_details_parent.this,R.layout.gender_item,graduationItems);
        ArrayAdapter<String> postGraduationAdapter = new ArrayAdapter<>( personal_details_parent.this,R.layout.gender_item,postGraduationItems);
        gender_p.setAdapter(genderAdapter);
        graduation_p.setAdapter(graduationAdapter);
        post_graduation__p.setAdapter(postGraduationAdapter);

        //birth_p.setEnabled(false);
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select Your Date Of Birth");
        MaterialDatePicker materialDatePicker = builder.build();

        birth_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(),"Date Picker");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                birth_p.setText(materialDatePicker.getHeaderText());
            }
        });

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        /*btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gender=gender_p.getText().toString().trim();
                String graduation=graduation_p.getText().toString().trim();
                String post_graduation=post_graduation__p.getText().toString().trim();
                Toast.makeText(personal_details_parent.this,gender+"\n"+graduation+"\n"+post_graduation,Toast.LENGTH_LONG).show();
            }
        });*/

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String age=age_p.getText().toString().trim();
                String gender=gender_p.getText().toString().trim();
                String occupation=occupation_p.getText().toString().trim();
                String graduation=graduation_p.getText().toString().trim();
               // String post_graduation=post_graduation__p.getText().toString().trim();
                String allergy=allergy_p.getText().toString().trim();
                String birth=birth_p.getText().toString().trim();
                String address=address_p.getText().toString().trim();
                if((TextUtils.isEmpty(gender))) {
                    gender_p.setError("gender is required");
                    return;
                }

                if((TextUtils.isEmpty(occupation))) {
                    occupation_p.setError("martial is required");
                    return;
                }
                if((TextUtils.isEmpty(birth))) {
                    birth_p.setError("birth date is required");
                    return;
                }
                if((TextUtils.isEmpty(address))) {
                    address_p.setError("address is required");
                    return;
                }
                if((TextUtils.isEmpty(allergy))) {
                    allergy_p.setError("address is required");
                    return;
                }

                if((TextUtils.isEmpty(graduation))) {
                    graduation_p.setError("address is required");
                    return;
                }
                userId_techer = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("PARENT_PERSONAL_DETAILS").document(userId_techer);
                Map<String, Object> user = new HashMap<>();
                //user.put("age",age );
                user.put("gender", gender);
                user.put("occupation",occupation ) ;
                user.put("graduation", graduation) ;
               // user.put("post graduation", post_graduation) ;
                user.put("allergy", allergy) ;
                user.put("date of birth", birth);
                user.put("address", address);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("info", "details added successfully" + userId_techer);
                        //. Log.i("info","on success:user  profile is created"+userId);
                        //Intent i = new Intent(personal_details_parent.this , professional_parent.class);
                        //startActivity(i);
                        Toast.makeText(personal_details_parent.this , "ThankYou for filling the form " , Toast.LENGTH_SHORT).show();
                        startActivity( new Intent(personal_details_parent.this,LOGOUTforParent.class));


                    }

                });



            }
        });
    }
}
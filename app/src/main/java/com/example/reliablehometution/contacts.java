package com.example.reliablehometution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class contacts extends AppCompatActivity {

    private CardView email;
    private CardView phone;
    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }


        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
    }

    private void makePhoneCall() {

        String phone_no = "9996056455";
        if(ContextCompat.checkSelfPermission(contacts.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(contacts.this,new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }
        else{
            String dial = "tel:" + phone_no;
            startActivity( new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      if(requestCode == REQUEST_CALL) {
          if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
              makePhoneCall();
          }
          else {
              Toast.makeText(this,"permission granted",Toast.LENGTH_SHORT).show();
          }
      }
    }

    private void sendMail() {
        String email_address = "reliablehometution@gmail.com";
        String subject = "regarding tution";

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + email_address));
        //intent.putExtra(Intent.EXTRA_EMAIL,email_address);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,"write your complain");
        //intent.setType("messege/rfc822");
        //startActivity(Intent.createChooser(intent,"Choose an email client"));
        startActivity(intent);

    }
}
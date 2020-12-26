package com.example.reliablehometution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class contacts extends AppCompatActivity {

    private CardView email;
    private CardView phone;

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
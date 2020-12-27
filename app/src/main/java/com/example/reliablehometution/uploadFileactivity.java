package com.example.reliablehometution;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class uploadFileactivity extends AppCompatActivity {

    private ImageView upload_confirm , search_for_pdf , cross ;
    private Button upload;
    private Uri filepath;
    private String file_name;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseStorage;
    private FirebaseUser firebaseUser;

    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_fileactivity);

        upload_confirm = findViewById(R.id.upload_confirm);
        search_for_pdf = findViewById(R.id.search);
        cross = findViewById(R.id.cross);
        upload = findViewById(R.id.upload);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseFirestore.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();


        storageReference = FirebaseStorage.getInstance().getReference();


        DocumentReference docref = firebaseStorage.collection("STUDENT").document(firebaseUser.getUid());
        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();
                    if(document.exists())
                    {
                        file_name = document.getString("name");
                    }
                }
                else
                {
                    Log.d("info" , "get failed with" , task.getException());
                }
            }
        });


        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload_confirm.setVisibility(View.GONE);
                cross.setVisibility(View.GONE);
                search_for_pdf.setVisibility(View.VISIBLE);

            }
        });

        search_for_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent();
                        intent.setType("application/pdf");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"select pdf files"),101);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                          permissionToken.continuePermissionRequest();
                    }
                }).check();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processUpload(filepath);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101 && resultCode == RESULT_OK){
            filepath = data.getData();
            upload_confirm.setVisibility(View.VISIBLE);
            cross.setVisibility(View.VISIBLE);
            search_for_pdf.setVisibility(View.GONE);
        }
    }

    private void processUpload(Uri filepath) {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("File uploading....!!");
        pd.show();

        final  StorageReference pdf_store = storageReference.child("student_document/"+file_name);
        pdf_store.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"File uploaded successfuly",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                float percent = (100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                pd.setMessage("Upload :"+(int)percent+"%");
            }
        });
    }
}
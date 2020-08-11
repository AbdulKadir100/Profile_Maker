package com.example.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ShowProfile extends AppCompatActivity {
    TextView nameTv,ageTv,bioTv,emailTv,websiteTv;
    ImageView imageView;
    FloatingActionButton floatingActionButton;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);


       imageView = findViewById(R.id.image_sp);
       nameTv = findViewById(R.id.name_tv_sp);
       ageTv = findViewById(R.id.age_tv_sp);
       bioTv = findViewById(R.id.bio_tv_sp);
       emailTv = findViewById(R.id.email_tv_sp);
       websiteTv = findViewById(R.id.web_tv_sp);
       floatingActionButton  = findViewById(R.id.floatingbtn);
       floatingActionButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(ShowProfile.this,ShowActivity.class);
               startActivity(intent);
           }
       });


        documentReference = db.collection("user").document("profile");
        storageReference = FirebaseStorage.getInstance().getReference("profile image");
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {

            documentReference.get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.getResult().exists()) {
                                String name_res = task.getResult().getString("name");
                                String age_res = task.getResult().getString("age");
                                String bio_res = task.getResult().getString("bio");
                                String email_res = task.getResult().getString("email");
                                String web_res = task.getResult().getString("website");
                                String Url = task.getResult().getString("url");

                                Picasso.get().load(Url).into(imageView);

                                nameTv.setText(name_res);
                                ageTv.setText(age_res);
                                bioTv.setText(bio_res);
                                emailTv.setText(email_res);
                                websiteTv.setText(web_res);

                            } else {
                                Toast.makeText(ShowProfile.this, "No Profile Exist", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ShowProfile.this,ShowActivity.class);
                                startActivity(intent);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
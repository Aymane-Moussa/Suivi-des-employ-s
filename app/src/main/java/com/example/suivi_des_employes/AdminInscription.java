package com.example.suivi_des_employes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import java.io.InputStream;
import java.util.Random;

public class AdminInscription extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText nameRegistration,emailRegistration,passwordRegistration,job;
    private TextView register,loginPage;

    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private Uri filepath;
    private ImageView img;
    private Button browse;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inscription);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        browse = findViewById(R.id.browse);
        img = findViewById(R.id.img);
        mAuth = FirebaseAuth.getInstance();
        nameRegistration = findViewById(R.id.fullnameRegistration);
        emailRegistration = findViewById(R.id.emailRegistration);
        passwordRegistration = findViewById(R.id.passwordRegistration);
        job = findViewById(R.id.professionRegistration);
        progressBar = findViewById(R.id.progressBarRegistration);
        register = findViewById(R.id.register);
        loginPage =  findViewById(R.id.gotologinpage);
        loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminInscription.this,Authentification.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }

            private void registerUser() {
                String email,password,fullname,profession;
                fullname = nameRegistration.getText().toString().trim();
                email = emailRegistration.getText().toString().trim();
                password = passwordRegistration.getText().toString().trim();
                profession = job.getText().toString().trim();

                if (fullname.isEmpty()){
                    nameRegistration.setError("Entrer le nom complet");
                    nameRegistration.requestFocus();
                    return;
                }else if (email.isEmpty()){
                    emailRegistration.setError("Entrer votre adresse email");
                    emailRegistration.requestFocus();
                    return;
                }else if (password.isEmpty()){
                    passwordRegistration.setError("Entrer le mot de passe ");
                    passwordRegistration.requestFocus();
                    return;
                }else if (password.length() < 6){
                    passwordRegistration.setError("Longeur du mot de passe inférieur à 6!!!");
                    passwordRegistration.requestFocus();
                    return;
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailRegistration.setError("Longeur du mot de passe inférieur à 6!!!");
                    emailRegistration.requestFocus();
                }else{
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.VISIBLE);
                            if (task.isSuccessful()){
                                uploadImage();
                                Admin admin = new Admin(fullname,email,profession,filepath.toString());
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(admin).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                                Toast.makeText(AdminInscription.this, "Inscrit avec Succes", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AdminInscription.this,Authentification.class));
                            }else{
                                Toast.makeText(AdminInscription.this, "Erreur : utilisateur non inscrit " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                uploadImage();
            }
        });

        browse= findViewById(R.id.browse);
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withActivity(AdminInscription.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image File"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

    }

    private void uploadImage() {
        FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference uploader=storage.getReference("Image1"+new Random().nextInt(50));

        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri){

                                FirebaseDatabase db=FirebaseDatabase.getInstance();
                                DatabaseReference root=db.getReference("users");

                                filepath = uri;

                                img.setImageResource(R.drawable.ic_launcher_foreground);
                                Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==1  && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            try{
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
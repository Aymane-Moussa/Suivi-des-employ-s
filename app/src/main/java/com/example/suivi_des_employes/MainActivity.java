package com.example.suivi_des_employes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView logout,name,job;
    private ImageView profilImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CardView ajouter= findViewById(R.id.ajouter);
        ajouter.setOnClickListener(this::openAjouter);
        CardView supprimer= findViewById(R.id.supprimer);
        supprimer.setOnClickListener(this::openSupprimer);
        CardView editer= findViewById(R.id.editer);
        editer.setOnClickListener(this::openEditer);
        CardView affichage= findViewById(R.id.affichage);


        affichage.setOnClickListener(this::openAffichage);
        CardView position= findViewById(R.id.position);
        position.setOnClickListener(this::openPosition);
        profilImage = findViewById(R.id.profileImage);
        name = findViewById(R.id.fullname);
        job = findViewById(R.id.profession);
        mAuth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this,Authentification.class));
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user== null){
            startActivity(new Intent(MainActivity.this,AdminInscription.class));
        }else {
            FirebaseDatabase db=FirebaseDatabase.getInstance();
            DatabaseReference reff=db.getReference().child("Users");
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    Admin admin = snapshot.child(mAuth.getCurrentUser().getUid()).getValue(Admin.class);
                    name.setText(admin.getFullname());
                    job.setText(admin.getProfession());
                    Uri uri = Uri.parse(admin.getImage());
                    profilImage.setImageURI(uri);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
    }

    public void openAjouter(View view){
        Intent intent=new Intent(this,AjouterEmploye.class);
        startActivity(intent);
    }
    public void openSupprimer(View view){
        Intent intent=new Intent(this,AffichageEmployes.class);
        startActivity(intent);
    }
    public void openEditer(View view){
        Intent intent=new Intent(this,AffichageEmployes.class);
        startActivity(intent);
    }
    public void openAffichage(View view){
        Intent intent=new Intent(this,AffichageEmployes.class);
        startActivity(intent);
    }
    public void openPosition(View view){
        Intent intent=new Intent(this,PositionEmployesMaps.class);
        startActivity(intent);
    }

}
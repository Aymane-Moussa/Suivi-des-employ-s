package com.example.suivi_des_employes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AjouterEmploye extends AppCompatActivity {
private EditText nom,prenom,tel,mission,dateDepart,dateFin;
private Button ajouter,retourner;
private DatabaseReference reff;
private Employe employe;
private long id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_employe);
        nom=findViewById(R.id.nom);
        prenom=findViewById(R.id.prenom);
        tel=findViewById(R.id.tele);
        mission=findViewById(R.id.mission);
        dateDepart=findViewById(R.id.date_deppart);
        dateFin=findViewById(R.id.date_fin);
        ajouter=findViewById(R.id.ajouter);
        retourner = findViewById(R.id.retourner);
        reff= FirebaseDatabase.getInstance().getReference().child("Employés");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    id=(snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        employe=new Employe();
        ajouter.setOnClickListener(this::ajouter);
        retourner.setOnClickListener(this::retourner);
    }
    public void ajouter (View view){
        employe.setNom(nom.getText().toString().trim());
        employe.setPrenom(prenom.getText().toString().trim());
        employe.setTele(tel.getText().toString().trim());
        employe.setMission(mission.getText().toString().trim());
        employe.setDateDepart(dateDepart.getText().toString().trim());
        employe.setDateFin(dateFin.getText().toString().trim());
        if(nom.getText().toString().trim().equals("")||prenom.getText().toString().trim().equals("")||tel.getText().toString().trim().equals("")||mission.getText().toString().trim().equals("")||dateDepart.getText().toString().trim()==""||dateFin.getText().toString().trim().equals("")){
            Toast.makeText(AjouterEmploye.this,"Tous les champs doivent être remplis",Toast.LENGTH_LONG).show();
        }
        else
        {
            reff.child(String.valueOf(id)).setValue(employe).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull  Task<Void> task) {
                    Toast.makeText(AjouterEmploye.this,"Les données sont enregistrés avec succès",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AjouterEmploye.this,AffichageEmployes.class));
                }
            });

        }
    }
    public void retourner (View view){
        startActivity(new Intent(AjouterEmploye.this,MainActivity.class));
    }
}
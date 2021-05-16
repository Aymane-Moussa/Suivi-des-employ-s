package com.example.suivi_des_employes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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
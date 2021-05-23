package com.example.suivi_des_employes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class Authentification extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView loginPage;
    private Button login;
    private EditText emailLogin, passwordLogin, job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);

        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        loginPage = findViewById(R.id.registerlogin);
        loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Authentification.this, AdminInscription.class));
            }
        });

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }

            private void loginUser() {
                String email, password, profession;
                email = emailLogin.getText().toString().trim();
                password = passwordLogin.getText().toString().trim();


                if (email.isEmpty()) {
                    emailLogin.setError("Entrer le nom complet");
                    emailLogin.requestFocus();
                } else if (password.isEmpty()) {
                    passwordLogin.setError("Entrer votre adresse email");
                    passwordLogin.requestFocus();
                } else {
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Authentification.this, "Bienvenue", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Authentification.this, MainActivity.class));
                            } else {
                                Toast.makeText(Authentification.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
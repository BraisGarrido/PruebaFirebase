package com.sixtema.pruebafirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class LoginActivity extends AppCompatActivity {
    Button login, signin;
    EditText log_correo, log_pass;
    Intent intent;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        signin=findViewById(R.id.signIn);
        login=findViewById(R.id.login);
        log_correo=findViewById(R.id.log_correo);
        log_pass=findViewById(R.id.log_pass);

        mAuth=FirebaseAuth.getInstance();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(LoginActivity.this, SingInActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login(){
        //Obtenemos los datos de los edit y los convertimos en un string
        String email=log_correo.getText().toString().trim();
        String password=log_pass.getText().toString().trim();

        //Verificamos que todos los campos estan cubiertos
        if(email.isEmpty()){
            Toast.makeText(this, "De debe ingresar un email", Toast.LENGTH_SHORT).show();
        }
        else if(password.isEmpty()){
            Toast.makeText(this, "De debe ingresar una contraseña", Toast.LENGTH_SHORT).show();
        }
        //Loguear usuario
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    //Informacion sobre el exito del registro y comprobacion si ya esta registrado
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            int pos=email.indexOf("@");
                            String user=email.substring(0, pos);
                            Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                            intent=new Intent(LoginActivity.this, HomeActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
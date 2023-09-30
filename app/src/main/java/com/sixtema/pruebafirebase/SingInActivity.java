package com.sixtema.pruebafirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseUser;

public class SingInActivity extends AppCompatActivity {
    Button signin;
    EditText sign_correo, sign_pass;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        signin=findViewById(R.id.signin);
        sign_correo=findViewById(R.id.sign_correo);
        sign_pass=findViewById(R.id.sign_pass);

        mAuth=FirebaseAuth.getInstance();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });
    }

    private void signin(){
        //Obtenemos los datos de los edit y los convertimos en un string
        String email=sign_correo.getText().toString().trim();
        String password=sign_pass.getText().toString().trim();

        //Verificamos que todos los campos estan cubiertos
        if(email.isEmpty()){
            Toast.makeText(this, "De debe ingresar un email", Toast.LENGTH_SHORT).show();
        }
        else if(password.isEmpty()){
            Toast.makeText(this, "De debe ingresar una contraseña", Toast.LENGTH_SHORT).show();
        }


        //creando nuevo usuario
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    //Informacion sobre el exito del registro y comprobacion si ya esta registrado
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SingInActivity.this, "Se ha registrado el usuario", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){//Comprobacion exitencia usuario
                                Toast.makeText(SingInActivity.this, "El usuario ya existe", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(SingInActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}
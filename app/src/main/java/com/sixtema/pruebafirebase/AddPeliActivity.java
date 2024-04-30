package com.sixtema.pruebafirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddPeliActivity extends AppCompatActivity {
    ImageView upl_imagen;
    Button registrar, add_imagen;
    EditText titulo, year, director, reparto, sinopsis;
    
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    StorageReference storageReference;
    
    private static final int COD_SEL_IMAGE=300;
    Intent intent;
    private String urlDescarga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_peli);

        registrar=(Button)findViewById(R.id.registrar);
        add_imagen=(Button)findViewById(R.id.add_imagen);

        titulo=(EditText) findViewById(R.id.titulo);
        year=(EditText) findViewById(R.id.year);
        director=(EditText) findViewById(R.id.director);
        reparto=(EditText) findViewById(R.id.reparto);
        sinopsis=(EditText) findViewById(R.id.sinopsis);
        
        upl_imagen=(ImageView) findViewById(R.id.upl_imagen);

        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance("");
        databaseReference=firebaseDatabase.getReference("");
        
        storageReference=FirebaseStorage.getInstance("").getReference();


        /*-----------------------------------------------------Eventos de click-------------------------------------------------------------------*/
        add_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Acceso a la galeria del movil
                intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, COD_SEL_IMAGE);
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargaDatos();
            }
        });
    }

    //Carga de la imagen al pulsar el boton add_imagen. Tambien se guarda la url de descarga para usarla en la base de datos
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==COD_SEL_IMAGE && resultCode==RESULT_OK){

            Uri uri=data.getData();

            StorageReference reference=storageReference.child("imagen").child(uri.getLastPathSegment());

            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddPeliActivity.this, "Se subio correctamente", Toast.LENGTH_SHORT).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                           urlDescarga=uri.toString();
                           Glide.with(AddPeliActivity.this).load(urlDescarga).into(upl_imagen);
                        }
                    });
                }
            });
        }
    }

    //Se cargan los datos para enviar.
    private void cargaDatos() {
        String s_titulo=titulo.getText().toString();
        String s_year=year.getText().toString();
        String s_director=director.getText().toString();
        String s_reparto=reparto.getText().toString();
        String s_sinopsis=sinopsis.getText().toString();

        if (s_titulo.isEmpty() || s_year.isEmpty() || s_director.isEmpty() || s_reparto.isEmpty() || s_sinopsis.isEmpty()) {
            Toast.makeText(AddPeliActivity.this, "Todos los campos deben estar cubiertos", Toast.LENGTH_SHORT).show();
        }
        else {
            DatabaseReference pelisRef = databaseReference.push();

            Map<String, Object> datos=new HashMap<>();
            datos.put("titulo", s_titulo);
            datos.put("year", s_year);
            datos.put("director", s_director);
            datos.put("reparto", s_reparto);
            datos.put("sinopsis", s_sinopsis);
            datos.put("url_imagen", urlDescarga);

            pelisRef.setValue(datos);
            Toast.makeText(AddPeliActivity.this, "Los datos han sido insertados", Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }

    //Limpiar todos los campos para cuando se termine
    private void limpiar(){
        titulo.setText("");
        year.setText("");
        year.setText("");
        director.setText("");
        reparto.setText("");
        sinopsis.setText("");
        upl_imagen.setImageDrawable(null);
    }
}

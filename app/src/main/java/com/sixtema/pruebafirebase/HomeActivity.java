package com.sixtema.pruebafirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sixtema.pruebafirebase.adaptadores.AdapterPelicula;
import com.sixtema.pruebafirebase.modelo.Pelicula;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    TextView txtUsuario;
    Button add_peli;
    Intent intent;
    FirebaseDatabase firebaseDatabase;
    RecyclerView lista;
    List<Pelicula> peliculas;
    AdapterPelicula adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtUsuario=findViewById(R.id.txtUsuario);
        add_peli=findViewById(R.id.add_peli);
        lista=findViewById(R.id.lista);
        lista.setLayoutManager(new LinearLayoutManager(this));

        peliculas=new ArrayList<>();
        adapter=new AdapterPelicula(peliculas);

        lista.setAdapter(adapter);

        firebaseDatabase= FirebaseDatabase.getInstance("");
        firebaseDatabase.getReference("pelicula").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                //Creacion de un blucle para mostrar los datos
                peliculas.removeAll(peliculas);
                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    Pelicula pelicula=snapshot.getValue(Pelicula.class);
                    peliculas.add(pelicula);
                }
                adapter.notifyDataSetChanged();
                //Evento que al pulsar en un recylcer se recogen los datos y se envian a otra pantalla
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s_titulo=peliculas.get(lista.getChildAdapterPosition(v)).getTitulo();
                        String s_year=peliculas.get(lista.getChildAdapterPosition(v)).getYear();
                        String s_director=peliculas.get(lista.getChildAdapterPosition(v)).getDirector();
                        String s_reparto=peliculas.get(lista.getChildAdapterPosition(v)).getReparto();
                        String s_sinopsis=peliculas.get(lista.getChildAdapterPosition(v)).getSinopsis();
                        String s_imagen=peliculas.get(lista.getChildAdapterPosition(v)).getUrl_imagen();

                        intent=new Intent(HomeActivity.this, DetalleRecyclerActivity.class);
                        intent.putExtra("titulo", s_titulo);
                        intent.putExtra("year", s_year);
                        intent.putExtra("director", s_director);
                        intent.putExtra("reparto", s_reparto);
                        intent.putExtra("sinopsis", s_sinopsis);
                        intent.putExtra("imagen", s_imagen);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Recogida de datos de loginActivity
        Bundle parametros=this.getIntent().getExtras();
        if(parametros != null){
            String user=parametros.getString("user");
            txtUsuario.setText("Bienvenido "+user);
        }
        add_peli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(HomeActivity.this, AddPeliActivity.class);
                startActivity(intent);
            }
        });
    }
}

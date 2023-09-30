package com.sixtema.pruebafirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetalleRecyclerActivity extends AppCompatActivity {
    TextView recy_titulo, recy_year, recy_director, recy_reparto, recy_sinopsis;
    ImageView recy_imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_recycler);

        recy_titulo=findViewById(R.id.recy_titulo);
        recy_year=findViewById(R.id.recy_year);
        recy_director=findViewById(R.id.recy_director);
        recy_reparto=findViewById(R.id.recy_reparto);
        recy_sinopsis=findViewById(R.id.recy_sinopsis);
        recy_imagen=findViewById(R.id.recy_imagen);

        Bundle datos=this.getIntent().getExtras();
        if (datos != null){
            String s_recy_titulo=datos.getString("titulo");
            String s_recy_year=datos.getString("year");
            String s_recy_director=datos.getString("director");
            String s_recy_reparto=datos.getString("reparto");
            String s_recy_sinopsis=datos.getString("sinopsis");
            String s_recy_imagen=datos.getString("imagen");

            recy_titulo.setText(s_recy_titulo);
            recy_year.setText("Año: "+s_recy_year);
            recy_director.setText("Director: "+s_recy_director);
            recy_reparto.setText(s_recy_reparto);
            recy_sinopsis.setText(s_recy_sinopsis);
            Glide.with(DetalleRecyclerActivity.this).load(s_recy_imagen).into(recy_imagen);
        }
    }
}
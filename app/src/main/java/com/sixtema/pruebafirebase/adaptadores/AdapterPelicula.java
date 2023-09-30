package com.sixtema.pruebafirebase.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sixtema.pruebafirebase.DetalleRecyclerActivity;
import com.sixtema.pruebafirebase.R;
import com.sixtema.pruebafirebase.modelo.Pelicula;

import java.util.List;

public class AdapterPelicula extends RecyclerView.Adapter<AdapterPelicula.PeliViewHolder> implements View.OnClickListener{
    List<Pelicula> peliculas;
    private View.OnClickListener listener;

    public AdapterPelicula(List<Pelicula> peliculas){
        this.peliculas=peliculas;
    }

    @NonNull
    @Override
    public PeliViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.platinlla_recycler, parent, false);
        v.setOnClickListener(this);
        return new PeliViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PeliViewHolder holder, int position) {
        Pelicula pelicula=peliculas.get(position);
        holder.viewTitulo.setText(pelicula.getTitulo());
        holder.viewYear.setText(pelicula.getYear());
    }

    @Override
    public int getItemCount() {
        return peliculas.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    public static class PeliViewHolder extends RecyclerView.ViewHolder{
        TextView viewTitulo, viewYear;
        public PeliViewHolder(View itemView){
            super(itemView);
            viewTitulo=itemView.findViewById(R.id.plantilla_titulo);
            viewYear=itemView.findViewById(R.id.plantilla_year);
        }
    }
}

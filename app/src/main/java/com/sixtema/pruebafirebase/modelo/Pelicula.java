package com.sixtema.pruebafirebase.modelo;

import android.graphics.drawable.Drawable;

public class Pelicula {

    private String director, reparto, sinopsis, titulo, year, url_imagen;
    private Drawable imagen;

    public Pelicula(){

    }

    public Pelicula(String director, String reparto, String sinopsis, String titulo, String year, String url_imagen) {
        this.director = director;
        this.reparto = reparto;
        this.sinopsis = sinopsis;
        this.titulo = titulo;
        this.year = year;
        this.url_imagen=url_imagen;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getReparto() {
        return reparto;
    }

    public void setReparto(String reparto) {
        this.reparto = reparto;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }


    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }
}

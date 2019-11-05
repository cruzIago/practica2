package com.example.practica2;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Respuesta {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="id_r")
    public String id_r;

    public String respuesta;
    public boolean isCorrecta;

    public int idImagen;

    public int id_pregunta;

    public Respuesta(){
        this.id_r="";
        this.respuesta="";
        this.isCorrecta=false;
        this.idImagen=-1;
    }

    public Respuesta(String id_r, int id_pregunta, String respuesta, boolean isCorrecta, int imagen) {
        this.id_r = id_r;
        this.id_pregunta=id_pregunta;
        this.respuesta = respuesta;
        this.isCorrecta=isCorrecta;
        this.idImagen = imagen;
    }
}

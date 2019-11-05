package com.example.practica2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Pregunta {
    @PrimaryKey
    @NonNull
    public int id_p;

    public String pregunta;

    //Tipo:0 radioButton, 1 imageButton, 2 audioSource, 3 videoSource;
    public int tipo;
    //Difcultad: 0 facil,1 normal,2 dificil
    public int dificultad; //0 facil,1 normal, 2 dificil
    //i:imagen,v:video,s:sonido,-1:Solo pregunta
    public int idMedia;


    public Pregunta(int id_p, String pregunta, int tipo, int dificultad, int idMedia) {
        this.id_p = id_p;
        this.pregunta = pregunta;
        this.tipo = tipo;
        this.dificultad = dificultad;
        this.idMedia = idMedia;
    }
}


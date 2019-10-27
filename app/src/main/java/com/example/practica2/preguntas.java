package com.example.practica2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class preguntas{
    @PrimaryKey
    public int id_p;

    public String pregunta;

    public int tipo;
    public int dificultad; //0 facil,1 normal, 2 dificil
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[]media;


    public preguntas(int id_p, String pregunta, int tipo, int dificultad, byte[] media) {
        this.id_p = id_p;
        this.pregunta = pregunta;
        this.tipo = tipo;
        this.dificultad = dificultad;
        this.media = media;
    }
}


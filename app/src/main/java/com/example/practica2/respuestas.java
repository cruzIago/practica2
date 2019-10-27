package com.example.practica2;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity=preguntas.class,parentColumns = "id_p",childColumns="id_r"))
public class respuestas {
    @PrimaryKey
    @ColumnInfo(name="id_r")
    public String id_r;

    public String respuesta;
    public boolean isCorrecta;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[]imagen;

    public respuestas(String id_r, String respuesta,boolean isCorrecta, byte[] imagen) {
        this.id_r = id_r;
        this.respuesta = respuesta;
        this.isCorrecta=isCorrecta;
        this.imagen = imagen;
    }
}

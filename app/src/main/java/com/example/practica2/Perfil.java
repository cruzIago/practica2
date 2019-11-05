package com.example.practica2;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
@TypeConverters(TimeConverter.class)
public class Perfil {
    @PrimaryKey(autoGenerate = true)
    public int id_perfil;

    public String nombre;
    public double maxPuntuacion;
    public int partidasJugadas;
    public Date ultimaPartida;

    public String foto;

    public Perfil(String nombre, double maxPuntuacion, int partidasJugadas, Date ultimaPartida, String foto) {
        this.nombre = nombre;
        this.maxPuntuacion = maxPuntuacion;
        this.partidasJugadas = partidasJugadas;
        this.ultimaPartida = ultimaPartida;
        this.foto = foto;
    }
}

package com.example.practica2;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Perfil {
    @PrimaryKey
    public int id_perfil;
    public String nombre;
    public int maxPuntuacion;
    public int partidasJugadas;
    public Date ultimaPartida;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] foto;

    public Perfil(int id_perfil, String nombre, int maxPuntuacion, int partidasJugadas, Date ultimaPartida, byte[] foto) {
        this.id_perfil = id_perfil;
        this.nombre = nombre;
        this.maxPuntuacion = maxPuntuacion;
        this.partidasJugadas = partidasJugadas;
        this.ultimaPartida = ultimaPartida;
        this.foto = foto;
    }
}

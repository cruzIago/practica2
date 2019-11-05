package com.example.practica2;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DAOpreguntasYRespuestas {


    @Insert
    void insertPregunta(Pregunta... pregunta);

    @Insert
    void insertRespuesta(Respuesta... respuesta);

    @Insert
    void nuevoPerfil(Perfil... perfil);

    @Delete
    void borraPerfil(Perfil p);

    @Query("UPDATE Perfil SET foto=:foto WHERE id_perfil=:id_perfil")
    void actualizaPerfilImagen(String foto, int id_perfil);

    @Query("UPDATE Perfil SET maxPuntuacion=:maxPuntuacion WHERE id_perfil=:id_perfil")
    void actualizaMaximaPuntuacion(int id_perfil,double maxPuntuacion);

    @Query("SELECT * from Perfil ORDER BY maxPuntuacion DESC")
    List<Perfil> cargaRanking();

    @Query("SELECT * FROM Pregunta WHERE dificultad=:dificultad1 or dificultad=:dificultad2")
    List<Pregunta> cargaPreguntas(int dificultad1,int dificultad2);

    @Query("SELECT * FROM Respuesta WHERE id_pregunta=:idPregunta")
    Respuesta[] cargarRespuestas(int idPregunta);

    @Query("SELECT * FROM Perfil")
    List<Perfil> cargarPerfiles();

    @Query("DELETE FROM Pregunta")
    void DeleteAllPreguntas();

    @Query("DELETE FROM Respuesta")
    void DeleteAllRespuestas();

    @Query("DELETE FROM Perfil")
    void DeleteAllPerfiles();

}

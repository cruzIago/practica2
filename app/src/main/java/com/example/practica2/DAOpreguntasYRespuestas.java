package com.example.practica2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DAOpreguntasYRespuestas {


    @Insert
    void insertPregunta(preguntas... pregunta);

    @Insert
    void insertRespuesta(respuestas... respuesta);

    @Insert
    void nuevoPerfil(Perfil... perfil);

    @Delete
    void borraPerfil(int id_perfil);

    @Query("UPDATE Perfil SET foto=':foto' WHERE id_perfil=':id_perfil'")
    void actualizaPerfilImagen(byte[] foto, int id_perfil);

    @Query("SELECT * FROM preguntas")
    preguntas[] cargaPreguntas();

    @SuppressWarnings("AndroidUnresolvedRoomSqlReference")
    @Query("SELECT * FROM respuestas WHERE id_p=':idPregunta'")
    respuestas[] cargarRespuestas(int idPregunta);

    @Query("DELETE FROM preguntas")
    void DeleteAllPreguntas();

    @Query("DELETE FROM respuestas")
    void DeleteAllRespuestas();

    @Query("DELETE FROM Perfil")
    void DeleteAllPerfiles();

}

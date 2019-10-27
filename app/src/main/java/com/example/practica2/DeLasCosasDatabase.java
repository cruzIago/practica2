package com.example.practica2;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {preguntas.class,Perfil.class,respuestas.class},version=1)
public abstract class DeLasCosasDatabase extends RoomDatabase {

    public abstract DAOpreguntasYRespuestas DAOpreguntasYRespuestas();

    private static volatile DeLasCosasDatabase INSTANCE;

    static DeLasCosasDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DeLasCosasDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DeLasCosasDatabase.class, "delascosas_database").addCallback(sDeLasCosasDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static DeLasCosasDatabase.Callback sDeLasCosasDatabaseCallback=
            new DeLasCosasDatabase.Callback(){
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void,Void,Void> {
        private final DAOpreguntasYRespuestas mDao;
        PopulateDbAsync(DeLasCosasDatabase db){
            mDao=db.DAOpreguntasYRespuestas();
        }

        @Override
        protected Void doInBackground(final Void... params){
            mDao.DeleteAllPreguntas();
            mDao.DeleteAllRespuestas();
            mDao.DeleteAllPerfiles();
            //Difcultad: 0 facil,1 normal,2 dificil
            //Tipo:0 radioButton, 1 imageButton, 2 audioSource, 3 videoSource;
            mDao.insertPregunta(new preguntas(0,"¿En qué año apareció el primer videojuego?",0,1,null));
            mDao.insertPregunta(new preguntas(1,"¿A qué videojuego pertenece la siguiente imagen?",0,0,null));// Super Mario
            mDao.insertPregunta(new preguntas(2,"¿Qué imagen pertenece al juego Bioshock?",1,0,null));
            mDao.insertPregunta(new preguntas(3,"¿A que videojuego pertenece esta canción?",0,1,null));//Call Of Duty: Modern Warfare 2 Rangers Victory Theme
            mDao.insertPregunta(new preguntas(4,"¿A que videojuego pertenece este video?",0,0,null)); //League of Legends
            mDao.insertPregunta(new preguntas(5,"¿A que videojuego pertenece esta canción?",0,2,null)); //We all become Transistor
            mDao.insertPregunta(new preguntas(6,"¿A que videojuego pertenece este video?",0,1,null)); // Guild Wars 2
            mDao.insertPregunta(new preguntas(7,"¿A que videojuego pertenece esta canción?",0,0,null));
            mDao.insertPregunta(new preguntas(8,"¿A que videojuego pertenece este video?",0,0,null));
            mDao.insertPregunta(new preguntas(9,"¿A que videojuego pertenece este sonido?",0,0,null));
            mDao.insertPregunta(new preguntas(10,"¿A que videojuego pertenece este sonido?",0,0,null));
            mDao.insertPregunta(new preguntas(11,"¿A que videojuego pertenece esta canción?",0,0,null));
            mDao.insertPregunta(new preguntas(12,"¿A que videojuego pertenece esta canción?",0,0,null));
            mDao.insertPregunta(new preguntas(13,"¿Cuál de estos videojuegos no es una franquicia de Nintendo?",0,0,null));
            mDao.insertPregunta(new preguntas(14,"¿A que videojuego pertenece esta imagen?",0,0,null));
            mDao.insertPregunta(new preguntas(15,"",0,0,null));
            mDao.insertPregunta(new preguntas(16,"",0,0,null));
            mDao.insertPregunta(new preguntas(17,"",0,0,null));
            mDao.insertPregunta(new preguntas(18,"",0,0,null));
            mDao.insertPregunta(new preguntas(19,"",0,0,null));
            mDao.insertPregunta(new preguntas(20,"",0,0,null));
            return null;
        }
    }
}

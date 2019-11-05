package com.example.practica2;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Pregunta.class, Perfil.class, Respuesta.class}, version = 1)
public abstract class DeLasCosasDatabase extends RoomDatabase {

    public abstract DAOpreguntasYRespuestas DAOpreguntasYRespuestas();

    private static volatile DeLasCosasDatabase INSTANCE;

    static DeLasCosasDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DeLasCosasDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DeLasCosasDatabase.class, "delascosas_database").allowMainThreadQueries().addCallback(sDeLasCosasDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static DeLasCosasDatabase.Callback sDeLasCosasDatabaseCallback =
            new DeLasCosasDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final DAOpreguntasYRespuestas mDao;

        PopulateDbAsync(DeLasCosasDatabase db) {
            mDao = db.DAOpreguntasYRespuestas();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.DeleteAllPreguntas();
            mDao.DeleteAllRespuestas();
            //mDao.DeleteAllPerfiles();
            //Difcultad: 0 facil,1 normal,2 dificil
            //Tipo:0 radioButton, 1 imageButton, 2 audioSource, 3 videoSource;
            mDao.insertPregunta(new Pregunta(0,"¿En qué año apareció el primer videojuego?",0,1,-1));
            mDao.insertRespuesta(new Respuesta("0_0",0,"1958",true,-1));
            mDao.insertRespuesta(new Respuesta("0_1",0,"1970",false,-1));
            mDao.insertRespuesta(new Respuesta("0_2",0,"1966",false,-1));
            mDao.insertRespuesta(new Respuesta("0_3",0,"1960",false,-1));
            mDao.insertPregunta(new Pregunta(1,"¿A qué videojuego pertenece la siguiente imagen?",0,1,R.drawable.i2_0));// Super Mario
            mDao.insertRespuesta(new Respuesta("1_0",1,"Super Mario Bros",true,-1));
            mDao.insertRespuesta(new Respuesta("1_2",1,"Rayman",false,-1));
            mDao.insertRespuesta(new Respuesta("1_3",1,"Sonic The Hedgehog",false,-1));
            mDao.insertRespuesta(new Respuesta("1_4",1,"Crash Bandicoot",false,-1));
            mDao.insertPregunta(new Pregunta(2,"¿Qué imagen pertenece al juego Bioshock?",1,1,-1));
            mDao.insertRespuesta(new Respuesta("2_0",2,"",false,R.drawable.r2_0));
            mDao.insertRespuesta(new Respuesta("2_1",2,"",true,R.drawable.r2_1));
            mDao.insertRespuesta(new Respuesta("2_2",2,"",false,R.drawable.r2_2));
            mDao.insertRespuesta(new Respuesta("2_3",2,"",false,R.drawable.r2_3));
            mDao.insertPregunta(new Pregunta(3,"¿A que videojuego pertenece esta canción?",0,1,R.raw.s4_0));//Call Of Duty: Modern Warfare 2 Rangers Victory Theme
            mDao.insertRespuesta(new Respuesta("3_0",3,"Call of Duty",true,-1));
            mDao.insertRespuesta(new Respuesta("3_1",3,"Battlefield",false,-1));
            mDao.insertRespuesta(new Respuesta("3_2",3,"DooM",false,-1));
            mDao.insertRespuesta(new Respuesta("3_3",3,"Halo",false,-1));
            mDao.insertPregunta(new Pregunta(4,"¿A que videojuego pertenece este video?",3,1,R.raw.v5_0)); //League of Legends
            mDao.insertRespuesta(new Respuesta("4_0",4,"Dota 2",false,-1));
            mDao.insertRespuesta(new Respuesta("4_1",4,"Heroes of the Storm",false,-1));
            mDao.insertRespuesta(new Respuesta("4_2",4,"Diablo",false,-1));
            mDao.insertRespuesta(new Respuesta("4_3",4,"League of Legends",true,-1));/*
            mDao.insertPregunta(new Pregunta(5,"¿A que videojuego pertenece esta canción?",0,2,-1)); //We all become Transistor
            mDao.insertRespuesta(new Respuesta("5_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("5_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("5_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("5_0","",false,-1));
            mDao.insertPregunta(new Pregunta(6,"¿A que videojuego pertenece este video?",0,1,-1)); // Guild Wars 2
            mDao.insertRespuesta(new Respuesta("6_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("6_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("6_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("6_0","",false,-1));
            mDao.insertPregunta(new Pregunta(7,"¿A que videojuego pertenece esta canción?",0,0,-1));
            mDao.insertRespuesta(new Respuesta("7_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("7_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("7_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("7_0","",false,-1));
            mDao.insertPregunta(new Pregunta(8,"¿A que videojuego pertenece este video?",0,0,-1));
            mDao.insertRespuesta(new Respuesta("8_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("8_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("8_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("8_0","",false,-1));
            mDao.insertPregunta(new Pregunta(9,"¿A que videojuego pertenece este sonido?",0,0,-1));
            mDao.insertRespuesta(new Respuesta("9_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("9_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("9_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("9_0","",false,-1));
            mDao.insertPregunta(new Pregunta(10,"¿A que videojuego pertenece este sonido?",0,0,-1));
            mDao.insertRespuesta(new Respuesta("10_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("10_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("10_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("10_0","",false,-1));
            mDao.insertPregunta(new Pregunta(11,"¿A que videojuego pertenece esta canción?",0,0,-1));
            mDao.insertRespuesta(new Respuesta("11_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("11_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("11_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("11_0","",false,-1));
            mDao.insertPregunta(new Pregunta(12,"¿A que videojuego pertenece esta canción?",0,0,-1));
            mDao.insertRespuesta(new Respuesta("12_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("12_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("12_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("12_0","",false,-1));
            mDao.insertPregunta(new Pregunta(13,"¿Cuál de estos videojuegos no es una franquicia de Nintendo?",0,0,-1));
            mDao.insertRespuesta(new Respuesta("13_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("13_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("13_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("13_0","",false,-1));
            mDao.insertPregunta(new Pregunta(14,"¿A que videojuego pertenece esta imagen?",0,0,-1));
            mDao.insertRespuesta(new Respuesta("14_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("14_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("14_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("14_0","",false,-1));
            mDao.insertPregunta(new Pregunta(15,"¿Cuál es el primer objeto que recoge Link en su primera aventura?",0,0,-1));
            mDao.insertRespuesta(new Respuesta("15_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("15_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("15_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("15_0","",false,-1));
            mDao.insertPregunta(new Pregunta(16,"¿En que año salió Half-Life 2?",0,0,-1));
            mDao.insertRespuesta(new Respuesta("16_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("16_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("16_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("16_0","",false,-1));
            mDao.insertPregunta(new Pregunta(17,"Nintendo comenzó su historia como...",0,0,-1));
            mDao.insertRespuesta(new Respuesta("17_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("17_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("17_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("17_0","",false,-1));
            mDao.insertPregunta(new Pregunta(18,"¿Cuántas generaciones de consola existen actualmente?",0,0,-1));
            mDao.insertRespuesta(new Respuesta("18_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("18_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("18_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("18_0","",false,-1));
            mDao.insertPregunta(new Pregunta(19,"¿Qué consola fue la primera en integrar online?",0,0,-1));
            mDao.insertRespuesta(new Respuesta("19_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("19_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("19_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("19_0","",false,-1));
            mDao.insertPregunta(new Pregunta(20,"¿Qué compañia ostenta el título de la más odiada en Estados Unidos?",0,0,-1));
            mDao.insertRespuesta(new Respuesta("20_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("20_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("20_0","",false,-1));
            mDao.insertRespuesta(new Respuesta("20_0","",false,-1));*/
            return null;
        }
    }
}

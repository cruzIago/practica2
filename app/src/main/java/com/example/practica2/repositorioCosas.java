package com.example.practica2;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class repositorioCosas {
    private DAOpreguntasYRespuestas mDao;
    private List<Perfil> perfiles;

    public repositorioCosas(Application application) {
        DeLasCosasDatabase db = DeLasCosasDatabase.getDatabase(application);
        mDao = db.DAOpreguntasYRespuestas();
        cargarPerfiles();
    }

    public void cargarPerfiles() {
        new CargaAsincronaPerfiles().execute();
    }

    public List<Perfil> getPerfiles() {
        return this.perfiles;
    }

    public void insertarPerfil(Perfil p) {
        new insertarAsincrono(p).execute();
    }

    class CargaAsincronaPerfiles extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            perfiles = mDao.cargarPerfiles();
            return null;
        }

    }

    class insertarAsincrono extends AsyncTask<Void, Void, Void> {
        private Perfil perfil;

        insertarAsincrono(Perfil p) {
            this.perfil = p;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.nuevoPerfil(perfil);
            return null;
        }
    }


}

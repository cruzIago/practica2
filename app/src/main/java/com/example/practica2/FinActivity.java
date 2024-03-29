package com.example.practica2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class FinActivity extends AppCompatActivity {

    private int puntuacion;
    private double tiempo;
    private final double MULT_LOW = 1;
    private final double MULT_MID = 1.2;
    private final double MULT_HIGH = 1.5;
    private final double MULT_GOD = 2;
    private double puntuacionFinal;
    private boolean itsDone=false;
    private TextView tPuntos;
    private Button bMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin);

        Intent intento = getIntent();
        puntuacion = intento.getIntExtra("puntuacion", 0);
        tiempo = intento.getDoubleExtra("tiempo", -1.0);

        tPuntos = findViewById(R.id.tPuntuaciones);
        bMenu = findViewById(R.id.bMenu);

        SharedPreferences settings = getSharedPreferences(OpcionesActivity.PREFS_NAME, 0);

        if (tiempo >= 130) {
            puntuacionFinal = puntuacion * MULT_LOW;
        } else if (tiempo >= 75) {
            puntuacionFinal = puntuacion * MULT_MID;
        } else if (tiempo >= 40) {
            puntuacionFinal = puntuacion * MULT_HIGH;
        } else if (tiempo >= 0) {
            puntuacionFinal = puntuacion * MULT_GOD;
        }

        puntuacionFinal = puntuacionFinal < 0 ? 0 : puntuacionFinal;
        tPuntos.setText("" + puntuacionFinal);
        int idPerfil = settings.getInt("idActual", 0);
        int partidasJugadas=settings.getInt("partidasTotalesid",0);
        new ActualizaMaximaPuntuacion(idPerfil,puntuacionFinal,partidasJugadas).execute();

        bMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itsDone) {
                    Intent intento = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intento);
                    finish();
                }
            }
        });
    }
    class ActualizaMaximaPuntuacion extends AsyncTask<Void, Void, Void> {
        private int idPerfil;
        private double puntuacionFinal;
        private int nPartidas;

        ActualizaMaximaPuntuacion(int idPerfil,double puntuacionFinal,int partidasJugadas){
            this.idPerfil=idPerfil;
            this.puntuacionFinal=puntuacionFinal;
            this.nPartidas=partidasJugadas;
        }
        @Override
        protected Void doInBackground(Void... voids) {

            DeLasCosasDatabase.getDatabase(getApplicationContext()).DAOpreguntasYRespuestas().actualizaMaximaPuntuacion(idPerfil, puntuacionFinal);
            DeLasCosasDatabase.getDatabase(getApplicationContext()).DAOpreguntasYRespuestas().actualizaPartidasJugadasd(idPerfil,nPartidas);
            DeLasCosasDatabase.getDatabase(getApplicationContext()).DAOpreguntasYRespuestas().actualizaUltimaPartida(idPerfil, Calendar.getInstance().getTime().toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            itsDone=true;
        }
    }
}

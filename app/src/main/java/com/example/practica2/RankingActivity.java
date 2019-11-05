package com.example.practica2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity {
    private final int MAX_PERFILES = 3;
    private CardView cartas_ranking[] = new CardView[MAX_PERFILES];
    private ImageView imagenesRanking[] = new ImageView[MAX_PERFILES];
    private TextView nombresRanking[] = new TextView[MAX_PERFILES];
    private TextView puntuacionRanking[] = new TextView[MAX_PERFILES];

    private List<Perfil> perfilesOrdenados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        cartas_ranking[0] = findViewById(R.id.carta_ranking_1);
        cartas_ranking[1] = findViewById(R.id.carta_ranking_2);
        cartas_ranking[2] = findViewById(R.id.carta_ranking_3);

        imagenesRanking[0] = findViewById(R.id.rFoto_1);
        imagenesRanking[1] = findViewById(R.id.rFoto_2);
        imagenesRanking[2] = findViewById(R.id.rFoto_3);

        nombresRanking[0] = findViewById(R.id.rAlias_1);
        nombresRanking[1] = findViewById(R.id.rAlias_2);
        nombresRanking[2] = findViewById(R.id.rAlias_3);

        puntuacionRanking[0] = findViewById(R.id.rPuntos_1);
        puntuacionRanking[1] = findViewById(R.id.rPuntos_2);
        puntuacionRanking[2] = findViewById(R.id.rPuntos_3);
        perfilesOrdenados = new ArrayList<>();
        new conseguirRanking().execute();
    }

    private void recargarPerfiles() {
        int i = 0;
        for (Perfil p : perfilesOrdenados) {
            puntuacionRanking[i].setText(Double.toString(p.maxPuntuacion));
            nombresRanking[i].setText(p.nombre);
            imagenesRanking[i].setImageURI(Uri.parse(p.foto));
            puntuacionRanking[i].setVisibility(View.VISIBLE);
            nombresRanking[i].setVisibility(View.VISIBLE);
            imagenesRanking[i].setVisibility(View.VISIBLE);
            cartas_ranking[i].setVisibility(View.VISIBLE);
            i++;
        }
    }

    class conseguirRanking extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            perfilesOrdenados = DeLasCosasDatabase.getDatabase(getApplicationContext()).DAOpreguntasYRespuestas().cargaRanking();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recargarPerfiles();
        }
    }
}

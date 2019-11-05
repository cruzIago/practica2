package com.example.practica2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Variables
    private Button bJugar, bOpciones, bRanking, bPerfil;
    private repositorioCosas rC;
    private List<Perfil> perfiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bJugar = findViewById(R.id.bJugar);
        bOpciones = findViewById(R.id.bOpciones);
        bRanking = findViewById(R.id.bRanking);
        bPerfil = findViewById(R.id.bPerfil);

        //Inicia la base de datos
        rC = new repositorioCosas(getApplication());
        rC.cargarPerfiles();
        perfiles = rC.getPerfiles();

        bJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CargaAsincronaPerfiles().execute();
            }

        });


        bOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(v.getContext(), OpcionesActivity.class);
                startActivity(intento);
            }
        });

        bRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(v.getContext(), RankingActivity.class);
                startActivity(intento);
            }
        });

        bPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(v.getContext(), PerfilActivity.class);
                startActivity(intento);
            }
        });


    }
    private void dialogoNoPerfil(){
        if (perfiles != null && perfiles.size() > 0) {
            Intent intento = new Intent(this, JugarActivity.class);
            startActivity(intento);
        } else {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle("Debes hacerte un perfil para continuar");
            dialogo.setCancelable(false);
            dialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intento = new Intent(getApplicationContext(), PerfilActivity.class);
                    startActivity(intento);
                }
            });
            dialogo.show();

        }
    }

    class CargaAsincronaPerfiles extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            perfiles = DeLasCosasDatabase.getDatabase(getApplicationContext()).DAOpreguntasYRespuestas().cargarPerfiles();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialogoNoPerfil();
        }

    }
}

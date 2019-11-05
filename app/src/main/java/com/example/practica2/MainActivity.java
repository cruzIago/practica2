package com.example.practica2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Variables
    private Button bJugar, bOpciones, bRanking,bPerfil;
    private repositorioCosas rC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bJugar = findViewById(R.id.bJugar);
        bOpciones = findViewById(R.id.bOpciones);
        bRanking = findViewById(R.id.bRanking);
        bPerfil=findViewById(R.id.bPerfil);

        //Inicia la base de datos
        rC=new repositorioCosas(getApplication());
        rC.cargarPerfiles();
        bJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(v.getContext(), JugarActivity.class);
                startActivity(intento);
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
               /* Intent intento = new Intent(v.getContext(), OpcionesActivity.class);
                startActivity(intento);*/
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


}

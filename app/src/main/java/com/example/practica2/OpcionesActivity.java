package com.example.practica2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class OpcionesActivity extends AppCompatActivity {

    public static final String PREFS_NAME="opciones_usuario";
    private SeekBar dificultad_bar,cantidad_bar;
    private TextView dificultad_t,cantidad_t;
    private int dificultad,cantidad,cantidad_p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
        dificultad_bar=findViewById(R.id.sbDificultad);
        dificultad_t=findViewById(R.id.tsDificultad);
        cantidad_bar=findViewById(R.id.sbCantidad);
        cantidad_t=findViewById(R.id.tsCantidad);
        restoreBefore();

        dificultad_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dificultad=progress;
                changeVisuals();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        cantidad_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cantidad=progress;
                changeVisuals();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void restoreBefore(){
        SharedPreferences settings=getSharedPreferences(PREFS_NAME,0);
        dificultad=settings.getInt("dificultad_preguntas",1);
        cantidad=settings.getInt("cantidad",1);
        cantidad_p=settings.getInt("cantidad_preguntas",5);
        changeVisuals();
    }

    @Override
    protected void onStop(){
        super.onStop();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor=settings.edit();
        editor.putInt("dificultad_preguntas",dificultad);
        editor.putInt("cantidad",cantidad);
        editor.putInt("cantidad_preguntas",cantidad_p);
        editor.commit();
    }
    private void changeVisuals(){
        switch(dificultad){
            case 0:
                dificultad_bar.setProgress(0);
                dificultad_t.setText(R.string.tDificultadFacil);
                break;
            case 1:
                dificultad_bar.setProgress(1);
                dificultad_t.setText(R.string.tDificultadNormal);
                break;
            case 2:
                dificultad_bar.setProgress(2);
                dificultad_t.setText(R.string.tDificultadDificil);
                break;
            default:
                dificultad_bar.setProgress(1);
                dificultad_t.setText(R.string.tDificultadNormal);
                break;
        }

        switch(cantidad){
            case 0:
                cantidad_bar.setProgress(0);
                cantidad_t.setText(R.string.tCantidad5);
                cantidad_p=5;
                break;
            case 1:
                cantidad_bar.setProgress(1);
                cantidad_t.setText(R.string.tCantidad10);
                cantidad_p=10;
                break;
            case 2:
                cantidad_bar.setProgress(2);
                cantidad_t.setText(R.string.tCantidad15);
                cantidad_p=15;
                break;
            default:
                cantidad_bar.setProgress(1);
                cantidad_t.setText(R.string.tCantidad10);
                cantidad_p=10;
                break;
        }
    }
}

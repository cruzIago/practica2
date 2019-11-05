package com.example.practica2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class JugarActivity extends AppCompatActivity {

    private final int MAX_PREGUNTAS = 20;

    private int nPreguntas = 0;
    private int dificultad, cantidad;
    private int puntuaciones;
    private boolean isTiempoOnline = false;

    private GridLayout glRadioPreguntas;
    private GridLayout glImageButtonPreguntas;
    private RadioGroup rgRespuestas;
    private TextView tPreguntas;
    private TextView tPuntuacion;
    private TextView tNPreguntas;
    private ImageView ivPreguntas;
    private VideoView vvPreguntas;
    private RadioButton rb0, rb1, rb2, rb3;
    private ImageButton ib0, ib1, ib2, ib3;
    private Chronometer cTiempo;
    private List<Pregunta> preguntas;
    private Respuesta[] respuestas;
    private Button bSonidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);

        puntuaciones = 0;

        tPreguntas = findViewById(R.id.textoPreguntas);
        tPuntuacion = findViewById(R.id.tPuntuacion);
        tNPreguntas = findViewById(R.id.tNPreguntas);
        tPuntuacion.setText("" + puntuaciones);
        ivPreguntas = findViewById(R.id.imagenPreguntas);
        rgRespuestas = findViewById(R.id.grupoRespuestas);

        rb0 = findViewById(R.id.radioRespuesta1);
        rb1 = findViewById(R.id.radioRespuesta2);
        rb2 = findViewById(R.id.radioRespuesta3);
        rb3 = findViewById(R.id.radioRespuesta4);

        ib0 = findViewById(R.id.imagen_boton_1);
        ib1 = findViewById(R.id.imagen_boton_2);
        ib2 = findViewById(R.id.imagen_boton_3);
        ib3 = findViewById(R.id.imagen_boton_4);

        bSonidos = findViewById(R.id.bSonidos);

        glRadioPreguntas = findViewById(R.id.radioPreguntas);
        glImageButtonPreguntas = findViewById(R.id.imagebuttonPreguntas);

        vvPreguntas = findViewById(R.id.videoPreguntas);
        cTiempo = findViewById(R.id.cTiempo);

        SharedPreferences settings = getSharedPreferences(OpcionesActivity.PREFS_NAME, 0);
        dificultad = settings.getInt("dificultad_preguntas", 1);
        cantidad = settings.getInt("cantidad_preguntas", 5);
        tNPreguntas.setText(nPreguntas + "/" + cantidad);
        retrieveQuiz();
    }

    private void startTimer() {
        if (!isTiempoOnline) {
            isTiempoOnline = true;
            cTiempo.start();
        }
    }

    private void checkEnding(Pregunta pregunta) {
        tPuntuacion.setText("" + puntuaciones);
        tNPreguntas.setText(nPreguntas + "/" + cantidad);
        if (nPreguntas >= cantidad || preguntas.size()<=nPreguntas) {
            Intent intento = new Intent(this, FinActivity.class);
            intento.putExtra("puntuacion", puntuaciones);
            intento.putExtra("tiempo", (double) SystemClock.elapsedRealtime() - cTiempo.getBase());
            startActivity(intento);
            finish();
        } else {
            //preguntas.remove(pregunta);
            setPreguntas();
        }

    }

    private void retrieveQuiz() {
        final Context c = this;
        class RetrieveQuiz extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                switch (dificultad) {
                    case 0:
                        preguntas = DeLasCosasDatabase.getDatabase(getApplicationContext()).DAOpreguntasYRespuestas().cargaPreguntas(dificultad, dificultad + 1);
                        break;
                    case 1:
                        preguntas = DeLasCosasDatabase.getDatabase(getApplicationContext()).DAOpreguntasYRespuestas().cargaPreguntas(dificultad, dificultad + 1);
                        break;
                    case 2:
                        preguntas = DeLasCosasDatabase.getDatabase(getApplicationContext()).DAOpreguntasYRespuestas().cargaPreguntas(dificultad - 1, dificultad + 1);
                        break;
                    default:
                        preguntas = DeLasCosasDatabase.getDatabase(getApplicationContext()).DAOpreguntasYRespuestas().cargaPreguntas(dificultad, dificultad + 1);
                        break;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Collections.shuffle(preguntas);
                setPreguntas();
            }
        }
        RetrieveQuiz rt = new RetrieveQuiz();
        rt.execute();
    }

    private void retrieveAnswers(Pregunta p) {
        final Pregunta pregunta = p;
        new RetrieveAnswer(pregunta).execute();
    }

    class RetrieveAnswer extends AsyncTask<Void, Void, Void> {
        private Pregunta pregunta;

        RetrieveAnswer(Pregunta pregunta) {
            this.pregunta = pregunta;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            respuestas = DeLasCosasDatabase.getDatabase(getApplicationContext()).DAOpreguntasYRespuestas().cargarRespuestas(pregunta.id_p);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (pregunta.tipo == 0 || pregunta.tipo == 2 || pregunta.tipo == 3) {
                setRadioRespuestas(pregunta);
            } else {
                setImageRespuestas(pregunta);
            }

        }
    }

    private void setPreguntas() {
        ivPreguntas.setVisibility(View.INVISIBLE);
        vvPreguntas.setVisibility(View.INVISIBLE);
        bSonidos.setVisibility(View.INVISIBLE);
        glRadioPreguntas.setVisibility(View.INVISIBLE);
        glImageButtonPreguntas.setVisibility(View.INVISIBLE);
        tPreguntas.setVisibility(View.INVISIBLE);

        //Random r = new Random();
        //Pregunta p = preguntas.get(preguntas.size()>0?r.nextInt(preguntas.size()):0);
        Pregunta p = preguntas.get(nPreguntas);
        nPreguntas += 1;
        tPreguntas.setText(p.pregunta);
        tPreguntas.setVisibility(View.VISIBLE);

        retrieveAnswers(p);
    }


    private void setImageRespuestas(Pregunta id_p) {
        final Pregunta pregunta = id_p;
        ib0.setImageResource(respuestas[0].idImagen);
        ib1.setImageResource(respuestas[1].idImagen);
        ib2.setImageResource(respuestas[2].idImagen);
        ib3.setImageResource(respuestas[3].idImagen);
        startTimer();
        glImageButtonPreguntas.setVisibility(View.VISIBLE);
        ib0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (respuestas[0].isCorrecta) {
                    puntuaciones += 3;
                } else {
                    puntuaciones -= 2;
                }
                checkEnding(pregunta);
            }
        });
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (respuestas[1].isCorrecta) {
                    puntuaciones += 3;
                } else {
                    puntuaciones -= 2;
                }
                checkEnding(pregunta);
            }
        });
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (respuestas[2].isCorrecta) {
                    puntuaciones += 3;
                } else {
                    puntuaciones -= 2;
                }
                checkEnding(pregunta);
            }
        });
        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (respuestas[3].isCorrecta) {
                    puntuaciones += 3;
                } else {
                    puntuaciones -= 2;
                }
                checkEnding(pregunta);
            }
        });

    }

    private boolean isPress=false;
    @SuppressLint("ClickableViewAccessibility")
    private void setRadioRespuestas(Pregunta id_p) {
        final Pregunta pregunta = id_p;
        rb0.setText(respuestas[0].respuesta);
        rb1.setText(respuestas[1].respuesta);
        rb2.setText(respuestas[2].respuesta);
        rb3.setText(respuestas[3].respuesta);
        if (pregunta.idMedia != -1) {
            if (getResources().getString(pregunta.idMedia).contains("i")) {
                ivPreguntas.setImageResource(pregunta.idMedia);
                ivPreguntas.setVisibility(View.VISIBLE);
            } else if (getResources().getString(pregunta.idMedia).contains("v")) {
                String path = "android.resource://" + getPackageName() + "/" + pregunta.idMedia;
                MediaController mc = new MediaController(this);
                vvPreguntas.setMediaController(mc);
                vvPreguntas.setVideoURI(Uri.parse(path));
                vvPreguntas.setVisibility(View.VISIBLE);
                vvPreguntas.requestFocus();
                vvPreguntas.start();
            } else if (getResources().getString(pregunta.idMedia).contains("s")) {
                final MediaPlayer mp = MediaPlayer.create(this, pregunta.idMedia);
                bSonidos.setVisibility(View.VISIBLE);
                bSonidos.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            mp.start();
                            return true;
                        } else {
                            mp.stop();
                            mp.prepareAsync();
                        }
                        return false;
                    }
                });
            }
        }
        startTimer();
        glRadioPreguntas.setVisibility(View.VISIBLE);
        rgRespuestas.clearCheck();
        isPress=false;
        rgRespuestas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(!isPress) {
                    switch (checkedId) {
                        case R.id.radioRespuesta1:
                            if (respuestas[0].isCorrecta) {
                                puntuaciones += 3;
                            } else {
                                puntuaciones -= 2;
                            }
                            break;
                        case R.id.radioRespuesta2:
                            if (respuestas[1].isCorrecta) {
                                puntuaciones += 3;
                            } else {
                                puntuaciones -= 2;
                            }
                            break;
                        case R.id.radioRespuesta3:
                            if (respuestas[2].isCorrecta) {
                                puntuaciones += 3;
                            } else {
                                puntuaciones -= 2;
                            }
                            break;
                        case R.id.radioRespuesta4:
                            if (respuestas[3].isCorrecta) {
                                puntuaciones += 3;
                            } else {
                                puntuaciones -= 2;
                            }
                            break;
                        default:
                            break;
                    }
                    isPress=true;
                    checkEnding(pregunta);
                }
            }
        });

    }
}

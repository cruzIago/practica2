package com.example.practica2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PerfilActivity extends AppCompatActivity {

    private final int MAX_PERFILES = 3;

    private TextView[] ultima = new TextView[MAX_PERFILES], maxPuntos = new TextView[MAX_PERFILES], partidas = new TextView[MAX_PERFILES], nombre = new TextView[MAX_PERFILES];
    private Button[] bNuevo = new Button[MAX_PERFILES], bEditar = new Button[MAX_PERFILES], bBorrar = new Button[MAX_PERFILES];
    private ImageView[] iFoto = new ImageView[MAX_PERFILES];
    private CardView[] carta_perfil = new CardView[MAX_PERFILES];
    private String alias;
    private Uri foto_uri_1;

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private repositorioCosas rC;
    private List<Perfil> perfiles;
    private int idActualizar;
    private boolean isNuevo = false, isActualiza = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        rC = new repositorioCosas(getApplication());
        perfiles = new ArrayList<>();

        ultima[0] = findViewById(R.id.tUltima_1);
        maxPuntos[0] = findViewById(R.id.tPuntos_1);
        partidas[0] = findViewById(R.id.tPartidas_1);
        nombre[0] = findViewById(R.id.tNombre_1);
        bNuevo[0] = findViewById(R.id.bNuevo_1);
        bEditar[0] = findViewById(R.id.bEditar_1);
        bBorrar[0] = findViewById(R.id.bBorrar_1);
        iFoto[0] = findViewById(R.id.iFoto_1);
        carta_perfil[0] = findViewById(R.id.carta_perfil_1);

        ultima[1] = findViewById(R.id.tUltima_2);
        maxPuntos[1] = findViewById(R.id.tPuntos_2);
        partidas[1] = findViewById(R.id.tPartidas_2);
        nombre[1] = findViewById(R.id.tNombre_2);
        bNuevo[1] = findViewById(R.id.bNuevo_2);
        bEditar[1] = findViewById(R.id.bEditar_2);
        bBorrar[1] = findViewById(R.id.bBorrar_2);
        iFoto[1] = findViewById(R.id.iFoto_2);
        carta_perfil[1] = findViewById(R.id.carta_perfil_2);

        ultima[2] = findViewById(R.id.tUltima_3);
        maxPuntos[2] = findViewById(R.id.tPuntos_3);
        partidas[2] = findViewById(R.id.tPartidas_3);
        nombre[2] = findViewById(R.id.tNombre_3);
        bNuevo[2] = findViewById(R.id.bNuevo_3);
        bEditar[2] = findViewById(R.id.bEditar_3);
        bBorrar[2] = findViewById(R.id.bBorrar_3);
        iFoto[2] = findViewById(R.id.iFoto_3);
        carta_perfil[2] = findViewById(R.id.carta_perfil_2);

        for (int i = 0; i < MAX_PERFILES; i++) {
            bNuevo[i].setVisibility(View.VISIBLE);
        }

        new CargaAsincronaPerfiles().execute();
        setNuevosPerfiles();
        setBorrarPerfil();
        setActualizarPerfil();
        setActivar();
    }

    private void dialogoNuevoPerfil() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Introduce un nombre");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        dialogo.setView(input);

        dialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alias = input.getText().toString();
                dialogoNuevaFoto();
            }
        });
        dialogo.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialogo.show();
    }

    private void dialogoNuevaFoto() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("¡Ahora una foto!");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pedirPermisos();
            }
        });
        dialogo.show();
    }

    private void dialogoBorrado(int aBorrar_) {
        final int aBorrar = aBorrar_;
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("¿Seguro que quieres borrar el perfil?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new BorradoAsincronoPerfil(perfiles.get(aBorrar)).execute();
            }
        });
        dialogo.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialogo.show();
    }

    private int iActivar;

    private void setActivar() {
        for (iActivar = 0; iActivar < MAX_PERFILES; iActivar++) {
            iFoto[iActivar].setOnClickListener(new View.OnClickListener() {
                private int idActual = iActivar;

                @Override
                public void onClick(View v) {
                    SharedPreferences settings = getSharedPreferences(OpcionesActivity.PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("idActual", perfiles.get(idActual).id_perfil);
                    editor.putInt("partidasTotalesid",perfiles.get(idActual).partidasJugadas);
                    editor.commit();
                    recargarPerfiles();
                }
            });
        }
    }

    private int iActualizado;

    private void setActualizarPerfil() {
        for (iActualizado = 0; iActualizado < MAX_PERFILES; iActualizado++) {
            bEditar[iActualizado].setOnClickListener(new View.OnClickListener() {
                private int aActualizar = iActualizado;

                @Override
                public void onClick(View v) {
                    idActualizar = aActualizar;
                    isActualiza = true;
                    dialogoNuevaFoto();
                }
            });
        }
    }

    private int iBorrado;

    private void setBorrarPerfil() {
        for (iBorrado = 0; iBorrado < MAX_PERFILES; iBorrado++) {
            bBorrar[iBorrado].setOnClickListener(new View.OnClickListener() {
                private int aBorrar = iBorrado;

                @Override
                public void onClick(View v) {
                    SharedPreferences settings = getSharedPreferences(OpcionesActivity.PREFS_NAME,0);
                    SharedPreferences.Editor editor=settings.edit();
                    if(settings.getInt("idActual",-1)==aBorrar){
                        editor.putInt("idActual",-1);
                        editor.commit();
                    }
                    dialogoBorrado(aBorrar);
                }
            });
        }
    }

    private int iNuevoPerfil;

    private void setNuevosPerfiles() {
        for (iNuevoPerfil = 0; iNuevoPerfil < MAX_PERFILES; iNuevoPerfil++) {
            bNuevo[iNuevoPerfil].setOnClickListener(new View.OnClickListener() {
                private int nId = iNuevoPerfil;

                @Override
                public void onClick(View v) {
                    isNuevo = true;
                    dialogoNuevoPerfil();
                }
            });
        }
    }

    private void recargarPerfiles() {
        SharedPreferences settings = getSharedPreferences(OpcionesActivity.PREFS_NAME, 0);
        int idActual = settings.getInt("idActual", 0);
        for (int i = 0; i < MAX_PERFILES; i++) {
            if (perfiles.size() > i) {
                nombre[i].setText(perfiles.get(i).nombre);
                maxPuntos[i].setText("Max.Puntos: " + perfiles.get(i).maxPuntuacion);
                ultima[i].setText("Ult.Partida: " + perfiles.get(i).ultimaPartida);
                partidas[i].setText("Partidas: " + perfiles.get(i).partidasJugadas);
                iFoto[i].setImageURI(Uri.parse(perfiles.get(i).foto));
                nombre[i].setVisibility(View.VISIBLE);
                maxPuntos[i].setVisibility(View.VISIBLE);
                ultima[i].setVisibility(View.VISIBLE);
                partidas[i].setVisibility(View.VISIBLE);
                iFoto[i].setVisibility(View.VISIBLE);
                bNuevo[i].setVisibility(View.INVISIBLE);
                bEditar[i].setVisibility(View.VISIBLE);
                bBorrar[i].setVisibility(View.VISIBLE);
                carta_perfil[i].setCardBackgroundColor(idActual == perfiles.get(i).id_perfil ? getResources().getColor(R.color.backCartaActivada) : getResources().getColor(R.color.backCartaDesactivada));
            } else {
                nombre[i].setVisibility(View.INVISIBLE);
                maxPuntos[i].setVisibility(View.INVISIBLE);
                ultima[i].setVisibility(View.INVISIBLE);
                partidas[i].setVisibility(View.INVISIBLE);
                iFoto[i].setVisibility(View.INVISIBLE);
                bNuevo[i].setVisibility(View.VISIBLE);
                bEditar[i].setVisibility(View.INVISIBLE);
                bBorrar[i].setVisibility(View.INVISIBLE);
            }
        }
    }


    private void pedirPermisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, PERMISSION_CODE);
            } else {
                hacerFoto();
            }
        } else {
            hacerFoto();
        }
    }


    private void hacerFoto() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        foto_uri_1 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, foto_uri_1);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //this method is called, when user presses Allow or Deny from Permission Request Popup
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission from popup was granted
                    hacerFoto();
                } else {
                    //permission from popup was denied
                    Toast.makeText(this, "Permiso denegado...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && isNuevo) {
            isNuevo = false;
            Perfil p = new Perfil(alias, 0, 0, Calendar.getInstance().getTime().toString(), foto_uri_1.toString());
            rC.insertarPerfil(p);
            new CargaAsincronaPerfiles().execute();
        } else if (resultCode == RESULT_OK && isActualiza) {
            isActualiza = false;
            new UpdateAsincronoPerfil(foto_uri_1.toString(), perfiles.get(idActualizar).id_perfil).execute();
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
            recargarPerfiles();
        }
    }

    class UpdateAsincronoPerfil extends AsyncTask<Void, Void, Void> {
        private String foto;
        private int id;

        UpdateAsincronoPerfil(String foto, int id) {
            this.foto = foto;
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DeLasCosasDatabase.getDatabase(getApplicationContext()).DAOpreguntasYRespuestas().actualizaPerfilImagen(foto, id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recargarPerfiles();
        }
    }

    class BorradoAsincronoPerfil extends AsyncTask<Void, Void, Void> {
        private Perfil perfil;

        BorradoAsincronoPerfil(Perfil p) {
            perfil = p;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DeLasCosasDatabase.getDatabase(getApplicationContext()).DAOpreguntasYRespuestas().borraPerfil(perfil);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            perfiles.remove(perfil);
            recargarPerfiles();
        }
    }
}

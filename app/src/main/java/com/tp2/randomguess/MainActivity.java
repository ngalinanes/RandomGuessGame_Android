package com.tp2.randomguess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText Guess;
    int numero;
    TextView PuntajeActual, MejorPuntaje;
    Button buttonGuess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGuess = (Button) findViewById(R.id.buttonGuess);
        MejorPuntaje = (TextView) findViewById(R.id.mejor_puntaje);
        PuntajeActual = (TextView) findViewById(R.id.puntaje_actual);
        Guess = (EditText) findViewById(R.id.guess);

        SharedPreferences preferencias = getSharedPreferences("puntos", Context.MODE_PRIVATE);
        SharedPreferences intentos = getSharedPreferences("intentos", Context.MODE_PRIVATE);
        String puntaje = String.valueOf(preferencias.getInt("puntos", 0));
        String msg = "Mejor puntaje: " + puntaje;
        MejorPuntaje.setText(msg);

        buttonGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificar(v);
            }

            private void verificar(View v) {
                numero = (int)(Math.random() * (5 - 1) + 1);
                int valor = Integer.parseInt(Guess.getText().toString());
                if (valor == numero) {
                    int puntosactual = Integer.parseInt(PuntajeActual.getText().toString());
                    int nuevopuntaje = puntosactual + 10;
                    PuntajeActual.setText(String.valueOf(nuevopuntaje));
                    Toast.makeText(MainActivity.this, "Adivinaste!. Ahora pienso otro numero.", Toast.LENGTH_SHORT).show();
                    Guess.setText("");
                    int puntos = preferencias.getInt("puntos", 0);
                    if (puntos < nuevopuntaje) {
                        SharedPreferences.Editor editor = preferencias.edit();
                        editor.putInt("puntos", nuevopuntaje);
                        editor.apply();
                    }
                    numero = (int)(Math.random() * (5 - 1) + 1);
                } else {
                    int puntosactual = Integer.parseInt(PuntajeActual.getText().toString());
                    int intento = intentos.getInt("intentos", 0);

                    if (intento < 5) {
                        intento++;
                        SharedPreferences.Editor editor = intentos.edit();
                        editor.putInt("intentos", intento);
                        editor.apply();
                        if (puntosactual >= 5) {
                            int nuevopuntaje = puntosactual - 5;
                            PuntajeActual.setText(String.valueOf(nuevopuntaje));
                            Toast.makeText(MainActivity.this, "Te equivocaste! Ingrese otro numero.", Toast.LENGTH_SHORT).show();
                            Guess.setText("");
                        } else {
                            Toast.makeText(MainActivity.this, "Te equivocaste! Ingrese otro numero.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                            PuntajeActual.setText(String.valueOf(0));
                            SharedPreferences.Editor editor = intentos.edit();
                            editor.putInt("intentos", 0);
                            editor.apply();
                            Toast.makeText(MainActivity.this, "Te has quedado sin intentos! Vuelve a intentarlo.", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });

    }

}

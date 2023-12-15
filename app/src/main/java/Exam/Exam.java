
package Exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Store.Connectionquestions;
import Store.Userconnection;
import DTOs.Dataquestions;
import DTOs.Userdata;
import mx.edu.tesoem.isc.proyecto.Principal;
import mx.edu.tesoem.isc.proyecto.R;

public class Exam extends AppCompatActivity {

    Button siguiente, anterior, terminar;
    TextView pregunta;
    RadioGroup rg;
    RadioButton rb1, rb2, rb3;
    List<Dataquestions> listaPreguntas = new ArrayList<>();
    List<Userdata> listaDatos = new ArrayList<>();
    int puntaje = 0, aciertos = 0, contador = 0, idC = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examen);

        siguiente = findViewById(R.id.btnsiguiente);
        anterior = findViewById(R.id.btnanterior);
        terminar = findViewById(R.id.btnterminar);

        pregunta = findViewById(R.id.tvpregunta);
        rg = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);

        siguiente.setEnabled(false);
        terminar.setEnabled(false);
        anterior.setEnabled(false);

        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        double latitud = intent.getDoubleExtra("latitud", 0.0);
        double longitud = intent.getDoubleExtra("longitud", 0.0);

        Connectionquestions connectionquestions = new Connectionquestions();
        if (connectionquestions.Leer(this)){
            listaPreguntas = connectionquestions.getDatos();
            if (listaPreguntas.size() > 0){
                mostrarPreguntaActual();

                siguiente.setOnClickListener(view -> {
                    String seleccion = seleccionr();
                    if(seleccion != null){
                        listaPreguntas.get(contador).setRespuestaSeleccionada(seleccion);
                    }
                    contador++;
                    if (contador < listaPreguntas.size()){
                        mostrarPreguntaActual();
                        rg.clearCheck();
                    }
                });

                rg.setOnCheckedChangeListener((radioGroup, i) -> {
                    boolean isAnyRadioButtonSelected = rb1.isChecked() || rb2.isChecked() || rb3.isChecked();

                    siguiente.setEnabled(isAnyRadioButtonSelected);
                    anterior.setEnabled(contador != 0);

                    if (contador == listaPreguntas.size() - 1) {
                        siguiente.setEnabled(false);
                        terminar.setEnabled(true);
                    }
                });

                anterior.setOnClickListener(v -> {
                    contador--;
                    if (contador >= 0 && contador < listaPreguntas.size()){
                        mostrarPreguntaActual();
                        rg.clearCheck();
                    }
                });

                terminar.setOnClickListener(v -> {
                    if (contador >= 0 && contador < listaPreguntas.size()) {
                        Dataquestions preguntaActual = listaPreguntas.get(contador);
                        String seleccion = seleccionr();

                        if (seleccion != null) {
                            preguntaActual.setRespuestaSeleccionada(seleccion);
                        }
                    }
                    puntaje = 0;
                    aciertos = 0;

                    for (Dataquestions pregunta : listaPreguntas) {
                        String respuestaSeleccionadaPregunta = pregunta.getRespuestaSeleccionada();

                        if (respuestaSeleccionadaPregunta != null) {
                            if (respuestaSeleccionadaPregunta.equals(pregunta.getRes())) {
                                puntaje++;
                                aciertos++;
                            }
                        }
                    }

                    double porcentaje = (double) aciertos / listaPreguntas.size() * 100;
                    //Toast.makeText(this, "Porcentaje de respuestas: " + porcentaje + "%", Toast.LENGTH_SHORT).show();
                    
                    grabar(nombre, latitud, longitud, porcentaje);
                    Intent intent1 = new Intent(this, Principal.class);
                    startActivity(intent1);
                });
            }
        }
    }

    private void mostrarPreguntaActual() {
        Dataquestions preguntaActual = listaPreguntas.get(idC - 1);
        pregunta.setText("Pregunta " + (idC) + ".\n" + "¿" + preguntaActual.getPregunta() + "?");
        rb1.setText("a) " + preguntaActual.getR1());
        rb2.setText("b) " + preguntaActual.getR2());
        rb3.setText("c) " + preguntaActual.getR3());
        idC++;
    }
    private String seleccionr(){
        if (rb1.isChecked()){
            return "a";
        } else if (rb2.isChecked()) {
            return "b";
        } else if (rb3.isChecked()) {
            return "c";
        }else{
            return null;
        }
    }

    private void grabar(String nombre, double latitud, double longitud, double porcentaje) {
        Userdata userdata = new Userdata(nombre, Double.toString(latitud), Double.toString(longitud), String.valueOf(porcentaje));
        listaDatos.add(userdata);
        Userconnection userconnection = new Userconnection();
        if (userconnection.Grabar(this, listaDatos)) {
            Toast.makeText(this, "Se guardaron las respuestas con éxito", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al guardar las respuestas", Toast.LENGTH_SHORT).show();
        }
    }
}
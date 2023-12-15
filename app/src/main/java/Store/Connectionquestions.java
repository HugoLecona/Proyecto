package Store;

import android.app.Activity;
import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import Conversions.ConversionQuestions;
import DTOs.Dataquestions;

public class Connectionquestions {
    String cadena;
    private final String NomArch="Preguntas.txt";
    ConversionQuestions conversionp = new ConversionQuestions();
    List<Dataquestions> listaPreguntas = new ArrayList<>();
    public boolean Grabar(Context context, List<Dataquestions> datosp) {
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(context.openFileOutput(NomArch, Activity.MODE_PRIVATE));
            for (Dataquestions dato : datosp) {
                cadena = conversionp.Cjson(dato);
                archivo.write(cadena + "\n");
            }

            archivo.flush();
            archivo.close();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean Leer(Context context) {
        try {
            InputStreamReader archivo = new InputStreamReader(context.openFileInput(NomArch));
            BufferedReader br = new BufferedReader(archivo);
            while ((cadena = br.readLine()) != null) {
                listaPreguntas.add(conversionp.Cdto(cadena));
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    public List<Dataquestions> getDatos(){return listaPreguntas;

    }
}

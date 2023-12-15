package Store;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import Conversions.UserConversion;
import DTOs.Userdata;

public class Userconnection {

    String cadena;
    private final String NomArch="Usuario.txt";
    UserConversion conversionu = new UserConversion();
    List<Userdata> listaDatos = new ArrayList<Userdata>();
    public boolean Grabar(Context context, List<Userdata> datos) {
        try {

            String[] archivos = context.fileList();
            if (archivos.length >= 20) {
                // Eliminar los archivos anteriores
                for (int i = 0; i < 20; i++) {
                    context.deleteFile(archivos[i]);
                }
            }

            OutputStreamWriter archivo = new OutputStreamWriter(context.openFileOutput(NomArch, Context.MODE_APPEND));
            for (Userdata dato : datos) {
                cadena = conversionu.Cjson(dato);
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
                listaDatos.add(conversionu.Cdto(cadena));
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public List<Userdata> getDatosUser(){return listaDatos;

    }
}

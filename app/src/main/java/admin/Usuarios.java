package admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import Store.Userconnection;
import DTOs.Userdata;
import mx.edu.tesoem.isc.proyecto.R;

public class Usuarios extends AppCompatActivity {

    GridView gvdatos;
    ArrayList <String> contenidogv = new ArrayList<>();
    CustomAdapter adaptador;
    List<Userdata> listadatos = new ArrayList<>();

    int a = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        gvdatos = findViewById(R.id.gvdatos);
        lee();

        gvdatos.setOnItemClickListener((parent, view, i, l) -> {
            int columnIndex = i % 6;
            if (columnIndex == 4 && "Ver".equals(contenidogv.get(i))) {

                String latitud = contenidogv.get(i - 2); // El índice de latitud es i - 2
                String longitud = contenidogv.get(i - 1); // El índice de longitud es i - 1
                String calif = contenidogv.get(i + 1); // El índice de calif es i

                a = listadatos.size();
                String numero = String.valueOf(a + 1);

                Intent intent = new Intent(this, MapaActivity.class);

                intent.putExtra("latitud", latitud);
                intent.putExtra("longitud", longitud);
                intent.putExtra("calif", calif);
                intent.putExtra("numero", numero);

                intent.putStringArrayListExtra("preguntas", contenidogv);
                startActivity(intent);
            }
        });
    }

    private void lee(){
        Userconnection conexionu = new Userconnection();
        if (conexionu.Leer(this)){
            listadatos = conexionu.getDatosUser();
            contenidogv.add("ID");
            contenidogv.add("Nombre");
            contenidogv.add("Latitud");
            contenidogv.add("Longitud");
            contenidogv.add("Calif");
            contenidogv.add("Vista");

            String numero = String.valueOf(listadatos.size());

            if (listadatos.size() > 0 ){
                int idC = 1;
                for (Userdata datos : listadatos){
                    contenidogv.add(String.valueOf(idC));
                    idC++;
                    contenidogv.add(datos.getNombre());
                    contenidogv.add(String.valueOf(Double.parseDouble(String.valueOf(datos.getLatitud()))));
                    contenidogv.add(String.valueOf(Double.parseDouble(String.valueOf(datos.getLongitud()))));
                    contenidogv.add("Ver");
                    contenidogv.add(String.valueOf(datos.getCalificacion()));
                }
            }

            adaptador = new CustomAdapter(this, android.R.layout.simple_list_item_1, contenidogv, 6);
            gvdatos.setAdapter(adaptador);
        }
    }

    public class CustomAdapter extends ArrayAdapter<String> {

        private int columnCount;

        public CustomAdapter(Context context, int resource, List<String> objects, int columnCount) {
            super(context, resource, objects);
            this.columnCount = columnCount;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            int currentColumn = position % columnCount;

            if (currentColumn == 5) {
                view.setVisibility(View.GONE);
            } else {
                view.setVisibility(View.VISIBLE);
            }

            return view;
        }
    }
}
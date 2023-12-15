package DTOs;

public class Userdata {
    String nombre, latitud, longitud, calificacion;

    public Userdata(String nombre, String latitud, String longitud, String calificacion) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.calificacion = calificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLatitud() {
        return latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public String getCalificacion() {
        return calificacion;
    }

}
package Conversions;

import com.google.gson.Gson;

import DTOs.Userdata;

public class UserConversion {
    private Gson gson = new Gson();
    public String Cjson(Userdata datos){
        return gson.toJson(datos);
    }

    public Userdata Cdto(String cadena){
        return gson.fromJson(cadena, Userdata.class);
    }
}

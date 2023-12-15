package Conversions;

import com.google.gson.Gson;

import DTOs.Dataquestions;

public class ConversionQuestions {
    private final Gson gson = new Gson();
    public String Cjson(Dataquestions datosp){
        return gson.toJson(datosp);
    }
    public Dataquestions Cdto(String cadena){
        return gson.fromJson(cadena, Dataquestions.class);
    }
}
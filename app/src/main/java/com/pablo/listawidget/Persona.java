package com.pablo.listawidget;

/**
 * Created by b036925 on 23/07/15.
 */
public class Persona {
    public String nombre ;
    public int edad;

    public Persona(String n, int e){
        nombre = n;
        edad = e;
    }

    public String getEdad() {
        return String.valueOf(edad);
    }

}

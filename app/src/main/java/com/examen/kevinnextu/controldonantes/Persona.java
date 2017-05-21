package com.examen.kevinnextu.controldonantes;

import java.io.Serializable;

/**
 * Created by kevin on 27/03/2017.
 */

public class Persona implements Serializable {

    String identificacion;
    String nombre;
    String apellido;
    String edad;
    String sangre;
    String rh;
    String peso;
    String estatura;

    public Persona(String identificacion, String nombre, String apellido, String edad, String sangre, String rh, String peso, String estatura) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.sangre = sangre;
        this.rh = rh;
        this.peso = peso;
        this.estatura = estatura;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getSangre() {
        return sangre;
    }

    public void setSangre(String sangre) {
        this.sangre = sangre;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getEstatura() {
        return estatura;
    }

    public void setEstatura(String estatura) {
        this.estatura = estatura;
    }
}

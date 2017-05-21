package com.examen.kevinnextu.controldonantes.BaseDeDatos;

import android.provider.BaseColumns;

/**
 * Created by kevin on 26/03/2017.
 */

public class Estructura {

    public Estructura() {
    }

    public static abstract class EstructuraBase implements BaseColumns
    {
        public static final String TABLE_NAME = "usuarios";
        public static final String USER = "user_name";
        public static final String PASS = "pass_user";

        public static final String TABLE_NAME_DONANTES = "donantes";
        public static final String IDENTIFICACION = "identificacion";
        public static final String NOMBRE = "nombre";
        public static final String APELLIDOS = "apellidos";
        public static final String EDAD = "edad";
        public static final String SANGRE = "tipo_sangre";
        public static final String RH = "rh";
        public static final String PESO = "peso";
        public static final String ESTATURA = "estatura";

    }
}

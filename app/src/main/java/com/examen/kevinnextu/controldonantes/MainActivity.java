package com.examen.kevinnextu.controldonantes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.examen.kevinnextu.controldonantes.BaseDeDatos.BaseDatos;
import com.examen.kevinnextu.controldonantes.BaseDeDatos.Estructura;
import com.examen.kevinnextu.controldonantes.Dialogos.DialogoCrearUsuario;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    EditText usuario,password;
    Button iniciar, nuevo_usuario;
    BaseDatos baseDatos;
    SQLiteDatabase sq;
    String usuarioDigitado,passDigitada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baseDatos = new BaseDatos(getApplicationContext());
        sq= baseDatos.getWritableDatabase();


        /*
        ContentValues values = new ContentValues();
        values.put(Estructura.EstructuraBase.USER,"Kevin");
        values.put(Estructura.EstructuraBase.PASS,"123");
        sq.insert(Estructura.EstructuraBase.TABLE_NAME,"_ ID",values);
         */
       // sq.delete(Estructura.EstructuraBase.TABLE_NAME,"_ID=? ",new String[]{"1"});

        //Casting de los elementos
        usuario = (EditText)findViewById(R.id.txt_usuario);
        password = (EditText)findViewById(R.id.txt_password);
        iniciar = (Button)findViewById(R.id.btn_iniciar);
        nuevo_usuario = (Button)findViewById(R.id.btn_nuevo_usuario);

        //Abre el dialogo de crear usuario
        nuevo_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoCrearUsuario dialogo = new DialogoCrearUsuario();
                dialogo.show(getSupportFragmentManager(),"MainActivity");
            }
        });

        //Boton de Iniciar Sesion
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                passDigitada = password.getText().toString();

                String usuarioSinEspacion = usuario.getText().toString();
                usuarioSinEspacion = usuarioSinEspacion.replace(" ", "");

                validarLogin(usuarioSinEspacion,passDigitada);
            }
        });
    }


    //Validacion de Login
    public void validarLogin(String us, String ps) {
        //Cursor selecciona todos los datos de la tabla usuarios
        Cursor c = sq.rawQuery("SELECT *FROM usuarios", null);

        //Esto me funcionó para saber si la DB estaba vacia al inicio
        //Log.d("Tag",String.valueOf(c.moveToFirst()));

        String user,pass;

        Boolean encontrado=false; //Variable que me servira si encontró el usuario y la password

        if(!c.moveToFirst()){
            Toast.makeText(this,"La DB esta vacia",Toast.LENGTH_SHORT).show();
        }

        else if (c.moveToFirst()) {
            do {
                user = (c.getString(c.getColumnIndex(Estructura.EstructuraBase.USER)));
                pass = (c.getString(c.getColumnIndex(Estructura.EstructuraBase.PASS)));

                //Registro en el Log
                Log.d("Tag",user+" "+pass + "--"+us +" "+ps);

                if(us.equals(user) && ps.equals(pass)){
                    encontrado = true;
                    break;
                }

            } while (c.moveToNext());

            if(encontrado){
                continuar(user);

            }

            if(encontrado==false){
                Toast.makeText(this,"Verifique sus datos",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void continuar(String user ){
        Toast.makeText(this,"Correcto",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this,Donantes.class);
        i.putExtra("LlaveUsuario",user);
        startActivity(i);
    }


}

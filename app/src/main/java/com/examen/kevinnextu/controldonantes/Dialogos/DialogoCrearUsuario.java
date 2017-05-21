package com.examen.kevinnextu.controldonantes.Dialogos;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.examen.kevinnextu.controldonantes.BaseDeDatos.BaseDatos;
import com.examen.kevinnextu.controldonantes.BaseDeDatos.Estructura;
import com.examen.kevinnextu.controldonantes.R;


public class DialogoCrearUsuario extends DialogFragment {

    Button crear, cancelar;
    BaseDatos baseDatos;
    SQLiteDatabase sq;
    EditText txtusuarioDesado;
    EditText txtpassDeseada;
    EditText txtpassConfirmada;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        baseDatos = new BaseDatos(getContext());
        sq = baseDatos.getWritableDatabase();


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogo = inflater.inflate(R.layout.fragment_dialogo_crear_usuario,null);
        builder.setView(dialogo);

        crear = (Button)dialogo.findViewById(R.id.btn_crear_usuario);
        cancelar = (Button)dialogo.findViewById(R.id.btn_cancelar_usuario);
        txtusuarioDesado = (EditText)dialogo.findViewById(R.id.usuario_deseado);
        txtpassDeseada = (EditText)dialogo.findViewById(R.id.pass_deseada);
        txtpassConfirmada = (EditText)dialogo.findViewById(R.id.pass_confirmada);


        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userDeseado,passDeseada, passConfirmada;
                userDeseado = txtusuarioDesado.getText().toString();
                passDeseada = txtpassDeseada.getText().toString();
                passConfirmada = txtpassConfirmada.getText().toString();

                if(txtusuarioDesado.getText().toString().isEmpty() || txtpassDeseada.getText().toString().isEmpty()
                        || txtpassConfirmada.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"No puede dejar campos vacios",Toast.LENGTH_SHORT).show();
                }else if(!passDeseada.equals(passConfirmada)){
                    Toast.makeText(getContext(),"Las passwords no coinciden",Toast.LENGTH_SHORT).show();
                }
                else if(!txtusuarioDesado.getText().toString().isEmpty() && passDeseada.equals(passConfirmada) ){
                    continuar(userDeseado,passConfirmada);
                }

            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();
    }

    public void continuar(String usDeseado,String pass){
        final ContentValues content = new ContentValues();

        Cursor c = sq.rawQuery("SELECT * FROM usuarios", null);
        String user;

        if(!c.moveToFirst()){
            content.put(Estructura.EstructuraBase.USER,usDeseado);
            content.put(Estructura.EstructuraBase.PASS,pass);
            sq.insert(Estructura.EstructuraBase.TABLE_NAME,null,content);
            Toast.makeText(getContext(),"Creado correctamente, puede iniciar sesion",Toast.LENGTH_LONG).show();
            dismiss();
        }

        else if (c.moveToFirst()) {
            do {
                user = (c.getString(c.getColumnIndex(Estructura.EstructuraBase.USER)));

            } while (c.moveToNext());

            if(usDeseado.equals(user)){
                Toast.makeText(getContext(),"El usuario ya existe!",Toast.LENGTH_SHORT).show();
            }else{
                content.put(Estructura.EstructuraBase.USER,usDeseado);
                content.put(Estructura.EstructuraBase.PASS,pass);
                sq.insert(Estructura.EstructuraBase.TABLE_NAME,null,content);
                Toast.makeText(getContext(),"Creado correctamente, puede iniciar sesion",Toast.LENGTH_LONG).show();
                dismiss();
            }
        }

    }

}

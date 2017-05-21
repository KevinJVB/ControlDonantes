package com.examen.kevinnextu.controldonantes.Dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.examen.kevinnextu.controldonantes.BaseDeDatos.BaseDatos;
import com.examen.kevinnextu.controldonantes.BaseDeDatos.Estructura;
import com.examen.kevinnextu.controldonantes.Donantes;
import com.examen.kevinnextu.controldonantes.MainActivity;
import com.examen.kevinnextu.controldonantes.R;

/**
 * Created by kevin on 26/03/2017.
 */

public class DialogoCambiarPass extends DialogFragment {

    BaseDatos baseDatos;
    EditText passActual,passNueva,confirmarPass;
    Button cancelar,guardar;
    String passwordActual,passwordNueva,passwordConfirmar;
    String usuario;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        usuario = getArguments().getString("ClaveUser");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogo = inflater.inflate(R.layout.fragment_dialogo_cambiar_pass,null);
        builder.setView(dialogo);

        passActual = (EditText)dialogo.findViewById(R.id.pass_actual);
        passNueva = (EditText)dialogo.findViewById(R.id.pass_nueva_deseada);
        confirmarPass = (EditText)dialogo.findViewById(R.id.pass_nueva_confirmada);
        cancelar = (Button)dialogo.findViewById(R.id.btn_cancelar_password);
        guardar = (Button)dialogo.findViewById(R.id.btn_cambiar_password);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordActual = passActual.getText().toString();
                passwordNueva = passNueva.getText().toString();
                passwordConfirmar = confirmarPass.getText().toString();

                if(passwordNueva.equals(passwordConfirmar)){
                    continuar(passwordActual,passwordConfirmar);
                }else{
                    Toast.makeText(getContext(), R.string.no_coincide,Toast.LENGTH_SHORT).show();
                }

            }
        });
        return builder.create();
    }

    public void continuar(String passA, String passN){
        //Toast.makeText(getContext(),usuario,Toast.LENGTH_SHORT).show();
        baseDatos = new BaseDatos(getContext());
        SQLiteDatabase sq = baseDatos.getWritableDatabase();


        Cursor c = sq.rawQuery("SELECT * FROM usuarios", null);
        String pass;
        Boolean coincide=false;

        if (c.moveToFirst()) {
            do {
                pass = (c.getString(c.getColumnIndex(Estructura.EstructuraBase.PASS)));
                if(passA.equals(pass)){
                    coincide =true;
                }

            } while (c.moveToNext());


        }

        if(coincide){
            ContentValues values = new ContentValues();
            values.put("pass_user",passN);
            sq.update(Estructura.EstructuraBase.TABLE_NAME,values,"user_name=?",new String[]{usuario});
            Toast.makeText(getContext(), R.string.cambiado,Toast.LENGTH_SHORT).show();
            dismiss();
        }

        if(coincide==false){
            Toast.makeText(getContext(), R.string.no_coincide,Toast.LENGTH_SHORT).show();
        }

    }

}

package com.examen.kevinnextu.controldonantes.Dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.examen.kevinnextu.controldonantes.BaseDeDatos.BaseDatos;
import com.examen.kevinnextu.controldonantes.BaseDeDatos.Estructura;
import com.examen.kevinnextu.controldonantes.MainActivity;
import com.examen.kevinnextu.controldonantes.R;

/**
 * Created by kevin on 27/03/2017.
 */

public class DialogoBorrarCuenta extends DialogFragment {

    BaseDatos baseDatos;
    SQLiteDatabase sq;
    Button borrarCuenta, noBorrarCuenta;
    String usuario;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        usuario = getArguments().getString("ClaveUser");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogo = inflater.inflate(R.layout.fragment_dialogo_eliminar,null);
        builder.setView(dialogo);

        borrarCuenta = (Button)dialogo.findViewById(R.id.btn_si_borrar);
        noBorrarCuenta = (Button)dialogo.findViewById(R.id.btn_no_borrar);

        noBorrarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        borrarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseDatos = new BaseDatos(getContext());
                sq = baseDatos.getWritableDatabase();

                sq.delete(Estructura.EstructuraBase.TABLE_NAME,"user_name=?",new String[]{usuario});
                Toast.makeText(getContext(), R.string.eliminado, Toast.LENGTH_SHORT).show();
                dismiss();
                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
            }
        });

        return builder.create();
    }
}

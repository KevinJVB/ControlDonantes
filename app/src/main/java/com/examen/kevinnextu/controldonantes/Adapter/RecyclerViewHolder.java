package com.examen.kevinnextu.controldonantes.Adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.examen.kevinnextu.controldonantes.BaseDeDatos.BaseDatos;
import com.examen.kevinnextu.controldonantes.BaseDeDatos.Estructura;
import com.examen.kevinnextu.controldonantes.Donantes;
import com.examen.kevinnextu.controldonantes.Persona;
import com.examen.kevinnextu.controldonantes.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 27/03/2017.
 */

public class RecyclerViewHolder extends RecyclerView.Adapter<RecyclerViewHolder.ViewHolder>  {

    private Context context;
    private BaseDatos baseDatos;
    private SQLiteDatabase sq;
    private List<Persona> personas = new ArrayList<>();
    private String sangre2,rh2;

    private String enviarId,enviarN,enviarAp,enviarEd,enviarSa,enviarRh,enviarPe,enviarEs;

    RecyclerView recyclerView;

    private EditText id1 ;
    private EditText nombre1 ;
    private EditText apellido1;
    private EditText edad1 ;
    private EditText peso1;
    private EditText estatura1 ;
    private Spinner spinerTipoSangre;
    private Spinner spinerRH;
    //LayoutInflater inflater;

    public RecyclerViewHolder(Context context,List<Persona> personas) {
        this.context = context;
        this.personas = personas;

        baseDatos = new BaseDatos(context);
        sq= baseDatos.getWritableDatabase();

        Cursor c = sq.rawQuery("SELECT * FROM "+ Estructura.EstructuraBase.TABLE_NAME_DONANTES,null);
        if(c.moveToFirst()){
            do{

                personas.add(new Persona(c.getString(c.getColumnIndex(Estructura.EstructuraBase.IDENTIFICACION)),
                        c.getString(c.getColumnIndex(Estructura.EstructuraBase.NOMBRE)),
                        c.getString(c.getColumnIndex(Estructura.EstructuraBase.APELLIDOS)),
                        c.getString(c.getColumnIndex(Estructura.EstructuraBase.EDAD)),
                        c.getString(c.getColumnIndex(Estructura.EstructuraBase.SANGRE)),
                        c.getString(c.getColumnIndex(Estructura.EstructuraBase.RH)),
                        c.getString(c.getColumnIndex(Estructura.EstructuraBase.PESO)),
                        c.getString(c.getColumnIndex(Estructura.EstructuraBase.ESTATURA))
                        ));

            }while (c.moveToNext());
        }
        c.close();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcardview,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        //Llenar el recycler con los datos obtenidos de la DB anteriormente instanciada
        Persona mpersona = personas.get(position);
        holder.iden.setText("ID: "+mpersona.getIdentificacion());
        holder.nombre.setText("Nombre: "+mpersona.getNombre()+" "+mpersona.getApellido());
        holder.edad.setText("Edad: "+mpersona.getEdad());
        holder.sangre.setText("Sangre: "+mpersona.getSangre() +" "+mpersona.getRh());
        holder.estatura.setText("Estatura: "+mpersona.getEstatura());
        holder.peso.setText("Peso: "+mpersona.getPeso());

        //Variable para traer la posicion del item seleccionado

        final Persona Mpersona = personas.get(position);

        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarDatos(Mpersona.getIdentificacion(),Mpersona.getNombre(),Mpersona.getApellido(),
                        Mpersona.getEdad(),Mpersona.getSangre(),Mpersona.getRh(),Mpersona.getPeso()
                ,Mpersona.getEstatura(),position);
            }
        });

        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Eliminar(Mpersona.getIdentificacion(),position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return personas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView iden,nombre,edad,sangre,estatura,peso;
        ImageButton editar,eliminar;

        public ViewHolder(View itemView) {
            super(itemView);
            iden = (TextView)itemView.findViewById(R.id.viewidentificacion);
            nombre = (TextView)itemView.findViewById(R.id.viewNombre);
            edad = (TextView)itemView.findViewById(R.id.viewEdad);
            sangre = (TextView)itemView.findViewById(R.id.viewSangre);
            estatura = (TextView)itemView.findViewById(R.id.viewEstatura);
            peso = (TextView)itemView.findViewById(R.id.viewPeso);
            editar = (ImageButton)itemView.findViewById(R.id.imageButton);
            eliminar = (ImageButton)itemView.findViewById(R.id.imageButton2);
        }

    }


    //********************************************************************************************//
    //-----------------------------METODOS DE LOS BOTOONES DEL ITEM**********************************
    //********************************************************************************************//


    //Metodo para ACTUALIZAR LOS DATOS

    private void editarDatos(String id, String nombre, String apellido,
                            String edad, String sagnre, String rh, String peso, String estatura, final int posicion){

        //Construye el Dialog y lo muestra
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.fragment_dialogo_donante, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);


        //Casteo de los elementos del dialogView
        id1 = (EditText)dialogView.findViewById(R.id.txt_identificacion);
        id1.setEnabled(false);
        id1.setText(id);
        nombre1 = (EditText)dialogView.findViewById(R.id.txt_nombre);
        nombre1.setText(nombre);
        apellido1 =(EditText)dialogView.findViewById(R.id.txt_apellido);
        apellido1.setText(apellido);
        edad1 =(EditText)dialogView.findViewById(R.id.txt_edad);
        edad1.setText(edad);
        peso1 =(EditText)dialogView.findViewById(R.id.txt_peso);
        peso1.setText(peso);
        estatura1 =(EditText)dialogView.findViewById(R.id.txt_estatura);
        estatura1.setText(estatura);


        spinerTipoSangre = (Spinner)dialogView.findViewById(R.id.spinner_sangre);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,R.array.tiposSangre,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerTipoSangre.setAdapter(adapter);
        spinerTipoSangre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sangre2 = spinerTipoSangre.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinerRH = (Spinner)dialogView.findViewById(R.id.spinner_rh);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(context,R.array.tipoRH,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerRH.setAdapter(adapter2);
        spinerRH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rh2 = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enviarId = id1.getText().toString();
                enviarN = nombre1.getText().toString();
                enviarAp = apellido1.getText().toString();
                enviarEd = edad1.getText().toString();
                enviarSa = sangre2;
                enviarRh = rh2;
                enviarPe = peso1.getText().toString();
                enviarEs = estatura1.getText().toString();
                actualizarDatos(enviarId,enviarN,enviarAp,enviarEd,enviarSa,enviarRh,enviarPe,enviarEs,posicion);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        builder.create().show();
    }

    private void actualizarDatos(String id,String nombre,String apellido,String edad,
                                String sagnre, String rh,String peso, String estatura, int posicionItem){

        ContentValues values = new ContentValues();
        values.put(Estructura.EstructuraBase.NOMBRE,nombre);
        values.put(Estructura.EstructuraBase.APELLIDOS,apellido);
        values.put(Estructura.EstructuraBase.EDAD,edad);
        values.put(Estructura.EstructuraBase.SANGRE,sagnre);
        values.put(Estructura.EstructuraBase.RH,rh);
        values.put(Estructura.EstructuraBase.PESO,peso);
        values.put(Estructura.EstructuraBase.ESTATURA,estatura);

        Log.d("Tag",id+nombre+apellido+edad+sagnre+rh+peso
                +estatura+posicionItem);

        sq.update(Estructura.EstructuraBase.TABLE_NAME_DONANTES,values,"identificacion=?",new String[]{id});
        Toast.makeText(context, R.string.editar,Toast.LENGTH_SHORT).show();


        personas.set(posicionItem,new Persona(id,nombre,apellido,edad,sagnre,rh,peso,estatura));

        //RecyclerViewHolder adapterRecycler = new RecyclerViewHolder(context,personas);
        //adapterRecycler.notifyItemChanged(posicionItem);

        ((Donantes)context).actualizarDatos();

    }


    private void Eliminar(final String id,final int posicionItem){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.fragment_dialogo_eliminar, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);

        Button boton = (Button)dialogView.findViewById(R.id.btn_si_borrar);
        boton.setVisibility(dialogView.GONE);
        Button boton2 = (Button)dialogView.findViewById(R.id.btn_no_borrar);
        boton2.setVisibility(dialogView.GONE);

        TextView titulo = (TextView)dialogView.findViewById(R.id.textView3);
        titulo.setText(R.string.borrar_donante);

        builder.setPositiveButton("Si",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                baseDatos = new BaseDatos(context);
                sq = baseDatos.getWritableDatabase();
                sq.delete(Estructura.EstructuraBase.TABLE_NAME_DONANTES,"identificacion=?",new String[]{id});
                Toast.makeText(context, R.string.eliminado, Toast.LENGTH_SHORT).show();

                personas.clear();
                RecyclerViewHolder adapterRecycler = new RecyclerViewHolder(context,personas);
                adapterRecycler.notifyItemRemoved(posicionItem);
                ((Donantes)context).actualizarDatos();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }

}



package com.examen.kevinnextu.controldonantes;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.examen.kevinnextu.controldonantes.Adapter.RecyclerViewHolder;
import com.examen.kevinnextu.controldonantes.BaseDeDatos.BaseDatos;
import com.examen.kevinnextu.controldonantes.BaseDeDatos.Estructura;
import com.examen.kevinnextu.controldonantes.Dialogos.DialogoBorrarCuenta;
import com.examen.kevinnextu.controldonantes.Dialogos.DialogoCambiarPass;
import java.util.ArrayList;
import java.util.List;


public class Donantes extends AppCompatActivity {

    String usuario;
    RecyclerView recyclerView;
    SQLiteDatabase sq;
    BaseDatos baseDatos;
    RecyclerViewHolder adapterRecycler;

    String sangre,rh;
    EditText txtident,txtnombre,txtapellido,txtedad,txtpeso,txtestatura;
    Spinner spinerTipoSangre, spinerRH;

    List<Persona> personas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donantes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        usuario = getIntent().getStringExtra("LlaveUsuario");
        toolbar.setSubtitle("Logueado como: "+usuario);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo();

            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        adapterRecycler = new RecyclerViewHolder(this,personas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapterRecycler);

        final TextView txt_buscar = (TextView)findViewById(R.id.txt_buscar);


        ImageButton buscar = (ImageButton)findViewById(R.id.btn_buscar);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txt_buscar.getText().toString().isEmpty()){
                    String id_buscar = txt_buscar.getText().toString();
                    Buscar(id_buscar);
                }else{
                    actualizarDatos();
                    /*Intent i = new Intent(getApplicationContext(),Donantes.class);
                    startActivity(i);*/

                    /*
                    recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
                    LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                    adapterRecycler = new RecyclerViewHolder(getApplicationContext(),personas);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setAdapter(adapterRecycler);*/
                    /*RecyclerViewHolder adapterRecycler = new RecyclerViewHolder(Donantes.this,personas);
                    adapterRecycler.notifyDataSetChanged();
                    actualizarDatos();*/
                }

            }
        });

        ImageButton borrar = (ImageButton)findViewById(R.id.btn_borrar_texto);
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_buscar.setText(null);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.cambiarPass) {
            DialogoCambiarPass dialogo = new DialogoCambiarPass();

            Bundle args = new Bundle();
            args.putString("ClaveUser",usuario);
            dialogo.setArguments(args);

            dialogo.show(getSupportFragmentManager(),getString(R.string.donantes));
        }

        if(id==R.id.eliminarCuenta){
            DialogoBorrarCuenta dialogo = new DialogoBorrarCuenta();
            Bundle args = new Bundle();
            args.putString("ClaveUser",usuario);
            dialogo.setArguments(args);
            dialogo.show(getSupportFragmentManager(),getString(R.string.donantes));
        }

        if(id==R.id.cerrarSesion){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void dialogo(){
        final View dialogView = getLayoutInflater().inflate(R.layout.fragment_dialogo_donante, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(Donantes.this);
        builder.setView(dialogView);


        spinerTipoSangre = (Spinner)dialogView.findViewById(R.id.spinner_sangre);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.tiposSangre,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerTipoSangre.setAdapter(adapter);
        spinerTipoSangre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sangre = spinerTipoSangre.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinerRH = (Spinner)dialogView.findViewById(R.id.spinner_rh);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.tipoRH,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerRH.setAdapter(adapter2);
        spinerRH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rh = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Casteo de EditText y Botones

        txtident = (EditText)dialogView.findViewById(R.id.txt_identificacion);
        txtnombre = (EditText)dialogView.findViewById(R.id.txt_nombre);
        txtapellido = (EditText)dialogView.findViewById(R.id.txt_apellido);
        txtedad = (EditText)dialogView.findViewById(R.id.txt_edad);
        txtpeso = (EditText)dialogView.findViewById(R.id.txt_peso);
        txtestatura = (EditText)dialogView.findViewById(R.id.txt_estatura);

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                validarGuardar();
                dialog.dismiss();
            }
        });


        builder.create().show();
    }

    public void validarGuardar(){

        String identificacion = txtident.getText().toString();
        Boolean repetido = false;

        baseDatos = new BaseDatos(getApplicationContext());
        sq = baseDatos.getWritableDatabase();

        String nombre = txtnombre.getText().toString();
        String apellido = txtapellido.getText().toString();
        String edad = txtedad.getText().toString();
        String peso = txtpeso.getText().toString();
        String estatura = txtestatura.getText().toString();

        Cursor c = sq.rawQuery("SELECT * FROM "+ Estructura.EstructuraBase.TABLE_NAME_DONANTES,null);

        if(!c.moveToFirst()){
            repetido = false;

        }
        else if(c.moveToFirst()){
            do{
                String id =c.getString(c.getColumnIndex(Estructura.EstructuraBase.IDENTIFICACION));
                if(id.equals(identificacion)){
                    repetido=true;
                }
            }while (c.moveToNext());

        }

        if(repetido){
            Toast.makeText(getApplicationContext(),"ID ya existente!",Toast.LENGTH_SHORT).show();
        }

        if(repetido==false){

            ContentValues values = new ContentValues();

            nombre = txtnombre.getText().toString();
            apellido = txtapellido.getText().toString();
            edad = txtedad.getText().toString();

            peso = txtpeso.getText().toString();
            estatura = txtestatura.getText().toString();

            values.put(Estructura.EstructuraBase.IDENTIFICACION,identificacion);
            values.put(Estructura.EstructuraBase.NOMBRE,nombre);
            values.put(Estructura.EstructuraBase.APELLIDOS,apellido);
            values.put(Estructura.EstructuraBase.EDAD,edad);
            values.put(Estructura.EstructuraBase.SANGRE,sangre);
            values.put(Estructura.EstructuraBase.RH,rh);
            values.put(Estructura.EstructuraBase.PESO,peso);
            values.put(Estructura.EstructuraBase.ESTATURA,estatura);

            sq.insert(Estructura.EstructuraBase.TABLE_NAME_DONANTES,null,values);
            Toast.makeText(getApplicationContext(),"Guardado exitosamente",Toast.LENGTH_SHORT).show();
            personas.add(new Persona(identificacion,nombre,apellido,edad,sangre,rh,peso,estatura));
            adapterRecycler.notifyDataSetChanged();
            c.close();
        }
        c.close();
    }

    public void actualizarDatos(){
        recyclerView.setAdapter(adapterRecycler);
    }

    private void Buscar(String id_buscar){

        baseDatos = new BaseDatos(getApplicationContext());
        sq = baseDatos.getWritableDatabase();

        Cursor c = sq.rawQuery("SELECT * FROM "+ Estructura.EstructuraBase.TABLE_NAME_DONANTES
                +" WHERE "+Estructura.EstructuraBase.IDENTIFICACION+"="+id_buscar,null);

        personas.clear();
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

        actualizarDatos();
        c.close();

    }
}

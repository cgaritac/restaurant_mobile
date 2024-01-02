package com.example.carlosgarita_proyectofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import static android.widget.Toast.makeText;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {
    private RadioButton rbMesa1, rbMesa2, rbMesa3, rbMesa4, rbMesa5, rbMesa6, rbMesa7, rbMesa8, rbMesa9, rbMesa10, rbIngresarPlato, rbResumen, rbNuevoCliente;
    private Spinner sClientes;
    private String mesa = "";

    AdminSQLiteOpenHelper orden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        orden = new AdminSQLiteOpenHelper(getApplicationContext(),"ordenes", null, 1);

        rbMesa1 = (RadioButton)findViewById(R.id.rbMesa1);
        rbMesa2 = (RadioButton)findViewById(R.id.rbMesa2);
        rbMesa3 = (RadioButton)findViewById(R.id.rbMesa3);
        rbMesa4 = (RadioButton)findViewById(R.id.rbMesa4);
        rbMesa5 = (RadioButton)findViewById(R.id.rbMesa5);
        rbMesa6 = (RadioButton)findViewById(R.id.rbMesa6);
        rbMesa7 = (RadioButton)findViewById(R.id.rbMesa7);
        rbMesa8 = (RadioButton)findViewById(R.id.rbMesa8);
        rbMesa9 = (RadioButton)findViewById(R.id.rbMesa9);
        rbMesa10 = (RadioButton)findViewById(R.id.rbMesa10);

        sClientes = (Spinner) findViewById(R.id.spCantPersonas);

        rbIngresarPlato = (RadioButton)findViewById(R.id.rbIngresar);
        rbResumen = (RadioButton)findViewById(R.id.rbResumen);
        rbNuevoCliente = (RadioButton)findViewById(R.id.rbNuevo);

        String[] clientes = {"1", "2", "3", "4","5", "6", "7", "8", "9", "10"};

        ArrayAdapter<String>adaptClientes = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,clientes);
        sClientes.setAdapter(adaptClientes);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void Mensaje(String mensaje){
        Toast notificacion= makeText(this,mensaje,Toast.LENGTH_LONG);
        notificacion.show();
    }

    public void AgregarOrden (String mesa){
        SQLiteDatabase bd = orden.getWritableDatabase();
        int orden = 1;
        String fin;

        try {
            int cantPer = Integer.parseInt(sClientes.getSelectedItem().toString());
            double total = 0.0;
            String observaciones = "";

            Cursor fila = bd.rawQuery("select orden, cantPer, mesa, total, observaciones from ordenes", null);
            do {
                if (fila.moveToNext()) {
                    fin = "Aun no";
                    orden = orden + 1;
                } else {
                    ContentValues registro = new ContentValues();

                    registro.put("orden", orden);
                    registro.put("cantPer", cantPer);
                    registro.put("mesa", mesa);
                    registro.put("total", total);
                    registro.put("observaciones", observaciones);

                    bd.insert("ordenes", null, registro);

                    bd.close();

                    String ord=String.valueOf(orden);

                    String mensaje = "Nueva orden N°"+ord+" generada para la " + mesa;

                    Mensaje(mensaje);
                    fin = "";
                }
            }while (fin.equals("Aun no"));

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error: "+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public String BuscarOrden (String mesa){
        String numOrden = "";

        SQLiteDatabase bd = orden.getWritableDatabase();

        Cursor fila = bd.rawQuery("select orden from ordenes where mesa='" + mesa +"'", null);

            if (fila.moveToLast()){
                numOrden = fila.getString(0);
            } else {
                numOrden = "problemas";
            }
            bd.close();

        return numOrden;
        }

    public String Mesa(View v){
        String mes = "";

        if (rbMesa1.isChecked()==false && rbMesa2.isChecked()==false &&rbMesa3.isChecked()==false &&rbMesa4.isChecked()==false &&rbMesa5.isChecked()==false && rbMesa6.isChecked()==false && rbMesa7.isChecked()==false && rbMesa8.isChecked()==false && rbMesa9.isChecked()==false && rbMesa10.isChecked()==false){
            mes = "Es necesario que elija una mesa para atender";
        }else{
            if (rbMesa1.isChecked()==true){
                mes = "Mesa 1";
            }
            if (rbMesa2.isChecked()==true){
                mes = "Mesa 2";
            }
            if (rbMesa3.isChecked()==true){
                mes = "Mesa 3";
            }
            if (rbMesa4.isChecked()==true){
                mes = "Mesa 4";
            }
            if (rbMesa5.isChecked()==true){
                mes = "Mesa 5";
            }
            if (rbMesa6.isChecked()==true){
                mes = "Mesa 6";
            }
            if (rbMesa7.isChecked()==true){
                mes = "Mesa 7";
            }
            if (rbMesa8.isChecked()==true){
                mes = "Mesa 8";
            }
            if (rbMesa9.isChecked()==true){
                mes = "Mesa 9";
            }
            if (rbMesa10.isChecked()==true){
                mes = "Mesa 10";
            }
        }
        return mes;
    }

    public void AceptarSiguiente (View v){
        mesa = Mesa(v);
        String ordenes = BuscarOrden(mesa);

        if (mesa.length()>7){
            Mensaje(mesa);
        }else{
            if (rbIngresarPlato.isChecked() == true){
                if (ordenes.equals("problemas")){
                    Mensaje("Debe indicar primero la opcion de nuevo cliente");
                }else{
                    Intent intent1 = new Intent (v.getContext(),Platillos_y_Productos.class);
                    intent1.putExtra("paramTexto", mesa);
                    intent1.putExtra("paramNumero", ordenes);
                    startActivityForResult(intent1,0);
                    onPause();
                }
            }
            if (rbResumen.isChecked() == true){
                if (ordenes.equals("problemas")){
                    Mensaje("Debe indicar primero la opcion de nuevo cliente");
                }else {
                    Intent intent2 = new Intent(v.getContext(), Resumen.class);
                    intent2.putExtra("paramTexto", mesa);
                    intent2.putExtra("paramNumero", ordenes);
                    startActivityForResult(intent2, 0);
                    onPause();
                }
            }
            if (rbNuevoCliente.isChecked() == true){
                AgregarOrden(mesa);
            }
            if (rbResumen.isChecked() == false && rbIngresarPlato.isChecked() == false && rbNuevoCliente.isChecked() == false){
                String mensaje = "Debe elegir una de las opciones para continuar";

                Mensaje(mensaje);
            }
        }
    }
}

package com.example.carlosgarita_proyectofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import static android.widget.Toast.makeText;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

public class Platillos_y_Productos extends AppCompatActivity {
    private EditText etCantBebidas, etCantEntradas, etCantPlatos, etCantPostres;
    private Spinner sBebidas, sEntradas, sPlatos, sPostres;
    private TextView tvNumMesa, tvNumOrden;
    private String numMesa,numOrden;
    private String[] preciosBebidas = {"1500", "1500", "1600", "1600", "1600", "1800"}, preciosEntradas = {"3500", "2900", "6700", "3500", "4100", "6100"}, preciosPlatos = {"4100", "5500", "6600", "7000", "5500", "5700"}, preciosPostres = {"2100", "2100", "2100", "3500", "3500", "3500"};
    private int codigo = 0;

    AdminSQLiteOpenHelper orden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platillos_y__productos);

        orden = new AdminSQLiteOpenHelper(getApplicationContext(),"ordenes", null, 1);

        Bundle bundle = getIntent().getExtras();

        tvNumMesa = (TextView) findViewById(R.id.tvNumMesa);
        numMesa = bundle.getString("paramTexto");
        tvNumMesa.setText(numMesa);

        tvNumOrden = (TextView) findViewById(R.id.tvNumOrden);
        numOrden = bundle.getString("paramNumero");
        tvNumOrden.setText(numOrden);

        etCantBebidas = (EditText)findViewById(R.id.etCantBebidas);
        etCantEntradas = (EditText)findViewById(R.id.etCantEntradas);
        etCantPlatos = (EditText)findViewById(R.id.etCantPlatos);
        etCantPostres = (EditText)findViewById(R.id.etCantPostres);

        sBebidas = (Spinner) findViewById(R.id.spBebidas);
        sEntradas = (Spinner) findViewById(R.id.spEntradas);
        sPlatos = (Spinner) findViewById(R.id.spPlato);
        sPostres = (Spinner) findViewById(R.id.spPostre);

        String[] bebidas = {"Natural Guanabana", "Natural Fresa", "Coca Cola Regular", "Coca Cola Light", "Sprite", "Pilsen"};
        String[] entradas = {"Ensalada Capresse", "Ensalada Verde", "Antipasto Italiano", "Sopa del día", "Sopa de cebolla", "Carpacio de carne"};
        String[] platosFuertes = {"Casado", "Pollo a la plancha", "Filet de tilapia", "Arroz con camarones", "Pasta carbonara", "Lasagna de carne"};
        String[] postres = {"Tiramizú", "Tres leches", "Flan de coco", "Queque de zanahoría", "Torta Chilena", "Pie de manzana"};

        ArrayAdapter<String>adaptBebidas = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,bebidas);
        sBebidas.setAdapter(adaptBebidas);
        ArrayAdapter<String>adaptEntradas = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,entradas);
        sEntradas.setAdapter(adaptEntradas);
        ArrayAdapter<String>adaptPlatos = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,platosFuertes);
        sPlatos.setAdapter(adaptPlatos);
        ArrayAdapter<String>adaptPostres = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,postres);
        sPostres.setAdapter(adaptPostres);
    }

    public void Mensaje(String mensaje){
        Toast notificacion= makeText(this,mensaje,Toast.LENGTH_LONG);
        notificacion.show();
    }

    public void AgregarBebidas (View v){
        String tipo = "Bebidas";
        Double precio = Double.parseDouble(preciosBebidas[(int) sBebidas.getSelectedItemId()]);
        String artic = sBebidas.getSelectedItem().toString();
        int cantArtic = Integer.parseInt(etCantBebidas.getText().toString());

        SQLiteDatabase bd = orden.getWritableDatabase();
        String fin;

        try {
            Cursor fila = bd.rawQuery("select codigo, orden, articulo, tipo, cantidad, precio from productos", null);
            do {
                if (fila.moveToNext()) {
                    fin = "Aun no";
                    codigo = codigo + 1;
                } else {
                    ContentValues registro = new ContentValues();

                    registro.put("codigo", codigo);
                    registro.put("orden", Integer.parseInt(numOrden));
                    registro.put("articulo", artic);
                    registro.put("tipo", tipo);
                    registro.put("cantidad", cantArtic);
                    registro.put("precio", precio);

                    bd.insert("productos", null, registro);

                    bd.close();

                    String mensaje = "Bebidas ingresadas";

                    Mensaje(mensaje);
                    fin = "";
                }
            }while (fin.equals("Aun no"));

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error: "+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void AgregarEntradas (View v){
        String tipo = "Entradas";
        Double precio = Double.parseDouble(preciosEntradas[(int) sEntradas.getSelectedItemId()]);
        String artic = sEntradas.getSelectedItem().toString();
        int cantArtic = Integer.parseInt(etCantEntradas.getText().toString());

        SQLiteDatabase bd = orden.getWritableDatabase();
        String fin;

        try {
            Cursor fila = bd.rawQuery("select codigo, orden, articulo, tipo, cantidad, precio from productos", null);
            do {
                if (fila.moveToNext()) {
                    fin = "Aun no";
                    codigo = codigo + 1;
                } else {
                    ContentValues registro = new ContentValues();

                    registro.put("codigo", codigo);
                    registro.put("orden", Integer.parseInt(numOrden));
                    registro.put("articulo", artic);
                    registro.put("tipo", tipo);
                    registro.put("cantidad", cantArtic);
                    registro.put("precio", precio);

                    bd.insert("productos", null, registro);

                    bd.close();

                    String mensaje = "Entradas ingresadas";

                    Mensaje(mensaje);
                    fin = "";
                }
            }while (fin.equals("Aun no"));

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error: "+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void AgregarPlatos (View v){
        String tipo = "Platos fuertes";
        Double precio = Double.parseDouble(preciosPlatos[(int) sPlatos.getSelectedItemId()]);
        String artic = sPlatos.getSelectedItem().toString();
        int cantArtic = Integer.parseInt(etCantPlatos.getText().toString());

        SQLiteDatabase bd = orden.getWritableDatabase();
        String fin;

        try {
            Cursor fila = bd.rawQuery("select codigo, orden, articulo, tipo, cantidad, precio from productos", null);
            do {
                if (fila.moveToNext()) {
                    fin = "Aun no";
                    codigo = codigo + 1;
                } else {
                    ContentValues registro = new ContentValues();

                    registro.put("codigo", codigo);
                    registro.put("orden", Integer.parseInt(numOrden));
                    registro.put("articulo", artic);
                    registro.put("tipo", tipo);
                    registro.put("cantidad", cantArtic);
                    registro.put("precio", precio);

                    bd.insert("productos", null, registro);

                    bd.close();

                    String mensaje = "Platos fuertes ingresados";

                    Mensaje(mensaje);
                    fin = "";
                }
            }while (fin.equals("Aun no"));

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error: "+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void AgregarPostres (View v){
        String tipo = "Postres";
        Double precio = Double.parseDouble(preciosPostres[(int) sPostres.getSelectedItemId()]);
        String artic = sPostres.getSelectedItem().toString();
        int cantArtic = Integer.parseInt(etCantPostres.getText().toString());

        SQLiteDatabase bd = orden.getWritableDatabase();
        String fin;

        try {
            Cursor fila = bd.rawQuery("select codigo, orden, articulo, tipo, cantidad, precio from productos", null);
            do {
                if (fila.moveToNext()) {
                    fin = "Aun no";
                    codigo = codigo + 1;
                } else {
                    ContentValues registro = new ContentValues();

                    registro.put("codigo", codigo);
                    registro.put("orden", Integer.parseInt(numOrden));
                    registro.put("articulo", artic);
                    registro.put("tipo", tipo);
                    registro.put("cantidad", cantArtic);
                    registro.put("precio", precio);

                    bd.insert("productos", null, registro);

                    bd.close();

                    String mensaje = "Postres ingresados";

                    Mensaje(mensaje);
                    fin = "";
                }
            }while (fin.equals("Aun no"));

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error: "+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void RegresarOrden (View v){
        Intent openMainActivity= new Intent(this, MainActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(openMainActivity, 0);
    }
}

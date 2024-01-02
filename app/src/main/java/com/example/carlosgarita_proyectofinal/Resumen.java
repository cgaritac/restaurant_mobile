package com.example.carlosgarita_proyectofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import static android.widget.Toast.makeText;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

public class Resumen extends AppCompatActivity {
    private EditText etObservaciones;
    private TextView tvDatoMesa, tvDatoTotBebidas, tvDatoTotEntradas, tvDatoTotPlatos, tvDatoTotPostres, tvDatoTotal;
    private TextView tvNumMesa, tvNumOrden;
    private String numMesa, numOrden;
    private Double totalFinal;

    AdminSQLiteOpenHelper orden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);

        orden = new AdminSQLiteOpenHelper(getApplicationContext(),"ordenes", null, 1);

        Bundle bundle = getIntent().getExtras();

        tvNumMesa = (TextView) findViewById(R.id.tvDatoMesa);
        numMesa = bundle.getString("paramTexto");
        tvNumMesa.setText(numMesa);

        tvNumOrden = (TextView) findViewById(R.id.tvDatoOrden);
        numOrden = bundle.getString("paramNumero");
        tvNumOrden.setText(numOrden);

        etObservaciones = (EditText)findViewById(R.id.etObservaciones);

        tvDatoTotBebidas = (TextView)findViewById(R.id.tvDatoTotBebidas);
        tvDatoTotEntradas = (TextView)findViewById(R.id.tvDatoTotEntradas);
        tvDatoTotPlatos = (TextView)findViewById(R.id.tvDatoTotPlato);
        tvDatoTotPostres = (TextView)findViewById(R.id.tvDatoTotPostres);
        tvDatoTotal = (TextView)findViewById(R.id.tvDatoTotal);


        tvDatoTotBebidas.setText("₡"+String.valueOf(CargarParciales("Bebidas")));
        tvDatoTotEntradas.setText("₡"+String.valueOf(CargarParciales("Entradas")));
        tvDatoTotPlatos.setText("₡"+String.valueOf(CargarParciales("Platos fuertes")));
        tvDatoTotPostres.setText("₡"+String.valueOf(CargarParciales("Postres")));

        totalFinal = CargarParciales("Bebidas")+CargarParciales("Entradas")+CargarParciales("Platos fuertes")+CargarParciales("Postres");
        tvDatoTotal.setText("₡"+String.valueOf(totalFinal));
    }

    public void Mensaje(String mensaje){
        Toast notificacion= makeText(this,mensaje,Toast.LENGTH_LONG);
        notificacion.show();
    }

    public double CargarParciales (String tipo) {
        int cant = 0;
        double prec = 0.0, totalParcial = 0.0, total = 0.0;

        SQLiteDatabase bd = orden.getWritableDatabase();

        Cursor fila = bd.rawQuery("select articulo, tipo, cantidad, precio from productos where tipo='" + tipo +"' and orden='" + numOrden +"'", null);

        while (fila.moveToNext()){
            cant = fila.getInt(2);
            prec = fila.getDouble(3);

            totalParcial = cant*prec;

            total = total + totalParcial;
        }
        bd.close();

        return total;
    }

    public void GuardarDatos (View v){
        String observaciones;

        SQLiteDatabase bd = orden.getWritableDatabase();

        try{
            observaciones = etObservaciones.getText().toString();
        }catch (Exception e){
            observaciones = "";
        }

        ContentValues registro = new ContentValues();

        registro.put("total",totalFinal);
        registro.put("observaciones",observaciones);

        int cant = bd.update("ordenes", registro, "orden="+numOrden, null);

        bd.close();

        if (cant == 1){
            Toast.makeText(this, "Datos actualizados con éxito", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "No existe el producto", Toast.LENGTH_SHORT).show();
    }

    public void AceptarResumen (View v) {
        GuardarDatos(v);
        String mensaje = "El total de ₡"+totalFinal+" ha sido enviado a caja, puede pasar a cancelar";

        Mensaje(mensaje);
    }

    public void RegresarReumen (View v){
        Intent openMainActivity= new Intent(this, MainActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(openMainActivity, 0);
    }
}

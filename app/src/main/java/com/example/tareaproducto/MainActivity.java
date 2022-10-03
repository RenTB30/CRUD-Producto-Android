package com.example.tareaproducto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tareaproducto.dao.ProductoDao;
import com.example.tareaproducto.model.Producto;
import com.example.tareaproducto.util.Mensajes;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private EditText edtNombre, edtStock, edtPrecio;
    private ProductoDao productoDAO;
    private Producto producto;
    private Button botonRegistrar;
    private int idprod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productoDAO = new ProductoDao(this);

        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtStock = (EditText) findViewById(R.id.edtStock);
        edtPrecio = (EditText) findViewById(R.id.edtPrecio);
        botonRegistrar = (Button) findViewById(R.id.btnRegistrar);

        idprod = getIntent().getIntExtra("PRODUCTO_ID",0);
        if(idprod > 0){
            Producto model = productoDAO.buscarProductoPorID(idprod);
            edtNombre.setText(model.getNombre());
            edtStock.setText(model.getStock());
            edtPrecio.setText(model.getPrecio().toString());
            setTitle("Actualizar Producto");
        }
    }
    @Override
    protected void onDestroy() {
        productoDAO.cerrarDB();
        super.onDestroy();
    }
    public void registrar(View view){
        boolean validar = true;
        String nombre = edtNombre.getText().toString();
        Integer stock = Integer.parseInt(edtStock.getText().toString());
        Double precio = Double.parseDouble(edtPrecio.getText().toString());
        if(nombre == null || nombre.equals("")){
            validar = false;
            edtNombre.setError(getString(R.string.validaNombre));
        }
        if(stock == null || stock.equals("")){
            validar = false;
            edtStock.setError(getString(R.string.validaStock));
        }
        if(precio == null || precio.equals("")){
            validar = false;
            edtPrecio.setError(getString(R.string.validaPrecio));
        }
        if(validar){
            producto = new Producto();
            producto.setNombre(nombre);
            producto.setStock(stock);
            producto.setPrecio(precio);
            if(idprod > 0){
                producto.set_id(idprod);
            }
            long resultado = productoDAO.modificaProducto(producto);
            if(resultado != -1){
                if(idprod > 0) {
                    Mensajes.Msg(this, getString(R.string.msg_producto_modificado));
                    startActivity(new Intent(this, ListadoProducto.class));
                }else{
                    Mensajes.Msg(this, getString(R.string.msg_producto_guardado));
                    startActivity(new Intent(this, ListadoProducto.class));
                }
                finish();
                //startActivity(new Intent(this, MainActivity.class));
            }else{
                Mensajes.Msg(this, getString(R.string.msg_producto_error));
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_show_listado:
                finish();
                startActivity(new Intent(this, ListadoProducto.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
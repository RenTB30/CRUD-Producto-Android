package com.example.tareaproducto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tareaproducto.adapter.ProductoAdapter;
import com.example.tareaproducto.dao.ProductoDao;
import com.example.tareaproducto.model.Producto;
import com.example.tareaproducto.util.Mensajes;

import java.util.List;

public class ListadoProducto extends AppCompatActivity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener{
    private ListView lista1;
    private List<Producto> listaList;
    private ProductoAdapter adapter;
    private ProductoDao productoDAO;
    private int idposi;
    private AlertDialog alertDialog, alertconfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_producto);
        alertDialog = Mensajes.crearAlertaDialog(this);
        alertDialog = Mensajes.crearAlertaDialog(this);
        alertconfirm = Mensajes.crearDialogConfirmacion(this);

        productoDAO = new ProductoDao(this);
        listaList = productoDAO.listarProductos();
        adapter = new ProductoAdapter(this,listaList);

        lista1 = (ListView) findViewById(R.id.lvProductos);
        lista1.setAdapter(adapter);

        lista1.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options_listado, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==R.id.action_salir){
            startActivity(new Intent(this, MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        int id = listaList.get(idposi).get_id();
        switch (which){
            case 0:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("PRODUCTO_ID",id);
                startActivity(intent);
                break;
            case 1:alertconfirm.show();
                break;
            case DialogInterface.BUTTON_POSITIVE:
                listaList.remove(idposi);
                productoDAO.eliminarProducto(id);
                lista1.invalidateViews();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                alertconfirm.dismiss();break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        idposi = position;
        alertDialog.show();
    }
}
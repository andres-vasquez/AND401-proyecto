package com.example.android_instructor.proyectomateria;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG = MainActivity.class.getSimpleName();

    private Context context;
    private ListaListViewAdapter adapter;

    private ListView listaListView;

    private List<Producto> listaProductos;
    private int id =5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=this;
        listaListView = (ListView)findViewById(R.id.listaListView);

        //inicializa la lista
        listaProductos = new ArrayList<>();
        listaProductos.add(new Producto(1,R.drawable.leche,"Leche",5));
        listaProductos.add(new Producto(2,R.drawable.pan,"Pan",0.5));
        listaProductos.add(new Producto(3,R.drawable.chocolate,"Chocolate",10));
        listaProductos.add(new Producto(4,R.drawable.cocacola,"Coca Cola",11.5));

        adapter = new ListaListViewAdapter(context,listaProductos);
        listaListView.setAdapter(adapter);

        listaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Producto producto = listaProductos.get(position);
                producto.setPrecio(9.99);
                adapter.notifyDataSetChanged();

                Toast.makeText(context,producto.getNombre(),Toast.LENGTH_SHORT).show();
            }
        });

        //Agregar boton
        Button agregarButton = new Button(context);
        agregarButton.setText("+ Agregar");
        agregarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarProducto();
            }
        });

        listaListView.addHeaderView(agregarButton);
        iniciarAcciones();
    }

    private void agregarProducto(){
        Producto producto = new Producto(5,R.mipmap.ic_launcher,"Dinamico"+id,10*id);

        FirebaseController.enviarProductos(producto);

        //listaProductos.add(producto);
        //adapter.notifyDataSetChanged();
        id++;
    }

    private void iniciarAcciones(){
        //Todo iniciar acciones
        new FirebaseController(new FirebaseController.DataChanges() {
            @Override
            public void onDataChanged(List<Producto> lstProductosRecibidos){
                listaProductos.clear();
                listaProductos.addAll(lstProductosRecibidos);
                adapter.notifyDataSetChanged();
            }
        }).recibidProductos();
    }

    private void iniciarSesion(){
        //Todo iniciar sesion
    }

    private void cerrarSesion() {
        //Todo cerrar sesion

    }
}

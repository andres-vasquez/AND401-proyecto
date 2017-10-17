package com.example.android_instructor.proyectomateria;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainRecyclerActivity extends AppCompatActivity {

    private static final String LOG = MainRecyclerActivity.class.getSimpleName();

    private Context context;
    private ListaRecyclerViewAdapter adapter;

    private RecyclerView listaRecyclerView;

    private List<Producto> listaProductos;
    private int id =5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler);

        context=this;
        listaRecyclerView = (RecyclerView) findViewById(R.id.listaRecyclerView);

        //inicializa la lista
        //TODO paso 4a: Llenar la lista
        listaProductos = new ArrayList<>();
        listaProductos.add(new Producto(1,R.drawable.leche,"Leche",5));
        listaProductos.add(new Producto(2,R.drawable.pan,"Pan",0.5));
        listaProductos.add(new Producto(3,R.drawable.chocolate,"Chocolate",10));
        listaProductos.add(new Producto(4,R.drawable.cocacola,"Coca Cola",11.5));

        //TODO paso 4b: Instanciar el adapter y agregarlo a la lista
        adapter = new ListaRecyclerViewAdapter(context,listaProductos);
        listaRecyclerView.setAdapter(adapter);

        //TODO paso 4c: Agregar el LayoutManager
        /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        listaRecyclerView.setLayoutManager(linearLayoutManager);*/

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2);
        listaRecyclerView.setLayoutManager(gridLayoutManager);

        //TODO paso 5f: Llamar el listener
        adapter.setOnProductoClickListener(new OnClickListener() {
            @Override
            public void onClick(Producto producto) {
                producto.setPrecio(producto.getPrecio()+9.99);
                Toast.makeText(context,producto.getNombre(),Toast.LENGTH_SHORT).show();

                Intent intent =new Intent(context,MainActivity.class);
                startActivity(intent);
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

        //listaRecyclerView.addHeaderView(agregarButton);
    }

    private void agregarProducto(){
        Producto producto = new Producto(5,R.mipmap.ic_launcher,"Dinamico"+id,10*id);
        listaProductos.add(producto);
        adapter.notifyDataSetChanged();
        id++;
    }
}

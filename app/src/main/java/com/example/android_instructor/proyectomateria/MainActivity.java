package com.example.android_instructor.proyectomateria;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG = MainActivity.class.getSimpleName();

    private Context context;

    private ListView listaListView;

    private List<Producto> listaProductos;

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

        ListaListViewAdapter adapter = new ListaListViewAdapter(context,listaProductos);
        listaListView.setAdapter(adapter);

        listaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Producto producto = listaProductos.get(position);
                Toast.makeText(context,producto.getNombre(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}

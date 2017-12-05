package com.example.android_instructor.proyectomateria;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainRecyclerActivity extends AppCompatActivity {

    private static final String LOG = MainRecyclerActivity.class.getSimpleName();

    private Context context;
    private ListaRecyclerViewAdapter adapter;

    private RecyclerView listaRecyclerView;

    private List<Producto> listaProductos;
    private int id =5;

    private static final int RC_SIGN_IN = 101;
    private FirebaseAuth firebaseAuth;


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
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            solicitarlogin();
        } else {
            iniciarAcciones();
            mostrarDatosUsuario();
        }
    }

    private void agregarProducto(){
        Producto producto = new Producto(5,R.mipmap.ic_launcher,"Dinamico"+id,10*id);
        listaProductos.add(producto);
        adapter.notifyDataSetChanged();
        id++;
    }

    private void solicitarlogin(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.drawable.chocolate)
                        .setAvailableProviders(
                                Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()
                                        //new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()
                                        //new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build()))
                                )).build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            firebaseAuth = FirebaseAuth.getInstance();
            if(firebaseAuth.getCurrentUser()!=null){
                iniciarAcciones();
                mostrarDatosUsuario();
            }
        }
    }

    private void mostrarDatosUsuario(){
        Toast.makeText(context,
                "Bienvenid@: "+firebaseAuth.getCurrentUser().getDisplayName()
                ,Toast.LENGTH_LONG).show();

        String email=firebaseAuth.getCurrentUser().getEmail();
        String photoUrl = firebaseAuth.getCurrentUser().getPhotoUrl().toString();

        Log.e("EMAIL",email);
        Log.e("PHOTO",""+photoUrl);
    }

    private void iniciarAcciones(){
        String token= FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("tekhne");
        Log.e("Token",token);

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
}

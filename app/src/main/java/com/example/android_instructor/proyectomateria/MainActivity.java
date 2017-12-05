package com.example.android_instructor.proyectomateria;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG = MainActivity.class.getSimpleName();

    private Context context;
    private ListaListViewAdapter adapter;

    private Button btnLogout;
    private ListView listaListView;

    private List<Producto> listaProductos;
    private int id =5;

    private static final int RC_SIGN_IN = 101;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=this;
        listaListView = (ListView)findViewById(R.id.listaListView);
        btnLogout = (Button)findViewById(R.id.btnLogout);

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

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firebaseAuth.getCurrentUser()!=null){
                    firebaseAuth.signOut();
                }
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            solicitarlogin();
        } else {
            iniciarAcciones();
            mostrarDatosUsuario();
        }
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

    private void agregarProducto(){
        Producto producto = new Producto(5,R.mipmap.ic_launcher,"Dinamico"+id,10*id);
        FirebaseController.enviarProductos(producto);
        //listaProductos.add(producto);
        //adapter.notifyDataSetChanged();
        id++;
    }

    private void iniciarAcciones(){
        String token=FirebaseInstanceId.getInstance().getToken();
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


    private void iniciarSesion(){
        //Todo iniciar sesion
    }

    private void cerrarSesion() {
        //Todo cerrar sesion

    }
}

package com.example.android_instructor.proyectomateria;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andresvasquez on 10/6/16.
 */
public class FirebaseController {

    private DataChanges response;
    public FirebaseController(DataChanges response) {

        this.response = response;
    }

    public static void enviarProductos(Producto producto){
        //TODO implementar Firebase aca
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("productos").push().setValue(producto, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.e("productos","Agregado");
            }
        });
    }


    public void recibidProductos(){
        //TODO implementar Firebase aca
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Producto> lstProductos=new ArrayList<Producto>();
                for(DataSnapshot dataProducto : dataSnapshot.getChildren()){
                    Producto objParticipante=dataProducto.getValue(Producto.class);
                    lstProductos.add(objParticipante);
                }
                response.onDataChanged(lstProductos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("productos","Error "+databaseError);
            }
        });
    }


    public interface DataChanges{
        public void onDataChanged(List<Producto> lstProductos);
    }
}

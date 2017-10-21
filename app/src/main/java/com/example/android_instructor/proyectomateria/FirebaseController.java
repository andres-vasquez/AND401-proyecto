package com.example.android_instructor.proyectomateria;
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
    }

    public void recibidProductos(){
        //TODO implementar Firebase aca
    }

    public interface DataChanges{
        public void onDataChanged(List<Producto> lstProductos);
    }
}

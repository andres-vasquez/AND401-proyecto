package com.example.android_instructor.proyectomateria;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Android-Instructor on 16/10/2017.
 */

//TODO paso 3b: Crear el adapter
public class ListaRecyclerViewAdapter extends RecyclerView.Adapter<ProductoViewHolder>{

    //TODO paso 5b: Agregar interface en adapter
    private OnClickListener onProductoClickListener;
    private Context context;
    private List<Producto> items;

    public ListaRecyclerViewAdapter(Context context, List<Producto> items) {
        this.context = context;
        this.items = items;
    }

    //TODO paso 5d: Crear un setter para el listener
    public void setOnProductoClickListener(OnClickListener onProductoClickListener) {
        this.onProductoClickListener = onProductoClickListener;
    }

    @Override
    public ProductoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_producto_card, null);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductoViewHolder holder, final int position) {
        final Producto producto = items.get(position);
        holder.productoImageView.setImageResource(producto.getImagen());
        holder.nombreTextView.setText(producto.getNombre());
        holder.precioTextView.setText(String.valueOf(producto.getPrecio()));

        //TODO paso 5c: Agregar el clickListener evento sobre el layout
        holder.productoLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO paso 4e: Cargar el listener
                if(onProductoClickListener!=null){
                    onProductoClickListener.onClick(producto);

                    //TODO paso 6: notify
                    notifyDataSetChanged();
                }
            }
        });

        holder.productoLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                items.remove(producto);
                notifyItemRemoved(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

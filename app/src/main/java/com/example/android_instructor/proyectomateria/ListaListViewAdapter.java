package com.example.android_instructor.proyectomateria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Android-Instructor on 13/10/2017.
 */

public class ListaListViewAdapter extends BaseAdapter{

    private Context context;
    private List<Producto> items;

    public ListaListViewAdapter(Context context, List<Producto> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Producto getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    static class ViewHolder{
        ImageView productoImageView;
        LinearLayout productoLinearLayout;
        TextView nombreTextView;
        TextView precioTextView;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        //View esta vacio si es que la fila no fue cargada en memoria
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_producto, null);

            viewHolder.nombreTextView = (TextView) view.findViewById(R.id.nombreTextView);
            viewHolder.precioTextView = (TextView) view.findViewById(R.id.precioTextView);
            viewHolder.productoImageView = (ImageView) view.findViewById(R.id.productoImageView);
            viewHolder.productoLinearLayout = (LinearLayout) view.findViewById(R.id.productoLinearLayout);

            //Guardamos las filas aun visibles
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //Identifico cual mostrar segun la position
        Producto producto = items.get(position);
        viewHolder.productoImageView.setImageResource(producto.getImagen());
        viewHolder.nombreTextView.setText(producto.getNombre());
        viewHolder.precioTextView.setText(String.valueOf(producto.getPrecio()));

        return view;
    }
}

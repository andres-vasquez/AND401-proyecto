package com.example.android_instructor.proyectomateria;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Android-Instructor on 16/10/2017.
 */
//TODO paso 3a: Creamos el ViewHolder
public class ProductoViewHolder  extends RecyclerView.ViewHolder{
    ImageView productoImageView;
    LinearLayout productoLinearLayout;
    TextView nombreTextView;
    TextView precioTextView;

    public ProductoViewHolder(View view) {
        super(view);
        nombreTextView = (TextView) view.findViewById(R.id.nombreTextView);
        precioTextView = (TextView) view.findViewById(R.id.precioTextView);
        productoImageView = (ImageView) view.findViewById(R.id.productoImageView);
        productoLinearLayout = (LinearLayout) view.findViewById(R.id.productoLinearLayout);
    }
}

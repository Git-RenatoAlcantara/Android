package com.example.renato.recycle.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.renato.recycle.R;

public class AdapterMovimentacao extends RecyclerView.Adapter<AdapterMovimentacao.myViewHoder> {
    @NonNull
    @Override
    public myViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLista = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_view, viewGroup, false);
        return new myViewHoder(itemLista);

    }

    @Override
    public void onBindViewHolder(@NonNull myViewHoder myViewHoder, int i) {
            myViewHoder.titilo.setText("Titulo de text");
            myViewHoder.genero.setText("Comedia");
            myViewHoder.ano.setText("2011");
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class myViewHoder extends RecyclerView.ViewHolder{
        TextView titilo;
        TextView ano;
        TextView genero;

        public myViewHoder(@NonNull View itemView) {
            super(itemView);
            titilo = itemView.findViewById(R.id.txtTitulo);
            ano = itemView.findViewById(R.id.txtAno);
            genero = itemView.findViewById(R.id.txtGenero);
        }
    }
}

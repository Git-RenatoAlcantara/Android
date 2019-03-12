package com.example.renato.organize.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.renato.organize.R;
import com.example.renato.organize.model.Movimentacao;

import java.util.ArrayList;
import java.util.List;

public class AdapterMovimentacao extends RecyclerView.Adapter<AdapterMovimentacao.myViewHoder> {
    private List<Movimentacao> listMovimentacao;
    Context context;


    public AdapterMovimentacao(List<Movimentacao> listMovimentacao, Context context) {
        this.listMovimentacao = listMovimentacao;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_movimentacao, parent, false);
        return new myViewHoder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHoder holder, int position) {
        Movimentacao movimentacao  = listMovimentacao.get(position);

        holder.textAdapterValor.setText(String.valueOf(movimentacao.getValor()));
        holder.textAdapterCategoria.setText(movimentacao.getDescricao());
        holder.textAdapterTitulo.setText(movimentacao.getCategoria());

        if (movimentacao.getTipo().equals("d"))

        {

            holder.textAdapterValor.setTextColor(context.getResources().getColor(R.color.colorAccent));

            holder.textAdapterValor.setText("-" + movimentacao.getValor());
        }

    }


        @Override
    public int getItemCount() {
        return this.listMovimentacao.size();
    }

    public class myViewHoder extends  RecyclerView.ViewHolder{

        TextView textAdapterTitulo;
        TextView textAdapterValor;
        TextView textAdapterCategoria;

        public myViewHoder(View itemView) {
            super(itemView);
            textAdapterTitulo = itemView.findViewById(R.id.textAdapterTitulo);
            textAdapterCategoria = itemView.findViewById(R.id.textAdapterCategoria);
            textAdapterValor = itemView.findViewById(R.id.textAdapterValor);

        }
    }
}

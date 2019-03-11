package com.cursoemprego.ce.cursoemprego.activity.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cursoemprego.ce.cursoemprego.R;
import com.cursoemprego.ce.cursoemprego.activity.activity.model.EmpregosResultado;

import java.util.List;

public class AdapterEmpregos extends RecyclerView.Adapter<AdapterEmpregos.MyViewHolder> {
    private List<EmpregosResultado> listaEmpregos;
    private Context context;

    public AdapterEmpregos(List<EmpregosResultado> listaEmpregos, Context context) {

        this.listaEmpregos = listaEmpregos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_conteudo, parent, false);

        return new AdapterEmpregos.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       EmpregosResultado resultado = listaEmpregos.get(position);

        holder.titulo.setText(resultado.getText());
        holder.capa.setImageResource(resultado.getImagem());
    }

    @Override
    public int getItemCount() {
        return listaEmpregos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titulo;
        ImageView capa;

        public MyViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textTitulo);
            capa = itemView.findViewById(R.id.imageViewListCapa);
        }
    }


}

package com.cursoemprego.ce.cursoemprego.activity.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cursoemprego.ce.cursoemprego.R;
import com.cursoemprego.ce.cursoemprego.activity.activity.model.EmpregosResultado;
import com.cursoemprego.ce.cursoemprego.activity.activity.model.InformaticaResultado;
import com.cursoemprego.ce.cursoemprego.activity.activity.model.ListaResultado;
import com.cursoemprego.ce.cursoemprego.activity.activity.model.framework.Framework;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AdapterCursos extends RecyclerView.Adapter<AdapterCursos.MyViewHolder> {
    private List<Framework> informaticaResultados;
    private Context contexto;

    public AdapterCursos(List<Framework> informaticaResultados, Context contexto) {
        this.informaticaResultados = informaticaResultados;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_conteudo, parent, false);

        return new AdapterCursos.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Framework  framework = informaticaResultados.get(position);
         holder.titulo.setText(framework.titulo);
        String url = framework.imagem;

        Glide.with(contexto)
                .load(url) // image url
                .into(holder.capa);
    }

    @Override
    public int getItemCount() {
        return informaticaResultados.size();
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

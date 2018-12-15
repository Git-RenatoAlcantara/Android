package com.cursoemprego.pontoce.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cursoemprego.pontoce.R;
import com.cursoemprego.pontoce.model.Pessoa;

import java.util.List;

public class AdapterMovimentacao extends RecyclerView.Adapter<AdapterMovimentacao.myViewHoder> {
    private List<Pessoa> listPessoa;
    private Context context;

    public AdapterMovimentacao(List<Pessoa> listPessoa, Context context) {
        this.listPessoa = listPessoa;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_movimentacao, parent, false);

        return new myViewHoder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHoder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class myViewHoder extends RecyclerView.ViewHolder{
        TextView sentido;
        TextView ano;
        TextView genero;

        public myViewHoder(@NonNull View itemView) {
            super(itemView);
            sentido = itemView.findViewById(R.id.textSentido);
        }
    }
}

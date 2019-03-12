package com.example.renato.organize.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.renato.organize.model.Movimentacao;

import java.util.List;

public class MeuAdpater extends RecyclerView.Adapter<MeuAdpater.MyViewHoder> {
    List<Movimentacao> recebeMovimentos;

    public MeuAdpater( List<Movimentacao> movimentacoes) {
        this.recebeMovimentos = movimentacoes;
    }

    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHoder extends RecyclerView.ViewHolder {
        public MyViewHoder(View itemView) {
            super(itemView);
        }
    }

}

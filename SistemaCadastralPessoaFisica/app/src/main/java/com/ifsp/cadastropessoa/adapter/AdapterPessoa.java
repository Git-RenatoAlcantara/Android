package com.ifsp.cadastropessoa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ifsp.cadastropessoa.R;
import com.ifsp.cadastropessoa.modal.Pessoa;

import java.util.List;

public class AdapterPessoa extends RecyclerView.Adapter<AdapterPessoa.myViewHoder> {

    private List<Pessoa> lpessoa;
    Context context;

    public AdapterPessoa(List<Pessoa> lpessoa, Context context) {
        this.lpessoa = lpessoa;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_pessoa, parent, false);
        return new myViewHoder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHoder holder, int position) {
        Pessoa pessoa = lpessoa.get(position);

        holder.textoNome.setText("Nome: " + pessoa.getNome());
        holder.textoCpf.setText("CPF: " +pessoa.getCpf());
        holder.textoIdade.setText("Idade: " +pessoa.getIdade());
        holder.textoTelefone.setText("Telefone: " +pessoa.getTelefone());
        holder.textoEmail.setText("Email: " +pessoa.getEmail());
    }

    @Override
    public int getItemCount() {
        return lpessoa.size();
    }

    public class myViewHoder extends RecyclerView.ViewHolder{
        TextView textoNome, textoCpf, textoIdade, textoTelefone, textoEmail;

        public myViewHoder(@NonNull View itemView) {
            super(itemView);
            textoNome = itemView.findViewById(R.id.textViewNomeAdapter);
            textoCpf = itemView.findViewById(R.id.textViewCpfAdapter);
            textoIdade = itemView.findViewById(R.id.textViewIdadeAdapter);
            textoTelefone = itemView.findViewById(R.id.textViewTelAdapter);
            textoEmail = itemView.findViewById(R.id.textViewEmailAdapter);
        }
    }
}
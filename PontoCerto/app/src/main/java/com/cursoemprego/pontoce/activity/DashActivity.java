package com.cursoemprego.pontoce.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cursoemprego.pontoce.R;
import com.cursoemprego.pontoce.adapter.AdapterMovimentacao;
import com.cursoemprego.pontoce.model.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class DashActivity extends AppCompatActivity {
    private AdapterMovimentacao adapterMovimentacao;
    private Pessoa pessoa;
    private RecyclerView recyclerView;
    private List<Pessoa> listPessoa = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        inicializarComponentes();
        pessoa = new Pessoa();
        pessoa.setNome("Renato");
        pessoa.setIdade("26");
        pessoa.setHorario("10:28");
        pessoa.setSentido("E");
        pessoa.setTelefone("2134630736");
        listPessoa.add(pessoa);

    }

    private void inicializarComponentes(){
        recyclerView = findViewById(R.id.recyclerViewPontos);


        //Configuracao adaper
        adapterMovimentacao = new AdapterMovimentacao(listPessoa, this);

        //Configuracao RecyclerView
        RecyclerView.LayoutManager  layoutManeger = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManeger);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterMovimentacao);
    }
}

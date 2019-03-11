package com.cursoemprego.ce.cursoemprego.activity.activity.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.cursoemprego.ce.cursoemprego.R;
import com.cursoemprego.ce.cursoemprego.activity.activity.adapter.AdapterCursos;
import com.cursoemprego.ce.cursoemprego.activity.activity.adapter.AdapterEmpregos;
import com.cursoemprego.ce.cursoemprego.activity.activity.api.DadosService;
import com.cursoemprego.ce.cursoemprego.activity.activity.helper.RetrofitConfig;
import com.cursoemprego.ce.cursoemprego.activity.activity.model.InformaticaResultado;
import com.cursoemprego.ce.cursoemprego.activity.activity.model.ListaResultado;
import com.cursoemprego.ce.cursoemprego.activity.activity.model.framework.Framework;
import com.cursoemprego.ce.cursoemprego.activity.activity.model.front_end.FrontEnd;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InformaticaActivity extends AppCompatActivity {
    private RecyclerView recyclerCursos;
    private AdapterCursos adapterCursos;

    List<Framework> listaInformatica = new ArrayList<>();
    Retrofit retrofit;
    InformaticaResultado informaticaResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informatica);

        recyclerCursos = findViewById(R.id.recyclerViewInformatica);



        //Configuracoes iniciais
        retrofit = RetrofitConfig.getRetrofit();


        //Configurar toolbar
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Informática");
        setSupportActionBar(toolbar);

        getResultado();


    }

    public void  getResultado(){


        DadosService dadosService = retrofit.create(DadosService.class);
        Call<InformaticaResultado> call = dadosService.recuperarCursos();

        call.enqueue(new Callback<InformaticaResultado>() {
            @Override
            public void onResponse(Call<InformaticaResultado> call, Response<InformaticaResultado> response) {
                if ( response.isSuccessful() ){
                    System.out.println("Sucesso: " + response.body());


                    informaticaResultado = response.body();

                    listaInformatica = informaticaResultado.framework;

                    //Iniciando RecyclerView
                    configurarRecyclerView();

                    Toast.makeText(InformaticaActivity.this, "Resultado: "+ informaticaResultado.framework.get(0).titulo, Toast.LENGTH_SHORT).show();
                    System.out.println("Resultado: "+ informaticaResultado.framework.get(0).imagem);
                }
            }

            @Override
            public void onFailure(Call<InformaticaResultado> call, Throwable t) {
                Toast.makeText(InformaticaActivity.this, "Erro", Toast.LENGTH_SHORT).show();
            }

        });

    }



    public void configurarRecyclerView(){
        adapterCursos = new AdapterCursos(listaInformatica, this);
        recyclerCursos.setHasFixedSize(true);
        recyclerCursos.setLayoutManager(new LinearLayoutManager(this));
        recyclerCursos.setAdapter(adapterCursos);
    }
}

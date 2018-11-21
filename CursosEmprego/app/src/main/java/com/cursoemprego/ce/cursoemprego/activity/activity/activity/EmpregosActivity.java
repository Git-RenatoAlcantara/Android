package com.cursoemprego.ce.cursoemprego.activity.activity.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.cursoemprego.ce.cursoemprego.R;
import com.cursoemprego.ce.cursoemprego.activity.activity.adapter.AdapterEmpregos;
import com.cursoemprego.ce.cursoemprego.activity.activity.api.DadosService;
import com.cursoemprego.ce.cursoemprego.activity.activity.helper.RecyclerItemClickListener;
import com.cursoemprego.ce.cursoemprego.activity.activity.helper.RetrofitConfig;
import com.cursoemprego.ce.cursoemprego.activity.activity.model.EmpregosResultado;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EmpregosActivity extends AppCompatActivity  {

    private RecyclerView recyclerEmpregos;
    private AdapterEmpregos adapterEmpregos;
    private Retrofit retrofit;
    private  EmpregosResultado empregosResultado;

    List<EmpregosResultado> listaEmpregos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empregos);


        //Referenicando os objetos
        recyclerEmpregos = findViewById(R.id.recyclerViewEmpregos);

        //Metodo configurar recyclerView
        configuraRecyclerView();

        //Retrofit
        retrofit = RetrofitConfig.getRetrofit();

        //Configurar toolbar
        /*android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Empregos");
        setSupportActionBar(toolbar);*/

        //Istanciando EmpregosResultado
        empregosResultado = new EmpregosResultado();
        empregosResultado.setImagem(R.drawable.post1);
        empregosResultado.setText("[VAGA] Desenvolvedor Front-End (Pleno) para Multinacional!");

        listaEmpregos.add(empregosResultado);
    }


    public void  getResultado(){


       DadosService dadosService = retrofit.create(DadosService.class);
       Call<EmpregosResultado> call = dadosService.recuperarDados();

       call.enqueue(new Callback<EmpregosResultado>() {
           @Override
           public void onResponse(Call<EmpregosResultado> call, Response<EmpregosResultado> response) {
                if ( response.isSuccessful() ){
                    EmpregosResultado resultado = response.body();
                }
           }

           @Override
           public void onFailure(Call<EmpregosResultado> call, Throwable t) {
                System.out.println("Erro");
           }
       });

    }


    public void configuraRecyclerView(){

        adapterEmpregos = new AdapterEmpregos(listaEmpregos, this);
        recyclerEmpregos.setHasFixedSize(true);
        recyclerEmpregos.setLayoutManager(new LinearLayoutManager(this));
        recyclerEmpregos.setAdapter(adapterEmpregos);

        //Configurar evento clique no recyclerView
        recyclerEmpregos.addOnItemTouchListener(

                new RecyclerItemClickListener(
                        this,
                        recyclerEmpregos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                final View imageView = view.findViewById(R.id.imageViewCapa);
                                Intent intent = new Intent(EmpregosActivity.this, Post_Conteudo_Activity.class);
                                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(EmpregosActivity.this,
                                        imageView, ViewCompat.getTransitionName(imageView));
                                startActivity(intent, options.toBundle());
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }



                        }
                )
        );

    }
}

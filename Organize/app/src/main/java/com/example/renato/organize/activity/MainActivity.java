package com.example.renato.organize.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.renato.organize.R;
import com.example.renato.organize.activity.CadastroActivity;
import com.example.renato.organize.activity.LoginActivity;
import com.example.renato.organize.config.ConfiguracaoFireBase;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MainActivity extends IntroActivity {
    private FirebaseAuth autenticar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        //Desabilita os botoes dos slides
        setButtonNextVisible(false);
        setButtonBackVisible(false);

        //Adiciona os fragmentos a api de slide
        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_blue_light)
                .fragment(R.layout.intro_1)
                .build()

        );
        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_blue_light)
                .fragment(R.layout.intro_2)
                .build()

        );
        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_blue_light)
                .fragment(R.layout.intro_3)
                .build()

        );
        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_blue_light)
                .fragment(R.layout.intro_4)

                .build()

        );

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_blue_light)
                .fragment(R.layout.intro_cadastro)
                .canGoForward(false)
                .build()

        );


    }

    //Ao abrir a activity chama o metodo para verificar se ja esta logado como o
    //Oncreate nao restarta a activity
    @Override
    public  void onStart() {
        super.onStart();
        verificarUsuarioLogade();
    }

    public void btnCadastrar(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }
    public  void btnEntrar(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

    //Metodo verifica usuario logado
    public void verificarUsuarioLogade(){
        autenticar = ConfiguracaoFireBase.getfireBaseAutenticacao();
       // autenticar.signOut();
        //Verifica se o usuario esta logado e chama o metodo para abrir a tela principal do aplicativo
        // Comando para deslogar usuario autenticar.signOut();
        if (autenticar.getCurrentUser() != null){
            abrirTelaPrincipal();
        }

    }

    //Metodo que abri a tela principal
    public void abrirTelaPrincipal(){
        startActivity(new Intent(this,  PrincipalActivity.class));
        finish();
    }
}

package com.cursoprogramacao.kodes.olxclone.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.cursoprogramacao.kodes.olxclone.R;
import com.cursoprogramacao.kodes.olxclone.helper.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class AnunciosActivity extends AppCompatActivity {
    private FirebaseAuth autencacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios);

        //Configuracoes iniciais
        autencacao = ConfiguracaoFirebase.getFirebaseAuth();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if( autencacao.getCurrentUser() == null ){//Usuario logado
            menu.setGroupVisible(R.id.group_deslgado, true);
        }else{//Usuario deslogado
            menu.setGroupVisible(R.id.group_logado, true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch ( item.getItemId()){
            case R.id.menu_cadastrar:
                startActivity( new Intent(getApplicationContext(), OlxActivity.class));
                break;
            case R.id.menu_sair:
                autencacao.signOut();
                invalidateOptionsMenu();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

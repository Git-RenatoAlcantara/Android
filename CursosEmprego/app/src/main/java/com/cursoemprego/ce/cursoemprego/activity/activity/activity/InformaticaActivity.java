package com.cursoemprego.ce.cursoemprego.activity.activity.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cursoemprego.ce.cursoemprego.R;

public class InformaticaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informatica);

        //Configurar toolbar
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Informática");
        setSupportActionBar(toolbar);
    }
}

package com.example.renato.organize.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renato.organize.R;
import com.example.renato.organize.adapter.AdapterMovimentacao;
import com.example.renato.organize.config.ConfiguracaoFireBase;
import com.example.renato.organize.helper.Base64Custom;
import com.example.renato.organize.model.Movimentacao;
import com.example.renato.organize.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {
    private MaterialCalendarView calendarView;
    private TextView textoSauda, textoSaldo;
    private RecyclerView recycleView;

    private FirebaseAuth autenticaocao = ConfiguracaoFireBase.getfireBaseAutenticacao();
    private DatabaseReference firebase = ConfiguracaoFireBase.getFirebaseDatabase();
    private DatabaseReference movimentacaoRef;
    private Movimentacao movimentacao;
    AdapterMovimentacao adapterMovimentacao;

    private DatabaseReference usuarioRef;
    private ValueEventListener valueEventListener;
    private ValueEventListener valueEventListenerMovimentacoes;

    private  Double despesaTotal = 0.0;
    private  Double receitaTotal = 0.0;
    private  Double resumoUsuario = 0.0;
    private String mesAnoSelecionado;


    List<Movimentacao> listMovimentacao = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recycleView = findViewById(R.id.recycleView);
        textoSaldo = findViewById(R.id.txtSaldo);
        textoSauda = findViewById(R.id.txtSaudacao);
        calendarView = findViewById(R.id.calendarView);


       //Configuracao adaper
        adapterMovimentacao = new AdapterMovimentacao(listMovimentacao, this);

      //Configuracao RecyclerView
        RecyclerView.LayoutManager  layoutManeger = new LinearLayoutManager(getApplicationContext());
        recycleView.setLayoutManager(layoutManeger);
        recycleView.setHasFixedSize(true);
        recycleView.setAdapter(adapterMovimentacao);
        configuraCalenderView();
        recuperarReceita();
        recuperarMovimentacoes();
        swipe();

    }

    //Metodo arrastar item do cycleview
    public void swipe(){
        //Criando objeto callback
        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                int swipFlags = ItemTouchHelper.START | ItemTouchHelper.END;


                return makeMovementFlags(dragFlags, swipFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                System.out.println("Item foi arrastado");
                excluirMovimentacao(viewHolder);

            }
        };
        //Acao de arrastar ao recycleView
        new ItemTouchHelper(itemTouch).attachToRecyclerView(recycleView);
    }
    public void excluirMovimentacao(final RecyclerView.ViewHolder viewHolder){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Excluir movimencao");
        alertDialog.setMessage("Voce tem certeza que deseja apagar a movimentacao");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Convifirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int position = viewHolder.getAdapterPosition();
                movimentacao = listMovimentacao.get(position);
                movimentacao.getKey();

                String email = autenticaocao.getCurrentUser().getEmail();
                String idUsuario = Base64Custom.base64Encode(email);

                movimentacaoRef = firebase.child("movimentacao").child(idUsuario).child(mesAnoSelecionado);
                movimentacaoRef.child(movimentacao.getKey()).removeValue();
                adapterMovimentacao.notifyItemRemoved(position);
                atualizarSaldo();
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(PrincipalActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
                adapterMovimentacao.notifyDataSetChanged();

            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    public void atualizarSaldo(){
        DatabaseReference emailUsuario = ConfiguracaoFireBase.getFirebaseDatabase().child("usuarios");
        String email = autenticaocao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.base64Encode(email);

        usuarioRef = firebase.child("usuarios").child(idUsuario);
        if (movimentacao.getTipo().equals("r")){
            receitaTotal = receitaTotal - movimentacao.getValor();
            usuarioRef.child("receitaTotal").setValue(receitaTotal);
        }
        if(movimentacao.getTipo().equals("d")){
            despesaTotal = despesaTotal - movimentacao.getValor();
            usuarioRef.child("despesaTotal").setValue(despesaTotal);
        }
    }
    public void recuperarMovimentacoes(){
        String email = autenticaocao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.base64Encode(email);

        movimentacaoRef = firebase.child("movimentacao").child(idUsuario).child(mesAnoSelecionado);

        valueEventListenerMovimentacoes = movimentacaoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               listMovimentacao.clear();
                for (DataSnapshot dados: dataSnapshot.getChildren()){
                    Movimentacao movimentacao = dados.getValue(Movimentacao.class);
                   System.out.println("Movitacao valor recuperado: "+ movimentacao.getValor());
                    movimentacao.setKey(dados.getKey());
                    listMovimentacao.add(movimentacao);

                }
                adapterMovimentacao.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }

    public void recuperarReceita(){
        DatabaseReference emailUsuario = ConfiguracaoFireBase.getFirebaseDatabase().child("usuarios");
        String email = autenticaocao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.base64Encode(email);

        usuarioRef = firebase.child("usuarios").child(idUsuario);
        Log.i("Evento", "Evento foi adicionado");

        valueEventListener = usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                despesaTotal = usuario.getDespesaTotal();
                receitaTotal = usuario.getReceitaTotal();
                resumoUsuario = receitaTotal - despesaTotal;

                //FORMATANDO SALDO
                DecimalFormat decimalFormat = new DecimalFormat("0.##");
                String resultadoFormatado = decimalFormat.format(resumoUsuario);
                textoSauda.setText("Ola," + usuario.getNome() );
                textoSaldo.setText("R$" + resultadoFormatado);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sair:
                autenticaocao = ConfiguracaoFireBase.getfireBaseAutenticacao();
                autenticaocao.signOut();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //Funcao chamar tela de adicionar despesesas
    public void adicionarDespesa(View view){
        startActivity(new Intent(this, DespesasActivity.class));
    }
    //Funcao chamar tela de adicionar receitas
    public  void adicionarReceita(View view){
        startActivity(new Intent(this, ReceitasActivity.class));
    }
    public void configuraCalenderView(){
        CalendarDay dataAtual = calendarView.getCurrentDate();
        String mesSelecionado = String.format("%02d", (dataAtual.getMonth() + 1));
        mesAnoSelecionado = String.valueOf(( mesSelecionado + dataAtual.getYear()));
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {

            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                String mesSelecionado = String.format("%02d", (date.getMonth() + 1));
                mesAnoSelecionado = String.valueOf(mesSelecionado+ ""+ date.getYear());
               movimentacaoRef.removeEventListener(valueEventListenerMovimentacoes);
               recuperarMovimentacoes();

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        recuperarReceita();
        recuperarMovimentacoes();
    }

    @Override
    public void onStop() {
        super.onStop();
       usuarioRef.removeEventListener(valueEventListener);
       movimentacaoRef.removeEventListener(valueEventListenerMovimentacoes);
        Log.i("Evento", "Evento foi removido");
    }
}

package com.ifsp.cadastropessoa.activity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.ifsp.cadastropessoa.R;
import com.ifsp.cadastropessoa.adapter.AdapterPessoa;
import com.ifsp.cadastropessoa.helper.RecyclerItemClickListener;
import com.ifsp.cadastropessoa.modal.Pessoa;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class ListaPessoasActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterPessoa adapter;
    private MaterialSearchView searchView;
    private Toolbar toolbar;
    private TextView setaVoltar;
    private TextView target;
    Pessoa pessoa;

    //Lista de objetos Pessoa
    private List<Pessoa> listaPessoa = new ArrayList<>();


    //Variavel de instacia sqlite
    private SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pessoas);

        tapTarget();


        inicializarComponetes();
        configurarRecyclerView();
        recuperarDados();
        clickListenerRecycler();
        swipe();

    }


    //Recupera dados do banco
    public void recuperarDados(){


        //Zerando listapessoa
        listaPessoa.clear();


        try{
            //Chamando metodo abrir banco
            abrirBanco();

            //Selecionando e recuperando dados da tabela
            Cursor cursor = bancoDados.rawQuery("select * from pessoas WHERE 1=1", null);
            System.out.println(cursor.getColumnIndex("id"));
            System.out.println(cursor.getColumnIndex("nome"));
            System.out.println(cursor.getColumnIndex("cpf"));
            //Iniciando o cursor indice 0
            cursor.moveToFirst();

            //percorre coluna enquanto diferente de vazio
            while (cursor != null) {

                //Instanciando objeto pessoa
                pessoa = new Pessoa();

                //Setando valores aos atributos da classe pesso com o retorno do banco
                pessoa.setId(cursor.getString(0));
                pessoa.setNome(cursor.getString(1));
                pessoa.setCpf( cursor.getString(2));
                pessoa.setIdade(cursor.getString(3));
                pessoa.setTelefone(cursor.getString(4));
                pessoa.setEmail(cursor.getString(5));


                //Adicionando pessoa a lista
                listaPessoa.add(pessoa);


                //Move o cursor enquanto diferente de null
                cursor.moveToNext();

            }



        }catch (Exception e){

            e.printStackTrace();

        }



    }



    private void inicializarComponetes(){
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new AdapterPessoa(listaPessoa, getApplicationContext());
        searchView = findViewById(R.id.search_view);
        toolbar = findViewById(R.id.toolbar);
        setaVoltar = findViewById(R.id.setaVoltar);
        target = findViewById(R.id.target);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        pesquisarCadastrados();

    }

    private void configurarRecyclerView(){

        RecyclerView.LayoutManager  layoutManeger = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManeger);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void clickListenerRecycler(){

        //Configurar evento clique no recyclerView
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Pessoa pessoaSelecionada = listaPessoa.get(position);
                                Toast.makeText(ListaPessoasActivity.this, pessoaSelecionada.getNome(), Toast.LENGTH_SHORT).show();

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

    //Metodo arrastar item do recyclerview
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

                excluirDadosCadastrais(viewHolder);
            }
        };


        //Acao de arrastar ao recycleView
        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerView);
    }
    public void excluirDadosCadastrais(final RecyclerView.ViewHolder viewHolder){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Excluir movimencao");
        alertDialog.setMessage("Voce tem certeza que deseja apagar a movimentacao");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int position = viewHolder.getAdapterPosition();
                Pessoa pessoaRemove = listaPessoa.get(position);

                if ( excluirItemBanco(pessoaRemove)){

                    adapter.notifyItemRemoved(position);
                    Toast.makeText(ListaPessoasActivity.this, "Registro excluido", Toast.LENGTH_LONG).show();

                }else{

                    Toast.makeText(ListaPessoasActivity.this, "Falha ao excluir", Toast.LENGTH_LONG).show();
                    adapter.notify();
                }

            }

        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ListaPessoasActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();

            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    private boolean excluirItemBanco(Pessoa pessoaRemove){

    String[] args = {pessoaRemove.getId()};
        try{

            bancoDados.execSQL("DELETE FROM pessoas WHERE id = ?", args);
            recuperarDados();

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void abrirBanco(){

        //Abrir banco de dados
        bancoDados = openOrCreateDatabase("cadastro", MODE_PRIVATE, null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        searchView.setMenuItem(item);
        return true;
    }

    private void pesquisarCadastrados(){

        //Listener para SearchView
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                setaVoltar.setVisibility(View.VISIBLE);
                adapter = new AdapterPessoa(listaPessoa, getApplicationContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        //Configurar metodo SearchView
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            //Pegando texto digitado
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            //Pegando texto digitado e pesquisando
            @Override
            public boolean onQueryTextChange(String newText) {
                setaVoltar.setVisibility(View.INVISIBLE);

                List<Pessoa> listaPessoaBusca = new ArrayList<>();
                if (newText != null && !newText.isEmpty()){

                    for (int i =0; i < listaPessoa.size(); i++)
                     for (String pessoa: listaPessoa.get(i).getDadosPessoa()){
                         if (pessoa.contains( newText)){
                            listaPessoaBusca.add(listaPessoa.get(i));
                             break;
                         }

                     }
                     adapter = new AdapterPessoa(listaPessoaBusca, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });



    }

    public void voltar(View view){
        finish();
    }

    private void tapTarget(){
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.layouttTarget), "Arrastar para Excluir", "Para excluir um cadatro mova-o para o lado, clique e sim e pronto.")
                        // All options below are optional
                        .outerCircleColor(R.color.colorAccent)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.colorWhite)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.colorWhite)      // Specify the color of the title text
                        .descriptionTextSize(10)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.colorAccent)  // Specify the color of the description text
                        .textColor(R.color.colorBlack)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.colorBlack)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        .targetRadius(60),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                        target.setVisibility(view.INVISIBLE);
                    }
                });
    }
}

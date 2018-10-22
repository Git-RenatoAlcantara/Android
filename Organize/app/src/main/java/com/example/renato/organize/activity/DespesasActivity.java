package com.example.renato.organize.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.renato.organize.R;
import com.example.renato.organize.config.ConfiguracaoFireBase;
import com.example.renato.organize.helper.Base64Custom;
import com.example.renato.organize.helper.DateCustom;
import com.example.renato.organize.model.Movimentacao;
import com.example.renato.organize.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DespesasActivity extends AppCompatActivity {
    private TextInputEditText editData, editCategoria, editDescricao;
    private EditText  editValor;
    private Button fabSalvar;
    private Movimentacao movimentacao;
    private DatabaseReference firebaseRef = ConfiguracaoFireBase.getFirebaseDatabase();
    private FirebaseAuth autencacao = ConfiguracaoFireBase.getfireBaseAutenticacao();
    private Double despesaTotal = 0.00;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        //Referenicando os objetos
        editValor = findViewById(R.id.editValor);
        editCategoria = findViewById(R.id.editCategoria);
        editDescricao = findViewById(R.id.editDescricao);
        editData = findViewById(R.id.editData);


        //Pegando a data atual da classe DateCustom
        editData.setText(DateCustom.dataAtual());
        recuperarDespesaTotal();

    }
    public void enviarDados(View view){

        if (! editValor.getText().toString().isEmpty()){
            if (!editData.getText().toString().isEmpty()){
                if (!editDescricao.getText().toString().isEmpty()){
                    if (!editCategoria.getText().toString().isEmpty()){
                        salvarDespesas();
                    }else{
                        Toast.makeText(DespesasActivity.this, "Preencha o campo categoria", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(DespesasActivity.this, "Preencha o campo Descricação", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(DespesasActivity.this, "Preencha o campo data", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(DespesasActivity.this, "Preencha o campo valor", Toast.LENGTH_SHORT).show();
        }

    }

    public void salvarDespesas(){
        movimentacao = new Movimentacao();
        Double valorRecuperado = Double.parseDouble(editValor.getText().toString());
        movimentacao.setValor(valorRecuperado);
        movimentacao.setTipo("d");
        movimentacao.setData(editData.getText().toString());
        movimentacao.setCategoria(editCategoria.getText().toString());
        movimentacao.setDescricao(editDescricao.getText().toString());

        Double despesaAtualizada = despesaTotal + valorRecuperado;
        atualizarDespasas(despesaAtualizada);


        movimentacao.salvar();
        finish();
    }

    public void recuperarDespesaTotal(){

        DatabaseReference usuarioRef = ConfiguracaoFireBase.getFirebaseDatabase().child("usuarios");
        String email = autencacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.base64Encode(email);


        usuarioRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);
                    despesaTotal = usuario.getDespesaTotal();
                    System.out.println("Despesa total depses: " + despesaTotal);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
        });

    }
    public void atualizarDespasas(Double despesa){

        DatabaseReference usuarioRef = ConfiguracaoFireBase.getFirebaseDatabase().child("usuarios");
        String email = autencacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.base64Encode(email);

        usuarioRef.child(idUsuario).child("despesaTotal").setValue(despesa);
    }
}

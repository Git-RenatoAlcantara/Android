package com.example.renato.whatsappclolne.config;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {
    private static FirebaseAuth autenticacao;
    private static DatabaseReference firebase;


    //Retorna instancia do fairebaseDatabase
    public static DatabaseReference getFirebaseDatabase(){
        if (firebase == null) {
            firebase = FirebaseDatabase.getInstance().getReference();
        }
        System.out.println("Metodo FirebaseDatabase chamado...");
        return firebase;
    }

    //Retorna instancia do FirebaseAuth
    public static FirebaseAuth getfireBaseAutenticacao(){
        if (autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        System.out.println("Metodo FirebaseAuth chamado..");
        return autenticacao;
    }
}

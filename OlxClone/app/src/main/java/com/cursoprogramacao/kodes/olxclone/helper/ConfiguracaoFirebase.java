package com.cursoprogramacao.kodes.olxclone.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfiguracaoFirebase {

    private static DatabaseReference referenciaDatabase;
    private static FirebaseAuth firebaseAuth;
    private static StorageReference referenciaStorage;



    //Retorna a referencia do Database
    public static DatabaseReference getDatabaseReference(){

        if (    referenciaDatabase  == null ){

            return referenciaDatabase = FirebaseDatabase.getInstance().getReference();

        }

        return referenciaDatabase;
    }

    public static  FirebaseAuth getFirebaseAuth(){

        if (    firebaseAuth  == null   ){

            return firebaseAuth = FirebaseAuth.getInstance();

        }

        return firebaseAuth;
    }

    public static StorageReference getReferenciaStorage(){

        if (    referenciaDatabase == null  ){

            return referenciaStorage = FirebaseStorage.getInstance().getReference();

        }

        return referenciaStorage;
    }
}

package com.example.renato.whatsappclolne.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validarPermissoes(String[] permissoes, Activity activity, int requestCode){

        if (Build.VERSION.SDK_INT   >= 23){
            List<String> listaPermissoes = new ArrayList<>();


            /*Percorrer permissoes passadas
               verificando uma a uma
                * se ja tem a permissao liberada*/
            for (String permissao: permissoes){
               Boolean temPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
                if (!temPermissao) listaPermissoes.add(permissao);
            }



            /*Caso a lista esteja vazia nao e necessario solicitar permissao */
            if (listaPermissoes.isEmpty()) return true;


            //Convertendo lista de permissoes em array de string
            String[] novasPermissoes = new String[listaPermissoes.size()];
            listaPermissoes.toArray(novasPermissoes);


            //Solicitar permissao
            ActivityCompat.requestPermissions(activity, novasPermissoes, 1);

        }

        return true;
    }
}

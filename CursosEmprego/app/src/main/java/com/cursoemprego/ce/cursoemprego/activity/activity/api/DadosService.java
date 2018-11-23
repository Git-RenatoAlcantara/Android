package com.cursoemprego.ce.cursoemprego.activity.activity.api;

import com.cursoemprego.ce.cursoemprego.activity.activity.model.EmpregosResultado;
import com.cursoemprego.ce.cursoemprego.activity.activity.model.InformaticaResultado;
import com.cursoemprego.ce.cursoemprego.activity.activity.model.ListaResultado;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DadosService {
    @GET("api.php")
    Call<EmpregosResultado> recuperarEmpregos();

    @GET("api.php")
    Call<InformaticaResultado> recuperarCursos();
}

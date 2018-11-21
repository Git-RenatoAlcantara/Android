package com.cursoemprego.ce.cursoemprego.activity.activity.api;

import com.cursoemprego.ce.cursoemprego.activity.activity.model.EmpregosResultado;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DadosService {
    @GET("api.php")
    Call<EmpregosResultado> recuperarDados();
}

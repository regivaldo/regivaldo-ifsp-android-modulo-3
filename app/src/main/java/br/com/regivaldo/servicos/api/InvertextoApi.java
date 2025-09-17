package br.com.regivaldo.servicos.api;

import br.com.regivaldo.servicos.model.Cnpj;
import br.com.regivaldo.servicos.model.Logradouro;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InvertextoApi {
    @GET("/v1/cep/{numero}")
    Call<Logradouro> getLogradouro(
            @Path("numero") String numero,
            @Query("token") String token);

    @GET("v1/validator")
    Call<Cnpj> getCnpj(
            @Query("token") String token,
            @Query("value") String value,
            @Query("type") String type);
}

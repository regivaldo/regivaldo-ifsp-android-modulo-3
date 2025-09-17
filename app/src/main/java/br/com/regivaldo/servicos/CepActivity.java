package br.com.regivaldo.servicos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.regivaldo.servicos.api.InvertextoApi;
import br.com.regivaldo.servicos.model.Logradouro;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cep);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etCep = findViewById(R.id.etCep);
        Button btBuscar = findViewById(R.id.btBuscar);
        TextView twEndereco = findViewById(R.id.twEndereco);

        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twEndereco.setText(R.string.loading);
                consultar(etCep.getText().toString());
            }
        });
    }


    public void consultar(String numero) {
        TextView twEndereco = findViewById(R.id.twEndereco);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InvertextoApi api = retrofit.create(InvertextoApi.class);

        String token = BuildConfig.API_TOKEN;
        Call<Logradouro> call = api.getLogradouro(numero, token);

        call.enqueue(new Callback<Logradouro>() {
            @Override
            public void onResponse(Call<Logradouro> call, Response<Logradouro> response) {
                if (response.isSuccessful()) {
                    Logradouro logradouro = response.body();
                    twEndereco.setText(logradouro.getEndereco());
                } else {
                    Toast.makeText(
                            CepActivity.this,
                            R.string.error_not_found,
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Logradouro> call, Throwable t) {
                Toast.makeText(
                        CepActivity.this,
                        R.string.error_connection,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
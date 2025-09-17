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
import br.com.regivaldo.servicos.model.Cnpj;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CnpjActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cnpj);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        EditText etCnpj = findViewById(R.id.etCnpj);
        Button btBuscar = findViewById(R.id.btBuscar);
        TextView twInfo = findViewById(R.id.twInfo);

        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twInfo.setText(R.string.loading);
                validar(etCnpj.getText().toString());
            }
        });
    }

    public void validar(String numero) {
        TextView twInfo = findViewById(R.id.twInfo);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InvertextoApi api = retrofit.create(InvertextoApi.class);

        String token = BuildConfig.API_TOKEN;
        Call<Cnpj> call = api.getCnpj(token, numero, "cnpj");

        call.enqueue(new Callback<Cnpj>() {
            @Override
            public void onResponse(Call<Cnpj> call, Response<Cnpj> response) {
                if (response.isSuccessful()) {
                    Cnpj cnpj = response.body();
                    twInfo.setText(cnpj.getInformation());
                } else {
                    Toast.makeText(
                            CnpjActivity.this,
                            R.string.error_cnpj_not_found,
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Cnpj> call, Throwable t) {
                Toast.makeText(
                        CnpjActivity.this,
                        R.string.error_connection,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
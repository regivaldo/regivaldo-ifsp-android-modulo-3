package br.com.regivaldo.servicos.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Logradouro {
    private String cep;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String complement;
    private String ibge;

    public String getEndereco() {
        return this.street.concat("\n")
                .concat(this.neighborhood).concat("\n")
                .concat(this.city).concat(" / ").concat(this.state);
    }
}

package br.com.regivaldo.servicos.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Cnpj {
    private Boolean valid;
    private String formatted;

    public String getInformation() {
        return this.valid ? "CNPJ Válido".concat("\n").concat(this.formatted): "CNPJ Inválido";
    }
}

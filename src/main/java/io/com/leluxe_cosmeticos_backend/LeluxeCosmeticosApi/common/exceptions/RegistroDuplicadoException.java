package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions;

public class RegistroDuplicadoException extends RuntimeException {

    private final String entidade;
    private final String campo;
    private final String valor;

    public RegistroDuplicadoException(String entidade, String campo, String valor) {
        super(String.format("%s com %s '%s' já existe", entidade, campo, valor));
        this.entidade = entidade;
        this.campo = campo;
        this.valor = valor;
    }

    public String getEntidade() {
        return entidade;
    }

    public String getCampo() {
        return campo;
    }

    public String getValor() {
        return valor;
    }
}
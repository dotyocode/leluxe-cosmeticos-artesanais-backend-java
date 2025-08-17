package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions;

public class CampoInvalidoException extends RuntimeException {

    private final String campo;
    private final String valor;

    public CampoInvalidoException(String campo, String valor) {
        super(String.format("Campo '%s' com valor '%s' é inválido", campo, valor));
        this.campo = campo;
        this.valor = valor;
    }

    public CampoInvalidoException(String campo, String valor, String message) {
        super(message);
        this.campo = campo;
        this.valor = valor;
    }

    public CampoInvalidoException(String campo, String valor, String message, Throwable cause) {
        super(message, cause);
        this.campo = campo;
        this.valor = valor;
    }

    public String getCampo() {
        return campo;
    }

    public String getValor() {
        return valor;
    }
}
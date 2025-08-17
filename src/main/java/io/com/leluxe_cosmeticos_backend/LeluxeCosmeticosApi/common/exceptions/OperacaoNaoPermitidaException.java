package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions;

public class OperacaoNaoPermitidaException extends RuntimeException {

    private final String operacao;
    private final String motivo;

    public OperacaoNaoPermitidaException(String operacao, String motivo) {
        super(String.format("Operação '%s' não é permitida: %s", operacao, motivo));
        this.operacao = operacao;
        this.motivo = motivo;
    }

    public OperacaoNaoPermitidaException(String operacao, String motivo, String message) {
        super(message);
        this.operacao = operacao;
        this.motivo = motivo;
    }

    public OperacaoNaoPermitidaException(String operacao, String motivo, String message, Throwable cause) {
        super(message, cause);
        this.operacao = operacao;
        this.motivo = motivo;
    }

    public String getOperacao() {
        return operacao;
    }

    public String getMotivo() {
        return motivo;
    }
}

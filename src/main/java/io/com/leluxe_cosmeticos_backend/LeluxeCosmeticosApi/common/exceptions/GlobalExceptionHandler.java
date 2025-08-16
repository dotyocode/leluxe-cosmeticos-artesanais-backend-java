package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.Data;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Data
    public static class ErrorResponse {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
        private Map<String, Object> details;

        public ErrorResponse() {
            this.timestamp = LocalDateTime.now();
            this.details = new HashMap<>();
        }
    }

    /**
     * Trata exceções de validação (Bean Validation)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError("Erro de Validação");
        errorResponse.setMessage("Dados de entrada inválidos");
        errorResponse.setPath(request.getDescription(false));

        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });
        errorResponse.setDetails(Map.of("validationErrors", validationErrors));

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Trata exceções de campo inválido
     */
    @ExceptionHandler(CampoInvalidoException.class)
    public ResponseEntity<ErrorResponse> handleCampoInvalidoException(
            CampoInvalidoException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError("Campo Inválido");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(request.getDescription(false));
        errorResponse.setDetails(Map.of(
                "campo", ex.getCampo(),
                "valor", ex.getValor()));

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Trata exceções de registro não encontrado
     */
    @ExceptionHandler(RegistroNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleRegistroNaoEncontradoException(
            RegistroNaoEncontradoException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setError("Registro Não Encontrado");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(request.getDescription(false));
        errorResponse.setDetails(Map.of(
                "entidade", ex.getEntidade(),
                "campo", ex.getCampo(),
                "valor", ex.getValor()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Trata exceções de registro duplicado
     */
    @ExceptionHandler(RegistroDuplicadoException.class)
    public ResponseEntity<ErrorResponse> handleRegistroDuplicadoException(
            RegistroDuplicadoException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        errorResponse.setError("Registro Duplicado");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(request.getDescription(false));
        errorResponse.setDetails(Map.of(
                "entidade", ex.getEntidade(),
                "campo", ex.getCampo(),
                "valor", ex.getValor()));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    /**
     * Trata exceções de operação não permitida
     */
    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    public ResponseEntity<ErrorResponse> handleOperacaoNaoPermitidaException(
            OperacaoNaoPermitidaException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
        errorResponse.setError("Operação Não Permitida");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(request.getDescription(false));
        errorResponse.setDetails(Map.of(
                "operacao", ex.getOperacao(),
                "motivo", ex.getMotivo()));

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    /**
     * Trata exceções genéricas (RuntimeException)
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(
            RuntimeException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setError("Erro Interno do Servidor");
        errorResponse.setMessage("Ocorreu um erro inesperado");
        errorResponse.setPath(request.getDescription(false));
        errorResponse.setDetails(Map.of("originalMessage", ex.getMessage()));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Trata exceções genéricas (Exception)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setError("Erro Interno do Servidor");
        errorResponse.setMessage("Ocorreu um erro inesperado");
        errorResponse.setPath(request.getDescription(false));
        errorResponse.setDetails(Map.of("originalMessage", ex.getMessage()));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

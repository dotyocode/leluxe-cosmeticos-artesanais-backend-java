package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.controllers.enderecos;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.GlobalExceptionHandler.SuccessResponse;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos.EnderecoCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos.EnderecoDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos.EnderecoUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.enderecos.EnderecoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("enderecos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> findAll() {
        List<EnderecoDTO> enderecos = enderecoService.findAll();
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> findById(@PathVariable Long id) {
        EnderecoDTO endereco = enderecoService.findByIdOrThrow(id);
        return ResponseEntity.ok(endereco);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<EnderecoDTO>> findByUsuarioId(@PathVariable Long usuarioId) {
        List<EnderecoDTO> enderecos = enderecoService.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(enderecos);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<EnderecoDTO>> create(
            @Valid @RequestBody EnderecoCreateDTO enderecoCreateDTO) {
        EnderecoDTO createdEndereco = enderecoService.create(enderecoCreateDTO);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String path = request.getRequestURI();

        SuccessResponse<EnderecoDTO> response = SuccessResponse.of(
                createdEndereco,
                "Endereço criado com sucesso",
                HttpStatus.CREATED.value(),
                path);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<EnderecoDTO>> update(@PathVariable Long id,
            @Valid @RequestBody EnderecoUpdateDTO enderecoUpdateDTO) {
        EnderecoDTO updatedEndereco = enderecoService.updateOrThrow(id, enderecoUpdateDTO);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String path = request.getRequestURI();

        SuccessResponse<EnderecoDTO> response = SuccessResponse.of(
                updatedEndereco,
                "Endereço atualizado com sucesso",
                HttpStatus.OK.value(),
                path);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Void>> delete(@PathVariable Long id) {
        enderecoService.delete(id);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String path = request.getRequestURI();

        SuccessResponse<Void> response = SuccessResponse.of(
                null,
                "Endereço excluído com sucesso",
                HttpStatus.OK.value(),
                path);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/principal")
    public ResponseEntity<SuccessResponse<EnderecoDTO>> setPrincipal(@PathVariable Long id) {
        EnderecoDTO endereco = enderecoService.setPrincipalOrThrow(id);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String path = request.getRequestURI();

        SuccessResponse<EnderecoDTO> response = SuccessResponse.of(
                endereco,
                "Endereço definido como principal com sucesso",
                HttpStatus.OK.value(),
                path);

        return ResponseEntity.ok(response);
    }
}

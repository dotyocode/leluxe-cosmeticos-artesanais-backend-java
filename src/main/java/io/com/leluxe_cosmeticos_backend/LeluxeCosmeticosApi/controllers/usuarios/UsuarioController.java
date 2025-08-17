package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.controllers.usuarios;

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
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioComEnderecoCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.usuarios.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        List<UsuarioDTO> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.findByIdOrThrow(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<UsuarioDTO>> create(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        UsuarioDTO createdUsuario = usuarioService.create(usuarioCreateDTO);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String path = request.getRequestURI();

        SuccessResponse<UsuarioDTO> response = SuccessResponse.of(
                createdUsuario,
                "Usuário criado com sucesso",
                HttpStatus.CREATED.value(),
                path);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/com-endereco")
    public ResponseEntity<SuccessResponse<UsuarioDTO>> createWithEndereco(
            @Valid @RequestBody UsuarioComEnderecoCreateDTO dto) {
        UsuarioDTO createdUsuario = usuarioService.createWithEndereco(dto);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String path = request.getRequestURI();

        SuccessResponse<UsuarioDTO> response = SuccessResponse.of(
                createdUsuario,
                "Usuário criado com endereço com sucesso",
                HttpStatus.CREATED.value(),
                path);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<UsuarioDTO>> update(@PathVariable Long id,
            @Valid @RequestBody UsuarioUpdateDTO dto) {
        UsuarioDTO updatedUsuario = usuarioService.update(id, dto);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String path = request.getRequestURI();

        SuccessResponse<UsuarioDTO> response = SuccessResponse.of(
                updatedUsuario,
                "Usuário atualizado com sucesso",
                HttpStatus.OK.value(),
                path);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Void>> delete(@PathVariable Long id) {
        usuarioService.delete(id);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String path = request.getRequestURI();

        SuccessResponse<Void> response = SuccessResponse.of(
                null,
                "Usuário excluído com sucesso",
                HttpStatus.OK.value(),
                path);

        return ResponseEntity.ok(response);
    }
}

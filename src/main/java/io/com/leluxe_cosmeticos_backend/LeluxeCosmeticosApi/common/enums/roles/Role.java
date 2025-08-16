package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.enums.roles;

public enum Role {
    ADMIN(1, "ADMIN"),
    USUARIO(2, "USUARIO");

    private final Integer codigo;
    private final String descricao;

    Role(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Role fromCodigo(Integer codigo) {
        for (Role role : Role.values()) {
            if (role.getCodigo().equals(codigo)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Código de role inválido: " + codigo);
    }

    public static Role fromDescricao(String descricao) {
        for (Role role : Role.values()) {
            if (role.getDescricao().equalsIgnoreCase(descricao)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Descrição de role inválida: " + descricao);
    }
}

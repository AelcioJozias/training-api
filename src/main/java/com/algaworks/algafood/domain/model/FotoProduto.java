package com.algaworks.algafood.domain.model;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class FotoProduto {

    @Id
    @Column(name = "pedido_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private Produto produto;

    private String nomeArquivo;

    private String descricao;

    private String contentType;

    private Long tamanho;

    public Long getRestauranteId() {
        if (getProduto() != null) {
            return getProduto().getRestaurante().getId();
        }

        return null;
    }

    
}

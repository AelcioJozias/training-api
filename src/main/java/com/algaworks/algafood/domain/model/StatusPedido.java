package com.algaworks.algafood.domain.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum StatusPedido {
    /**
     * O segundo argumento significa, por exemplo, que pedido só pode ir para o x, se ele conter o y
     */
    CRIADO("Criado"),
    CONFIRMADO("Confirmado", CRIADO),
    ENTREGUE("Entregue", CONFIRMADO),
    CANCELADO("Cancelado", CRIADO);

    private final String descricao;
    private final List<StatusPedido> statusAnteriores;

    private StatusPedido(String descricao, StatusPedido... statusAnteriores) {
        this.descricao = descricao;
        this.statusAnteriores = Arrays.asList(statusAnteriores);
    }

    /**
     * Verifica se o status atual não pode ser alterado para o status passado como parâmetro.
     * A comparação é feita, verificando se no enum passado, contém o enum atual (this). Se ele contém, a resposta é true
     * tornando-o o como false, ao negar toda a sentença (!), o que significa que não tem problema o status x ir para o y.
     * Resumindo, só pode ser alterado para o próximo status
     * quando o novo status, contém o status anterior.

     * Caso contrário, quando não contém o status anterior, o método retornará true, pois o status não pode ser alterado.
     *
     * @param novoStatus status que será comparado se pode haver uma alteração.
     * @return TRUE se não pode ser alterado e FALSE quando é permitido
     */
    public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
        return !novoStatus.statusAnteriores.contains(this);
    }


}
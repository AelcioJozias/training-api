package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.input.ProdutoInputDTO;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoDisassembler {

    @Autowired
    ModelMapper modelMapper;

    public Produto toDomainObject(ProdutoInputDTO produtoInputDTO) {
        return modelMapper.map(produtoInputDTO, Produto.class);
    }

    public void copyToDomainObject(ProdutoInputDTO produtoInputDTO, Produto produto) {
        modelMapper.map(produtoInputDTO, produto);
    }

}

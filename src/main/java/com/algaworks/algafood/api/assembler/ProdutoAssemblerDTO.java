package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.ProdutoDTO;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoAssemblerDTO {

    @Autowired
    ModelMapper modelMapper;

    public ProdutoDTO toDTO(Produto produto) {
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public List<ProdutoDTO> toCollectionDTO(Collection<Produto> produtos) {
        return produtos.stream().map(this::toDTO).collect(Collectors.toList());
    }

}

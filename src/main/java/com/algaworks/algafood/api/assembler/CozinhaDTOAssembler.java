package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaDTOAssembler {
  
  @Autowired
    private ModelMapper modelMapper;

    public CozinhaDTO toDTO(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaDTO.class);
    }

    public List<CozinhaDTO> toCollectionDTO(List<Cozinha> cozinhas) {
        return cozinhas.stream().map(this::toDTO)
                .collect(Collectors.toList());
    }
}

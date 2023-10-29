package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.CozinhaDTO;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaDTODisassembler {

  @Autowired
  ModelMapper mapper;
  
  public Cozinha toDomainObject(CozinhaDTO cozinhaDTO) {
    return mapper.map(cozinhaDTO, Cozinha.class);
  }
}

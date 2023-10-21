package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.CidadeDTO;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeDTOAssembler {

    @Autowired
    ModelMapper modelMapper;

    public CidadeDTO toDTO(Cidade cidade) {
        return modelMapper.map(cidade, CidadeDTO.class);
    }

    public List<CidadeDTO> toCollectionDTO(List<Cidade> cidadeList) {
        return cidadeList.stream().map(this::toDTO).collect(Collectors.toList());
    }
}

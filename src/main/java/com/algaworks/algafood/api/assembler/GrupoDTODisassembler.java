package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.input.GrupoInputDTO;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoDTODisassembler {

    @Autowired
    ModelMapper modelMapper;

    public Grupo toModel(GrupoInputDTO grupoInputDTO) {
        return modelMapper.map(grupoInputDTO, Grupo.class);
    }

}

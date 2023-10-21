package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.input.EstadoInputDTO;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Estado toModel(EstadoInputDTO estadoInputDTO) {
        return modelMapper.map(estadoInputDTO, Estado.class);
    }

    public void copyToDomainObject(EstadoInputDTO estadoInputDTO, Estado estado) {
        modelMapper.map(estadoInputDTO, estado);
    }
}

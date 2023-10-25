package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.input.CidadeInputDTO;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade toModel(CidadeInputDTO cidadeInputDTO) {
        return modelMapper.map(cidadeInputDTO, Cidade.class);
    }

    public void copyToDomainObject(CidadeInputDTO cidadeInputDTO, Cidade cidade) {
        cidade.setEstado(new Estado());
        modelMapper.map(cidadeInputDTO, cidade);
    }

}

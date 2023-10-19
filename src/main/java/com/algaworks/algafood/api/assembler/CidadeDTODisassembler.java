package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.api.model.EstadoDTO;
import com.algaworks.algafood.api.model.input.CidadeInputDTO;
import com.algaworks.algafood.domain.model.Cidade;
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
}

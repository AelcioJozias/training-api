package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.PermissaoDTO;
import com.algaworks.algafood.domain.model.Permissao;

@Component
public class PermissaoAssemblerDTO {

    @Autowired
    ModelMapper modelMapper;

    public PermissaoDTO toDTO(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoDTO.class);
    }

    public List<PermissaoDTO> toCollectionDTO(Collection<Permissao> permissoes) {
        return permissoes.stream().map(this::toDTO).collect(Collectors.toList());
    }

}

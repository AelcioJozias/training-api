package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.input.UsuarioAtualizarInputDTO;
import com.algaworks.algafood.api.dto.input.UsuarioCadastroInputDTO;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toModelObject(UsuarioCadastroInputDTO usuarioCadastroInputDTO) {
        return modelMapper.map(usuarioCadastroInputDTO, Usuario.class);
    }

    public void copyDTOToModel(UsuarioAtualizarInputDTO usuarioAtualizarInputDTO, Usuario usuario) {
        modelMapper.map(usuarioAtualizarInputDTO, usuario);
    }

    public void copiarUsuarioAtualizacaoParaUsuarioModel(UsuarioAtualizarInputDTO usuarioAtualizarInputDTO, Usuario usuario) {
        modelMapper.map(usuarioAtualizarInputDTO, usuario);
    }
    
}

package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradaExcpetion;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CadastroPermissaoService {

    @Autowired
    PermissaoRepository permissaoRepository;


    public Permissao buscar(Long id) {
        return buscarOuFalhar(id);
    }

    public Permissao buscarOuFalhar(Long id) {
        return permissaoRepository.findById(id).orElseThrow(() -> new PermissaoNaoEncontradaExcpetion(id));
    }
}

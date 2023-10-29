package com.algaworks.algafood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.assembler.GrupoDTODisassembler;
import com.algaworks.algafood.api.dto.GrupoDTO;
import com.algaworks.algafood.api.dto.input.GrupoInputDTO;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class CadastroGrupoService {

    

    private GrupoDTOAssembler grupoDTOAssembler;
    private GrupoDTODisassembler grupoDTODisassembler;
    private GrupoRepository grupoRepository;

   public CadastroGrupoService(GrupoDTOAssembler grupoDTOAssembler, GrupoDTODisassembler grupoDTODisassembler,
                               GrupoRepository grupoRepository){
        setGrupoRepository(grupoRepository);
        setGrupoDTODisassembler(grupoDTODisassembler);
        setGrupoDTOAssembler(grupoDTOAssembler);
   }

   @Transactional
    public GrupoDTO salvar(GrupoInputDTO grupoInputDTO) {
        return getGrupoDTOAssembler().toDTO(grupoRepository.save(grupoDTODisassembler.toDomainObject(grupoInputDTO)));
    }

    public GrupoDTO buscar(Long id) {
       return grupoDTOAssembler.toDTO(buscarOuFalhar(id));
    }

    public List<GrupoDTO> listar() {
       return grupoDTOAssembler.toCollectionDTO(grupoRepository.findAll());
    }

    @Transactional
    public void excluir(Long id) {
      try {
        buscarOuFalhar(id);
        grupoRepository.deleteById(id);
        grupoRepository.flush();
      } catch (ConstraintViolationException e) {
        throw new EntidadeEmUsoException(String.format("Grupo de id %d não pode ser removido, pois está em uso", id));
      } 
       
    }

    public Grupo buscarOuFalhar(Long id) {
       return grupoRepository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }
}

package com.algaworks.algafood.domain.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoDisassembler;
import com.algaworks.algafood.api.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.dto.input.FormaPagamentoInputDTO;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Service
public class CadastroFormaPagamentoService {

    public static final String FORMA_DE_PAGAMENTO_DE_ID_D_NAO_PODE_SER_REMOVIDA_POIS_ESTA_EM_USO = "Forma de Pagamento de id %s não pode ser removida, pois está em uso";


    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    FormaPagamentoAssembler formaPagamentoAssembler;

    @Autowired
    FormaPagamentoDisassembler formaPagamentoDisassembler;


    @Transactional
    public FormaPagamentoDTO salvar(FormaPagamentoInputDTO formaPagamentoInputDTO) {
        return formaPagamentoAssembler.toDTO(formaPagamentoRepository.save(formaPagamentoDisassembler.toDomainObeject(formaPagamentoInputDTO)));
    }

    public FormaPagamentoDTO atualizarRecursoCompleto (Long id, FormaPagamentoInputDTO formaPagamentoInputDTO) {
        FormaPagamento formaPagamentoAtual = buscarOuFalhar(id);
        BeanUtils.copyProperties(formaPagamentoInputDTO, formaPagamentoAtual);
        return formaPagamentoAssembler.toDTO(formaPagamentoRepository.save(formaPagamentoAtual));
    }

    public List<FormaPagamentoDTO> listarFormasPagamento() {
        return formaPagamentoAssembler.toCollectionDTO(formaPagamentoRepository.findAll());
    }

    public FormaPagamentoDTO buscarPorId(Long id) {
        return formaPagamentoAssembler.toDTO(buscarOuFalhar(id));
    }

    public FormaPagamento buscarOuFalhar(Long id) {
        return formaPagamentoRepository.findById(id).orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
    }

    @Transactional
    public void excluir(Long id) {
        try {
            buscarOuFalhar(id);
            formaPagamentoRepository.deleteById(id);
            formaPagamentoRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(FORMA_DE_PAGAMENTO_DE_ID_D_NAO_PODE_SER_REMOVIDA_POIS_ESTA_EM_USO, id));
        } catch (EmptyResultDataAccessException e) {
          throw new FormaPagamentoNaoEncontradaException(id);
        }
    }

    public String genereteEtag(ServletWebRequest request) {
        // desabilitando o etag que é gerado automaticamente, devido a instancia de bean na classe WebConfig, desse projeto
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        // definido pra zero, caso não encontre um registro no banco
        String eTag = "0";

        OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getMaxValueDataAtualizacao();


        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }
        return eTag;
    }
}

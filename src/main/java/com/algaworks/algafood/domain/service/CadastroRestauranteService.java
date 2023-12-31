package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

    private static final String NAO_EXISTE_UM_RESTAURANTE_COM_O_ID = "Não existe um restaurante com o id: %s";

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    CadastroUsuarioService cadastroUsuarioService;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        restaurante.setCozinha(cadastroCozinhaService.buscarOuFalhar(restaurante.getCozinha().getId()));
        restaurante.getEndereco().setCidade(buscarOuFalharCidade(restaurante.getEndereco().getCidade().getId()));
        return restauranteRepository.save(restaurante);
    }

    public void buscarOuFalharCozinha(Restaurante restaurante) {
        cadastroCozinhaService.buscarOuFalhar(restaurante.getCozinha().getId());
    }

    public Restaurante buscarOuFalhar(Long id) {
        return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(
                String.format(NAO_EXISTE_UM_RESTAURANTE_COM_O_ID, id)));
    }

    @Transactional
    public void ativar(Long id) {
        Restaurante restaurante = buscarOuFalhar(id);
        restaurante.ativar();
    }

    @Transactional
    public void desativar(Long id) {
        Restaurante restaurante = buscarOuFalhar(id);
        restaurante.desativar();
    }

    @Transactional
    public void ativar(List<Long> ids) {
        ids.forEach(this::ativar);
    }

    @Transactional
    public void desativar(List<Long> ids) {
        ids.forEach(this::desativar);
    }

    public Cidade buscarOuFalharCidade(Long id) {
        return cadastroCidadeService.buscarOuFalhar(id);
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);
        restaurante.adicionarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);
        restaurante.removerFormaPagamento(formaPagamento);
    }

    @Transactional
    public void abrir(Long id) {
        Restaurante restaurante = buscarOuFalhar(id);
        restaurante.abrir();
    }

    @Transactional
    public void fechar(Long id) {
        Restaurante restaurante = buscarOuFalhar(id);
        restaurante.fechar();
    }

    @Transactional
    public void adicionarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Usuario usuario = cadastroUsuarioService.buscarPorId(usuarioId);
        restaurante.adicionarReponsavel(usuario);
    }

    @Transactional
    public void removerResponsavel(Long restauranteId, Long usuarioId) {
        // dúvidas pro vini
        /*
        * é mais correto fazer uma exclusão da maneira abaixo
        * ou fazer logo um sql pra deletar, junto de um trycatch,
        * para caso não exista o rescurso??
        * */
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Usuario usuario = cadastroUsuarioService.buscarPorId(usuarioId);
        restaurante.removerResponsavel(usuario);
    }

}

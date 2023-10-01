package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {

  @Autowired
  CozinhaRepository cozinhaRepository;

  @PostMapping("/{cozinhaId}")
  public void deletarCozinhaTeste(@PathVariable Long cozinhaId) {
    cozinhaRepository.deleteById(90l);
  }
}

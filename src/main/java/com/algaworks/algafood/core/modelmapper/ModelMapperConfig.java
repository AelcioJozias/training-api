package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.dto.EnderecoDTO;
import com.algaworks.algafood.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();



        /*
        * Todo o código abaixo nao funciona nas versoes mais nova de java
        *  seta no @JsonSetter o nome da propriedade que é mais fácil
        * */
        // código abaixo serve para fazer um mapeamento manual, usando o o modelMapper

        // cria a relacao entre o que você quer mapear
//        @Deprecated
//        var enderecoToEnderecoDTOTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class).include(Endereco);
//
//        // faz o mapeamento de acordo com valor que você fornece pela expressão lambda
//        enderecoToEnderecoDTOTypeMap.<String>addMapping(src -> src.getCidade().getEstado().getNome(),
//                (dest, value) -> dest.getCidade().setNomeEstado(value));


        return modelMapper;
    }
}

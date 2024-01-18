package com.algaworks.algafood.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // esse bean ativa o CORS global no projeto
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*");
    }


    /**
     * Esse filtro além de criar o Etag, também faz a comparação do que vem no header if-non-match,
     * para saber se o que está sendo requitado na api, já não seria a mesma coisa que o client tem e cacha, se a
     * reposta for positiva, retorna apenas um 304, indicando para o client que nada mudou. E assim o client continua
     * usando o que já tem em cache, que estava 'stale'.
     * @return nova instancia de um Filtro ShallowEtag, que vai gerar um entity tag no cabeçalho.
     *
     */
    @Bean
    public Filter shallowEtagFilter() {
        return new ShallowEtagHeaderFilter();
    }
}

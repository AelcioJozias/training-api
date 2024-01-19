package com.algaworks.algafood.core.openapi;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {


    /**
     * instância para ativar o SpringFox.
     * Docket é uma classe do spring fox que representa a configuração da api para gerar a definição
     * usando a especicação open Api.
     * Esse docket irá estabelecer o conjutno de servicos que devem ser documentados.
     * @return new Docket com as configurações estabelecidas
     */
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // indica quais endpoins dever sem documentados
                .apis(RequestHandlerSelectors.any())
                .build();
    }
}

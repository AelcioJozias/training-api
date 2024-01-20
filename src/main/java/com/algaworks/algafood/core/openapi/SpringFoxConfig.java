package com.algaworks.algafood.core.openapi;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig implements WebMvcConfigurer {


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
                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
                .paths(PathSelectors.any()) // -> esse já o valor padrão, está aqui apenas para referência
                // no exemplo abaixo pegaria apenas os endpoints que começacem com restaurantes
                //.paths(PathSelectors.regex("/restaurantes"))
                .build();
    }

    /**
     *  Sobreescrita do método que faz a configuração de recursos para ficar acessíveis.
     *
      * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        /**
         * addResourceHandler: o arquivo(s) que desejamos dicionar.
         * addResourceLocations: o path para chegar nesse arquivo.
          */
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        // Obs: esses arquivos são pegos do swagger-ui.
    }

}

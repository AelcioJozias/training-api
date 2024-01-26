package com.algaworks.algafood.core.openapi;

import com.algaworks.algafood.api.dto.CozinhaDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.openapi.dto.CozinhasDtoOpenApi;
import com.algaworks.algafood.openapi.dto.PageableDtoOpenApi;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
/*
  @Import(BeanValidatorPluginsConfiguration.class)
 * importando para a nossa configuração, algumas configs do bean valifation para serem aplicadas ao spring-fox.
 */
@Import(BeanValidatorPluginsConfiguration.class)
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
        // instancia do objeto que vai resolver o tipo que queromos adicionar ao model do swagger
        TypeResolver typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // indica quais endpoins dever sem documentados
                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
                .paths(PathSelectors.any()) /* -> esse já o valor padrão, está aqui apenas para referência
                 no exemplo abaixo pegaria apenas os endpoints que começacem com restaurantes
                .paths(PathSelectors.regex("/restaurantes")). */
                .build()

                // instancia dos status code globais
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
                .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())

                // ignora todos os parametros que herdam o tipo servletRequest.class, como por exemplo no endpoint
                // de buscar forma de pagamento por id
                .ignoredParameterTypes(ServletWebRequest.class)

                // adiciona um model no doc
                .additionalModels(typeResolver.resolve(Problem.class))

                // substitui o objeto pageable pelo pageable model api, que é apenas o resumo do necessário para a documentacão. Isso nos parâmetros de entrada apenas...
                .directModelSubstitute(Pageable.class, PageableDtoOpenApi.class)

                /*
                 * Vamos desmabrar esse metodo.
                 * alternateTypeRules -> vai adicionar uma nova regra de substituição
                 * AlternateTypeRules.newRule -> vai explicitar a regra, vou querer trocar um page<Cozinha> ->
                 * pelo objeto CozinhaDTOOpenApi
                 */
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Page.class, CozinhaDTO.class),
                        CozinhasDtoOpenApi.class
                ))
                // tags
                .tags(new Tag("Cidade", "Gerencias as cidades"))
                .tags((new Tag("Grupos", "Gerencia os grupos de usuários")))
                .tags((new Tag("Cozinhas", "Gerencia as cozinhas")))

                //informacoes da api
                .apiInfo(apiInfo());
    }
    private List<ResponseMessage> globalGetResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno do servidor")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build()
        );
    }

    private List<ResponseMessage> globalPostPutResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Requisição inválida (erro do cliente)")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno no servidor")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                        .message("Requisição recusada porque o corpo está em um formato não suportado")
                        .responseModel(new ModelRef("Problema"))
                        .build()
        );
    }

    private List<ResponseMessage> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Requisição inválida (erro do cliente)")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno no servidor")
                        .responseModel(new ModelRef("Problema"))
                        .build()
        );
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

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Trainig Api")
                .description("Api apenas para treinamento")
                .contact(new Contact("Training Api", "http.trainingapi.com", "traningapi@gmail.com"))
                .version("1")
                .build();
    }
}

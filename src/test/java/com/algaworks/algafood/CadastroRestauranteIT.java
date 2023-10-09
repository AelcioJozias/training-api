package com.algaworks.algafood;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.when;

import java.io.IOException;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ReadAllTextFromFileUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource({ "/application-test.properties" })
public class CadastroRestauranteIT {

	private static final String PATH_CADASTRO_RESTAURATE_JSON_FILE = "src/test/resources/jsons/cadastroRestaurante.json";

	@LocalServerPort
	private int port;

	@Autowired
	DatabaseCleaner databaseCleaner;

	@Autowired
	CozinhaRepository cozinhaRepository;

	@Autowired
	CadastroRestauranteService cadastroRestauranteService;

	private Restaurante restaurante;

	@Before
	public void setUp() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = this.port;
		basePath = "/restaurantes";
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarRestauranteComSucesso() {
		String jsonCadastroRestaurante = ReadAllTextFromFileUtils.
				getAllStringFile(PATH_CADASTRO_RESTAURATE_JSON_FILE);
		RestAssured.given()
			.body(jsonCadastroRestaurante)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarDadosDeUmRestauranteEStatus200_QuandoPesquisarRestauranteExistente() {
		RestAssured.given()
			.pathParam("restauranteId", restaurante.getId())
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", Matchers.equalTo(restaurante.getNome()));
	}
	
	public void prepararDados() {
		Cozinha cozinhaTailandresa = new Cozinha();
		cozinhaTailandresa.setNome("Tailandesa");
		cozinhaRepository.save(cozinhaTailandresa);
		salvaRestauranteCadastradoNoJsonFile();
	}

	private void salvaRestauranteCadastradoNoJsonFile() {
		String jsonCadastroRestaurante = ReadAllTextFromFileUtils.getAllStringFile(PATH_CADASTRO_RESTAURATE_JSON_FILE);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			restaurante = objectMapper.readValue(jsonCadastroRestaurante, Restaurante.class);
			cadastroRestauranteService.salvar(restaurante);
		} catch (IOException e) {
			throw new NegocioException("Erro ao desserializar json", e);
		}
		
	}
}

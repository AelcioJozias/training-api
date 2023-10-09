package com.algaworks.algafood;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;

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

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtills;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource({"/application-test.properties"})
public class CadastroCozinhaIT {

	private static final String JSONS_CADASTRO_COZINHA_PATH = "/jsons/cadastroCozinha.json";

	private static final int cozinhaIdInexistente = 100;
	
	private int numeroDeCozinhas = 0;
	
	private Cozinha cozinhaTailandresa;

	@LocalServerPort
	private int port;
	
	@Autowired
	CozinhaRepository cozinhaRepository;

	@Autowired
	DatabaseCleaner databaseCleaner;
	
	@Before
	public void setUp() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = this.port;
		basePath = "/cozinhas";
		databaseCleaner.clearTables();
		prepararDados();
		System.out.println("-----------------------  NÚMERO DE COZINHAS: " + this.numeroDeCozinhas + " ----------------" );
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveConternumeroDeCozinhasCozinhasAdicionadaEmPreradarDados_QuandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(this.numeroDeCozinhas));
	}
	
	
	@Test
	public void deveRetornaStatus201_QuandoCadastrarCozinha() {
		String json = ResourceUtills.getContentFromResource(JSONS_CADASTRO_COZINHA_PATH);
		given()
			.body(json)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
		
	}
	
	public void deveRetornarConsultaEStatusCorreto_QuandoConsultarCozinhaExistente() {
		RestAssured.given()
			.pathParam("cozinhaId", cozinhaTailandresa.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", Matchers.equalTo(cozinhaTailandresa.getNome()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		RestAssured.given()
			.pathParam("cozinhaId", cozinhaIdInexistente)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	public void prepararDados() {
		cozinhaTailandresa = new Cozinha();
		cozinhaTailandresa.setNome("Tailandesa");
		cozinhaRepository.save(cozinhaTailandresa);
		incrementarNumeroDeCozinhasAdicionada();
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Americana");
		cozinhaRepository.save(cozinha2);
		incrementarNumeroDeCozinhasAdicionada();
	}

	private void incrementarNumeroDeCozinhasAdicionada() {
		++numeroDeCozinhas;
	}
}





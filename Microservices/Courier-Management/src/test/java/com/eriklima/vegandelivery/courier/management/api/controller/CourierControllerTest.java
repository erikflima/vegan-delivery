package com.eriklima.vegandelivery.courier.management.api.controller;
import com.eriklima.vegandelivery.courier.management.domain.model.Courier;                  // Entidade Courier
import com.eriklima.vegandelivery.courier.management.domain.repository.CourierRepository;   // Repositório do Courier
import io.restassured.RestAssured;                                                          // Biblioteca para testes REST
import io.restassured.http.ContentType;                                                     // Tipos de conteúdo HTTP
import org.hamcrest.Matchers;                                                               // Matchers para validações
import org.junit.jupiter.api.BeforeEach;                                                    // Executa antes de cada teste
import org.junit.jupiter.api.Test;                                                          // Marca métodos de teste
import org.springframework.beans.factory.annotation.Autowired;                              // Injeta dependências
import org.springframework.boot.test.context.SpringBootTest;                                // Sobe contexto Spring
import org.springframework.boot.test.web.server.LocalServerPort;                            // Obtém porta aleatória
import org.springframework.http.HttpStatus;                                                 // Status HTTP
import java.util.UUID;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT ) //RANDOM_PORT cria uma porta aleatoria para o endpoint.
class CourierControllerTest {

    @LocalServerPort
    private int port; // Porta em que a aplicação iniciou.

    @Autowired
    private CourierRepository courierRepository;


    @BeforeEach
    void setup() {

        RestAssured.port = port;                   // Configura porta do RestAssured
        RestAssured.basePath = "/api/v1/couriers"; // Caminho base do endpoint
    }

    //-------------------------------------------------------

    //Teste realizando uma requisicao ao endpoint. Salvando um entregador no banco de dados.
    @Test
    public void shouldReturn201() {

        //Criando o body da requisicao
        String requestBody = """
        {
            "name": "João da Silva",
            "phone": "11999998888"
        }
        """;

        //Realizando a requisicao de fato.
        RestAssured
                .given()                                            // Início da configuração da requisição
                .body( requestBody )                                // Define corpo da requisição
                .contentType( ContentType.JSON )                    // Informa que está enviando JSON
                .accept( ContentType.JSON )                         // Espera resposta em JSON
                .when()                                             // Executa a requisição
                .post()                                             // Faz POST em /api/v1/couriers
                .then()                                             // Início das validações
                .statusCode( HttpStatus.CREATED.value() )           // Espera HTTP 201
                .body( "id", Matchers.notNullValue() )              // Valida que ID existe
                .body( "name", Matchers.equalTo("João da Silva") ); // Valida nome retornado
    }

    //-------------------------------------------------------

    //Teste realizando uma requisicao ao endpoint. Buscando um entregador pelo id.
    @Test
    void shouldReturn200() {

        // Salva courier(entregador) no banco e obtem ID gerado.
        UUID courierId = courierRepository.saveAndFlush( Courier.brandNew( "Maria Souza", "11912341234" ) ).getId();

        //Realizando a requisicao de fato.
        RestAssured
                .given()                                            // Início da requisição
                .pathParam("courierId", courierId)                  // Define parâmetro da URL
                .accept(ContentType.JSON)                           // Espera JSON
                .when()                                             // Executa requisição
                .get("/{courierId}")                                // Faz GET por ID
                .then()                                             // Início das validações
                .statusCode(HttpStatus.OK.value())                  // Espera HTTP 200
                .body("id", Matchers.equalTo(courierId.toString())) // Valida ID
                .body("name", Matchers.equalTo("Maria Souza"))      // Valida nome
                .body("phone", Matchers.equalTo("11912341234"));    // Valida telefone
    }

}
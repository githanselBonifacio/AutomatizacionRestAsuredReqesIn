package co.com.sofka.stepdefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;


public class RegisterStepsDefinition extends Reqresin{
    private static  final Logger logger = LogManager.getLogger(RegisterStepsDefinition.class);
    private RequestSpecification request;
    private Response response;


    @Given("el usuario está en la página de registro e ingresa su correo como {string} y {string} como contraseña")
    public void elUsuarioEstaEnLaPaginaDeRegistroEIngresaSuCorreoComoYComoContrasena(String email, String password) {
        try {
            generalSetUp();
            request = given().body("{\n" +
                    "    \"email\": \"" + email + "\",\n" +
                    "    \"password\": \"" + password + "\"\n" +
                    "}");
        } catch (Exception e){
            logger.warn("error al ingresar credenciales para registro\n"+e);
            Assertions.fail(e.getMessage());
        }
    }
    @When("cuando el usuario hace una petición de registrar")
    public void cuandoElUsuarioHaceUnaPeticionDeRegistrar() {
        try {
            response = request
                    .post(RESOURCE_REGISTER);
        } catch (Exception e){
            logger.warn("error al realizar petición\n"+e);
            Assertions.fail(e.getMessage());
        }
    }
    @Then("el usuario deberá ver un código id de usuario y un token de respuesta")
    public void elUsuarioDeberaVerUnCodigoIdDeUsuarioYUnTokenDeRespuesta() {
        try {
            response
                    .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body("token", notNullValue())
                    .body("id",notNullValue());

        } catch (Exception e) {
            logger.warn("error en la respuesta\n"+e);
            Assertions.fail(e.getMessage());
        }
    }

    @When("el usuario ingresa al recurso con in {string}")
    public void elUsuarioIngresaALRecursoConIn(String id) {
       try{
           generalSetUp();
           response  = given().when().get(RESOURCE_ID_2+id);

       }catch (Exception e){
           logger.warn("error al acceder al recurso\n"+e);
           Assertions.fail(e.getCause());
       }
    }
    @Then("el usuario deberá ver los datos del recurso")
    public void elUsuarioDeberaVerLosDatosDelRecurso() {
        try {
            //carpetas con caracteres especiales o espacios en el nombre provocara errores en la obtencion de la ruta
            String rootPath=System.getProperty("user.dir");
            String pathResponseExpected =  "/src/test/resources/files/resourceId2.Json" ;
            JsonPath resourceJson = new JsonPath(new File(rootPath+pathResponseExpected));
            response
                    .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body("",equalTo(resourceJson.getMap("")));

        } catch (Exception e) {
            logger.warn("error en la respuesta\n"+e);
            Assertions.fail(e.getCause());
        }
    }
}

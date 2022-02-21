package Clase5.Ejercicio4;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Ej4_Obtenerheaders {

    //Target host is Null (no se puede hacer)

    @BeforeTest
    public void setup() {
        RestAssured.baseURI = "loripsum.net/api/";
    }

    @Test
    public void getTheHeaderNumber4() {
        //Este ejercicio no tiene la URL correcta
        Response response =
                given()
                        .contentType(ContentType.TEXT)
                        .when()
                        .get("4/short/headers")
                        .then()
                        .log().body().extract().response();
    }

    @Test
    public void getTheHeaderNumber5() {
        //Este ejercicio no tiene la URL correcta
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("5/short/headers")
                        .then()
                        .log().body().extract().response();
    }

    //
}

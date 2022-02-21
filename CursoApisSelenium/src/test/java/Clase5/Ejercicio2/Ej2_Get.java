package Clase5.Ejercicio2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Ej2_Get {

    @BeforeTest
    public void setup() {

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

    }

    @Test
    public void getAllTheComments(){

        Response response =
                given()
                        .contentType(ContentType.JSON)
                .when()
                        .get("/comments")
                .then()
                        //.log().all()
                        .statusCode(200)
                        .extract().response();

    System.out.println("== extracción de response ==> ");
    System.out.println(response.getBody().asString());
    System.out.println("== Status Code ==");
    System.out.println(response.getStatusCode());
    }

}

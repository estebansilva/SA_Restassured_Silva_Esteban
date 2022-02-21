package Clase5.Ejercicio1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Ejercicio1_reqres {

    @BeforeTest
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api/";
    }

    @Test
    public void getAUser() {

        Response response =
                given()
                        .contentType(ContentType.JSON)
                .when()
                        .get("users/2")
                .then().log().body()
                        .statusCode(200)//Valido que el status code sea 200
                        .extract().response();
    }

    @Test
    public void getANonExistentUser(){

        Response response =
                given()
                        .contentType(ContentType.JSON)
                .when().get("users/23")
                .then()
                        .log().body()
                        .statusCode(404)//Valido que de error pq no existe el usuario
                        .extract().response();
    }


    String js_CreateUser = "{\n" +
            "    \"name\": \"Stevenson\",\n" +
            "    \"job\": \"Lead Tester\"\n" +
            "}";
    @Test
    public void createAUser(){

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(js_CreateUser)
                .when()
                        .post("users")
                .then()
                        .log().body()
                        .statusCode(201)
                        .extract().response();
    }

    String js_UpdateUser = "{\n" +
            "    \"name\": \"Stevenson\",\n" +
            "    \"job\": \"Lead Tester\"\n" +
            "}";

    @Test
    public void updateAUser(){

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(js_UpdateUser)
                .when()
                        .put("users/2")
                .then()
                        .log().body()
                        .statusCode(200)
                        .extract().response();


    }
}

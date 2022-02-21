package Clase5.Ejercicio2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Ej2_Post {

    @BeforeTest
    public void setup() {
        RestAssured.baseURI = "https://reqbin.com/";
    }

    @Test
    public void postAEchoPosts() {
        //Este ejercicio no tiene la URL correcta
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .post("echo/post")
                        .then()
                        .log().body().extract().response();

        JsonPath jsonPath = response.jsonPath();

        Assert.assertTrue(jsonPath.getBoolean("success"));

    }


}


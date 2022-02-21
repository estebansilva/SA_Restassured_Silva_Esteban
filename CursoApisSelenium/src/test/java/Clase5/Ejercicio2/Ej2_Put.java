package Clase5.Ejercicio2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Ej2_Put {

    @BeforeTest
    public void setup(){

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

    }

    String requestBody_Post ="{\n" +
            "    \"userId\": 1,\n" +
            "    \"id\": 4,\n" +
            "    \"title\": \"Rock and Roll\",\n" +
            "    \"body\": \"GiveMeAWhisky\"\n" +
            "}";

    @Test
    public void actualizeAPostAndShowMeTheInformation(){

        Response response = given()
                    .contentType(ContentType.JSON).body(requestBody_Post)
                .when()
                    .put("posts/4")
                .then()
                    .statusCode(200)
                    .extract().response();

        JsonPath js_posts = response.jsonPath();

        int userId = js_posts.get("userId");
        int id = js_posts.get("id");
        String title = js_posts.get("title");
        String body = js_posts.get("body");

        System.out.println(response.body().asString());

        Assert.assertEquals(title, "Rock and Roll", "Error: El titulo no es el esperado");
        Assert.assertEquals(body, "GiveMeAWhisky", "Error el cuerpo no es el esperado");
        Assert.assertEquals(response.getStatusCode(), 200, "Error: El sistema no devuelve el status code esperado");
    }

}

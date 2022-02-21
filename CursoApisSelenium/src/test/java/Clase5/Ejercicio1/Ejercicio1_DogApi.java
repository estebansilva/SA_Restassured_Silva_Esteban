package Clase5.Ejercicio1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Ejercicio1_DogApi {

    @BeforeTest
    public void setup() {
        RestAssured.baseURI = "https://dog.ceo/";
    }

    @Test
    public void getARandomDog() {

        Response response = given().contentType(ContentType.JSON)
                .when().get("api/breeds/image/random")
                .then().log().all().extract().response();

    }

    @Test
    public void getAllTheDogImages() {

        Response response = given().contentType(ContentType.JSON)
                .when().get("api/breed/hound/images")
                .then().log().all().extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);

        JsonPath jsDogImages = response.jsonPath();
        int cantImagenes = jsDogImages.getInt("message.size()");
        System.out.println("la cantidad de imagenes son " + cantImagenes);

        for (int i = 0; i < cantImagenes; i++) {
            String imageLink = jsDogImages.get("message[" + i + "]");
            //System.out.println("Link===> "+imageLink);
            Assert.assertTrue(imageLink.endsWith(".jpg"), "Error: Al menos una imagen no es jpg");
        }
    }


    @Test
    public void getAllDogsFact(){
        RestAssured.baseURI = "https://dog-facts-api.herokuapp.com/api";

        Response response = given().contentType(ContentType.JSON)
                .when().get("/v1/resources/dogs/all")
                .then().log().body().extract().response();
    }
}

package Clase6;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class SalesForceRequest {

    @BeforeTest
    public void setup(){
        RestAssured.baseURI = "https://login.salesforce.com/services/oauth2";
    }

    @Test
    public void getTokenSalesForce(){

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .queryParam("grant_type", "password")
                        .queryParam("client_id", "3MVG9riCAn8HHkYWL9mGRkhxRoTSVYWj1iavpQznnQKFsv7CWxn2Sgsc.zuR0KtTBUBP05GeKNTuCZ5PsbDtN")
                        .queryParam("client_secret", "BB5244EDE94A2B8322619856A436B8C2FF68F771AAF1516EA0B2D66A16631AF8")
                        .queryParam("username", "estebansilva_utn@hotmail.com")
                        .queryParam("password", "juxtapose.201x81Jkf9B2ujuoSLdqhimpIGd3")
                .when()
                        .post("/token")
                .then().extract().response();

        JsonPath js_response = response.jsonPath();

        System.out.println("===>" + response.getBody().asString());

        //Saving Data
        String access_token = js_response.get("access_token");
        String instance_url = js_response.get("instance_url");
        String token_type = js_response.get("token_type");

        Contact contact1 = new Contact("Julio");

        RestAssured.baseURI = instance_url;


        //Workbench URL = "https://workbench.developerforce.com/login.php"
        //Elegir RestExplorer

        String createAContact =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + access_token)
                        .body(contact1)
                .when()
                        .post("/services/data/v51.0/sobjects/Contact")
                .then()
                        .assertThat().statusCode(201)
                        .extract().asString();

        System.out.println(createAContact);

    }

}

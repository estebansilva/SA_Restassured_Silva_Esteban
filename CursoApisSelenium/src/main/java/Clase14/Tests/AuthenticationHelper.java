package Clase14.Tests;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class AuthenticationHelper {

    public static String ACCESS_TOKEN = "";
    public static String INSTANCE_URL = "";
    public static String TOKEN_TYPE = "";

    private RequestSpecification requestSpecificationForToken = new RequestSpecBuilder()
            .addParam("grant_type", "password")
            .addParam("client_id", "3MVG9riCAn8HHkYWL9mGRkhxRoTSVYWj1iavpQznnQKFsv7CWxn2Sgsc.zuR0KtTBUBP05GeKNTuCZ5PsbDtN")
            .addParam("client_secret", "BB5244EDE94A2B8322619856A436B8C2FF68F771AAF1516EA0B2D66A16631AF8")
            .addParam("username", "estebansilva_utn@hotmail.com")
            .addParam("password", "juxtapose.2014aPkoGtzlWDycmGBzolgR9rUS")
            .setBaseUri("https://login.salesforce.com")
            .setBasePath("/services/oauth2/")
            .build();

    @Given("I have a token and Instance")
    public void i_have_a_token_and_instance() {
        Response getTokenResponse =
                given()
                        //.contentType(ContentType.JSON) No funciona previo al RequestSpecification
                        .spec(requestSpecificationForToken)
                .when()
                        .post("token")
                .then()
                        .log().all()
                        .assertThat().statusCode(200)
                        .extract().response();

        JsonPath js_GetTokenresponse = getTokenResponse.jsonPath();

        ACCESS_TOKEN = js_GetTokenresponse.get("access_token");
        INSTANCE_URL = js_GetTokenresponse.get("instance_url");
        TOKEN_TYPE = js_GetTokenresponse.get("token_type");

        System.out.println(TOKEN_TYPE + "--->" + ACCESS_TOKEN + "----->" + INSTANCE_URL);
        RestAssured.baseURI = INSTANCE_URL;


    }
}

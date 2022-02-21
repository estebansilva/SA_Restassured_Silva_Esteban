package Clase11.Tests;

import Clase11.Constants.AuthorizationConstant;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import static io.restassured.RestAssured.given;

public class BaseTest {

    protected static String ACCESS_TOKEN = "";
    protected static String INSTANCE_URL = "";
    protected static String TOKEN_TYPE = "";


    @BeforeTest
    @Parameters({"org"})
    public void setup(@Optional("dev1") String org) {

        if (org.equals("dev1")) {

            System.out.println("*****SE INICIA EL ACCESO DEV1*****");
            obtainAuthorizationToken(AuthorizationConstant.DEV1_GRANT_TYPE, AuthorizationConstant.DEV1_CLIENTE_ID, AuthorizationConstant.DEV1_CLIENT_SECRET, AuthorizationConstant.DEV1_USERNAME, AuthorizationConstant.DEV1_PASSWORD, AuthorizationConstant.DEV1_SECURITY_TOKEN);

        }

        else if (org.equals("dev2")) {

            System.out.println("*****SE INICIA EL ACCESO DEV2*****");
            obtainAuthorizationToken(AuthorizationConstant.DEV2_GRANT_TYPE, AuthorizationConstant.DEV2_CLIENTE_ID, AuthorizationConstant.DEV2_CLIENT_SECRET, AuthorizationConstant.DEV2_USERNAME, AuthorizationConstant.DEV2_PASSWORD, AuthorizationConstant.DEV2_SECURITY_TOKEN);


        }

        else if (org.equals("uat")) {

            System.out.println("*****SE INICIA EL ACCESO DEV2*****");
            obtainAuthorizationToken(AuthorizationConstant.UAT_GRANT_TYPE, AuthorizationConstant.UAT_CLIENTE_ID, AuthorizationConstant.UAT_CLIENT_SECRET, AuthorizationConstant.UAT_USERNAME, AuthorizationConstant.UAT_PASSWORD, AuthorizationConstant.UAT_SECURITY_TOKEN);

        }

    }


    public void obtainAuthorizationToken(String grant_type, String client_id, String client_secret, String username, String password, String security_token) {


        RestAssured.baseURI = "https://login.salesforce.com/services/oauth2";

        Response tokenAccessRequest =
                given()
                        .contentType(ContentType.JSON)
                        .queryParam("grant_type", grant_type)
                        .queryParam("client_id", client_id)
                        .queryParam("client_secret", client_secret)
                        .queryParam("username", username)
                        .queryParam("password", password + security_token)
                .when()
                        .post("token")
                .then()
                        .assertThat().statusCode(200)
                        .extract().response();


        JsonPath js_TokenAccess = tokenAccessRequest.jsonPath();

        ACCESS_TOKEN = js_TokenAccess.get("access_token");
        INSTANCE_URL = js_TokenAccess.get("instance_url");
        TOKEN_TYPE = js_TokenAccess.get("token_type");

        System.out.println("***** SE GENERO EL TOKEN DE ACCESSO *****");

    }
}

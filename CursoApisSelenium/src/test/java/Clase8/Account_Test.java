package Clase8;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Account_Test {

    private static String ACCESS_TOKEN = "";
    private static String INSTANCE_URL = "";
    private static String TOKEN_TYPE = "";

    @DataProvider(name = "Accounts")
    public Object[][] dataProviderAccounts() {
        return new Object[][]{
                //{"Gon Freecs", "1234", "456123456", "Gon created from RestAssured"},
                //{"Leorio Paladin", "1235", "456654321", "Leorio created from RestAssured"}
                {"Killua Zoldick", "1236", "456412365", "Killua createf from RestAssured"}
        };
    }

    @BeforeMethod
    public void setup(){
        RestAssured.baseURI="https://login.salesforce.com/services/oauth2";

        Response response_TokenAccess =
                given()
                        .contentType(ContentType.JSON)
                        .queryParam("grant_type", "password")
                        .queryParam("client_id", "3MVG9riCAn8HHkYWL9mGRkhxRoTSVYWj1iavpQznnQKFsv7CWxn2Sgsc.zuR0KtTBUBP05GeKNTuCZ5PsbDtN")
                        .queryParam("client_secret", "BB5244EDE94A2B8322619856A436B8C2FF68F771AAF1516EA0B2D66A16631AF8")
                        .queryParam("username", "estebansilva_utn@hotmail.com")
                        .queryParam("password", "juxtapose.201x81Jkf9B2ujuoSLdqhimpIGd3")
                        .when()
                        .post("/token")
                        .then().log().body()
                        .assertThat().statusCode(200)
                        .extract().response();


        JsonPath js_TokenAccess = response_TokenAccess.jsonPath();

        ACCESS_TOKEN = js_TokenAccess.get("access_token");
        INSTANCE_URL = js_TokenAccess.get("instance_url");
        TOKEN_TYPE = js_TokenAccess.get("token_type");

        System.out.println("***** SE GENERO EL ACCESO MEDIANTE EL TOKEN *****");

    }

    @Test (dataProvider = "Accounts")
    public void createAccountTest(String accountName, String accountNumber, String accountPhone, String accountDescription){

        RestAssured.baseURI= INSTANCE_URL;

        Account anAccount = new Account(accountName, accountNumber, accountPhone, accountDescription);

        Response createAccount_Response =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                        .body(anAccount)
                .when()
                        .post("/services/data/v51.0/sobjects/Account")
                .then()
                        .log().body()
                        .assertThat().statusCode(201)
                        .extract().response();

        JsonPath js_Account = createAccount_Response.jsonPath();
        String id_account = js_Account.get("id");
        Boolean success_created = js_Account.get("success");

        Assert.assertTrue(id_account.contains("001"), "Error: El id de la cuenta no inicia con 001");
        Assert.assertTrue(success_created, "Error: El campo success debería ser true");

    }

    //No entendí como hacerlo con una Query como pedía el enunciado//Se ve en la Clase 9
    @Test
    public void checkAnAccountTest(){

        RestAssured.baseURI= INSTANCE_URL;

        Response getAccount_Response =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .when()
                        .get("/services/data/v51.0/sobjects/Account/0018c00002AJdp8AAD")
                .then()
                        .log().body()
                        .assertThat().statusCode(200)
                        .extract().response();

        JsonPath js_AccountResponse = getAccount_Response.jsonPath();
        String account_Id = js_AccountResponse.get("Id");
        String account_Name = js_AccountResponse.get("Name");
        String account_Number = js_AccountResponse.get("AccountNumber");

        Assert.assertEquals(account_Id, "0018c00002AJdp8AAD", "Error: El Id es distinto al esperado");
        Assert.assertEquals(account_Name, "Kurapika", "Error: Se esperaba otro nombre en la cuenta");
        Assert.assertEquals(account_Number, "6454", "Error: Se esperaba otro numero de cuenta");

    }

    @Test
    public void emptyBodyTest(){

        RestAssured.baseURI= INSTANCE_URL;

        Response newAccount_Response =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                        .body("")//Empty Body
                        .when()
                        .post("/services/data/v51.0/sobjects/Account")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400) //Bad Request
                        .extract().response();

        JsonPath js_emptyBody_response = newAccount_Response.jsonPath();

        String message = js_emptyBody_response.get("[0].message");
        String errorCode = js_emptyBody_response.get("[0].errorCode");

        Assert.assertEquals(message, "The HTTP entity body is required, but this request has no entity body.", "Error: Se esperaba otro mensaje");
        Assert.assertEquals(errorCode, "JSON_PARSER_ERROR", "Error: Se esperaba otro mensaje");


    }

    @Test
    public void invalidJsonTest(){

        RestAssured.baseURI= INSTANCE_URL;

        Response newAccount_Response =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                        .body("{name \"Name error \", accountNumber: \"68546\"}")//Al name le faltan los dos puntos en este json
                .when()
                        .post("/services/data/v51.0/sobjects/Account")
                .then()
                        .log().all()
                        .assertThat().statusCode(400) //Bad Request
                        .extract().response();

        JsonPath js_invalidJsonResponse = newAccount_Response.jsonPath();

        String message = js_invalidJsonResponse.get("[0].message");
        String errorCode = js_invalidJsonResponse.get("[0].errorCode");

        Assert.assertEquals(message, "Unexpected character ('n' (code 110)): was expecting double-quote to start field name at [line:1, column:3]", "Error: Se esperaba otro mensaje de error" );
        Assert.assertEquals(errorCode, "JSON_PARSER_ERROR", "Error: Se esperaba otro código de error" );

    }

    @Test
    public void noAccessTokenTest(){

        RestAssured.baseURI= INSTANCE_URL;

        Account anAccount = new Account("Nombre", "351315", "4651651561", "TotalNoVaACrearse");

        Response newAccount_Response =
                given()
                        .contentType(ContentType.JSON)
                        //.header("Authorization", "Bearer " + ACCESS_TOKEN)
                        .body(anAccount)
        .when()
                        .post("/services/data/v51.0/sobjects/Account")
        .then()
                        .log().all()
                        .assertThat().statusCode(401)
                        .extract().response();

        JsonPath js_noAccessTokenResponse = newAccount_Response.jsonPath();

        String message = js_noAccessTokenResponse.get("[0].message");
        String errorCode = js_noAccessTokenResponse.get("[0].errorCode");

        Assert.assertEquals(message, "Session expired or invalid", "Error: Se esperaba otro mensaje de error" );
        Assert.assertEquals(errorCode, "INVALID_SESSION_ID", "Error: Se esperaba otro código de error" );





    }

}

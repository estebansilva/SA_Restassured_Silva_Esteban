package Clase7;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Ej7_ContactManager {

    private static String ACCESS_TOKEN = "";
    private static String INSTANCE_URL = "";
    private static String TOKEN_TYPE = "";
    private static String CONTACT_ID = "";

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

        System.out.println("***** SE GENERO EL ACCESO *****");

    }

    @Test
    public void createAContactFromAObject(){
        RestAssured.baseURI= INSTANCE_URL;

        Contact aContact = createContact();
        System.out.println(aContact.FirstName +" " + aContact.LastName);

        String JsonCreado = new Gson().toJson(aContact);
        System.out.println("=====JSONCREADO============");
        System.out.println(JsonCreado);

        System.out.println(INSTANCE_URL);

        Response createContact_Response =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                        .body(aContact)
                .when()
                        .post("/services/data/v51.0/sobjects/Contact")
                .then()
                        .log().all()
                        .assertThat().statusCode(201)
                        .extract().response();

        JsonPath js_Contact = createContact_Response.jsonPath();
        String contactID = js_Contact.get("id");

        CONTACT_ID = contactID;

        System.out.println("El ID del contacto es: " + contactID);

        Assert.assertEquals(createContact_Response.getStatusCode(), 201, "Se esperaba un status code 200");
        Assert.assertTrue(js_Contact.get("success"), "Se esperaba que el valor retornado de success sea verdadero");

        System.out.println("****** SE CREO EL CONTACTO *****");

    }

    @Test
    public void getContact(){

        RestAssured.baseURI= INSTANCE_URL;

        Response getAContact_Response =
                given().contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .when()
                        .get("/services/data/v51.0/sobjects/Contact/" + "0038c00002etsolAAA")
                .then()
                        .log().all()
                        .assertThat().statusCode(200)
                        .extract().response();

        JsonPath js_Contact = getAContact_Response.jsonPath();
        String contact_LastName = js_Contact.get("LastName");

        System.out.println("***** SE GENERO LA CONSULTA *****");

        Assert.assertEquals(contact_LastName, "Ronaldinho", "El apellido no era el esperado");



    }

    @Test
    public void deleteContact(){

        RestAssured.baseURI= INSTANCE_URL;

        Response deleteContact_Response =
                given().contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .when()
                        .delete("/services/data/v51.0/sobjects/Contact/" + "0038c00002etsolAAA")
                .then()
                        .log().all()
                        .assertThat().statusCode(204)
                        .extract().response();

    }



    public Contact createContact(){

        String contact_LastName = "Tercero";
        String contact_FirstName = "Human";
        String contact_Phone = "123456783";
        String contact_Email = "Tercerohuman@person.com";
        String contact_Title = "RandomPerson";
        String contact_description = "This is my new Account of Selenium";

        Contact newContact = new Contact(contact_LastName, contact_FirstName, contact_Phone, contact_Email, contact_Title, contact_description);
        System.out.println("***** Se creo una instancia de contacto ******");
        return (newContact);
    }


}

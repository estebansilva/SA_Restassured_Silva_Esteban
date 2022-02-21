package Clase12;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

public class Lead_Tests {

    private static String ACCESS_TOKEN = "";
    private static String INSTANCE_URL = "";
    private static String LEAD_ID = "";
    private static String CONVERTED_ACCOUNT_ID = "";
    private static String CONVERTED_CONTACT_ID = "";

    //Create a conection with SalesForce
    @BeforeMethod
    public void setup_NewCredentialEJ12(){

        RestAssured.baseURI = "https://login.salesforce.com/services/oauth2/";

        Response getCredentialToEJ12 =
                given()
                        .contentType(ContentType.JSON)
                        .queryParam("grant_type", "password")
                        .queryParam("client_id", "3MVG9cHH2bfKACZYj3S3as4gJ9.9.zVTytlu8Q61HwPUN.NtPEwJqbFOp4pEy5gm6fsezrxm_WMW9YkQAcPW3")
                        .queryParam("client_secret", "696513F287215272F6EC9E2398C94873CF596E8CF92CC9F9F096ED4FE1C8A37F")
                        .queryParam("username", "seleniumcurso@gmail.com")
                        .queryParam("password", "holahola123PkC9nQP5ZkNgQahPfnQgWWHc")
                .when()
                        .post("token")
                .then()
                        .log().all()
                        .assertThat().statusCode(200)
                        .extract().response();

        JsonPath credential = getCredentialToEJ12.jsonPath();

        ACCESS_TOKEN = credential.get("access_token");
        INSTANCE_URL = credential.get("instance_url");
    }

    @Test
    public void convertALeadTest(){
        //CREATE A LEAD
        System.out.println("************* CREANDO LEAD ******************");
        RestAssured.baseURI = INSTANCE_URL;

        String lead_LastName = "Leorio";
        String lead_Status = "Open";
        String lead_Company = "Hunter x Hunter";

        Lead aLead = new Lead(lead_LastName, lead_Status, lead_Company);

        Response createALead_Response =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                        .body(aLead)
                        .when()
                        .post("/services/data/v51.0/sobjects/Lead")
                        .then()
                        .log().all()
                        .assertThat().statusCode(201)
                        .body("success",is(true))
                        .body("id", startsWith("00Q"))
                        .extract().response();

        JsonPath js_createALead = createALead_Response.jsonPath();

        Assert.assertEquals(createALead_Response.getStatusCode(), 201, "Error: El status code no es created 201");
        Assert.assertEquals(js_createALead.getBoolean("success"), true, "Error: Success no es el boolean true");

        JsonPath js_createdLeadResponse = createALead_Response.jsonPath();
        LEAD_ID = js_createdLeadResponse.get("id");

        System.out.println("************* LEAD CREADO ******************");
        System.out.println("ID LEAD CREADO:  " + LEAD_ID);


        // UPDATING A LEAD

        System.out.println("************* UPDATING LEAD ******************");
        Response updateALead_Response =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                        .body("{\"title\": \"Titulo \"}")
                .when()
                        .patch("/services/data/v51.0/sobjects/Lead/" + LEAD_ID)
                .then()
                        .log().all()
                        .assertThat().statusCode(204)
                        .extract().response();

        System.out.println( "===Status Code===" + updateALead_Response.statusCode());
        Assert.assertEquals(updateALead_Response.statusCode(), 204, "Error: El status code no es 204");

        System.out.println("************* LEAD UPDATE ******************");

        //UPDATE A LEAD WITH RATING = HOT, THIS CREATE A ACCOUNT AND CONTACT

        System.out.println("************* CONVERTING LEAD ******************");

        Response convertALeadInAClient_Response =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                        .body("{\"Rating\": \"Hot\"}")
                        .when()
                        .patch("/services/data/v51.0/sobjects/Lead/" + LEAD_ID)
                        .then()
                        .log().all()
                        .assertThat().statusCode(204)
                        .extract().response();

        Assert.assertEquals(convertALeadInAClient_Response.statusCode(), 204, "Error: El status code no es 204");

        System.out.println("************* LEAD CONVERTED ******************");

        //GET THE LEAD INFORMATION
        System.out.println("************* GETTING LEAD INFORMATION ******************");
        Response getALeadInformation_Response =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .when()
                        .get("/services/data/v51.0/sobjects/Lead/" + LEAD_ID)
                .then()
                        .log().all()
                        .assertThat().statusCode(200)
                        .extract().response();

        System.out.println("************* LEAD -> INFORMATION GETTED ******************");

        JsonPath js_LeadInformation = getALeadInformation_Response.jsonPath();

        Assert.assertEquals(js_LeadInformation.get("Id"), LEAD_ID, "ERROR: Los ID del LEAD no son IGUALES");
        Assert.assertEquals(js_LeadInformation.get("LastName"), lead_LastName, "ERROR: El Last Name es distinto");

        CONVERTED_ACCOUNT_ID = js_LeadInformation.get("ConvertedAccountId");
        CONVERTED_CONTACT_ID = js_LeadInformation.get("ConvertedContactId");

        //GET THE ACCOUNT INFORMATION
        System.out.println("************* GETTING ACCOUNT INFORMATION ******************");
        Response getAnAccountInformation_Response =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                        .when()
                        .get("/services/data/v51.0/sobjects/Account/" + CONVERTED_ACCOUNT_ID)
                        .then()
                        .log().all()
                        .assertThat().statusCode(200)
                        .extract().response();

        System.out.println("************* ACCOUNT -> INFORMATION GETTED ******************");

        JsonPath js_AccountInformation_Response = getAnAccountInformation_Response.jsonPath();

        Assert.assertEquals(js_AccountInformation_Response.get("Id"), CONVERTED_ACCOUNT_ID, "ERROR: Los ID del ACCOUNT no son IGUALES");
        Assert.assertEquals(js_AccountInformation_Response.get("Name"), lead_Company, "ERROR: El Name Account es distinto");//Usa el Company Name para crearlo

        //GET THE CONTACT INFORMATION
        System.out.println("************* GETTING CONTACT INFORMATION ******************");

        Response getAContactInformation_Response =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                        .when()
                        .get("/services/data/v51.0/sobjects/Contact/" + CONVERTED_CONTACT_ID)
                        .then()
                        .log().all()
                        .assertThat().statusCode(200)
                        .extract().response();

        System.out.println("************* CONTACT -> INFORMATION GETTED ******************");

        JsonPath js_ContactInformation = getAContactInformation_Response.jsonPath();

        Assert.assertEquals(js_ContactInformation.get("Id"), CONVERTED_CONTACT_ID, "ERROR: Los ID del CONTACT no son IGUALES");
        Assert.assertEquals(js_ContactInformation.get("LastName"), lead_LastName, "ERROR: El Last name CONTACT es distinto");//Usa el LastName de LEAD para crearlo
        Assert.assertEquals(js_ContactInformation.get("Name"), lead_LastName, "ERROR: El Name CONTACT es distinto");//Usa el LastName del LEAD para crearlo
        Assert.assertEquals(js_ContactInformation.get("AccountId"), CONVERTED_ACCOUNT_ID, "ERROR: El ACCOUNT id no coincide con el creado");//Usa el Account ID de la cuenta creada por el Lead para asociar este dato

    }
}

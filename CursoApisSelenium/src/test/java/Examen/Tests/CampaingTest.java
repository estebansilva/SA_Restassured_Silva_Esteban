package Examen.Tests;

import Examen.Clases.Campaing;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class CampaingTest {
    //Variables

    private static Response obtainATokenResponse;
    private static JsonPath js_obtainATokenResponse;
    private static Response createANewCampaing;
    private static JsonPath js_createANewCampaing;

    private static Response obtainCampaingtInformation;
    private static JsonPath js_obtainCampaingtInformation;


    private static String ACCESS_TOKEN;
    private static String INSTANCE_URL;
    private static String TOKEN_TYPE;

    private Campaing aCampaing;

    private static Boolean CAMPAING_ISACTIVE = true;
    private static String CAMPAING_NAME = "";
    private static String CAMPAING_STATUS = "Completed";
    private static String CAMPAING_ID = "";


    private RequestSpecification requestSpecificationForToken = new RequestSpecBuilder()
            .addParam("grant_type", "password")
            .addParam("client_id", "3MVG9riCAn8HHkYWL9mGRkhxRoTSVYWj1iavpQznnQKFsv7CWxn2Sgsc.zuR0KtTBUBP05GeKNTuCZ5PsbDtN")
            .addParam("client_secret", "BB5244EDE94A2B8322619856A436B8C2FF68F771AAF1516EA0B2D66A16631AF8")
            .addParam("username", "estebansilva_utn@hotmail.com")
            .addParam("password", "juxtapose.2014aPkoGtzlWDycmGBzolgR9rUS")
            .setBaseUri("https://login.salesforce.com")
            .setBasePath("/services/oauth2/")
            .build();

    @Given("I had a token and instance to connect with Salesforce")
    public void i_had_a_token_and_instance_to_connect_with_salesforce() {
        // Write code here that turns the phrase above into concrete actions

        obtainATokenResponse =
                given()
                        //.contentType(ContentType.JSON) No funciona previo al RequestSpecification
                        .spec(requestSpecificationForToken)
                        .when()
                        .post("token")
                        .then()
                        .log().all()
                        .assertThat().statusCode(200)
                        .extract().response();

        js_obtainATokenResponse = obtainATokenResponse.jsonPath();

        ACCESS_TOKEN = js_obtainATokenResponse.get("access_token");
        INSTANCE_URL = js_obtainATokenResponse.get("instance_url");
        TOKEN_TYPE = js_obtainATokenResponse.get("token_type");

        System.out.println(TOKEN_TYPE + "--->" + ACCESS_TOKEN + "----->" + INSTANCE_URL);
        RestAssured.baseURI = INSTANCE_URL;

    }
    @Given("I have a campaign  with {string}")
    public void i_have_a_campaign_with(String nameCampaing) {
        // Write code here that turns the phrase above into concrete actions
        aCampaing = new Campaing(CAMPAING_ISACTIVE, nameCampaing, CAMPAING_STATUS);
        CAMPAING_NAME = nameCampaing;

        System.out.println(CAMPAING_ISACTIVE +" "+nameCampaing+" "+" "+ CAMPAING_STATUS);
    }
    @When("I send a request to create a new campaign")
    public void i_send_a_request_to_create_a_new_campaign() {
        // Write code here that turns the phrase above into concrete actions
        createANewCampaing =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", TOKEN_TYPE + " "+ ACCESS_TOKEN)
                        .body(aCampaing)
                        .when()
                        .post("/services/data/v51.0/sobjects/Campaign")
                        .then()
                        .log().all()
                        .assertThat().statusCode(201)
                        .extract().response();

        js_createANewCampaing = createANewCampaing.jsonPath();
        CAMPAING_ID = js_createANewCampaing.get("id");

        Assert.assertTrue(js_createANewCampaing.get("success"), "Error: Success no esta devolviendo true");



    }
    @Then("A campaing has been created")
    public void a_campaing_has_been_created() {
        // Write code here that turns the phrase above into concrete actions

        obtainCampaingtInformation =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", TOKEN_TYPE + " " + ACCESS_TOKEN)
                        .when()
                        .get("/services/data/v51.0/sobjects/Campaign/" + CAMPAING_ID)
                        .then()
                        .log().all()
                        .assertThat().statusCode(200)
                        .extract().response();

        js_obtainCampaingtInformation = obtainCampaingtInformation.jsonPath();

        Assert.assertEquals(js_obtainCampaingtInformation.get("Name"), CAMPAING_NAME, "ERROR: Los nombres de la campaña son distintos");
        Assert.assertEquals(js_obtainCampaingtInformation.get("Status"), CAMPAING_STATUS, "Error: Los status son distintos");
        Assert.assertTrue(js_obtainCampaingtInformation.getBoolean("IsActive"), "Error: La campaña no esta activa");

    }
}

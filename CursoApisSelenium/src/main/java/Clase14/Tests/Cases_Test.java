package Clase14.Tests;

import Clase14.Classes.Case;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import static Clase14.Tests.AuthenticationHelper.ACCESS_TOKEN;
import static Clase14.Tests.AuthenticationHelper.TOKEN_TYPE;
import static io.restassured.RestAssured.given;

public class Cases_Test {

    private Case aCase;
    private static Response createANewCaseResponse;
    private static Response getAcreatedCaseResponse;
    private static JsonPath js_createANewCaseResponse;
    private static JsonPath js_getACreatedCaseResponse;
    private static String case_id;

    @Given("I have a case")
    public void i_have_a_case() {

        aCase = new Case("New", "Electronic", "Phone", "Electronic Company Information", "Electronic Company Description", "Electronic Company Comment");

    }
    @When("I send a request to create a new case")
    public void i_send_a_request_to_create_a_new_case() {

        createANewCaseResponse =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", TOKEN_TYPE + " "+ ACCESS_TOKEN)
                        .body(aCase)
                .when()
                        .post("/services/data/v51.0/sobjects/Case")
                .then()
                        .assertThat().statusCode(201)
                        .log().all()
                        .extract().response();


        js_createANewCaseResponse = createANewCaseResponse.jsonPath();
        case_id = js_createANewCaseResponse.get("id");
        Assert.assertTrue(js_createANewCaseResponse.getBoolean("success"), "ERROR: The success vale is not True");
        Assert.assertTrue(case_id.startsWith("500"));

    }
    @Then("A case has been created")
    public void a_case_has_been_created() {

        getAcreatedCaseResponse =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", TOKEN_TYPE + " "+ ACCESS_TOKEN)
                        .body(aCase)
                .when()
                        .get("/services/data/v51.0/sobjects/Case/" + case_id)
                .then()
                        .assertThat().statusCode(200)
                        .log().all()
                        .extract().response();

        js_getACreatedCaseResponse = getAcreatedCaseResponse.jsonPath();

        String obtainedTypeCase = js_getACreatedCaseResponse.get("Type");
        String obtainedStatus = js_getACreatedCaseResponse.get("Status");
        String obtainedOrigin = js_getACreatedCaseResponse.get("Origin");

        Assert.assertEquals(obtainedTypeCase, aCase.getType(), "Error: El TYPE del case no coincide");
        Assert.assertEquals(obtainedStatus, "New", "ERROR: el case no se generó con el estado NEW");
        Assert.assertEquals(obtainedOrigin, aCase.getOrigin(), "Error: El ORIGIN del case es distinto al generado");
    }
}

package Clase14.Tests;

import Clase14.Classes.Lead;
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

public class Lead_Test {

    private Lead aLead;
    private Response createANewLeadResponse;
    private Response getALeadResponse;
    private JsonPath js_createANewLead;
    private JsonPath js_getALeadResponse;
    private String lead_Id;

    @Given("I have a lead")
    public void i_have_a_lead() {

        aLead = new Lead ("Rei", "Evangelion Company", "Driver Specialist");

    }
    @When("I send a request to create a lead")
    public void i_send_a_request_to_create_a_lead() {
        // Write code here that turns the phrase above into concrete actions

        createANewLeadResponse =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", TOKEN_TYPE + " "+ ACCESS_TOKEN)
                        .body(aLead)
                .when()
                        .post("/services/data/v51.0/sobjects/Lead")
                .then()
                        .log().all()
                        //.assertThat().statusCode(201)
                        .extract().response();

        js_createANewLead = createANewLeadResponse.jsonPath();
        lead_Id = js_createANewLead.get("id");


        Assert.assertTrue(js_createANewLead.getBoolean("success"),"Error: Success value not is true");
        Assert.assertTrue(lead_Id.startsWith("00Q"));

    }

    @Then("A lead has been created")
    public void a_lead_has_been_created() {

        getALeadResponse =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization",TOKEN_TYPE + " "+ ACCESS_TOKEN)
                .when()
                        .get("/services/data/v51.0/sobjects/Lead/" + lead_Id)
                .then()
                        .log().all()
                        .assertThat().statusCode(200)
                        .extract().response();

        js_getALeadResponse = getALeadResponse.jsonPath();
        String obtainedLastName = js_getALeadResponse.get("LastName");
        String obtainedCompany = js_getALeadResponse.get("Company");
        String obtainedStatus = js_getALeadResponse.get("Status");
        String obtainedCourse = js_getALeadResponse.get("Course__c");

        Assert.assertEquals(obtainedLastName, aLead.getLastName());
        Assert.assertEquals(obtainedCompany, aLead.getCompany());
        Assert.assertEquals(obtainedCourse, aLead.getCourse__c());
        Assert.assertEquals(obtainedStatus, "Open - Not Contacted");

    }

    @When("I send a post without body")
    public void i_send_a_post_without_body() {

        createANewLeadResponse =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", TOKEN_TYPE + " "+ ACCESS_TOKEN)
                .when()
                        .post("/services/data/v51.0/sobjects/Lead")
                .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();

        js_createANewLead = createANewLeadResponse.jsonPath();


    }

    @Then("System show me about the request need a body")
    public void system_show_me_about_the_request_need_a_body() {

        String obtainedMessage =  js_createANewLead.get("[0].message");
        String obtainErrorCode = js_createANewLead.get("[0].errorCode");
        Assert.assertEquals(obtainedMessage,"The HTTP entity body is required, but this request has no entity body.", "ERROR: se esperaba otro msg de validación");
        Assert.assertEquals(obtainErrorCode, "JSON_PARSER_ERROR", "ERROR: Se esperaba otro error code");
    }

    @Given("I have a lead without course")
    public void i_have_a_lead_without_course() {

        aLead = new Lead ("Rei", "Evangelion Company");

    }

    @When("I send a request with a lead without course")
    public void i_send_a_request_with_a_lead_without_course(){
    createANewLeadResponse =
    given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", TOKEN_TYPE + " "+ ACCESS_TOKEN)
                        .body(aLead)
                .when()
                        .post("/services/data/v51.0/sobjects/Lead")
                .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .extract().response();

    js_createANewLead = createANewLeadResponse.jsonPath();

    }

    @Then("System validate is necessary a course")
    public void system_validate_is_necessary_a_course() {

        String obtainedMessage =  js_createANewLead.get("[0].message");
        String obtainErrorCode = js_createANewLead.get("[0].errorCode");
        Assert.assertEquals(obtainedMessage,"Required fields are missing: [Course__c]", "ERROR: se esperaba un mensaje indicando que falta cargar un course");
        Assert.assertEquals(obtainErrorCode, "REQUIRED_FIELD_MISSING", "ERROR: Se esperaba otro error code");

    }


}

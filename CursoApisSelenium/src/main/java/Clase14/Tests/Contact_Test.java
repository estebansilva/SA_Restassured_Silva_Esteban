package Clase14.Tests;

import Clase14.Classes.Contact;
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

public class Contact_Test {

    //variables
    private Contact aContact;
    private static JsonPath js_CreateANewContact;
    private static JsonPath js_getAContactInformation;
    private static String contact_id;

    //Tests
    @Given("I have a contact")
    public void i_have_a_contact() {

        aContact = new Contact("Yagi", "Toshinori","Hero - BNH", "11111116", "Description about Yagi Toshinori");


    }
    @When("I send a request to create a contact")
    public void i_send_a_request_to_create_a_contact() {

        Response createANewContactResponse =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", TOKEN_TYPE + " "+ ACCESS_TOKEN)
                        .body(aContact)
                .when()
                        .post("/services/data/v51.0/sobjects/Contact")
                .then()
                        .log().all()
                        .assertThat().statusCode(201)
                        .extract().response();

        js_CreateANewContact = createANewContactResponse.jsonPath();
        contact_id = js_CreateANewContact.get("id");

        Assert.assertTrue(js_CreateANewContact.getBoolean("success"),"Error: Success value not is true");
        Assert.assertTrue(contact_id.startsWith("003"));
        System.out.println("Se creo la cuenta");

    }
    @Then("The contact has been created")
    public void the_contact_has_been_created() {

    Response getAContactInformation =
            given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", TOKEN_TYPE + " " + ACCESS_TOKEN)
            .when()
                    .get("/services/data/v51.0/sobjects/Contact/" + contact_id)
            .then()
                    .log().all()
                    .assertThat().statusCode(200)
                    .extract().response();

    js_getAContactInformation = getAContactInformation.jsonPath();
    String obtainedName = js_getAContactInformation.get("FirstName");
    String obtainedLastName = js_getAContactInformation.get("LastName");
    String obtainedDescription = js_getAContactInformation.get("Description");
    String obtainedTitle = js_getAContactInformation.get("Title");

    Assert.assertEquals(obtainedName, aContact.getFirstName(), "ERROR: No era el Name que se esperaba");
    Assert.assertEquals(obtainedLastName, aContact.getLastName(), "ERROR: No era el LastName que se esperaba");
    Assert.assertEquals(obtainedDescription, aContact.getDescription(), "ERROR: No coinciden las Descriptions");
    Assert.assertEquals(obtainedTitle, aContact.getTitle(), "ERROR: No coinciden los Titles");


    System.out.println("**** Se encontro el contacto ****");

    }
}

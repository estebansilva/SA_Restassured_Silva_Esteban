package Clase11.Tests;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import Clase11.Classes.Lead;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

public class Lead_Test extends BaseTest{

    @Test
    public void createALeadTest(){

        RestAssured.baseURI = INSTANCE_URL;

        Lead aLead = new Lead("Carlos", "Open", "No le hace compañia", "no estudia", "el_vago@mail.com");

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


    }

    @Test
    public void updateLeadTest(){

        RestAssured.baseURI = INSTANCE_URL;
        String leadId = "00Q8c00000xM4FfEAK";

        Response updateALead_Response =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                        .body("{\"Rating\": \"Hot\"}")
                        .log().all()
                        .when()
                        .patch("/services/data/v51.0/sobjects/Lead/" + leadId)
                        .then()
                        .log().all()
                        .assertThat().statusCode(204)
                        .extract().response();

        System.out.println("===Status Code===" + updateALead_Response.statusCode());
        Assert.assertEquals(updateALead_Response.statusCode(), 204, "Error: El status code no es 204");


    }

    @Test
    public void invalidFieldInRequest_ToCreateALeadTest(){

        RestAssured.baseURI = INSTANCE_URL;

        Response invalidFieldInARequest =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                        .body("{\"LastName\": \"InvalidRequest\", \"Status\": \"Open\", \"Descripcion\": \"This is Wrong\"}")
                        .when()
                        .post("/services/data/v51.0/sobjects/Lead/")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .body("[0].message",is("No such column 'Descripcion' on sobject of type Lead")) //Validación en request
                        .body("[0].errorCode",is("INVALID_FIELD")) //Validación en request
                        .extract().response();
    }

    @Test
    public void courseFieldIsRequiredOnLeadTest(){

        RestAssured.baseURI = INSTANCE_URL;
        Lead aLead = new Lead("Cowen", "Open", "Academia");

        Response RequiredField_Request =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                        .body(aLead)
                        .when()
                        .post("/services/data/v51.0/sobjects/Lead/")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .body("[0].message",is("Required fields are missing: [Course__c]")) //Validación en request
                        .body("[0].errorCode",is("REQUIRED_FIELD_MISSING")) //Validación en request
                        .extract().response();

    }

    @Test
    public void duplicateErrorOnLeadTest(){

        RestAssured.baseURI = INSTANCE_URL;

        Lead aLead = new Lead("ALberto quintuplicado", "Open", "Clone Company", "Un Curso", "mail3@mailclone.com" );

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

        System.out.println("***** SE CREO EL LEAD *****");

        Response createTheSameLead_Response =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                        .body(aLead)
                        .when()
                        .post("/services/data/v51.0/sobjects/Lead")
                        .then()
                        .log().all()
                        .assertThat().statusCode(400)
                        .body("[0].duplicateResut.duplicateRule",is("Standard_Lead_Duplicate_Rule"))
                        .body("[0].duplicateResut.duplicateRuleEntityType",is("Lead"))
                        .extract().response();
    }

}

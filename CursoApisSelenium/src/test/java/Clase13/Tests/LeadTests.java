package Clase13.Tests;

import Clase13.Clases.Lead;
import Clase13.Utilities.LeadDataLoad;
import com.opencsv.exceptions.CsvValidationException;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

public class LeadTests {

    private static String ACCESS_TOKEN = "";
    private static String INSTANCE_URL = "";
    private static List<Lead> leadList;
    private String LEAD_ID;

    //Using RequestSpecification
    private RequestSpecification requestSpecificationForUAT = new RequestSpecBuilder()
            .addParam("grant_type", "password")
            .addParam("client_id", "3MVG9cHH2bfKACZYj3S3as4gJ9.9.zVTytlu8Q61HwPUN.NtPEwJqbFOp4pEy5gm6fsezrxm_WMW9YkQAcPW3")
            .addParam("client_secret", "696513F287215272F6EC9E2398C94873CF596E8CF92CC9F9F096ED4FE1C8A37F")
            .addParam("username", "seleniumcurso@gmail.com")
            .addParam("password", "holahola123PkC9nQP5ZkNgQahPfnQgWWHc")
            .setBaseUri("https://login.salesforce.com")
            .setBasePath("/services/oauth2/")
            .build();



    @BeforeClass
    public void getLeadInfoFromCsv() throws CsvValidationException, IOException {
      leadList = LeadDataLoad.getListLeadFromCSV();
    }

    @BeforeTest
    public void getToken (){


        //RestAssured.baseURI = "https://login.salesforce.com/services/oauth2/";


        Response getTokenResponse =
                given()
                        //.contentType(ContentType.JSON)
                        .spec(requestSpecificationForUAT)
                        /*.queryParam("grant_type", "password")
                        .queryParam("client_id", "3MVG9cHH2bfKACZYj3S3as4gJ9.9.zVTytlu8Q61HwPUN.NtPEwJqbFOp4pEy5gm6fsezrxm_WMW9YkQAcPW3")
                        .queryParam("client_secret", "696513F287215272F6EC9E2398C94873CF596E8CF92CC9F9F096ED4FE1C8A37F")
                        .queryParam("username", "seleniumcurso@gmail.com")
                        .queryParam("password", "holahola123PkC9nQP5ZkNgQahPfnQgWWHc")*/
                .when()
                        .post("token")
                .then()
                        .log().all()
                        .assertThat().statusCode(200)
                        .extract().response();

        JsonPath credential = getTokenResponse.jsonPath();

        ACCESS_TOKEN = credential.get("access_token");
        INSTANCE_URL = credential.get("instance_url");
    }


    @Test
    public void createLeadsFromCSV (){

        System.out.println("************* CREANDO LEAD ******************");
        RestAssured.baseURI = INSTANCE_URL;

        for (Lead aLead : leadList ) {
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
                            .body("success", is(true))
                            .body("id", startsWith("00Q"))
                            .extract().response();

            JsonPath js_createALead = createALead_Response.jsonPath();

            Assert.assertEquals(createALead_Response.getStatusCode(), 201, "Error: El status code no es created 201");
            Assert.assertEquals(js_createALead.getBoolean("success"), true, "Error: Success no es el boolean true");

            JsonPath js_createdLeadResponse = createALead_Response.jsonPath();
            LEAD_ID = js_createdLeadResponse.get("id");

            System.out.println("************* LEAD CREADO ******************");
            System.out.println("ID LEAD CREADO:  " + LEAD_ID);

        }
    }


}

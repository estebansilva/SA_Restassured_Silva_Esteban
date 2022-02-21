package Clase9;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class WebToLead {

    //Lo que se mostro en la clase, Acá aparece lo de Querys en la request

    private static String ACCESS_TOKEN = "";
    private static String INSTANCE_URL = "";

    @DataProvider(name = "accounts")
    public Object[][] dataProviderAccounts() {
        return new Object[][]{
                {"Juan", "Esta es la cuenta de Juan"},
                {"Maria", "Esta es la cuenta de Maria"}
        };
    }


    @BeforeTest
    public void obtainAuthorizationFromSalesforce() {
//obtener la autorizacion para trabajar en la instancia de Salesforce...

        RestAssured.baseURI = "https://login.salesforce.com/services/oauth2/";

        String respuesta = given()
//.header("Content-type", "application/json")
                .queryParam("grant_type", "password")
                .queryParam("client_id", "3MVG9p1Q1BCe9GmCSj33tBJjfBszMSaJDvJDfwwj2VeMpQy4rRnWS_IXrAPj41qd.0V3e50FHZMBySnXCLnzC")
                .queryParam("client_secret", "E63928F2EF75E18F2562AE5CEA50F56897C29092C6D6E3AF9718E16218FE4FC8")
                .queryParam("username", "seleniumcurso+123@gmail.com")
                .queryParam("password", "holamundo123FxZ0KNxCgPgIQ0TDPYW7HmkmF")
                .when()
                .post("token")
                .then().assertThat().statusCode(200)
                .extract().asString();

        System.out.println(respuesta);

        JsonPath js = new JsonPath(respuesta);

        ACCESS_TOKEN = js.get("access_token");
        INSTANCE_URL = js.get("instance_url");

        RestAssured.baseURI = INSTANCE_URL;
        System.out.println("Access token: --> " + ACCESS_TOKEN);
        System.out.println("Instance Url: --> " + INSTANCE_URL);
    }


    @Test
    public void webToLeadTest() throws InterruptedException {

    /*  System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.seleniumacademy.net/registro-web");

        driver.manage().window().fullscreen();

        System.out.println("-- " + driver.getTitle());
        //Assert.assertEquals(driver.getTitle());
        Assert.assertEquals(driver.getTitle(), "Web to Lead - Selenium Academy", "Error: se esperaba otro titulo");
        driver.findElement(By.id("first_name")).sendKeys("Jonathan");
        driver.findElement(By.id("last_name")).sendKeys("Rodriguez");
        driver.findElement(By.id("email")).sendKeys("testing@testqa.com");
        driver.findElement(By.id("company")).sendKeys("Selenium Academy");
        driver.findElement(By.id("city")).sendKeys("Madrid");
        driver.findElement(By.id("state")).sendKeys("FL");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@type='submit']")).click();
        Assert.assertEquals(driver.getTitle(), "Home - Selenium Academy", "Error: se esperaba otro titulo");
*/
        String query = "select+id+,+lastname+,+status+from+Lead+where+lastname=+'Rodriguez'";

        String salesforceId = given()
                .contentType(JSON)
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .urlEncodingEnabled(false)
                .queryParam("q", query)
                .when()
                .get("/services/data/v51.0/query/")
                .then().log().all()
                .assertThat().statusCode(200).extract().path("records[0].Id");

        System.out.println("---> " + salesforceId);

        String newLeadInfo = given()
                .contentType(JSON)
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .urlEncodingEnabled(false)
                .queryParam("q", query)
                .when()
                .get("/services/data/v51.0/query/")
                .then().log().all()
                .assertThat().statusCode(200).extract().asString();

        JsonPath js = new JsonPath(newLeadInfo);

        int totalSize = js.get("totalSize");
        Assert.assertEquals(totalSize, 1, " Error: se esperaba un solo elemento");

        String leadId = js.get("records[0].Id");
        Assert.assertTrue(leadId.startsWith("00Q"), "Error: el objeto obtenido no es un lead!!");

        String lastName = js.get("records[0].LastName");
        Assert.assertEquals(lastName, "Rodriguez", "Error: se esperaba otro apellido");

        String status = js.get("records[0].Status");
        System.out.println(status);


    }


}



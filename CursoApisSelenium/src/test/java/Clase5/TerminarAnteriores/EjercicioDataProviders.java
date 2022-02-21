package Clase5.TerminarAnteriores;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class EjercicioDataProviders {

    @DataProvider(name="ciudades")
    public Object[][] datosDePersonas(){
        return new Object[][] {
                {"Montevideo"},
                {"Cordoba"}
        };
    }
    @Test(dataProvider = "ciudades")
    public void DataProviderJsonResponse(String unaCiudad)
    {
        String endpoint = "http://demoqa.com/utilities/weather/city/" + unaCiudad;
        Response response = given().header("Content-Type", "application/json")
                .when().get(endpoint)
                .then().contentType(ContentType.JSON)
                .log().all()
                .extract().response();
        JsonPath jsonPathEvaluator = response.jsonPath();
        String city = jsonPathEvaluator.get("City");
        Assert.assertEquals(city, unaCiudad, "Se esperaba otra ciudad");
    }
}

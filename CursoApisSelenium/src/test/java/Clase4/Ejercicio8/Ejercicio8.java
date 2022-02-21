package Clase4.Ejercicio8;

import io.restassured.path.json.JsonPath;

public class Ejercicio8 {

    public static String getAValueParameterInAResponse (String response, String parameter){
        JsonPath jsonPath = new JsonPath(response);
        String valueParameter = jsonPath.get(parameter);

        return valueParameter;
    }
}

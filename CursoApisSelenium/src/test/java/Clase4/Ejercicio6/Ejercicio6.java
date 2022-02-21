package Clase4.Ejercicio6;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class Ejercicio6 {

    @Test
    public void useJsonToCreateAListObject() {

        String jsonPerson = "{\n" +
                "  \"firstName\": \"Duke\",\n" +
                "  \"lastName\": \"Java\",\n" +
                "  \"age\": 18,\n" +
                "  \"streetAddress\": \"100 Internet Dr\",\n" +
                "  \"city\": \"JavaTown\",\n" +
                "  \"state\": \"JA\",\n" +
                "  \"postalCode\": \"12345\",\n" +
                "  \"phoneNumbers\": [\n" +
                "    { \"Mobile\": \"111-111-1111\" },\n" +
                "    { \"Home\": \"222-222-2222\" }\n" +
                "  ]\n" +
                "}";

        JsonPath jsPerson = new JsonPath(jsonPerson);

        String jsFirstName = jsPerson.get("firstName");
        String jsLastName = jsPerson.get("lastName");
        int jsAge = jsPerson.get("age");;
        String jsStreetAddress = jsPerson.get("streetAddress");;
        String jsCity = jsPerson.get("city");;
        String jsState = jsPerson.get("state");;
        String jsPostalCode = jsPerson.get("postalCode");;
        String jsMobilePhoneNumber = jsPerson.get("phoneNumbers[0].Mobile");
        String jsHomePhoneNumber = jsPerson.get("phoneNumbers[1].Home");

        Person createdPerson = new Person(jsFirstName, jsLastName, jsAge, jsStreetAddress, jsCity, jsState, jsPostalCode, jsMobilePhoneNumber, jsHomePhoneNumber);

        System.out.println(createdPerson.firstName);
    }

}

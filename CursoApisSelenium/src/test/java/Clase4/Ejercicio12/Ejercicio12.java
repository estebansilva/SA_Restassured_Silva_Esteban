package Clase4.Ejercicio12;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Ejercicio12 {

    @Test
    public void Ej12_Cursos() {

        JsonPath js_Cursos = new JsonPath(readJsonCursos());

        int cantCursos = js_Cursos.getInt("dashboard.courses.size()");
        System.out.println(cantCursos);

        int finalPrice = 0;
        for (int i = 0; i < cantCursos; i++) {

            String course_title = js_Cursos.get("dashboard.courses[" + i + "].title");
            int course_price = js_Cursos.get("dashboard.courses[" + i + "].price");
            int course_copies = js_Cursos.get("dashboard.courses[" + i + "].copies");
            int totalForBook = course_price*course_copies;

            finalPrice += totalForBook;

            System.out.println("curso: " +course_title+ " precio " +course_price+ " cantidad de copias " + course_copies);

            Assert.assertTrue(totalForBook == (course_price*course_copies), "los montos en la multiplicación de copias por precio son incorrectos");
        }

        Assert.assertTrue(finalPrice == 910, "la suma total no son igual");
        System.out.println("El valor total de los cursos es de " + finalPrice);


    }

    public String readJsonCursos() {
        String json_Cursos = "{\n" +
                "  \"dashboard\": {\n" +
                "    \"purchaseAmount\": 910,\n" +
                "    \"website\": \"rahulshettyacademy.com\",\n" +
                "    \"courses\": [\n" +
                "      {\n" +
                "        \"title\": \"Selenium Python\",\n" +
                "        \"price\": 50,\n" +
                "        \"copies\": 6\n" +
                "      },\n" +
                "      {\n" +
                "        \"title\": \"Cypress\",\n" +
                "        \"price\": 40,\n" +
                "        \"copies\": 4\n" +
                "      },\n" +
                "      {\n" +
                "        \"title\": \"RPA\",\n" +
                "        \"price\": 45,\n" +
                "        \"copies\": 10\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        return (json_Cursos);
    }
}

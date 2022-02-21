package Clase4.Ejercicio10;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class Ej10_Cats {

    @Test
    public void showMeTheCats() {

        String catJson = getStringJsonCats();
        JsonPath jsCat = new JsonPath(catJson);

        System.out.println("***Cat Information***");
        System.out.println("type: " + jsCat.get("type"));
        System.out.println("status: " + jsCat.get("status.verified"));
        System.out.println("last update: " + jsCat.get("updatedAt"));


    }

    public String getStringJsonCats() {

        String catJson = "{\n" +
                "  \"status\": {\n" +
                "    \"verified\": true,\n" +
                "    \"sentCount\": 1,\n" +
                "    \"feedback\": \"\"\n" +
                "  },\n" +
                "  \"type\": \"cat\",\n" +
                "  \"deleted\": false,\n" +
                "  \"_id\": \"5887e1d85c873e0011036889\",\n" +
                "  \"user\": \"5a9ac18c7478810ea6c06381\",\n" +
                "  \"text\": \"Cats make about 100 different sounds. Dogs make only about 10.\",\n" +
                "  \"__v\": 0,\n" +
                "  \"source\": \"user\",\n" +
                "  \"updatedAt\": \"2020-09-03T16:39:39.578Z\",\n" +
                "  \"createdAt\": \"2018-01-15T21:20:00.003Z\",\n" +
                "  \"used\": true\n" +
                "}";

        return catJson;
    }
}

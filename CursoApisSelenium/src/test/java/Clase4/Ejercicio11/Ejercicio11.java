package Clase4.Ejercicio11;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.HashMap;

public class Ejercicio11 {

    @Test
    public void readingJson() {

        JsonPath js_info = new JsonPath(getStringJson());

        String accessToken = js_info.get("access_token");
        String instanceUrl = js_info.get("instance_url");
        String id = js_info.get("id");
        String signature = js_info.get("signature");

        System.out.println("====> accessToken:" + accessToken);
        System.out.println("====> instanceUrl:" + instanceUrl);
        System.out.println("====> id:" + id);
        System.out.println("====> signature:" + signature);


        HashMap<String, String> infoMap = new HashMap<>();

        infoMap.put("access_token", accessToken);
        infoMap.put("instance_url", instanceUrl);
        infoMap.put("id",id);
        infoMap.put("signature", signature);

        System.out.println("====> accessToken:" + infoMap.get("access_token"));
        System.out.println("====> instanceUrl:" + infoMap.get("instance_url"));
        System.out.println("====> id:" + infoMap.get("id"));
        System.out.println("====> signature:" + infoMap.get("signature"));
    }

    public String getStringJson() {

        String js_string_info = "{\n" +
                "    \"access_token\": \"00D1N000001Rznq!AQYAQGGqJYk6Jqt3g8oVNzy2m6QKXGN_utdsdli5xUiwW9O1LROsoQEDmTGI8PO4V17pPC2tJCjlW0b5txdsbOsSL8CG8rW5\",\n" +
                "    \"instance_url\": \"https://emilianognocchi-dev-ed.my.salesforce.com\",\n" +
                "    \"id\": \"https://login.salesforce.com/id/00D1N000001RznqUAC/0051N000005TNbMQAW\",\n" +
                "    \"token_type\": \"Bearer\",\n" +
                "    \"issued_at\": \"1624384798213\",\n" +
                "    \"signature\": \"BSowPjEKEdR55Z8EX/7ym3TDSu/g8EC8SekYP7xBwYs=\"\n" +
                "}\n";

        return (js_string_info);
    }
}

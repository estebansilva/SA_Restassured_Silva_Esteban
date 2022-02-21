package Clase4.Ejercicio5;

import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateAndReadCrediCardJson {

    @Test
    public void createAndReadJsonCreditCard() {
        CreditCard creditCards1 = new CreditCard("Mastercard", 123456789, 901234789);
        CreditCard creditCards2 = new CreditCard("Visa", 987654321, 909876321);
        CreditCard creditCards3 = new CreditCard("Orumaito", 159753137, 901553137);

        List<CreditCard> listCCard = new ArrayList<>();
        listCCard.add(creditCards1);
        listCCard.add(creditCards2);
        listCCard.add(creditCards3);

        String jsonCCard = new Gson().toJson(listCCard);
        jsonCCard = "{\"creditcards\":"+jsonCCard+"}";
        System.out.println("===>" + jsonCCard);

        JsonPath jsCCards = new JsonPath(jsonCCard);
        int cantCreditCards = jsCCards.getInt("creditcards.size()");
        System.out.println("cantidad de tarjetas "+cantCreditCards);

        for (int i = 0; i<cantCreditCards; i++){
            String typeCCard = jsCCards.get("creditcards["+i+"].type");
            int numberCCard = jsCCards.get("creditcards["+i+"].number");
            int cvuCCard = jsCCards.get("creditcards["+i+"].cvu");

            System.out.println(typeCCard+" - "+numberCCard+ " - " + cvuCCard);
        }

    }
}

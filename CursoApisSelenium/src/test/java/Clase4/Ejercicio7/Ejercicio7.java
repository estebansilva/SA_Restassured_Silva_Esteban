package Clase4.Ejercicio7;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class Ejercicio7 {

    @Test
    public void Ej7_readBookAndPrices(){

        //Dado que no esta subido el json se usará el que es creado en el Ejercicio 4


        JsonPath jsBooks = new JsonPath(readJson());
        int cantBooks = jsBooks.getInt("libros.size()");
        //System.out.println("===> cantidad de libros " + cantBooks);

        System.out.println("**** ALL THE BOOKS ***");

        int totalPriceBook = 0;
        for (int i = 0; i < cantBooks; i++){

            String bookTitle = jsBooks.get("libros["+i+"].titulo");
            int bookPrice = jsBooks.get("libros["+i+"].precio");
            System.out.println("Libro: " + bookTitle + " - Precio: " + bookPrice);
            totalPriceBook += bookPrice;

        }

        System.out.println("Final Price: " + totalPriceBook);
    }


    public String readJson(){

        String jsonBook = "{\n" +
                "  \"libros\": [\n" +
                "    {\n" +
                "      \"categoria\": \"Ciencia ficción\",\n" +
                "      \"autor\": \"Dan Brown\",\n" +
                "      \"titulo\": \"Inferno\",\n" +
                "      \"precio\": 3500\n" +
                "    },\n" +
                "    {\n" +
                "      \"categoria\": \"Finanzas\",\n" +
                "      \"autor\": \"Robert Kiyosaki\",\n" +
                "      \"titulo\": \"Padre Rico, Padre Pobre\",\n" +
                "      \"precio\": 2100\n" +
                "    },\n" +
                "    {\n" +
                "      \"categoria\": \"Testing\",\n" +
                "      \"autor\": \"Lisa Crispin\",\n" +
                "      \"titulo\": \"Agile Testing\",\n" +
                "      \"precio\": 7500\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        return jsonBook;

        }
}

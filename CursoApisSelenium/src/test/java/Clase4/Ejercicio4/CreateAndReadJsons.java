package Clase4.Ejercicio4;

import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateAndReadJsons {

    @Test
    public void createAndReadJsonEJ4() {
        Libro libro1 = new Libro("Ciencia ficción", "Dan Brown", "Inferno", 3500);
        Libro libro2 = new Libro("Finanzas", "Robert Kiyosaki", "Padre Rico, Padre Pobre", 2100);
        Libro libro3 = new Libro("Testing", "Lisa Crispin", "Agile Testing", 7500);

        List<Libro> listaLibros = new ArrayList<>();
        listaLibros.add(libro1);
        listaLibros.add(libro2);
        listaLibros.add(libro3);

        String jsonLibros = new Gson().toJson(listaLibros);
        jsonLibros = "{\"libros\":" + jsonLibros + "}";
        System.out.println(jsonLibros);

        JsonPath jsLibros = new JsonPath(jsonLibros);

        System.out.println("La cantidad de libros es igual " + jsLibros.getInt("libros.size()"));
        int cantLibros = jsLibros.getInt("libros.size()");

        int SumValorLibro = 0;

        for (int i = 0; i < cantLibros; i++) {
            String nombreLibro = jsLibros.get("libros[" + i + "].titulo");
            int valorLibro = jsLibros.get("libros[" + i + "].precio");
            SumValorLibro = SumValorLibro + valorLibro;
            System.out.println("El libro "+ nombreLibro+ " tiene un precio de " +valorLibro);

        }

        System.out.println("La suma total de todos los libros es de " + SumValorLibro);

    }

}

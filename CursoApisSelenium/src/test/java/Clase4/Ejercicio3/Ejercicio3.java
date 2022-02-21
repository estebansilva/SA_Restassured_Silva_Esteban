package Clase4.Ejercicio3;

import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class Ejercicio3 {

    @Test
    public void crearJsonEstudiante() {

        Estudiante estudiante1 = new Estudiante("Juan", "Lopez", 123123, "Informatica");
        Estudiante estudiante2 = new Estudiante("Maria", "Rodriguez", 22222, "Administración");
        Estudiante estudiante3 = new Estudiante("Juan", "Cowen", 123456, "Finanzas");

        List<Estudiante> listaEstudiantes = new ArrayList<>();
        listaEstudiantes.add(estudiante1);
        listaEstudiantes.add(estudiante2);
        listaEstudiantes.add(estudiante3);

        //Gson ---> Convertir un objeto u objetos a Json
        String jsonEstudiante = new Gson().toJson(listaEstudiantes);
        System.out.println(jsonEstudiante);
        // "Libros": [{"autor": J. rodriguez", ....}]
        //Convertimos la lista de estudiantes en un Json correcto
        jsonEstudiante = "{\"Estudiantes\":" + jsonEstudiante + "}";
        System.out.println(jsonEstudiante);


        //JsonPath ---> convertir un JSon a un formato que podamos extraer los datos

        JsonPath jsEstudiantes = new JsonPath(jsonEstudiante);
        int cantEstudiante = jsEstudiantes.getInt("Estudiantes.size()");
        System.out.println("la cantidad de estudiantes son " + cantEstudiante);


        for (int i = 0; i < cantEstudiante; i++) {
            String nombre_est = jsEstudiantes.get("Estudiantes[" + i + "].nombre");
            String apellido_est = jsEstudiantes.get("Estudiantes[" + i + "].apellido");
            int dni_est = jsEstudiantes.get("Estudiantes[" + i + "].dni");
            String colegio_est = jsEstudiantes.get("Estudiantes[" + i + "].curso");

            System.out.println("El estudiante " + nombre_est + " " + apellido_est + " con DNI " + dni_est + " es del curso " + colegio_est);
        }

    }
}

package Clase4.Ejercicio2;

import com.google.gson.Gson;
import org.testng.annotations.Test;

public class CrearJsonEstudiante {

    EstudianteEj2 estudiante = new EstudianteEj2();

    @Test
    public void crearJsonEstudiantes(){

        String jsonEstudiantes = new Gson().toJson(estudiante);
        System.out.println("====>" + jsonEstudiantes);
    }
}

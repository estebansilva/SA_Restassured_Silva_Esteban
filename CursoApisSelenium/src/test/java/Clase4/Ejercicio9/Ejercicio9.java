package Clase4.Ejercicio9;

import com.google.gson.Gson;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class Ejercicio9 {

    @Test
    public void ej9_futbolistas(){

        List<String> MessiPosition = new ArrayList<>();
        MessiPosition.add("Centrocampista");
        MessiPosition.add("Delantero");

        List<String> Batigol = new ArrayList<>();
        Batigol.add("Delantero");

        Futbolista futbolista1 = new Futbolista("Messi", "Newells", MessiPosition);
        Futbolista futbolista2 = new Futbolista("Batistuta", "Newells", Batigol);

        List<Futbolista> listaCombocados = new ArrayList<>();
        listaCombocados.add(futbolista1);
        listaCombocados.add(futbolista2);


        String jsFutbolistaCombocados = new Gson().toJson(listaCombocados);
        jsFutbolistaCombocados = "{\"futbolistascombocados\":"+jsFutbolistaCombocados+"}";
        System.out.println(jsFutbolistaCombocados);
    }
}

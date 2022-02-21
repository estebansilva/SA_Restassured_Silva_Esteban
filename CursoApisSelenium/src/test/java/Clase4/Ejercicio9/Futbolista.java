package Clase4.Ejercicio9;

import java.util.ArrayList;
import java.util.List;

public class Futbolista {

    String nombre;
    String equipo;
    List<String> posicion = new ArrayList<>();


    public Futbolista(String unNombre, String unEquipo, List<String> unaListaDePosiciones){

        this.nombre = unNombre;
        this.equipo = unEquipo;

        for (int i =0; i < unaListaDePosiciones.size(); i++){

            this.posicion.add(unaListaDePosiciones.get(i));
        }
    }
}

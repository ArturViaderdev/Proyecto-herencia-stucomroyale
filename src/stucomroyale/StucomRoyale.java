/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stucomroyale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author arturviadermataix
 */
public class StucomRoyale {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here       
        //Se incializan las listas
        Metodos.inicializalistas();
        String opcion = "";
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        //se añaden las cartas por defecto
        Metodos.anadecartasdefecto();
        System.out.println("Cartas añadidas.");
        //se añaden los jugadores por defecto
        Metodos.anadejugadoresdefecto();
        System.out.println("Jugadores añadidos");
        System.out.println("Usuarios de ejemplo: artur 1234      pedro aaaa");
        do {
            //se muestra el menú            
            System.out.println("Menú principal");
            System.out.println("a-Conseguir cartas");
            System.out.println("b-Batalla");
            System.out.println("c-Ranking");
            System.out.println("d-Intercambiar una carta entre dos jugadores");
            System.out.println("e-Mostrar cartas de usuario");
            System.out.println("Elige una opción por la letra o introduce s para salir:");
            try {
                opcion = lector.readLine();
                if(opcion.toLowerCase().equals("a"))
                {
                    Metodos.conseguircartas();
                }
                else if(opcion.toLowerCase().equals("b"))
                {
                    Metodos.batalla();
                }
                else if(opcion.toLowerCase().equals("c"))
                {
                    Metodos.ranking();
                }
                else if(opcion.toLowerCase().equals("d"))
                {
                    Metodos.intercambiocarta();
                }
                else if(opcion.toLowerCase().equals("e"))
                {
                    Metodos.mostrarcartasdelusuario();
                }
            } catch (IOException ex) {
                System.out.println("Error de entrada.");
            }
        } while (!opcion.equals("s") && !opcion.equals("S"));
    }

}

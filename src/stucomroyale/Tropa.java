/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stucomroyale;

import java.util.Random;

/**
 *
 * @author arturviadermataix
 */
public class Tropa extends Carta {
    private int nivelfuerza;
    
    @Override
    public int getnivelfuerza()
    {
        return nivelfuerza;
    }
    
    /**
     * Constructor
     * @param nombre Nombre de la nueva carta tropa
     */
    public Tropa(String nombre)
    {
        super(nombre);
        //Se genera un nivel de fuerza aleatoriamente
        Random rand = new Random();
        nivelfuerza = rand.nextInt(5) +1;
    }
    
    /**
     * Una carta tropa al atacar devuelve en nivel de ataque por el nivel de fuerza
     * @return Nivel del ataque
     */
    @Override
    public int ataca()
    {
        return (super.getnivelataque()*nivelfuerza);
    }
}

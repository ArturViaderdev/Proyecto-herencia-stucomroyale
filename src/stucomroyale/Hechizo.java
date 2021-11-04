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
public class Hechizo extends Carta{
    private int nivelalcance;
    
    @Override
    public int getnivelalcance()
    {
        return nivelalcance;
    }
    
    /**
     * Constructor
     * @param nombre Nombre de la nueva carta hechizo 
     */
    public Hechizo(String nombre)
    {
        super(nombre);
        //se genera el nivel de alcance de forma aleatoria
        Random rand = new Random();
        nivelalcance = rand.nextInt(10 + 1 - 5) + 5;
    }
    
    /**
     * Cuando carta hechizo es atacada devuelve el niveldedefensa + el nivel de alcance
     * @return Nivel defensa
     */
    @Override
    public int esatacada()
    {
        return(super.getniveldefensa()+nivelalcance);
    }
    
    /**
     * Cuando una carta hechizo es atacada devuelve el nivel de ataque + el nivel de alcance
     * @return Nivel del ataque
     */
    @Override
    public int ataca()
    {
        return (super.getnivelataque()+nivelalcance);
    }
}

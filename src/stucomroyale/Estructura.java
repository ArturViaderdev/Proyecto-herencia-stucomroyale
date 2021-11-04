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
//Estructura hereda de carta
public class Estructura extends Carta{
    //Variables propias
    private int nivelescudo;
    
    @Override
    public int getnivelescudo()
    {
        return nivelescudo;
    }
 
    //Constructor
    public Estructura(String nombre)
    {
        super(nombre);
        //se genera un nivel de escudo aleatorio
        Random rand = new Random();
        nivelescudo = rand.nextInt(5) +1;
    }
    
    /**
     * Cuando una carta estructura es atacada devuelve su nivel de defensa por su nivel de escudo
     * @return Valor de defensa
     */
    @Override
    public int esatacada()
    {
         return (super.getniveldefensa() * nivelescudo);
    }
}

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
public abstract class Carta {
    private String nombre;
    private int nivelataque;
    private int niveldefensa;
    private int costeelixir;
    private int nivelvida;
       
    /**
     * Comparador que sirve para determinar si dos cartas son la misma
     * @param obj
     * @return 
     */
    public boolean equals(Carta obj)
    {
        return (this==obj);
    }
    
    //Los tres métodos a continuación no se ejecutan, los usan las clases hijas
    public int getnivelfuerza()
    {
        System.out.println("Este no se ejecuta");
        return 0;
    }
    
    public int getnivelalcance()
    {
        System.out.println("Este no se ejecuta");
        return 0;
    }
    
    public int getnivelescudo()
    {
        System.out.println("Este no se ejecuta");
        return 0;
    }
    
    public int ataca()
    {
        return nivelataque;
    }
    
    public int esatacada()
    {
        return niveldefensa;
    }
    
    public String getnombre()
    {
        return nombre;
    }
    
    public int getnivelataque()
    {
        return nivelataque;
    }
    
    public int getniveldefensa()
    {
        return niveldefensa;
    }
    
    public int getcosteelixir()
    {
        return costeelixir;
    }
    
    public int getnivelvida()
    {
        return nivelvida;
    }
    
    public void setnivelvida(int nivelvida)
    {
        this.nivelvida = nivelvida;
    }
    
    /**
     * Constructor con todos los datos
     * @param nombre
     * @param nivelataque
     * @param niveldefensa
     * @param costeelixir
     * @param nivelvida 
     */
    public Carta(String nombre, int nivelataque, int niveldefensa, int costeelixir, int nivelvida)
    {
       this.nombre = nombre;
       this.nivelataque = nivelataque;
       this.niveldefensa = niveldefensa;
       this.costeelixir = costeelixir;
       this.nivelvida = nivelvida;
    }
    
    /**
     * Constructor con generación de datos aleatorios
     * @param nombre Nombre de la carta
     */
    public Carta(String nombre)
    {
        Random rand = new Random();
        this.nombre = nombre;
       /* nivelataque=0;
        niveldefensa=0;*/
        costeelixir = rand.nextInt(5) + 1;
        nivelvida = rand.nextInt(100) + 1;
        
        nivelataque = rand.nextInt(5) + 1;
        niveldefensa = rand.nextInt(5) + 1;
        
        
    }
    
    public void setnivelataque(int nivelataque)
    {
        this.nivelataque = nivelataque;
    }
          
    public void setniveldefensa(int niveldefensa)
    {
        this.niveldefensa = niveldefensa;
    }   
}

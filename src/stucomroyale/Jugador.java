/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stucomroyale;

import java.util.ArrayList;

/**
 *
 * @author arturviadermataix
 */
public class Jugador implements Comparable<Jugador> {

    private String username;
    private String password;
    private int numtrofeos;
    //Lista de cartas que tiene el jugador
    private Carta cartas[];
    final static int maxcartas = 6;
    private int numcartas;

    /**
     * Constructor con nombre de usuario y password
     * @param username Nombre de usuario
     * @param password Password
     */
    public Jugador(String username, String password) {
        //se inicializan las variables
        numtrofeos = 0;
        cartas = new Carta[maxcartas];
        numcartas = 0;
        //se ponen el nombre de usuario y el password del nuevo jugador
        this.username = username;
        this.password = password;
    }

    public int getnumtrofeos() {
        return numtrofeos;
    }

    /**
     * Dice si el jugador tiene una carta
     * @param lacarta Carta a buscar
     * @return Si la carta se ha encontrado
     */
    public boolean tienecarta(Carta lacarta)
    {
        return (Metodos.buscaenlista(lacarta, cartas, numcartas));
    }
    
    public void setnumtrofeos(int numtrofeos) {
        this.numtrofeos = numtrofeos;
    }

    public int getnumcartas() {
        return numcartas;
    }
    
    /**
     * Devuelve una carta de una posición concreta de la lista de cartas del jugador
     * @param posicion Posición de la carta a obtener.
     * @return La carta
     */
    public Carta getcarta(int posicion) {
        return cartas[posicion];
    }
    
    /**
     * Introduce una carta en la posición deseada de la lista de cartas del jugador
     * No se controla si se sale de la lista pero no se producirán llamadas incorrectas
     * @param posicion Posición de la lista donde poner la carta
     * @param lacarta Carta a poner en la lista
     */
    public void setcarta(int posicion, Carta lacarta)
    {
        cartas[posicion]= lacarta;
    }
    
    /**
     * Añade una carta, pero se asegura de que la lista no está llena.
     * @param lacarta La carta a añadir
     * @return Si la carta ha sido añadida o la lista está llena aunque ya se controla el número en otros métodos.
     */
    public boolean anadecarta(Carta lacarta) {
        boolean anadida = false;
        //Si la carta se encuentra y la lista de cartas no está llena
        if (numcartas < maxcartas) {
            //Se guarda la carta en la lista
            cartas[numcartas] = lacarta;
            numcartas++;
            //si se ha añadido una carta
            anadida = true;
        } else {
            //Si la lista de cartas está llena no se añade carta
            anadida = false;
        }
        return anadida;
    }
    
    /**
     * Método fuera de uso ya que ahora al añadirse la carta de un usuario se verifica en todos los usuarios que no la tengan y no solo en este
     * Anade una carta a la lista de cartas del jugador solo si esta no se encuentra ya en la lista
     * @param lacarta Carta para añadir a la lista
     * @return Si la carta se ha añadido o no
     */
    public boolean anadecartasinoexiste(Carta lacarta) {
        boolean anadida = false;
        //Si la carta se ha encontrado en la lista
        if (Metodos.buscaenlista(lacarta, cartas, numcartas)) {
            //no se va a añadir la carta
            anadida = false;
        } else {
            //Si la carta se encuentra y la lista de cartas no está llena
            if (numcartas < maxcartas) {
                //Se guarda la carta en la lista
                cartas[numcartas] = lacarta;
                numcartas++;
                //si se ha añadido una carta
                anadida = true;
            } else {
                //Si la lista de cartas está llena no se añade carta
                anadida = false;
            }
        }
        //Devuelve si se ha añadido la carta o no
        return anadida;
    }

    public String getusername() {
        return username;
    }

    public String getpassword() {
        return password;
    }

    @Override
    public int compareTo(Jugador o) {
        return o.getnumtrofeos() - getnumtrofeos();
    }

}

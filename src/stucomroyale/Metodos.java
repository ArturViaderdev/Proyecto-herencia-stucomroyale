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
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author arturviadermataix
 */
public class Metodos {

    //lista de cartas
    private static ArrayList<Carta> cartas;
    //lista de jugadores
    private static ArrayList<Jugador> jugadores;
    //constante máximo de cartas en batalla por jugador
    final static int maxcartasbatalla = 3;

    //Pide el login de un usuario por teclado.
    //Si el login es correcto devuelve la posiciónn del usuario en la lista
    //Si el login no es correcto devuelve -1

    /**
     * Pide el login de un usuario por teclado.
     * @return Devuelve la posición del usuario en la lista o -1 si el login no es correcto
     */
    private static int loginusuarioteclado() {
        int posusuario;
        String username, pass;
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Introduce el nombre de usuario:");
        try {
            username = lector.readLine();
            if (!username.isEmpty() && username != null) {
                System.out.println("Introduce el password.");
                pass = lector.readLine();
                if (!pass.isEmpty() && pass != null) {
                    posusuario = compruebausuario(username, pass);
                    if (posusuario != -1) {
                        System.out.println("Login correcto.");
                    }
                } else {
                    posusuario = -1;
                }
            } else {
                posusuario = -1;
            }
        } catch (IOException ex) {
            posusuario = -1;
        }
        return posusuario;
    }

    /**
     * Selecciona las cartas del jugador para la batalla preguntando por teclado
     * @param eljugador Jugador para el que se seleccionarán las cartas
     * @return Lista de cartas seleccionadas
     */
    private static Carta[] seleccionacartasbatalla(Jugador eljugador) {
        //lista de cartas para la batalla
        Carta seleccionadas[];
        //se inicializa la lista
        seleccionadas = new Carta[maxcartasbatalla];
        boolean sal, salbusca, encontrado;
        sal = false;
        int cuantas, elegida, conta, sumaelixir;
        cuantas = 0;

        do {
            //se muestran las cartas del usuario
            mostrarcartasusuario(eljugador);
            //Se da la opción de elegir una de las cartas mostradas
            elegida = eligeunacarta(eljugador.getnumcartas());
            //si se ha elegido una carta
            if (elegida != -1) {
                //se busca que esa carta no esté ya en la lista para la batalla
                salbusca = false;
                encontrado = false;
                conta = 0;
                //miro que la carta no esté en la lista
                while (!salbusca) {
                    if (conta < cuantas) {
                        if (cartas.get(conta).equals(eljugador.getcarta(elegida))) {
                            encontrado = true;
                            salbusca = true;
                        } else {
                            conta++;
                        }
                    } else {
                        salbusca = true;
                    }
                }
                if (!encontrado) {
                    //si la carta no se había añadido
                    seleccionadas[cuantas] = eljugador.getcarta(elegida);
                    cuantas++;
                    conta = 0;
                    sumaelixir = 0;
                    //Compruebo el elixir de todas las cartas seleccionadas
                    for (conta = 0; conta < cuantas; conta++) {
                        sumaelixir += seleccionadas[conta].getcosteelixir();
                    }
                    if (sumaelixir > 10) {
                        //si el elixir super 10 se cancela la selección
                        System.out.println("Selección cancelada, el elixir supera 10.");
                        //se vacia la lista para devolverla vacía como caso de error
                        seleccionadas = new Carta[0];
                        sal = true;
                    } else {
                        //Si el elixir no es mayor que 10 y ya se han seleccionado suficientes cartas se sale de bucle
                        if (cuantas == maxcartasbatalla) {
                            sal = true;
                        }
                    }
                } else {
                    System.out.println("La carta ya estaba en la lista no se ha añadido.");
                }
            } else {
                System.out.println("Selección incorrecta.");
            }
        } while (!sal);
        return seleccionadas;
    }
    
    /**
     * Ataque de una carta de un jugador a otra carta de otro jugador
     * @param posjugadora Posición del jugador 1 en la lista de jugadores.
     * @param posjugadorb Posición del jugador 2 en la lista de jugadores.
     * @param cont Número de carta que ataca. Si es 0 la primera carta ataca a la primera carta
     */
    private static void ataque(int posjugadora, int posjugadorb, int cont) {
        int nivelataque, niveldefensa, resultado;
        //Se muestra información del jugador 1
        System.out.print("Ataca el usuario " + jugadores.get(posjugadora).getusername() + " con la carta:");
        //Se muestra información de la carta del jugador 1 que ataca
        mostrarcartausuario(jugadores.get(posjugadora), cont);
        //Se muestra información del jugador 2
        System.out.print("VS el usuario " + jugadores.get(posjugadorb).getusername() + " atacando a la carta");
        //Se muestra información de la carta del jugador 2 que recibe el ataque
        mostrarcartausuario(jugadores.get(posjugadorb), cont);

        /*nivelataque = jugadores.get(posjugadora).getcarta(cont).getnivelataque();
        
         */
        //se obtiene el nivel de ataque, se calcula diferente según el tipo de carta
        nivelataque = jugadores.get(posjugadora).getcarta(cont).ataca();

        //se obtiene el nivel de defensa se calcula diferente según el tipo de carta
        niveldefensa = jugadores.get(posjugadorb).getcarta(cont).esatacada();

        /*
        niveldefensa = jugadores.get(posjugadorb).getcarta(cont).getniveldefensa();
         */
        System.out.println("Nivel de ataque: " + nivelataque);
        System.out.println("Nivel de defensa: " + niveldefensa);

        //El resultado del ataque es restar el nivel de ataque y el nivel de defensa
        resultado = nivelataque - niveldefensa;

        System.out.println("Resultado: " + resultado);

        if (resultado < 0) {
            //Si el resultado es negativo el ataque es fallido
            System.out.println("Ataque fallido, neutro.");
        } else {
            //Si el resultado no es negativo
            //Se le quita vida a la carta que recibe el ataque
            jugadores.get(posjugadorb).getcarta(cont).setnivelvida(jugadores.get(posjugadorb).getcarta(cont).getnivelvida() - resultado);
            //Se muestra la vida de la carta que recibe el ataque
            System.out.println("Vida de " + jugadores.get(posjugadorb).getcarta(cont).getnombre() + " " + jugadores.get(posjugadorb).getcarta(cont).getnivelvida());
        }
    }
    
    /**
     * Cuenta la suma de la vida de todas las cartas de una array de cartas
     * @param cartas Lista de cartas
     * @return Suma de la vida de todas las cartas de la lista
     */
    private static int sumavidacartas(Carta cartas[]) {
        int suma = 0;
        for (int cont = 0; cont < cartas.length; cont++) {
            suma += cartas[cont].getnivelvida();
        }
        return suma;
    }
    
    /**
     * Muestra el usuario ganador y pone los trofeos.
     * Se considera que el ganador es el primer jugador, viene de otros métodos.
     * @param posjugadora Posición del primer jugador en la lista de jugadores
     * @param posjugadorb Posición del segundo jugador en la lista de jugadores
     */
    private static void muestraganadorypontrofeos(int posjugadora, int posjugadorb) {
        System.out.println("El ganador es " + jugadores.get(posjugadora).getusername() + " y gana 5 trofeos.");
        System.out.println("El perdedor es " + jugadores.get(posjugadorb).getusername() + " y gana 1 trofeo.");
        jugadores.get(posjugadora).setnumtrofeos(jugadores.get(posjugadora).getnumtrofeos() + 5);
        jugadores.get(posjugadorb).setnumtrofeos(jugadores.get(posjugadorb).getnumtrofeos() + 1);
        System.out.println("El jugador " + jugadores.get(posjugadora).getusername() + " ahora tiene " + jugadores.get(posjugadora).getnumtrofeos() + " trofeos.");
        System.out.println("El jugador " + jugadores.get(posjugadorb).getusername() + " ahora tiene " + jugadores.get(posjugadorb).getnumtrofeos() + " trofeos.");
    }
    
    /**
     * Muestra el caso de empate y pone los trofeos
     * @param posjugadora Posición del primer jugador en la lista de jugadores
     * @param posjugadorb Posición del segundo jugador en la lista de jugadores
     */
    private static void muestraempateypontrofeos(int posjugadora, int posjugadorb) {
        System.out.println("Los jugadores " + jugadores.get(posjugadora).getusername() + " y " + jugadores.get(posjugadorb).getusername() + " han empatado.");
        System.out.println("Los dos jugadores ganan 1 trofeo.");
        jugadores.get(posjugadora).setnumtrofeos(jugadores.get(posjugadora).getnumtrofeos() + 1);
        jugadores.get(posjugadorb).setnumtrofeos(jugadores.get(posjugadorb).getnumtrofeos() + 1);
        System.out.println("El jugador " + jugadores.get(posjugadora).getusername() + " ahora tiene " + jugadores.get(posjugadora).getnumtrofeos() + " trofeos.");
        System.out.println("El jugador " + jugadores.get(posjugadorb).getusername() + " ahora tiene " + jugadores.get(posjugadorb).getnumtrofeos() + " trofeos.");
    }

    /**
     * Muestra el ranking de jugadores ordenador por trofeos
     */
    public static void ranking() {
        //Define una lista
        ArrayList<Jugador> rankjugadores;
        //Copia la lista de jugadores en una lista para el ranking
        rankjugadores = jugadores;
        //se utiliza sort para ordenar, en la clase está definido que se ordene por trofeos
        Collections.sort(rankjugadores);

        for (int cont = 0; cont < rankjugadores.size(); cont++) {
            System.out.println("Jugador " + rankjugadores.get(cont).getusername() + " Trofeos " + rankjugadores.get(cont).getnumtrofeos());
        }
    }

    /**
     * Permite al jugador seleccionar una carta de las que tiene, antes se muestran
     * @param posjugador
     * @return 
     */
    private static int pidecartajugador(int posjugador) {
        int cartajugador;
        System.out.println("Jugador " + jugadores.get(posjugador).getusername() + " - Selecciona una carta para intercambiar.");
        mostrarcartasusuario(jugadores.get(posjugador));
        //Se da la opción de elegir una de las cartas mostradas
        cartajugador = eligeunacarta(jugadores.get(posjugador).getnumcartas());
        return cartajugador;
    }

    /**
     * Hace login un usuario para que se muestren sus cartas
     */
    public static void mostrarcartasdelusuario() {
        int posjugador;
        posjugador = loginusuarioteclado();
        if (posjugador != -1) {
            if (jugadores.get(posjugador).getnumcartas() > 0) {
                mostrarcartasusuario(jugadores.get(posjugador));
            } else {
                System.out.println("El usuario no tiene cartas.");
            }
        } else {
            System.out.println("Login incorrecto.");
        }
    }

    /**
     * Consulta a los usuarios por teclado para intercambiarse una carta
     */
    public static void intercambiocarta() {
        int cartajugadora, cartajugadorb;
        Carta temporal;
        //hacen login los dos jugadores
        int posjugadora, posjugadorb;
        System.out.println("Login jugador 1");
        //login de usuario 1
        posjugadora = loginusuarioteclado();
        if (posjugadora != -1) {
            if (jugadores.get(posjugadora).getnumcartas() > 0) {
                System.out.println("Login jugador 2");
                //login usuario 2
                posjugadorb = loginusuarioteclado();
                if (posjugadorb != -1) {
                    if (jugadores.get(posjugadorb).getnumcartas() > 0) {
                        //selecciona una carta cada jugador
                        cartajugadora = pidecartajugador(posjugadora);
                        //si se ha elegido una carta
                        if (cartajugadora != -1) {
                            cartajugadorb = pidecartajugador(posjugadorb);
                            if (cartajugadorb != -1) {
                                //Se procede a intercambiar las cartas                      
                                temporal = jugadores.get(posjugadora).getcarta(cartajugadora);
                                jugadores.get(posjugadora).setcarta(cartajugadora, jugadores.get(posjugadorb).getcarta(cartajugadorb));
                                jugadores.get(posjugadorb).setcarta(cartajugadorb, temporal);
                                System.out.println("Las cartas se han intercambiado.");
                            } else {
                                System.out.println("Selección incorrecta.");
                            }
                        } else {
                            System.out.println("Selección incorrecta.");
                        }
                    }
                    else
                    {
                        System.out.println("El jugador no tiene cartas.");
                    }
                } else {
                    System.out.println("Login incorrecto.");
                }
            } else {
                System.out.println("El jugador no tiene cartas.");
            }
        } else {
            System.out.println("Login incorrecto.");
        }
    }

    /**
     * Los usuarios realizan una batalla, se deben introducir los datos por teclado
     */
    public static void batalla() {
        Carta cartasjugadora[];
        Carta cartasjugadorb[];
        Random rand = new Random();
        boolean turno;
        int posjugadora, posjugadorb, cont, temp, sumavidaa, sumavidab;
        cont = 0;
        System.out.println("Login jugador 1");
        //login de usuario 1
        posjugadora = loginusuarioteclado();
        if (posjugadora != -1) {
            if (jugadores.get(posjugadora).getnumcartas() >= 3) {
                System.out.println("Login jugador 2");
                //login usuario 2
                posjugadorb = loginusuarioteclado();
                if (posjugadorb != -1) {
                    if (jugadores.get(posjugadorb).getnumcartas() >= 3) {
                        //si los dos jugadores han hecho login y tienen almenos 3 cartas
                        System.out.println("Intoducción de cartas para la batalla del jugador a");
                        //se seleccionan las cartas para la batalla de las cartas del jugador
                        cartasjugadora = seleccionacartasbatalla(jugadores.get(posjugadora));
                        if (cartasjugadora.length != 3) {
                            System.out.println("No se han seleccionado las cartas.");
                        } else {
                            System.out.println("Intoducción de cartas para la batalla del jugador b");
                            //se seleccionan las cartas para la batalla del jugador b
                            cartasjugadorb = seleccionacartasbatalla(jugadores.get(posjugadorb));
                            if (cartasjugadora.length != 3) {

                                System.out.println("No se han seleccionado las cartas del jugador 2");
                            } else {

                                //Los jugadores han seleccionado 3 cartas
                                //Se decide por el número de trofeos que jugador empieza.
                                //Si empatan se decide aleatorio
                                if (jugadores.get(posjugadora).getnumtrofeos() > jugadores.get(posjugadorb).getnumtrofeos()) {
                                    turno = false;
                                } else if (jugadores.get(posjugadora).getnumtrofeos() < jugadores.get(posjugadorb).getnumtrofeos()) {
                                    turno = true;
                                } else {
                                    if (rand.nextInt(1) == 1) {
                                        turno = true;
                                    } else {
                                        turno = false;
                                    }

                                    //Si el turno es del segundo jugador se invierten las variables
                                    if (turno) {
                                        temp = posjugadorb;
                                        posjugadorb = posjugadora;
                                        posjugadora = temp;
                                    }

                                    //se muestra el nombre del primer jugador
                                    System.out.println("Primer jugador - " + jugadores.get(posjugadora).getusername());

                                    //se realizan ataques en ambos sentidos para cada carta de un jugador a cada carta del otro.
                                    //Carta 1 ataca a carta 1, 2 a 2
                                    for (cont = 0; cont < maxcartasbatalla; cont++) {
                                        System.out.println("Round - " + (cont + 1));
                                        //Ataque de jugador a a b
                                        ataque(posjugadora, posjugadorb, cont);
                                        //Ataque de jugador b a a
                                        ataque(posjugadorb, posjugadora, cont);

                                    }
                                    System.out.println("La batalla ha terminado.");
                                    //Se obtienen las sumas de las vidas de las cartas de cada jugador
                                    sumavidaa = sumavidacartas(cartasjugadora);
                                    sumavidab = sumavidacartas(cartasjugadorb);

                                    System.out.println("Vida de las cartas de " + jugadores.get(posjugadora).getusername() + " " + sumavidaa);
                                    System.out.println("Vida de las cartas de " + jugadores.get(posjugadorb).getusername() + " " + sumavidab);

                                    if (sumavidaa > sumavidab) {
                                        //Si gana el primer jugador se muestra que ha ganado pasándolo como primero en este método
                                        muestraganadorypontrofeos(posjugadora, posjugadorb);
                                    } else if (sumavidaa < sumavidab) {
                                        //si gana el jugador 2 se muestra
                                        muestraganadorypontrofeos(posjugadorb, posjugadora);
                                    } else {
                                        //si empatan se muestra
                                        muestraempateypontrofeos(posjugadora, posjugadorb);
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("El jugador no tiene suficientes cartas.");
                    }
                } else {
                    System.out.println("Error de login");
                }
            } else {
                System.out.println("El jugador no tiene suficientes cartas.");
            }

        } else {
            System.out.println("Error de login.");
        }

    }

    /**
     * Inicializa las listas de cartas y jugadores
     */
    public static void inicializalistas() {
        cartas = new ArrayList<>();
        jugadores = new ArrayList<>();
    }

    /**
     * Añade cartas por defecto
     */
    public static void anadecartasdefecto() {
        cartas.add(new Estructura("Torre"));
        cartas.add(new Estructura("Edificio"));
        cartas.add(new Estructura("Casa"));
        cartas.add(new Hechizo("Magia"));
        cartas.add(new Hechizo("Kame"));
        cartas.add(new Hechizo("Magia negra"));
        cartas.add(new Tropa("Soldado"));
        cartas.add(new Tropa("Capitan"));
        cartas.add(new Tropa("Reina"));

        cartas.add(new Estructura("Coche"));
        cartas.add(new Estructura("Avion"));
        cartas.add(new Estructura("Camion"));
        cartas.add(new Hechizo("Humor"));
        cartas.add(new Hechizo("Tristeza"));
        cartas.add(new Hechizo("Emocion"));
        cartas.add(new Tropa("Teniente"));
        cartas.add(new Tropa("Coronel"));
        cartas.add(new Tropa("Rey"));

    }

    /**
     * Añade jugadores por defecto
     */
    public static void anadejugadoresdefecto() {
        jugadores.add(new Jugador("artur", "1234"));
        jugadores.add(new Jugador("pedro", "aaaa"));
        jugadores.add(new Jugador("jesus", "0000"));
        jugadores.add(new Jugador("jose", "1234"));
    }

    /**
     * Comprueba si un usuario existe por el nombre de usuario y el password
     * @param username El nombre de usuario
     * @param pass El password
     * @return Posición del usuario en la lista o -1 si no lo ha encontrado
     */
    private static int compruebausuario(String username, String pass) {
        boolean sal, encontrado;
        int cont;
        cont = 0;
        sal = false;
        encontrado = false;
        while (!sal) {
            if (cont < jugadores.size()) {
                if (jugadores.get(cont).getusername().equals(username) && jugadores.get(cont).getpassword().equals(pass)) {
                    encontrado = true;
                    sal = true;
                } else {
                    cont++;
                }
            } else {
                sal = true;
            }
        }
        if (!encontrado) {
            cont = -1;
        }
        return cont;
    }
    
    /**
     * Muestra una carta de un usuario
     * @param elusuario Jugador del que se quiere mostrar la carta
     * @param cont Número de carta del jugador
     */
    public static void mostrarcartausuario(Jugador elusuario, int cont) {
        Carta lacarta;
        //obtiene el objeto carta del usuario
        lacarta = elusuario.getcarta(cont);
        //Muestra los datos de la carta
        System.out.print("Carta " + (cont + 1) + " - Nombre:" + lacarta.getnombre() + " ");
        System.out.print("Nivel ataque - " + lacarta.getnivelataque() + " ");
        System.out.print("Nivel defensa - " + lacarta.getniveldefensa() + " ");
        System.out.print("Coste elixir - " + lacarta.getcosteelixir() + " ");
        System.out.println("Nivel vida - " + lacarta.getnivelvida() + " ");
        if (lacarta instanceof Estructura) {
            System.out.print("Tipo de carta - Estructura. ");
            System.out.println("Nivel escudo - " + lacarta.getnivelescudo());
        } else if (lacarta instanceof Hechizo) {
            System.out.print("Tipo de carta - Hechizo .");
            System.out.println("Nivel alcance - " + lacarta.getnivelalcance());
        } else if (lacarta instanceof Tropa) {
            System.out.print("Tipo de carta - Tropa. ");
            System.out.println("Nivel fuerza - " + lacarta.getnivelfuerza());
        }
    }
    
    /**
     * Muestra todas las cartas de un usuario
     * @param elusuario Jugador del que se quieren ver las cartas
     */
    private static void mostrarcartasusuario(Jugador elusuario) {
        Carta lacarta;
        for (int cont = 0; cont < elusuario.getnumcartas(); cont++) {
            mostrarcartausuario(elusuario, cont);
        }
    }

    /**
     * Lista las cartas disponibles con todos sus datos
     */
    private static void listacartasdisponibles() {
        for (int cont = 0; cont < cartas.size(); cont++) {
            System.out.print("Carta " + (cont + 1) + " - Nombre:" + cartas.get(cont).getnombre() + " ");
            System.out.print("Nivel ataque - " + cartas.get(cont).getnivelataque() + " ");
            System.out.print("Nivel defensa - " + cartas.get(cont).getniveldefensa() + " ");
            System.out.print("Coste elixir - " + cartas.get(cont).getcosteelixir() + " ");
            System.out.println("Nivel vida - " + cartas.get(cont).getnivelvida() + " ");
            if (cartas.get(cont) instanceof Estructura) {
                System.out.print("Tipo de carta - Estructura. ");
                System.out.println("Nivel escudo - " + cartas.get(cont).getnivelescudo());
            } else if (cartas.get(cont) instanceof Hechizo) {
                System.out.print("Tipo de carta - Hechizo. ");
                System.out.println("Nivel alcance - " + cartas.get(cont).getnivelalcance());
            } else if (cartas.get(cont) instanceof Tropa) {
                System.out.print("Tipo de carta - Tropa. ");
                System.out.println("Nivel fuerza - " + cartas.get(cont).getnivelfuerza());
            }
        }

    }

    /**
     * El usuario introduce un número para elegir una carta
     * 
     * @param numcartas Número de cartas que hay. El usuario no debe seleccionar una carta no existente
     * @return Número de carta que ha introducido el usuario o -1 si no ha introducido algo correcto
     */
    private static int eligeunacarta(int numcartas) {
        int cual;
        Scanner entrada = new Scanner(System.in);
        System.out.println("Elige una carta introduciendo el número o pon 0 u otro valor para salir:");

        try {
            cual = entrada.nextInt();
            cual--;
            if (!(cual >= 0 && cual < numcartas)) {
                cual = -1;
            }
        } catch (Exception ex) {
            System.out.println("Error de entrada");
            cual = -1;
        }
        return cual;
    }
 
    /**
     * Busca una carta en una lista de cartas
     * @param lacarta La carta a buscar
     * @param cartas Lista de cartas
     * @param numcartas Número de cartas que hay en la lista ya que puede no estar llena
     * @return Si la carta se ha encontrado o no en la lista
     */
    public static boolean buscaenlista(Carta lacarta, Carta cartas[], int numcartas) {
        boolean sal, encontrado;
        int cont;
        sal = false;
        encontrado = false;
        cont = 0;
        while (!sal) {
            if (cont < numcartas) {
                if (cartas[cont].equals(lacarta)) {
                    encontrado = true;
                    sal = true;
                } else {
                    cont++;
                }
            } else {
                sal = true;
            }
        }
        return encontrado;
    }

    /**
     * Comprueba si algun usuario tiene una carta
     * @param lacarta Carta a buscar en las listas de los usuarios
     * @return Si la carta ha sido encontrada
     */
    public static boolean compruebacartaentodosusuarios(Carta lacarta) {
        boolean sal, encontrado;
        int cont;
        sal = false;
        encontrado = false;
        cont = 0;
        do {
            //Mira en la lista de todos los usuarios
            if (cont < jugadores.size()) {
                //A ver si el usuario tiene la carta
                if (jugadores.get(cont).tienecarta(lacarta)) {
                    encontrado = true;
                    sal = true;
                } else {
                    cont++;
                }
            } else {
                sal = true;
            }
        } while (!sal);
        return encontrado;
    }

    /**
     * El usuario puede introducir cartas en su lista cogiendolas de la lista principal
     */
    public static void conseguircartas() {
        String username, pass;
        boolean sal;
        int cual, posusuario;
        String continua;
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        //Se hace login
        System.out.println("Introduce el nombre de usuario:");
        try {
            username = lector.readLine();
            if (!username.isEmpty() && username != null) {
                System.out.println("Introduce el password.");
                pass = lector.readLine();
                if (!pass.isEmpty() && pass != null) {
                    posusuario = compruebausuario(username, pass);
                    if (posusuario != -1) {
                        System.out.println("Login correcto.");
                        sal = false;
                        do {
                            if (jugadores.get(posusuario).getnumcartas() == Jugador.maxcartas) {
                                sal = true;
                                System.out.println("Se han llenado la lista de cartas del jugador.");
                                mostrarcartasusuario(jugadores.get(posusuario));
                            } else {
                                //se listan las cartas disponibles
                                listacartasdisponibles();
                                //El usuario elige una carta de las disponibles
                                cual = eligeunacarta(cartas.size());
                                if (cual != -1) {

                                    if (compruebacartaentodosusuarios(cartas.get(cual))) {
                                        System.out.println("La carta ya estaba añadida por ti o por otro jugador.");
                                    } else {
                                        if (jugadores.get(posusuario).anadecarta(cartas.get(cual))) {
                                            System.out.println("Carta añadida.");
                                        } else {
                                            //Este caso no debería cumplirse.
                                            System.out.println("La carta no se ha añadido. La lista debe estar llena.");
                                        }
                                    }
                                    /*  
                                if (jugadores.get(posusuario).anadecartasinoexiste(cartas.get(cual))) {
                                    System.out.println("Carta añadida.");
                                } else {
                                    System.out.println("La carta ya estaba añadida, no se ha añadido.");
                                }*/
                                } else {
                                    System.out.println("Selección incorrecta.");
                                    sal = true;
                                }
                            }
                        } while (!sal);
                    } else {
                        System.out.println("Login incorrecto");
                    }
                } else {
                    System.out.println("Pass vacio.");
                }
            } else {
                System.out.println("Nombre de usuario vacio.");
            }
        } catch (IOException ex) {
            System.out.println("Error de entrada.");
        }
    }
}

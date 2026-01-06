package Jugadores;

import Casillas.CasillaAdquirible;
import Casillas.Propiedades.Propiedad;
import Resources.GeneralMethods;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Jugador {
    //Atributos de jugador
    private String nombre;
    private int posicion; //define la posicion del jugador en el tablero (array de casillas)
    private int balance; //dinero que maneja el jugador en el juego
    private final ArrayList<CasillaAdquirible> propiedades = new ArrayList<>(); //propiedades que el jugador va almacenando
    private int turnosJugados; //se almacena la cantidad de turnos consecutivos jugados para establecer si debe ir preso
    private boolean juegaDeNuevo; //se almacena el booleano que guarda si el jugador debe jugar de nuevo si saco doble
    private boolean isFree; //se almacena el boolean que indica si posee la carta de casualidad que permite ir libre
    private boolean preso; //se almacena el booleano que indica si el jugador esta preso

    /**@param nombre recibe unicamente el nombre del jugador
     * metodo constructor de la clase
     * */
    public Jugador(String nombre) {
        this.nombre = nombre;
        posicion = 0;
        balance = 1500;
        turnosJugados = 0;
        juegaDeNuevo = false;
        isFree = false;
        preso = false;
    }

    //métodos

    /**
     * Esta es la función que verificará en cada turno si el jugador tiene un balance negativo o no.
     *
     * @return {@code boolean} Si el balance es negativo, el jugador estará forzado a hipotecar casas y propiedades hasta que
     *       su balance no sea positivo; pero si no es posible obtener un balance positivo, devuelve {@code true}
     *       lo cual indica que el juego ha terminado dado que un jugador ha quedado en bancarrota
     */
    public boolean bancarrota() {
        //Si el balance es negativo, debe hipotecar, pero para prevenir perdida de tiempo...
        int possibleBalance = this.balance;

        if (this.balance < 0) {
            System.out.println("Oh no! Estás en bancarrota! Tienes que hipotecar tus propiedades hasta tener un balance positivo");
            for (CasillaAdquirible propiedad : propiedades){
                if(!propiedad.isHipotecado()) {
                    possibleBalance += propiedad.getHipoteca();
                }
                //Se calcula su balance si hipotecara todas sus propiedades...
                if (propiedad instanceof Propiedad propiedad1 && propiedad1.getNumeroCasas() != 0){
                    //Sumado a la venta de todas las casas que tiene
                    possibleBalance += propiedad1.getNumeroCasas() * propiedad1.getPrecioCasa() / 2;
                }
            }

            //Si aún así queda en números negativos, está en bancarrota
            if (possibleBalance < 0){
                System.out.println("It's so over");
                return true;
            }

            //Pero si no... Debe hipotecar hasta que su balance sea positivo
            else {
                while (this.balance < 0){
                    System.out.println("Tu balance actual es: $ " + balance);
                    salvarBancarrota();
                }
            }
        }
        return false;
    }

    /**El metodo tirarDados se encarga de almacenar un numero aleatorio en un rango de 1 a 6
    * que es el rango de valores posibles para un dado
    *@return {@code int}
     * siendo el numero que el dado retorno
     * Se establece que el boolean de juegaDeNuevo es true si cada dice posee el mismo valor
     */
    public int tirarDados() {
        int value = 0, dice1 = 0, dice2 = 0;
        Random dice = new Random();

        for (int i = 0; i < 2; ++i) {

            value += dice.nextInt(1, 6);

            if (i == 0) {
                dice1 = value;
            } else {
                dice2 = value - dice1;
            }

        }

        if (dice1 == dice2) {
            juegaDeNuevo = true;
        }
        System.out.println("Los dados marcan " + value);
        return value;
    }

    /**@param cantidad este metoodo unicamente recibe la cantidad de dinero que cobra el jugador
     * Se encarga de actualizar el balance del jugador en base al pago que realiza*/
    public void pagar(int cantidad) {

        setBalance(this.balance - cantidad);
    }

    /**@param cantidad este metodo unicamente recibe la cantidad de dinero que cobra el jugador
     * Se encarga de actualizar el balance del jugador en base al cobro que realiza
     * */
    public void cobrar(int cantidad) {

        setBalance(this.balance + cantidad);
    }

    /**@param resultado_dados recibe el entero que el metodo de tirar dados devuelve
     * El metodo se encarga de actualizar la posicion del jugador verificando que no pase
     * el entero del limite del numero de casillas del tablero, y si el jugador pasa por la
     * entrada entonces se le pagan los $200, el atributo de posicion se debe actualizar
     * para controlar el avance del jugador a traves del array de casillas del tablero
     * */
    public void mover_casilla(int resultado_dados) {
        if (posicion + resultado_dados > 39) {
            setPosicion(posicion + resultado_dados - 40);
            if (posicion != 0) {
                System.out.println("Ha pasado por la entrada! cobra $200");
                balance += 200;
            }
        } else {
            setPosicion(posicion + resultado_dados);
        }
    }

    /** @param jugador es el jugador al cual se le envía una oferta para negociar y que debe aceptar
     * El metodo se encarga de almacenar la propiedad que el jugador desea comprarle a otro jugador
     * realiza la logica para que el propietario acepte o rechace la oferta
     * Finalmente, realiza la logica para remover de la lista del propietario la propiedad en cuestion
     * y agrega en la lista del jugador que compra la propiedad en su lista de propiedades*/
    public void negociar(Jugador jugador) throws IOException {

        Scanner input = new Scanner(System.in);
        int numPropiedad = 0, oferta = 0;

        int i = 1;
        CasillaAdquirible propiedadDeseada = null;

        for(CasillaAdquirible propiedad : jugador.getPropiedades()) {

            System.out.println("[" + i + "] " + propiedad.getNombre() + "(" +
                    (propiedad instanceof Propiedad ? ((Propiedad) propiedad).getColorCasilla() + ")" : ")"));
            i++;
        }

        i = 1;

        System.out.println("Ingrese el numero de la propiedad por la que desea negociar");
        System.out.print("Propiedad: #");

        try {
            numPropiedad = input.nextInt();
            input.nextLine();
        } catch(InputMismatchException e) {
            System.out.println("Ingrese una opcion valida");
        }

        System.out.println("Oferta: ");

        try {
            oferta = input.nextInt();
            input.nextLine();
        } catch(InputMismatchException e) {
            System.out.println("Ingrese una opcion valida");
        }

        if (oferta > balance){
            System.out.println("No tienes esa cantidad. Pobre");
            return;
        }

        for(CasillaAdquirible propiedad : jugador.getPropiedades()) {

            if(numPropiedad == i) {
                propiedadDeseada = propiedad;
                if(propiedadDeseada.isHipotecado()) {
                    System.out.println("La propiedad " + propiedadDeseada.getNombre() + " se encuentra hipotecada, no se puede negociar por ella");
                    GeneralMethods.systemPause();
                    return;
                }
            }

            i++;
        }

        try {

            if(propiedadDeseada == null) {
                throw new NullPointerException();
            }

            if(jugador.aceptar(propiedadDeseada, oferta, input)) {

                System.out.println("El jugador " + jugador.getNombre() + " ha aceptado la oferta");

                GeneralMethods.systemPause();

                this.pagar(oferta);
                jugador.cobrar(oferta);

                for(CasillaAdquirible propiedad : jugador.getPropiedades()) {

                    if(propiedadDeseada.getNombre().equals(propiedad.getNombre())) {

                        jugador.getPropiedades().remove(propiedad);
                        this.adquirir_propiedad(propiedad);

                        break;
                    }
                }

            } else {

                System.out.println("El jugador " + jugador.getNombre() + " no ha aceptado la oferta");

                GeneralMethods.systemPause();
            }
        } catch(NullPointerException e) {

            System.out.println("Ninguna propiedad de " + jugador.getNombre() + " ha sido escogida");

            GeneralMethods.systemPause();
        }
    }

    /**
     * Este método es para aceptar una oferta de negocio de algún jugador
     * @param propiedad recibe un objeto de casillaAdquirible el cual es la propiedad que se desea comprar
     * al propietario de dicha propiedad
     * @param oferta es lo que se ofrece para adquirir dicha propiedad
     * @param input es un objeto Scanner para almacenar la decision del jugador
     * el metodo se encarga de mostrarle la oferta del jugador que desea comprar
     * al propietario de la propiedad
     * @return {@code boolean} retorna si el propietario acepto o no la oferta
     * */
    public boolean aceptar(CasillaAdquirible propiedad, int oferta, Scanner input) throws IOException {
    //The Scanner parameter bugs me a lot
        try {

            System.out.println("OFERTA PARA " + this.nombre);
            System.out.println("Propiedad: " + propiedad.getNombre());
            System.out.println("Precio: $" + oferta + "\n");

            System.out.println("[1] Aceptar oferta");
            System.out.println("[2] Rechazar oferta");
            System.out.print("Decision: ");

            return input.nextInt() == 1;

        } catch(InputMismatchException e) {

            GeneralMethods.systemCls(); //Aqui se debe limpiar la pantalla
            return aceptar(propiedad, oferta, input);
        }
    }

    /**@param propiedad recibe una casilla adquirible la cual es la casilla que esta
     * adquiriendo el jugador al momento de llamar al metodo
     * El metodo se encarga de settear el propietario del objeto propiedad, siendo el jugador this
     * Ademas, al jugador this se le agrega dicha propiedad a la lista de propiedades*/
    public void adquirir_propiedad(CasillaAdquirible propiedad) {
        propiedad.setPropietario(this);
        this.propiedades.add(propiedad);
    }

    /**
     * Sí, esta es la función con la cual se hace la hipoteca en caso de bancarrota. No estoy muy orgulloso,
     * pues es básicamente lo mismo que lo que se encuentra en tablero; pero ya fue.
     */
    public void salvarBancarrota(){
        int i = 1;
        int option;
        Scanner sc = new Scanner(System.in);
        ArrayList<CasillaAdquirible> propiedadesHipotecables = new ArrayList<>(); //hipotecables es una palabra que existe

        for (CasillaAdquirible casillaAdquirible : propiedades) {
            if (!casillaAdquirible.isHipotecado()){
                propiedadesHipotecables.add(casillaAdquirible);
                System.out.print("[" + i + "]   ");
                System.out.print("$" + casillaAdquirible.getHipoteca() + "   ");
                System.out.println(casillaAdquirible.getNombre());
                ++i;
            }
        }

        System.out.println("\nIngrese el número de la propiedad a hipotecar");

        try {
            option = sc.nextInt() - 1;
            sc.nextLine();

            //hipotecar casas
            if (propiedadesHipotecables.get(option) instanceof Propiedad propiedad && propiedad.getNumeroCasas() != 0) {
                System.out.println("Usted posee casas [" + propiedad.getNumeroCasas() + "] en la propiedad. ¿Desea vender una casa?\n[1] Sí\n[2] No");
                try {
                    if (sc.nextInt() == 1) {
                        propiedad.setNumeroCasas(propiedad.getNumeroCasas() - 1);
                        cobrar(propiedad.getPrecioCasa() / 2);

                        System.out.println("Recibes $" + propiedad.getPrecioCasa() / 2);
                    }

                } catch (InputMismatchException e) {
                    throw new IndexOutOfBoundsException();
                }
            } else {
                propiedadesHipotecables.get(option).setHipotecado(true);
                cobrar(propiedadesHipotecables.get(option).getHipoteca());
                System.out.println(propiedadesHipotecables.get(option).getNombre() + " ha sido hipotecada.");
                System.out.println("Por la hipoteca recibes $" + propiedadesHipotecables.get(option).getHipoteca());
            }

        } catch (Exception e) {
            System.out.println("Ingrese una opción válida");
        }
    }

    //setters y getters 
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isJuegaDeNuevo() {
        return juegaDeNuevo;
    }

    public void setJuegaDeNuevo(boolean juegaDeNuevo) {
        this.juegaDeNuevo = juegaDeNuevo;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public boolean isPreso() {
        return preso;
    }

    public void setPreso(boolean preso) {
        this.preso = preso;
    }

    public int getTurnosJugados() {
        return turnosJugados;
    }

    public void setTurnosJugados(int turnosJugados) {
        this.turnosJugados = turnosJugados;
    }

    public ArrayList<CasillaAdquirible> getPropiedades() {
        return propiedades;
    }
}

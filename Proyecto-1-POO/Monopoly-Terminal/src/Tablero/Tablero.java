package Tablero;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Casillas.Casilla;
import Casillas.CasillaAdquirible;
import Casillas.CasillasEspeciales.Carcel;
import Casillas.FactoryCasillas.FactoriesCasillasEspeciales.*;
import Casillas.FactoryCasillas.FactoryCasillaAdquirible;
import Casillas.FactoryCasillas.FactoryCasillaPropiedad;
import Casillas.FactoryCasillas.FactoryCasillasEspeciales;
import Casillas.FactoryCasillas.FactoriesCasillaAdquirible.FactoryEstacion;
import Casillas.FactoryCasillas.FactoriesCasillaAdquirible.FactoryServicios;
import Casillas.FactoryCasillas.FactoriesCasillaPropiedad.FactoryPropiedad;
import Casillas.Propiedades.ColorCasilla;
import Casillas.Propiedades.Propiedad;
import Jugadores.Jugador;
import Resources.ExtraEnums;
import Resources.GeneralMethods;

public class Tablero {

    private final ArrayList<Casilla> casillas; //coleccion de casillas que simulan el tablero como tal
    private final ArrayList<Jugador> jugadores; //coleccion de jugadores
    private static Tablero tablero; //objeto tablero que va a almacenar la unica instancia de esta clase

    /**
     * Se define el constructor de la clase privado
     * Se implementa el patron de diseno Singleton
     */
    private Tablero() {
        casillas = agregarCasilla();
        jugadores = new ArrayList<>();
    }

    /**
     * Metodo estatico que devuelve la instancia unica de la clase
     * Si ya existe una instancia almacenada retorna dicha instancia de tablero
     * Si no existe ninguna instancia almacenada la crea y la devuelve
     *
     * @return una instancia de tablero
     */
    public static Tablero get_tablero() {

        if (tablero == null) {

            tablero = new Tablero();
        }

        return tablero;
    }

    //getters
    public ArrayList<Casilla> getCasillas() {
        return casillas;
    }
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    /**@param jugador
     * metodo que agrega cada jugador que recibe
     * a la lista de jugadores
     * */
    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    /**
     * @param jugador un objeto de tipo jugador con el cual se realizarán las accionens
     * el metodo turno ejecuta todas las acciones posibles que un jugador puede realizar
     * verificando si este esta preso o no, ademas, se implementa toda la logica
     * que se requiere para que el jugador pueda ejecutar todas las acciones posibles
     * basadas en el juego real
     **/
    public void turno(Jugador jugador) throws IOException {
        int option, i, option1;
        Scanner sc = new Scanner(System.in);

        jugador.setJuegaDeNuevo(false);

        if (jugador.getTurnosJugados() == 3) {
            jugador.setTurnosJugados(0);
            jugador.setPreso(true);
            jugador.setPosicion(10);
            System.out.println("Te han salido tres veces dobles, te vas a la carcel!");
            return;
        }

        System.out.println("Turno de " + jugador.getNombre());
        if (jugador.isPreso()) {
            GeneralMethods.systemCls();
            System.out.println(jugador.getNombre() + " estas preso");
            System.out.println("Que deseas hacer?");
            System.out.println("""
                    [1] Tirar dados e intentar salir\s
                    [2] Pagar $50 y salir de la carcel\s""");

            try{
                option = Integer.parseInt(sc.nextLine());
            }catch (Exception e){
                option = 9;
            }

            switch (option) {
                case 1:

                    jugador.tirarDados();
                    if (jugador.isJuegaDeNuevo()) {
                        ((Carcel) casillas.get(10)).getPresos().remove(((Carcel) casillas.get(10)).searchPreso(jugador.getNombre()));
                        jugador.setPreso(false);
                        System.out.println("Son dobles! " + jugador.getNombre() + " sale de la carcel! (corrupto >:c)");
                    } else
                        System.out.println("Lo siento mi compa, tendras que continuar preso, tu nivel de corrupcion no alcanzo");

                    break;
                case 2:
                    if (jugador.getBalance() < 50) {
                        System.out.println("Ni para pagar la fianza te alcanza");
                        break;
                    }

                    jugador.pagar(50);
                    System.out.println("Gracias por pagar la fianza!");
                    jugador.setPreso(false);
                    for (Casilla carcel : casillas) {
                        if (carcel instanceof Carcel carcel1) {
                            carcel1.getPresos().remove(carcel1.searchPreso(jugador.getNombre()));
                        }
                    }

                    break;
                default:
                    System.out.println("Por pelotudo perdes turno");
            }
        } else {
            do {
                GeneralMethods.systemCls();
                System.out.println("Balance: $" + jugador.getBalance());
                System.out.println("Que desea hacer?");
                System.out.println("""
                        [1] Tirar dados y mover\s
                        [2] Negociar\s
                        [3] Hipotecar propiedad / deshipotecar propiedad\s
                        [4] Comprar casas\s
                        [5] Ver estadísticas""");

                try {
                    option = Integer.parseInt(sc.nextLine());

                    if (option < 1) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    option = 9;
                }

                GeneralMethods.systemCls();

                switch (option) {
                    case 1:
                        jugador.mover_casilla(jugador.tirarDados());
                        if (jugador.isJuegaDeNuevo()) {
                            System.out.println("Dados dobles, vuelve a tirar!");
                        }
                        Casilla casilla = casillas.get(jugador.getPosicion());


                        try {
                            if (jugador.getNombre().equals("Test")) {
                                casilla.realizarAccion(jugador);
                            }

                            casilla.realizarAccion(jugador);

                        } catch (InterruptedException e) {
                            System.out.println("problem");
                        }


                        break;
                    case 2:
                        i = 0;
                        System.out.println("Elija el jugador con quien quiera negociar:");

                        for (Jugador jugador1 : jugadores) {

                            if (jugador1 != jugador) {
                                System.out.println("[" + (i + 1) + "] " + jugador1.getNombre());
                            }

                            i++;
                        }
                        try {
                            option1 = sc.nextInt() - 1;
                            sc.nextLine();
                            if (jugadores.get(option1) == jugador) {
                                throw new Exception();
                            }
                            jugador.negociar(jugadores.get(option1));
                        } catch (Exception e) {
                            System.out.println("Ingrese una opción válida");
                        }

                        break;
                    case 3:

                        System.out.println("Ingrese el número de la propiedad a hipotecar / deshipotecar");
                        i = 1;
                        for (CasillaAdquirible casillaAdquirible : jugador.getPropiedades()) {
                            System.out.println("[" + i + "] " + casillaAdquirible.getNombre() +
                                    "("+ (casillaAdquirible instanceof Propiedad ? ((Propiedad) casillaAdquirible).getColorCasilla() : "") +
                                    ") - " + (casillaAdquirible.isHipotecado() ? " Hipotecado" : ""));
                            ++i;
                        }

                        try {
                            option1 = Integer.parseInt(sc.nextLine());

                            if (!jugador.getPropiedades().get(option1 - 1).isHipotecado()) {
                                while (true) {
                                    if (jugador.getPropiedades().get(option1 - 1) instanceof Propiedad propiedad && propiedad.getNumeroCasas() != 0) {
                                        System.out.println("Usted posee casas en la propiedad. ¿Desea vender casas?\n[1] Si\n[2] No");
                                        try {
                                            if (sc.nextInt() == 1) {
                                                propiedad.setNumeroCasas(propiedad.getNumeroCasas() - 1);
                                                jugador.setBalance(jugador.getBalance() + (propiedad.getPrecioCasa() / 2));
                                                System.out.println("Recibes $" + propiedad.getPrecioCasa() / 2);
                                            } else break;
                                        } catch (InputMismatchException e) {
                                            throw new IndexOutOfBoundsException();
                                        }

                                    } else {
                                        jugador.getPropiedades().get(option1 - 1).setHipotecado(true);
                                        jugador.setBalance(jugador.getBalance() + jugador.getPropiedades().get(option1 - 1).getHipoteca());
                                        System.out.println(jugador.getPropiedades().get(option1 - 1).getNombre() + " ha sido hipotecada.");
                                        System.out.println("Por la hipoteca recibes $" + jugador.getPropiedades().get(option1 - 1).getHipoteca());
                                        break;
                                    }
                                }
                            } else {
                                if (jugador.getBalance() - jugador.getPropiedades().get(option1).getHipoteca() > 0) {
                                    System.out.println(jugador.getPropiedades().get(option1).getNombre() + " ha sido deshipotecada");
                                    jugador.pagar(jugador.getPropiedades().get(option1).getHipoteca());
                                    jugador.getPropiedades().get(option1).setHipotecado(false);
                                } else {
                                    System.out.println("No tiene suficiente dinero para deshipotecar la propiedad.");
                                }
                            }
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("Ingrese una opción válida");
                        }
                        break;
                    case 4:

                        i = 0;
                        System.out.println("Ingrese el número de la propiedad en la que quiera comprar casas:");
                        for (CasillaAdquirible casillaAdquirible : jugador.getPropiedades()) {
                            if (casillaAdquirible instanceof Propiedad) {
                                System.out.println("[" + (i + 1) + "] " + casillaAdquirible.getNombre() + "(" +
                                        ((Propiedad) casillaAdquirible).getColorCasilla() + ")" + "\n\tPrecio: $" +
                                        ((Propiedad)casillaAdquirible).getPrecioCasa());
                            }
                            ++i;
                        }
                        try {
                            option1 = Integer.parseInt(sc.nextLine());

                            Propiedad propiedad = (Propiedad) jugador.getPropiedades().get(option1 - 1);

                            if (propiedad.getNumeroCasas() != 5 && propiedad.grupoCompleto(jugador)) {

                                if (jugador.getBalance() > propiedad.getPrecioCasa()) {
                                    jugador.setBalance(jugador.getBalance() - propiedad.getPrecioCasa());
                                    System.out.println("Has comprado una casa en: " + propiedad.getNombre());
                                    propiedad.setNumeroCasas(propiedad.getNumeroCasas() + 1);
                                } else {
                                    System.out.println("Lo sentimos, " + jugador.getNombre() + ". No tienes suficiente dinero para comprar casa");
                                    GeneralMethods.systemPause();
                                }

                            } else if (!propiedad.grupoCompleto(jugador)) {
                                System.out.println("El grupo de color no está completo, no se pudo comprar la casa.");
                            } else {
                                System.out.println("La propiedad ya alcanzo la cantidad máxima de casas y hoteles.");
                            }
                        } catch (Exception e) {
                            System.out.println("Elija una opción válida");
                            GeneralMethods.systemPause();
                            GeneralMethods.systemCls();
                        }
                        break;
                    case 5:
                        System.out.println("Balance: $" + jugador.getBalance());
                        System.out.println("Propiedades: ");
                        for (CasillaAdquirible casillaAdquirible : jugador.getPropiedades()) {
                            System.out.print("\t- " + casillaAdquirible.getNombre() +
                                    (casillaAdquirible.isHipotecado() ? "\t- Hipotecado" : ""));
                            if (casillaAdquirible instanceof Propiedad) {
                                System.out.println("\t- Color: " + ((Propiedad) casillaAdquirible).getColorCasilla());
                            } else System.out.println(" ");
                        }
                        System.out.println("Casilla actual: " + tablero.getCasillas().get(jugador.getPosicion()).getNombre());
                        break;
                    default:
                        System.out.println("Ingrese una opción válida");
                        break;
                }

            } while (option != 1);
        }

        if (jugador.isJuegaDeNuevo()) {
            jugador.setTurnosJugados(jugador.getTurnosJugados() + 1);
            jugador.setJuegaDeNuevo(false);
            turno(jugador);
        }
    }

    /**
     * Se encarga de leer el archivo de tablero.txt y llenar un array el cual
     * el metodo devuelve para llenar el array de casillas necesario
     * para el tablero
     * @return El array de tablero lleno
     */
    public ArrayList<Casilla> agregarCasilla() {
        FactoryCasillasEspeciales factoryEspecial;
        FactoryCasillaAdquirible factoryAdquirible;
        FactoryCasillaPropiedad factoryPropiedad = new FactoryPropiedad();
        ArrayList<Casilla> tablero = new ArrayList<>();
        ArrayList<Integer> precios = new ArrayList<>();
        Casilla casilla;
        Enum type;
        String name, price, hipoteca, housePrice, description;


        try {
            Path file = Path.of("Monopoly/TextFiles/Tablero.txt");
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String Type = sc.next();

                try {
                    type = ColorCasilla.valueOf(Type);
                    name = sc.next().replace(".", " ");
                    price = sc.next();
                    for (int i = 0; i < 6; ++i) {
                        precios.add(Integer.parseInt(sc.next()));
                    }

                    housePrice = sc.next();
                    hipoteca = sc.next();
                    description = "Propiedad de color";

                    casilla = factoryPropiedad.createCasillaPropiedad(name, description, Integer.parseInt(price), (ArrayList<Integer>) precios.clone(), Integer.parseInt(hipoteca), (ColorCasilla) type, Integer.parseInt(housePrice));

                    tablero.add(casilla);
                    precios.clear();

                } catch (IllegalArgumentException e) {
                    type = ExtraEnums.valueOf(Type);

                    switch (type) {
                        case ExtraEnums.CARCEL -> {
                            factoryEspecial = new FactoryCarcel();
                            casilla = factoryEspecial.CreateCasillaEspecial();
                        }
                        case ExtraEnums.CASUALIDAD -> {
                            factoryEspecial = new FactoryCasualidad();
                            casilla = factoryEspecial.CreateCasillaEspecial();
                        }
                        case ExtraEnums.ENTRADA -> {
                            factoryEspecial = new FactoryEntrada();
                            casilla = factoryEspecial.CreateCasillaEspecial();
                        }
                        case ExtraEnums.ESTACION -> {
                            factoryAdquirible = new FactoryEstacion(sc.next().replace(".", " "));
                            casilla = factoryAdquirible.CreateCasillaAdquirible();
                        }
                        case ExtraEnums.GOJAIL -> {
                            factoryEspecial = new FactoryGoJail();
                            casilla = factoryEspecial.CreateCasillaEspecial();
                        }
                        case ExtraEnums.HACIENDA -> {
                            factoryEspecial = new FactoryHacienda();
                            casilla = factoryEspecial.CreateCasillaEspecial();
                        }
                        case ExtraEnums.PARADALIBRE -> {
                            factoryEspecial = new FactoryParadaLibre();
                            casilla = factoryEspecial.CreateCasillaEspecial();
                        }
                        default -> {
                            factoryAdquirible = new FactoryServicios(sc.next().replace(".", " "));
                            casilla = factoryAdquirible.CreateCasillaAdquirible();
                        }
                    }

                    tablero.add(casilla);

                }
            }

            return tablero;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
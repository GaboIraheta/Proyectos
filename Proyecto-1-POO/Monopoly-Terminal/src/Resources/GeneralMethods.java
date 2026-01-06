package Resources;

import java.io.IOException;
import java.util.Scanner;

public class GeneralMethods {
    /**
     * Este método solicita una lectura de cualquier entrada, convirtiéndola en esencia en un método
     * que pare la ejecución del programa hasta que se realice una entrada de teclado.
     * @throws IOException
     */
    public static void systemPause() throws IOException {
        System.out.println("Presiona cualquier tecla para continuar...");
        System.in.read();
    }

    /**
     * Este método utiliza expresiones regulares para limpiar la consola. Es literalmente lo único que hace.
     */

    /**
     * Este método utiliza expresiones regulares para limpiar la consola. Es literalmente lo único que hace.
     */

    public static void systemCls() throws IOException{
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

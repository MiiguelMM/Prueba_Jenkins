import java.util.Arrays;
import java.util.Scanner;

public class Ahorcado {
    public static void main(String[] args) throws Exception {
        System.out.println("Cual es la palabra a adivinar?");
        Scanner n = new Scanner(System.in);
        String palabra = n.next();
        char letrasp[] = palabra.toCharArray();
        char longitud[] = new char[letrasp.length];
        Arrays.fill(longitud, '_');

        System.out.println(longitud);
        cambio(palabra, letrasp, longitud);
        n.close(); // Cierra el scanner al final
    }

    static void cambio(String palabra, char letrasp[], char longitud[]) {
        boolean bandera = false;
        Scanner n = new Scanner(System.in);
        int intentos = 10; 

        while (!bandera && intentos > 0) {
            System.out.println("Dime una letra");
            char letra = n.next().charAt(0);
            boolean acerto = false;

            for (int i = 0; i < letrasp.length; i++) {
                if (letra == letrasp[i]) {
                    longitud[i] = letra;
                    acerto = true;
                }
            }

            if (acerto) {
                System.out.println("Acertaste: ");
            } else {
                intentos--; 
                System.out.println("Fallaste. Te quedan " + intentos + " intentos.");
            }

            for (int i = 0; i < longitud.length; i++) {
                System.out.print(longitud[i] + " ");
            }

            if (Arrays.equals(longitud, letrasp)) {
                System.out.println("Ganaste. La palabra es: " + palabra);
                bandera = true;
            }
        }

        if (!bandera) {
            System.out.println("Perdiste. La palabra era: " + palabra);
        }
        n.close();
    }
}
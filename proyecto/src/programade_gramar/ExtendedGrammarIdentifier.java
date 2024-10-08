package programade_gramar;
import java.util.regex.Pattern;
import java.util.Scanner;


public class ExtendedGrammarIdentifier {
	 // Verifica si el lenguaje dado sigue una gramática regular
    public static boolean isRegularGrammar(String text) {
    	 // Definimos más expresiones regulares para abarcar más casos de gramáticas regulares
        String[] regularExpressions = {
        		  "a*b*",         // Cadenas de a's seguidas por b's
                  "(ab)*",        // Cadenas alternadas de "ab"
                  "[01]*",        // Cadenas binarias (0's y 1's)
                  "[a-z]*",       // Cadenas de letras minúsculas
                  "[A-Za-z]*",    // Cadenas de letras mayúsculas y minúsculas
                  "[0-9]*",       // Cadenas de dígitos
                  "[A-Za-z0-9]*"  // Cadenas alfanumérica
        };
        // Iteramos sobre las expresiones regulares para ver si alguna coincide con el texto dado
        for (String regex : regularExpressions) {
        	 Pattern pattern = Pattern.compile(regex);
             if (pattern.matcher(text).matches()) {
            	 return true;
             }
        }
        return false;
    }
 // Verifica si el lenguaje dado puede ser independiente de contexto
    public static boolean isContextFreeGrammar(String text) {
    	 // Verificamos si contiene patrones de balanceo de símbolos (paréntesis, llaves, corchetes)
        return isBalancedSymbols(text);
    }
 // Función auxiliar que verifica si hay balanceo en diferentes tipos de símbolos
    public static boolean isBalancedSymbols(String text) {
    	int roundBrackets = 0; // Para paréntesis ()
        int curlyBrackets = 0; // Para llaves {}
        int squareBrackets = 0; // Para corchetes []
        for (char c : text.toCharArray()) {
        	 switch (c) {
        	 case '(': roundBrackets++; break;
             case ')': roundBrackets--; break;
             case '{': curlyBrackets++; break;
             case '}': curlyBrackets--; break;
             case '[': squareBrackets++; break;
             case ']': squareBrackets--; break;
        	 }
        	 // Si en algún momento hay un desequilibrio, ya sabemos que no está bien balanceado
             if (roundBrackets < 0 || curlyBrackets < 0 || squareBrackets < 0) {
            	 return false;
             }
                
        }
        // Al final, todos deben estar balanceados
        return roundBrackets == 0 && curlyBrackets == 0 && squareBrackets == 0;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        // Bucle infinito para seguir recibiendo cadenas hasta que el programa sea terminado manualmente
        while (true) {
            System.out.println("Introduce un párrafo o cadena para identificar su gramática:");
            input = scanner.nextLine();

            // Primero intentamos identificar si es una gramática regular
            if (isRegularGrammar(input)) {
                System.out.println("El lenguaje pertenece a una gramática regular.");
            }
            // Si no es regular, vemos si es independiente de contexto
            else if (isContextFreeGrammar(input)) {
                System.out.println("El lenguaje pertenece a una gramática independiente de contexto.");
            }
            // Si no es ni regular ni independiente de contexto, asumimos que es más complejo
            else {
                System.out.println("El lenguaje es más complejo que una gramática independiente de contexto.");
            }
        }
    }

    }


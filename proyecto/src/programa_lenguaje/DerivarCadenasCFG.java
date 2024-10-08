package programa_lenguaje;

import java.util.*;

public class DerivarCadenasCFG {
    // Clase para representar una gramática independiente de contexto (CFG)
    static class ContextFreeGrammar {
        private Map<String, List<String>> productions = new HashMap<>();

        // Añadir una regla de producción a la gramática
        public void addProduction(String nonTerminal, String production) {
            productions.computeIfAbsent(nonTerminal, k -> new ArrayList<>()).add(production);
        }

        // Derivar cadenas a partir de un símbolo no terminal (por defecto el símbolo inicial es 'S')
        public void generateLanguage(String startSymbol, int maxSteps) {
            Set<String> generated = new HashSet<>();
            derive(startSymbol, "", 0, maxSteps, generated);
            for (String s : generated) {
                System.out.println(s);
            }
        }

        // Método recursivo para derivar cadenas
        private void derive(String current, String derived, int step, int maxSteps, Set<String> generated) {
            if (step > maxSteps) return; // Limitar la profundidad de las derivaciones

            boolean expanded = false;
            for (Map.Entry<String, List<String>> entry : productions.entrySet()) {
                String nonTerminal = entry.getKey();
                if (current.contains(nonTerminal)) {
                    for (String production : entry.getValue()) {
                        String newCurrent = current.replaceFirst(nonTerminal, production);
                        derive(newCurrent, derived + production, step + 1, maxSteps, generated);
                        expanded = true;
                    }
                }
            }
            if (!expanded && !generated.contains(current)) {
                generated.add(current);
            }
        }

        // Verificar si el texto dado es derivable por la gramática
        public boolean isDerivable(String input) {
            // Utilizamos un enfoque de backtracking para verificar si la cadena se puede derivar
            return canDerive(input, "S", 0, input.length());
        }

        // Método recursivo para comprobar si la cadena es derivable
        private boolean canDerive(String input, String current, int start, int length) {
            if (start == length && current.isEmpty()) return true;

            for (Map.Entry<String, List<String>> entry : productions.entrySet()) {
                String nonTerminal = entry.getKey();
                if (current.startsWith(nonTerminal)) {
                    for (String production : entry.getValue()) {
                        if (canDerive(input, current.replaceFirst(nonTerminal, production), start, length)) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }

    // Método principal (main) que inicializa el programa
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        String input;
        String continueOption = "sí"; // Inicializamos con "sí" para entrar en el ciclo
        int maxSteps = 5; // Número máximo de derivaciones para generar el lenguaje

        // Añadimos algunas producciones iniciales
        cfg.addProduction("S", "aSb");
        cfg.addProduction("S", "");

        // Bucle para permitir entradas continuas
        while (continueOption.equalsIgnoreCase("sí") || continueOption.equalsIgnoreCase("s")) {
            System.out.println("Introduce una opción: \n1. Derivar cadenas \n2. Verificar si una cadena es derivable \n3. Añadir nuevas reglas de producción");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.println("Introduce el símbolo de inicio (por defecto 'S'):");
                    String startSymbol = scanner.nextLine();
                    if (startSymbol.isEmpty()) startSymbol = "S";
                    System.out.println("¿Cuántos pasos máximos para generar el lenguaje?");
                    if (scanner.hasNextInt()) {
                        maxSteps = scanner.nextInt();
                        scanner.nextLine(); // Limpiar buffer
                        System.out.println("Generando cadenas derivadas desde el símbolo '" + startSymbol + "' en " + maxSteps + " pasos:");
                        cfg.generateLanguage(startSymbol, maxSteps);
                    } else {
                        System.out.println("Entrada no válida. Se utilizarán los pasos máximos por defecto: 5.");
                        scanner.nextLine(); // Limpiar buffer por si hay caracteres no numéricos
                    }
                    break;

                case "2":
                    System.out.println("Introduce una cadena para verificar si es derivable:");
                    input = scanner.nextLine();
                    if (cfg.isDerivable(input)) {
                        System.out.println("La cadena '" + input + "' es derivable por la gramática.");
                    } else {
                        System.out.println("La cadena '" + input + "' no es derivable por la gramática.");
                    }
                    break;

                case "3":
                    System.out.println("Introduce un nuevo símbolo no terminal:");
                    String nonTerminal = scanner.nextLine();
                    System.out.println("Introduce la producción para '" + nonTerminal + "':");
                    String production = scanner.nextLine();
                    cfg.addProduction(nonTerminal, production);
                    System.out.println("Regla añadida: " + nonTerminal + " → " + production);
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }

            // Preguntar si el usuario desea ingresar otra opción
            System.out.println("¿Deseas ingresar otra opción? (sí/no):");
            continueOption = scanner.nextLine().trim(); // Usamos .trim() para eliminar espacios adicionales
        }

        System.out.println("Fin del programa.");
        scanner.close();
    }
}

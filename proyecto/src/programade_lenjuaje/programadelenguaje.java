package programade_lenjuaje;
import java.util.*;

public class programadelenguaje {

	  // Clase para las reglas de producción
    static class Production{
    	String lhs; // Lado izquierdo (no terminal)
        String rhs; // Lado derecho (producción)

        public Production(String lhs, String rhs) {
            this.lhs = lhs;
            this.rhs = rhs;

        }
        @Override
        
        public String toString() {
            return lhs + " -> " + rhs;
        }
    }
    // Clase para Gramática Regular
    static class RegularGrammar{
        private List<Production> productions = new ArrayList<>();
        private Set<String> terminals = new HashSet<>();
        private Set<String> nonTerminals = new HashSet<>();
        public void addProduction(String lhs, String rhs) {
            nonTerminals.add(lhs);
            for (char c : rhs.toCharArray()) {
                if (Character.isLowerCase(c)) {
                    terminals.add(String.valueOf(c));
                }else if (Character.isUpperCase(c)){
                    nonTerminals.add(String.valueOf(c));
                }
            }
            productions.add(new Production(lhs, rhs));
        }
        public void generateLanguage(String startSymbol, int maxLength) {
            Set<String> generatedStrings = new HashSet<>();
            Queue<String> queue = new LinkedList<>();
            queue.add(startSymbol);
            while (!queue.isEmpty()) {
                String current = queue.poll();
                if (current.length() > maxLength) {
                	continue;
                	
                }
                if (isTerminalString(current)) {
                    generatedStrings.add(current);
                }else {
                	for (Production p : productions) {
                        if (current.contains(p.lhs)) {
                            String newString = current.replaceFirst(p.lhs, p.rhs);
                            queue.add(newString);

                        }
                	}
                }
           }
            System.out.println("Generated Language: " + generatedStrings);
        }
        private boolean isTerminalString(String str) {
            for (char c : str.toCharArray()) {
            	if (Character.isUpperCase(c)) {
                    return false;
            	}
            }
            return true;
        }
    }

    // Clase para Gramática Independiente del Contexto
    static class ContextFreeGrammar {
        private List<Production> productions = new ArrayList<>();
        private Set<String> terminals = new HashSet<>();
        private Set<String> nonTerminals = new HashSet<>();
        
        public void addProduction(String lhs, String rhs) {
            nonTerminals.add(lhs);
            for (char c : rhs.toCharArray()) {
            	if (Character.isLowerCase(c)) {
                    terminals.add(String.valueOf(c));
            	}else if (Character.isUpperCase(c)) {
            		nonTerminals.add(String.valueOf(c));
            	}
            }
            productions.add(new Production(lhs, rhs));
        }
        public void generateLanguage(String startSymbol, int maxLength) {
        	Set<String> generatedStrings = new HashSet<>();
            Queue<String> queue = new LinkedList<>();
            queue.add(startSymbol);

            while (!queue.isEmpty()) {
                String current = queue.poll();
                if (current.length() > maxLength) {
                	continue;
                }
                if (isTerminalString(current)) {
                    generatedStrings.add(current);
                }else {
                    for (Production p : productions) {
                        if (current.contains(p.lhs)) {
                            String newString = current.replaceFirst(p.lhs, p.rhs);
                            queue.add(newString);

                        }
                    }
                }
            }
            System.out.println("Generated Language: " + generatedStrings);
        }
        private boolean isTerminalString(String str) {
        	for (char c : str.toCharArray()) {
                if (Character.isUpperCase(c)) {
                	return false;
                }
        	}
        	return true;
        }
    }
    // Método principal
    public static void main(String[] args) {
        // Gramática Regular (Reglas de producción)
        RegularGrammar rg = new RegularGrammar();
        rg.addProduction("S", "aA");
        rg.addProduction("A", "b");
        rg.addProduction("A", "a");

        System.out.println("Regular Grammar Language Generation:");
        rg.generateLanguage("S", 5);

        // Gramática Independiente del Contexto (Reglas de producción)
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction("S", "aSb");
        cfg.addProduction("S", "");

        System.out.println("\nContext-Free Grammar Language Generation:");
        cfg.generateLanguage("S", 5);

    }

}

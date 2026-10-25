import java.util.Arrays;

/**
 * Atividade de POO: escreva os testes unitários desta classe usando JUnit.
 * Cada método descreve suas regras de entrada e saída.
 * Nos métodos que dividem palavras, espaços, tabulações e quebras de linha
 * separam palavras; a saída usa um espaço entre elas e nenhum nas extremidades.
 * A pontuação permanece ligada à palavra. Considere caracteres do alfabeto
 * latino, incluindo letras acentuadas, e dígitos de 0 a 9.
 */
public class ConversorDeTexto {

    /**
     * Troca maiúsculas por minúsculas e minúsculas por maiúsculas.
     * Preserva espaços, números e pontuação. Aceita texto vazio ou em branco.
     * Exemplo: "Java 17!" retorna "jAVA 17!".
     * @throws IllegalArgumentException se texto for nulo
     */
    public String trocarMaiusculasMinusculas(String texto) {
        if (texto == null) {
            throw new IllegalArgumentException("Texto não deve ser nulo");
        }
        StringBuilder resultado = new StringBuilder();
        for (char c : texto.toCharArray()) {
            if (Character.isUpperCase(c)) {
                resultado.append(Character.toLowerCase(c));
            } else if (Character.isLowerCase(c)) {
                resultado.append(Character.toUpperCase(c));
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    /**
     * Ordena palavras sem diferenciar maiúsculas de minúsculas.
     * Preserva a grafia e as repetições. Palavras iguais nessa comparação
     * mantêm sua ordem relativa. Acentos não são removidos; a ordenação segue
     * String.CASE_INSENSITIVE_ORDER, sem regras de dicionário português.
     * Exemplo: "uva Banana abacaxi" retorna "abacaxi Banana uva".
     * @throws IllegalArgumentException se texto for nulo, vazio ou em branco
     */
    public String ordenarPalavras(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("Informe um texto com palavras");
        }
        String[] palavras = texto.trim().split("\\s+");
        Arrays.sort(palavras, String.CASE_INSENSITIVE_ORDER);
        return String.join(" ", palavras);
    }

    /**
     * Conta os dígitos de 0 a 9, inclusive os repetidos.
     * Texto vazio ou sem dígitos retorna zero.
     * Exemplo: "Sala 203, bloco 4" retorna 4.
     * @throws IllegalArgumentException se texto for nulo
     */
    public int contarDigitos(String texto) {
        if (texto == null) {
            throw new IllegalArgumentException("Texto não deve ser nulo");
        }
        int contador = 0;
        for (char c : texto.toCharArray()) {
            if (c >= '0' && c <= '9') {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Verifica se o primeiro e o último caractere são letras iguais,
     * ignorando maiúsculas e minúsculas e espaços nas extremidades (trim).
     * Retorna false para nulo, vazio, branco ou extremidades que não são letras.
     * Uma letra isolada retorna true. Não remove pontuação nem acentos.
     * Exemplos: "Amora" retorna true; "java" e "arara!" retornam false.
     */
    public boolean comecaETerminaComMesmaLetra(String texto) {
        if (texto == null || texto.isBlank()) {
            return false;
        }
        String limpo = texto.trim();
        char primeira = limpo.charAt(0);
        char ultima = limpo.charAt(limpo.length() - 1);
        return Character.isLetter(primeira) && Character.isLetter(ultima)
                && Character.toLowerCase(primeira) == Character.toLowerCase(ultima);
    }

    /**
     * Mantém as primeiras quantidadeMaxima palavras.
     * Retorna todas quando o limite excede a quantidade existente.
     * Limite zero ou texto vazio/em branco retorna texto vazio.
     * Exemplo: ("hoje temos aula de Java", 3) retorna "hoje temos aula".
     * @throws IllegalArgumentException se texto for nulo ou o limite negativo
     */
    public String limitarPalavras(String texto, int quantidadeMaxima) {
        if (texto == null || quantidadeMaxima < 0) {
            throw new IllegalArgumentException("Texto nulo ou limite negativo");
        }
        if (texto.isBlank() || quantidadeMaxima == 0) {
            return "";
        }
        String[] palavras = texto.trim().split("\\s+");
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < palavras.length && i < quantidadeMaxima; i++) {
            resultado.append(palavras[i]).append(" ");
        }
        return resultado.toString().trim();
    }

    /**
     * Substitui cada dígito de 0 a 9 pelo caractere informado.
     * Preserva os demais caracteres. Aceita qualquer caractere substituto.
     * Texto vazio retorna texto vazio; espaços são preservados.
     * Exemplo: ("RA 123-4", '*') retorna "RA ***-*".
     * @throws IllegalArgumentException se texto for nulo
     */
    public String mascararDigitos(String texto, char substituto) {
        if (texto == null) {
            throw new IllegalArgumentException("Texto não deve ser nulo");
        }
        StringBuilder resultado = new StringBuilder();
        for (char c : texto.toCharArray()) {
            resultado.append(c >= '0' && c <= '9' ? substituto : c);
        }
        return resultado.toString();
    }

    /**
     * Conta palavras cujo primeiro caractere corresponde à inicial informada,
     * ignorando maiúsculas e minúsculas. Acentos permanecem distintos.
     * Retorna zero para texto nulo, vazio ou em branco.
     * A inicial aceita qualquer caractere, inclusive número ou pontuação.
     * Exemplo: ("Java e JavaScript no jardim", 'j') retorna 3.
     */
    public int contarPalavrasComInicial(String texto, char inicial) {
        if (texto == null || texto.isBlank()) {
            return 0;
        }
        int contador = 0;
        String[] palavras = texto.trim().split("\\s+");
        for (String palavra : palavras) {
            if (Character.toLowerCase(palavra.charAt(0))
                    == Character.toLowerCase(inicial)) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Move o primeiro caractere de cada palavra para o final dela.
     * Mantém a ordem das palavras, maiúsculas e minúsculas.
     * Uma palavra de um caractere permanece igual. Pontuação também é movida.
     * Exemplo: "java e legal" retorna "avaj e egall".
     * @throws IllegalArgumentException se texto for nulo, vazio ou em branco
     */
    public String moverPrimeiraLetraParaFinal(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("Informe um texto com palavras");
        }
        StringBuilder resultado = new StringBuilder();
        String[] palavras = texto.trim().split("\\s+");
        for (String palavra : palavras) {
            resultado.append(palavra.substring(1))
                    .append(palavra.charAt(0)).append(" ");
        }
        return resultado.toString().trim();
    }
}

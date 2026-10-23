import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConversorDeTextoTest {

    private ConversorDeTexto conversor;

    @BeforeEach
    void setup() {
        // Instancia a classe sob teste antes de cada caso
        conversor = new ConversorDeTexto();
    }

    // -------------------------
    // Teste - capitalizarPalavras
    // -------------------------

    @Test
    void capitalizarPalavrasTest() {
        // String
        String entrada = "java é divertido";
        //ação
        String resultado = conversor.capitalizarPalavras(entrada);
        // Assert
        assertEquals("Java É Divertido", resultado);
    }

    @Test
    void capitalizarPalavrasExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> conversor.capitalizarPalavras(null));
        assertThrows(IllegalArgumentException.class, () -> conversor.capitalizarPalavras("   "));
    }

    // -------------------------
    // Teste - inverterPalavras
    // -------------------------

    @Test
    void inverterPalavrasTest() {
        // String
        String entrada = "ola mundo bonito";
        //ação
        String resultado = conversor.inverterPalavras(entrada);
        // Assert
        assertEquals("bonito mundo ola", resultado);
    }

    @Test
    void inverterPalavrasExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> conversor.inverterPalavras(null));
        assertThrows(IllegalArgumentException.class, () -> conversor.inverterPalavras("   "));
    }

    // -------------------------
    // Teste - contarVogais
    // -------------------------

    @Test
    void contarVogaisTest() {
        // String
        String entrada1 = "BanAna";
        String entrada2 = "rhythms";
        //ação
        int qtd1 = conversor.contarVogais(entrada1); //deve retornar 3 vogais
        int qtd2 = conversor.contarVogais(entrada2);  // deve retornar 0 vogais
        // assertAll permite executar varios assertEquals simultaneamente
        assertAll(
            ()->assertEquals(3, qtd1),
            ()->assertEquals(0, qtd2),
            ()->assertThrows(IllegalArgumentException.class,()->conversor.contarVogais(null)) //assertThrows pede 2 argumentos
        );
    }

    // -------------------------
    // Teste - ehPalindromo
    // -------------------------

    @Test
    void ehPalindromo() {
        String entrada = "arara";
        Boolean resultado = conversor.ehPalindromo(entrada);

        assertEquals(true, resultado);
    }

    @Test
    void ehPalindromoFalseTest() {
        assertFalse(conversor.ehPalindromo("java"));
        assertFalse(conversor.ehPalindromo(null));
        assertFalse(conversor.ehPalindromo("   "));
    }

    // ------------------------------
    // Teste - removerPalavrasCurtas
    // ------------------------------

    @Test
    void removerPalavrasCurtas() {
        String entradaText = "hoje é um lindo dia";
        int entradaNum = 3;
        String resultado = conversor.removerPalavrasCurtas(entradaText, entradaNum);

        assertEquals("hoje lindo dia", resultado);
    }

    @Test
    void removerPalavrasCurtasExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> conversor.removerPalavrasCurtas(null, 3));
    }

    // ------------------------------
    // Teste - substituirPalavra
    // ------------------------------

    @Test
    void substituirPalavra() {
        String entradaTexto = "java é legal";
        String entradaAlvo = "java";
        String entradaNova = "python";

        String resultado = conversor.substituirPalavra(entradaTexto, entradaAlvo, entradaNova);

        assertEquals("python é legal", resultado);
    }

    @Test
    void substituirPalavraExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> conversor.substituirPalavra(null, "java", "python"));
        assertThrows(IllegalArgumentException.class, () -> conversor.substituirPalavra("java é legal", null, "python"));
        assertThrows(IllegalArgumentException.class, () -> conversor.substituirPalavra("java é legal", "java", null));
    }

    // ------------------------------
    // Teste - contarPalavras
    // ------------------------------

    @Test
    void contarPalavras() {
        String entrada = "Java é muito legal";
        int resultado = conversor.contarPalavras(entrada);

        assertEquals(4, resultado);
    }

    @Test
    void contarPalavrasVazioTest() {
        assertEquals(0, conversor.contarPalavras(null));
        assertEquals(0, conversor.contarPalavras("   "));
    }

    // ------------------------------
    // Teste - inverterLetrasPorPalavra
    // ------------------------------

    @Test
    void inverterLetrasPorPalavra() {
        String entrada = "java é legal";
        String resultado = conversor.inverterLetrasPorPalavra(entrada);

        assertEquals("avaj é lagel", resultado);
    }

    @Test
    void inverterLetrasPorPalavraExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> conversor.inverterLetrasPorPalavra(null));
        assertThrows(IllegalArgumentException.class, () -> conversor.inverterLetrasPorPalavra("   "));
    }
}
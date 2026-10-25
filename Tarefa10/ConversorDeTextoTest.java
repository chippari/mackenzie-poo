import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConversorDeTextoTest {
    private ConversorDeTexto conversor;

    @BeforeEach
    void setup() {
        conversor = new ConversorDeTexto();
    }

    // -----------------------------------
    // Teste - trocarMaiusculasMinusculas
    // -----------------------------------
    @Test
    void trocarMaiusculasMinusculas() {
        String entrada = "Java 17!";
        String resultado = conversor.trocarMaiusculasMinusculas(entrada);

        assertEquals("jAVA 17!", resultado);
    }

    @Test
    void trocarMaiusculasMinusculasExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> conversor.trocarMaiusculasMinusculas(null));
    }

    // -----------------------------------
    // Teste - ordenarPalavras
    // -----------------------------------
    @Test
    void ordenarPalavras() {
        String entrada = "uva Banana abacaxi";
        String resultado = conversor.ordenarPalavras(entrada);

        assertEquals("abacaxi Banana uva", resultado);
    }

    @Test
    void ordenarPalavrasExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> conversor.ordenarPalavras(null));
        assertThrows(IllegalArgumentException.class, () -> conversor.ordenarPalavras("   "));
    }

    // -----------------------------------
    // Teste - contarDigitos
    // -----------------------------------
    @Test
    void contarDigitos() {
        String entrada = "Sala 203, bloco 4";
        int resultado = conversor.contarDigitos(entrada);

        assertEquals(4, resultado);
    }

    @Test
    void contarDigitosExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> conversor.contarDigitos(null));
    }

    // -----------------------------------
    // Teste - comecaETerminaComMesmaLetra
    // -----------------------------------
    @Test
    void comecaETerminaComMesmaLetra() {
        String entrada = "Amora";
        Boolean resultado = conversor.comecaETerminaComMesmaLetra(entrada);

        assertEquals(true, resultado);
    }

    @Test
    void comecaETerminaComMesmaLetraFalseTest() {
        assertFalse(conversor.comecaETerminaComMesmaLetra("java"));
        assertFalse(conversor.comecaETerminaComMesmaLetra("arara!"));
        assertFalse(conversor.comecaETerminaComMesmaLetra(null));
        assertFalse(conversor.comecaETerminaComMesmaLetra("   "));
    }

    // -----------------------------------
    // Teste - limitarPalavras
    // -----------------------------------
    @Test
    void limitarPalavras() {
        String entradaText = "hoje temos aula de Java";
        int entradaNum = 3;
        String resultado = conversor.limitarPalavras(entradaText, entradaNum);
        
        assertEquals("hoje temos aula", resultado);
    }

    @Test
    void limitarPalavrasExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> conversor.limitarPalavras(null, 3));
        assertThrows(IllegalArgumentException.class, () -> conversor.limitarPalavras("hoje temos aula", -1));
    }

    // -----------------------------------
    // Teste - mascararDigitos
    // -----------------------------------
    @Test
    void mascararDigitos() {
        String entradaText = "RA 123-4";
        char entradaChar = '*';
        String resultado = conversor.mascararDigitos(entradaText, entradaChar);

        assertEquals("RA ***-*", resultado);
    }

    @Test
    void mascararDigitosExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> conversor.mascararDigitos(null, '*'));
    }

    // -----------------------------------
    // Teste - contarPalavrasComInicial
    // -----------------------------------
    @Test
    void contarPalavrasComInicial() {
        String entradaText = "Java e JavaScript no jardim";
        char entradaChar = 'j';
        int resultado = conversor.contarPalavrasComInicial(entradaText, entradaChar);

        assertEquals(3, resultado);
    }

    @Test
    void contarPalavrasComInicialVazioTest() {
        assertEquals(0, conversor.contarPalavrasComInicial(null, 'j'));
        assertEquals(0, conversor.contarPalavrasComInicial("   ", 'j'));
    }

    // -----------------------------------
    // Teste - moverPrimeiraLetraParaFinal
    // -----------------------------------
    @Test
    void moverPrimeiraLetraParaFinal() {
        String entrada = "java e legal";
        String resultado = conversor.moverPrimeiraLetraParaFinal(entrada);

        assertEquals("avaj e egall", resultado);
    }

    @Test
    void moverPrimeiraLetraParaFinalExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> conversor.moverPrimeiraLetraParaFinal(null));
        assertThrows(IllegalArgumentException.class, () -> conversor.moverPrimeiraLetraParaFinal("   "));
    }
}
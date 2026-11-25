import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class LojaVirtualTest {

    // -----------------------------------
    // Teste - Produto
    // -----------------------------------
    @Test
    void produtoGuardaSeusDados() {
        Produto caderno = new Produto("P01", "Caderno", 25.0, 0.4);

        assertAll(
            () -> assertEquals("P01", caderno.getCodigo(), "o código deve ser o informado"),
            () -> assertEquals("Caderno", caderno.getDescricao(), "a descrição deve ser a informada"),
            () -> assertEquals(25.0, caderno.getPreco(), 0.001, "o preço deve ser o informado"),
            () -> assertEquals(0.4, caderno.getPeso(), 0.001, "o peso deve ser o informado")
        );
    }

    @Test
    void produtosComMesmoCodigoSaoIguais() {
        Produto a = new Produto("P01", "Caderno", 25.0, 0.4);
        Produto b = new Produto("P01", "Caderno azul", 30.0, 0.5);
        Produto c = new Produto("P02", "Caneta", 5.0, 0.02);

        assertEquals(a, b, "produtos com o mesmo código devem ser iguais");
        assertNotEquals(a, c, "produtos com códigos diferentes devem ser diferentes");
    }

    @Test
    void produtosIguaisTemMesmoHashCode() {
        // preparar
        Produto a = new Produto("P01", "Caderno", 25.0, 0.4);
        Produto b = new Produto("P01", "Caderno azul", 30.0, 0.5);

        // executar
        int hashA = a.hashCode();
        int hashB = b.hashCode();

        // verificar
        assertEquals(hashA, hashB, "produtos iguais segundo equals devem ter o mesmo hashCode");
    }

    // -----------------------------------
    // Teste - Kit
    // -----------------------------------
    @Test
    void kitSomaTodosOsProdutosEAplicaDesconto() {
        // preparar
        Kit kit = new Kit("Escolar", 10.0);
        kit.adicionar(new Produto("P01", "Caderno", 25.0, 0.4));
        kit.adicionar(new Produto("P02", "Caneta", 5.0, 0.02));

        // executar
        double preco = kit.getPreco();

        // verificar
        assertEquals(27.0, preco, 0.001, "o kit deve somar todos os produtos (30,00) e aplicar 10% de desconto");
    }

    @Test
    void kitSomaOPesoDeTodosOsProdutos() {
        Kit kit = new Kit("Escolar", 10.0);
        kit.adicionar(new Produto("P01", "Caderno", 25.0, 0.4));
        kit.adicionar(new Produto("P02", "Caneta", 5.0, 0.02));

        assertEquals(0.42, kit.getPeso(), 0.001, "o peso do kit deve ser a soma dos pesos");
    }

    @Test
    void kitTemDescricaoComNome() {
        Kit kit = new Kit("Escolar", 10.0);

        assertEquals("Kit Escolar", kit.getDescricao(), "a descrição deve ser 'Kit ' + nome");
    }

    // -----------------------------------
    // Teste - ItemComAdicionais
    // -----------------------------------
    @Test
    void itemSemAdicionaisMantemPrecoDoProduto() {
        Produto mochila = new Produto("P03", "Mochila", 120.0, 0.9);
        ItemComAdicionais item = new ItemComAdicionais(mochila, false, false, false);

        assertEquals(120.0, item.getPreco(), 0.001, "sem adicionais o preço deve ser o do produto");
    }

    @Test
    void itemComEmbalagemEGarantiaCalculaGarantiaSobreEmbalagem() {
        Produto mochila = new Produto("P03", "Mochila", 120.0, 0.9);
        ItemComAdicionais item = new ItemComAdicionais(mochila, true, true, false);

        assertEquals(137.5, item.getPreco(), 0.001, "(120 + 5) + 10% de garantia deve dar 137,50");
    }

    @Test
    void itemComTodosAdicionaisSomaSeguro() {
        Produto mochila = new Produto("P03", "Mochila", 120.0, 0.9);
        ItemComAdicionais item = new ItemComAdicionais(mochila, true, true, true);

        assertEquals(145.5, item.getPreco(), 0.001, "(120 + 5) + 10% + 8 de seguro deve dar 145,50");
    }

    @Test
    void itemMantemPesoDoProduto() {
        Produto mochila = new Produto("P03", "Mochila", 120.0, 0.9);
        ItemComAdicionais item = new ItemComAdicionais(mochila, true, true, true);

        assertEquals(0.9, item.getPeso(), 0.001, "os adicionais não mudam o peso");
    }

    @Test
    void itemDescreveOsAdicionais() {
        Produto mochila = new Produto("P03", "Mochila", 120.0, 0.9);
        ItemComAdicionais item = new ItemComAdicionais(mochila, true, true, true);

        assertEquals("Mochila + embalagem + garantia + seguro", item.getDescricao(),
            "a descrição deve listar os adicionais");
    }

    // -----------------------------------
    // Teste - Cupom e CentralDeCupons
    // -----------------------------------
    @Test
    void cupomGuardaSeusDados() {
        Cupom cupom = new Cupom("VOLTA10", 10.0);

        assertEquals("VOLTA10", cupom.getCodigo(), "o código deve ser o informado");
        assertEquals(10.0, cupom.getPercentual(), 0.001, "o percentual deve ser o informado");
    }

    @Test
    void centralEncontraCupomComCodigoDigitado() {
        // preparar
        CentralDeCupons central = new CentralDeCupons();
        Cupom cupom = new Cupom("VOLTA10", 10.0);
        central.cadastrar(cupom);
        String codigoDigitado = new String("VOLTA10");

        // executar
        Cupom encontrado = central.buscar(codigoDigitado);

        // verificar
        assertSame(cupom, encontrado, "o cupom deve ser encontrado pelo conteúdo do código digitado");
    }

    @Test
    void centralDevolveNullParaCupomInexistente() {
        CentralDeCupons central = new CentralDeCupons();
        central.cadastrar(new Cupom("VOLTA10", 10.0));

        assertNull(central.buscar("NATAL20"), "cupom inexistente deve devolver null");
    }

    // -----------------------------------
    // Teste - Estoque
    // -----------------------------------
    @Test
    void estoqueSomaQuantidadeAoSaldo() {
        Estoque estoque = new Estoque();
        Produto caneta = new Produto("P02", "Caneta", 5.0, 0.02);

        estoque.adicionar(caneta, 50);
        estoque.adicionar(caneta, 10);

        assertEquals(60, estoque.getSaldo(caneta), "o saldo deve acumular as entradas");
    }

    @Test
    void estoqueDevolveZeroParaProdutoNovo() {
        Estoque estoque = new Estoque();
        Produto caneta = new Produto("P02", "Caneta", 5.0, 0.02);

        assertEquals(0, estoque.getSaldo(caneta), "produto que nunca entrou deve ter saldo zero");
    }

    @Test
    void estoqueCompartilhaSaldoEntreProdutosComMesmoCodigo() {
        // preparar
        Estoque estoque = new Estoque();
        estoque.adicionar(new Produto("P02", "Caneta", 5.0, 0.02), 10);
        Produto mesmaCaneta = new Produto("P02", "Caneta", 5.0, 0.02);

        // executar
        int saldo = estoque.getSaldo(mesmaCaneta);

        // verificar
        assertEquals(10, saldo, "produtos com o mesmo código devem compartilhar o saldo");
    }

    @Test
    void baixarRetiraQuantidadeDoSaldo() {
        // preparar
        Estoque estoque = new Estoque();
        Produto caneta = new Produto("P02", "Caneta", 5.0, 0.02);
        estoque.adicionar(caneta, 50);

        // executar
        estoque.baixar(caneta, 1);

        // verificar
        assertEquals(49, estoque.getSaldo(caneta), "depois de baixar 1 de 50, o saldo deve ser 49");
    }

    @Test
    void baixarComSaldoInsuficienteLancaExcecao() {
        Estoque estoque = new Estoque();
        Produto caneta = new Produto("P02", "Caneta", 5.0, 0.02);
        estoque.adicionar(caneta, 2);

        assertThrows(IllegalStateException.class, () -> estoque.baixar(caneta, 3),
            "baixar mais do que o saldo deve lançar exceção");
    }

    // -----------------------------------
    // Teste - Pedido
    // -----------------------------------
    @Test
    void pedidoGuardaOsItensAdicionados() {
        Pedido pedido = new Pedido("PAC");
        Produto caneta = new Produto("P02", "Caneta", 5.0, 0.02);
        Kit kit = new Kit("Escolar", 10.0);
        ItemComAdicionais item = new ItemComAdicionais(caneta, true, false, false);

        pedido.adicionar(caneta);
        pedido.adicionar(kit);
        pedido.adicionar(item);

        assertEquals(List.of(caneta, kit, item), pedido.getItens(), "o pedido deve guardar os três itens");
    }

    @Test
    void listaDeItensNaoAceitaAlteracaoExterna() {
        // preparar
        Pedido pedido = new Pedido("PAC");
        Produto caneta = new Produto("P02", "Caneta", 5.0, 0.02);
        List<Object> itens = pedido.getItens();

        // executar e verificar
        assertThrows(UnsupportedOperationException.class, () -> itens.add(caneta),
            "a lista devolvida por getItens() não deve aceitar alterações");
    }

    @Test
    void pedidoFinalizadoNaoAceitaItens() {
        Pedido pedido = new Pedido("PAC");
        pedido.finalizar();

        assertTrue(pedido.isFinalizado(), "o pedido deve ficar finalizado");
        assertThrows(IllegalStateException.class,
            () -> pedido.adicionar(new Produto("P02", "Caneta", 5.0, 0.02)),
            "pedido finalizado não deve aceitar novos itens");
    }

    @Test
    void subtotalSomaTodosOsTiposDeItem() {
        Pedido pedido = new Pedido("PAC");
        pedido.adicionar(new Produto("P02", "Caneta", 5.0, 0.02));
        pedido.adicionar(new ItemComAdicionais(new Produto("P03", "Mochila", 120.0, 0.9), true, false, false));

        assertEquals(130.0, pedido.calcularSubtotal(), 0.001, "5 + (120 + 5) deve dar 130,00");
    }

    @Test
    void pesoTotalSomaTodosOsItens() {
        Pedido pedido = new Pedido("PAC");
        pedido.adicionar(new Produto("P01", "Caderno", 25.0, 0.4));
        pedido.adicionar(new ItemComAdicionais(new Produto("P03", "Mochila", 120.0, 0.9), true, false, false));

        assertEquals(1.3, pedido.calcularPesoTotal(), 0.001, "0,4 + 0,9 deve dar 1,3 kg");
    }

    @Test
    void fretePacCobraFixoMaisPorKg() {
        Pedido pedido = new Pedido("PAC");
        pedido.adicionar(new Produto("P01", "Caderno", 25.0, 0.4));
        pedido.adicionar(new Produto("P03", "Mochila", 120.0, 0.9));

        assertEquals(17.6, pedido.calcularFrete(), 0.001, "PAC: 15 + 1,3 kg x 2 deve dar 17,60");
    }

    @Test
    void freteSedexCobraFixoMaisPorKg() {
        Pedido pedido = new Pedido("SEDEX");
        pedido.adicionar(new Produto("P01", "Caderno", 25.0, 0.4));

        assertEquals(26.6, pedido.calcularFrete(), 0.001, "SEDEX: 25 + 0,4 kg x 4 deve dar 26,60");
    }

    @Test
    void freteTransportadoraEGratisAPartirDe300() {
        // preparar
        Pedido pedido = new Pedido("TRANSPORTADORA");
        pedido.adicionar(new Produto("P10", "Monitor", 300.0, 5.0));

        // executar
        double frete = pedido.calcularFrete();

        // verificar
        assertEquals(0.0, frete, 0.001, "com subtotal de exatamente 300,00 o frete deve ser grátis");
    }

    @Test
    void freteTransportadoraCobra40AbaixoDe300() {
        Pedido pedido = new Pedido("TRANSPORTADORA");
        pedido.adicionar(new Produto("P10", "Monitor", 299.99, 5.0));

        assertEquals(40.0, pedido.calcularFrete(), 0.001, "abaixo de 300,00 o frete deve ser 40,00");
    }

    @Test
    void freteInvalidoLancaExcecao() {
        Pedido pedido = new Pedido("DRONE");

        assertThrows(IllegalArgumentException.class, () -> pedido.calcularFrete(),
            "tipo de frete desconhecido deve lançar exceção");
    }

    @Test
    void totalAplicaCupomNoSubtotalESomaFrete() {
        Pedido pedido = new Pedido("SEDEX");
        pedido.adicionar(new Produto("P01", "Caderno", 25.0, 0.4));
        pedido.aplicarCupom(new Cupom("VOLTA10", 10.0));

        assertEquals(49.1, pedido.calcularTotal(), 0.001, "25 - 10% + frete 26,60 deve dar 49,10");
    }

    @Test
    void totalSemCupomESubtotalMaisFrete() {
        Pedido pedido = new Pedido("SEDEX");
        pedido.adicionar(new Produto("P01", "Caderno", 25.0, 0.4));

        assertEquals(51.6, pedido.calcularTotal(), 0.001, "25 + frete 26,60 deve dar 51,60");
    }
}
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LojaVirtualMelhoriasTest {

    // -----------------------------------
    // M1 - Strategy: nova forma de frete
    // -----------------------------------
    @Test
    void novaFormaDeFreteEntraSemMudarPedido() {
        // preparar
        Pedido pedido = new Pedido(new RetiradaNaLoja());
        pedido.adicionar(new Produto("P01", "Caderno", 25.0, 0.4));

        // executar
        double total = pedido.calcularTotal();

        // verificar
        assertEquals(25.0, total, 0.001, "com retirada na loja o total deve ser só o subtotal");
    }

    // -----------------------------------
    // M2 - Composite: kit dentro de kit
    // -----------------------------------
    @Test
    void kitAceitaOutroKitEItemComAdicionais() {
        // preparar
        Kit kitEscrita = new Kit("Escrita", 0.0);
        kitEscrita.adicionar(new Produto("P01", "Caderno", 25.0, 0.4));
        kitEscrita.adicionar(new Produto("P02", "Caneta", 5.0, 0.02));
        Kit kitCompleto = new Kit("Completo", 10.0);
        kitCompleto.adicionar(kitEscrita);
        kitCompleto.adicionar(new ItemComAdicionais(new Produto("P03", "Mochila", 120.0, 0.9), true, false, false));

        // executar
        double preco = kitCompleto.getPreco();
        double peso = kitCompleto.getPeso();

        // verificar
        assertEquals(139.5, preco, 0.001, "(30 + 125) com 10% de desconto deve dar 139,50");
        assertEquals(1.32, peso, 0.001, "o peso deve somar os itens de todos os níveis");
    }

    // -----------------------------------
    // M3 - Decorator: novo adicional
    // -----------------------------------
    @Test
    void novoAdicionalEntraSemMudarClassesExistentes() {
        // preparar
        ItemPedido mochila = new Produto("P03", "Mochila", 120.0, 0.9);
        ItemPedido presente = new CartaoPresente(new EmbalagemPresente(mochila));

        // executar
        double preco = presente.getPreco();
        String descricao = presente.getDescricao();

        // verificar
        assertEquals(128.0, preco, 0.001, "120 + 5 de embalagem + 3 de cartão deve dar 128,00");
        assertEquals("Mochila + embalagem + cartão", descricao, "a descrição deve listar os adicionais");
    }
}

class RetiradaNaLoja implements EstrategiaFrete {
    @Override
    public double calcular(double pesoTotal, double subtotal) {
        return 0.0;
    }
}

class CartaoPresente extends Adicional {
    CartaoPresente(ItemPedido item) {
        super(item);
    }

    @Override
    public double getPreco() {
        return item.getPreco() + 3.0;
    }

    @Override
    public String getDescricao() {
        return item.getDescricao() + " + cartão";
    }
}

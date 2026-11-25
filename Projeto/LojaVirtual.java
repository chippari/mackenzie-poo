import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Loja virtual simplificada usada na atividade de auditoria de código.
 * Todas as classes estão neste arquivo para facilitar a leitura.
 */
public class LojaVirtual {

    public static void main(String[] args) {
        Produto caderno = new Produto("P01", "Caderno", 25.0, 0.4);
        Produto caneta = new Produto("P02", "Caneta", 5.0, 0.02);
        Produto mochila = new Produto("P03", "Mochila", 120.0, 0.9);

        Kit kitEscolar = new Kit("Escolar", 10.0);
        kitEscolar.adicionar(caderno);
        kitEscolar.adicionar(caneta);

        ItemComAdicionais mochilaPresente = new ItemComAdicionais(mochila, true, true, false);

        Pedido pedido = new Pedido("SEDEX");
        pedido.adicionar(kitEscolar);
        pedido.adicionar(mochilaPresente);
        pedido.adicionar(caneta);

        CentralDeCupons central = new CentralDeCupons();
        central.cadastrar(new Cupom("VOLTA10", 10.0));
        pedido.aplicarCupom(central.buscar("VOLTA10"));

        Estoque estoque = new Estoque();
        estoque.adicionar(caneta, 50);
        estoque.baixar(caneta, 1);

        System.out.println("Item: " + mochilaPresente.getDescricao());
        System.out.printf("Subtotal: R$ %.2f%n", pedido.calcularSubtotal());
        System.out.printf("Frete:    R$ %.2f%n", pedido.calcularFrete());
        System.out.printf("Total:    R$ %.2f%n", pedido.calcularTotal());
        System.out.println("Saldo de canetas: " + estoque.getSaldo(caneta));
    }
}

/**
 * Qualquer coisa que pode entrar em um pedido ou em um kit:
 * produto, kit ou produto com adicionais.
 */
interface ItemPedido {
    double getPreco();

    /** Peso em quilogramas. */
    double getPeso();

    String getDescricao();
}

/**
 * Produto vendido individualmente.
 * Dois produtos são iguais quando têm o mesmo código.
 */
class Produto implements ItemPedido {
    private final String codigo;
    private final String descricao;
    private final double preco;
    private final double peso;

    Produto(String codigo, String descricao, double preco, double peso) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
        this.peso = peso;
    }

    String getCodigo() {
        return codigo;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public double getPreco() {
        return preco;
    }

    /** Peso em quilogramas. */
    @Override
    public double getPeso() {
        return peso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produto)) {
            return false;
        }
        Produto outro = (Produto) o;
        return codigo.equals(outro.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}

/**
 * Conjunto de itens vendido com desconto percentual.
 * Aceita produtos, outros kits e itens com adicionais.
 */
class Kit implements ItemPedido {
    private final String nome;
    private final double percentualDesconto;
    private final List<ItemPedido> itens = new ArrayList<>();

    Kit(String nome, double percentualDesconto) {
        this.nome = nome;
        this.percentualDesconto = percentualDesconto;
    }

    void adicionar(ItemPedido item) {
        itens.add(item);
    }

    /** Soma os preços de todos os itens do kit e aplica o desconto percentual. */
    @Override
    public double getPreco() {
        double soma = 0.0;
        for (ItemPedido item : itens) {
            soma += item.getPreco();
        }
        return soma * (1 - percentualDesconto / 100);
    }

    /** Soma os pesos de todos os itens do kit, em quilogramas. */
    @Override
    public double getPeso() {
        double soma = 0.0;
        for (ItemPedido item : itens) {
            soma += item.getPeso();
        }
        return soma;
    }

    @Override
    public String getDescricao() {
        return "Kit " + nome;
    }
}

/**
 * Base dos adicionais: envolve um item e repassa a ele
 * o que o adicional não muda.
 */
abstract class Adicional implements ItemPedido {
    protected final ItemPedido item;

    Adicional(ItemPedido item) {
        this.item = item;
    }

    @Override
    public double getPeso() {
        return item.getPeso();
    }
}

/** Embalagem para presente: R$ 5,00. */
class EmbalagemPresente extends Adicional {
    EmbalagemPresente(ItemPedido item) {
        super(item);
    }

    @Override
    public double getPreco() {
        return item.getPreco() + 5.0;
    }

    @Override
    public String getDescricao() {
        return item.getDescricao() + " + embalagem";
    }
}

/** Garantia estendida: 10% sobre o preço do item que ela envolve. */
class GarantiaEstendida extends Adicional {
    GarantiaEstendida(ItemPedido item) {
        super(item);
    }

    @Override
    public double getPreco() {
        return item.getPreco() * 1.10;
    }

    @Override
    public String getDescricao() {
        return item.getDescricao() + " + garantia";
    }
}

/** Seguro contra roubo: R$ 8,00. */
class SeguroRoubo extends Adicional {
    SeguroRoubo(ItemPedido item) {
        super(item);
    }

    @Override
    public double getPreco() {
        return item.getPreco() + 8.0;
    }

    @Override
    public String getDescricao() {
        return item.getDescricao() + " + seguro";
    }
}

/**
 * Produto vendido com adicionais opcionais:
 * embalagem para presente (R$ 5,00), garantia estendida
 * (10% sobre o preço do produto já somado à embalagem, quando houver)
 * e seguro contra roubo (R$ 8,00).
 * Monta os adicionais como decoradores, nessa ordem.
 * Novos adicionais podem ser usados direto, envolvendo o item.
 */
class ItemComAdicionais implements ItemPedido {
    private final ItemPedido item;

    ItemComAdicionais(Produto produto, boolean embalagem, boolean garantia, boolean seguro) {
        ItemPedido montado = produto;
        if (embalagem) {
            montado = new EmbalagemPresente(montado);
        }
        if (garantia) {
            montado = new GarantiaEstendida(montado);
        }
        if (seguro) {
            montado = new SeguroRoubo(montado);
        }
        this.item = montado;
    }

    @Override
    public double getPreco() {
        return item.getPreco();
    }

    @Override
    public double getPeso() {
        return item.getPeso();
    }

    @Override
    public String getDescricao() {
        return item.getDescricao();
    }
}

/** Cupom de desconto percentual sobre o subtotal do pedido. */
class Cupom {
    private final String codigo;
    private final double percentual;

    Cupom(String codigo, double percentual) {
        this.codigo = codigo;
        this.percentual = percentual;
    }

    String getCodigo() {
        return codigo;
    }

    double getPercentual() {
        return percentual;
    }
}

/** Cadastro dos cupons válidos da loja. */
class CentralDeCupons {
    private final List<Cupom> cupons = new ArrayList<>();

    void cadastrar(Cupom cupom) {
        cupons.add(cupom);
    }

    /**
     * Devolve o cupom com o código informado, ou null se ele não existir.
     * O código costuma vir de um campo digitado pelo cliente.
     */
    Cupom buscar(String codigo) {
        for (Cupom cupom : cupons) {
            if (cupom.getCodigo().equals(codigo)) {
                return cupom;
            }
        }
        return null;
    }
}

/**
 * Controla o saldo de cada produto.
 * Produtos com o mesmo código compartilham o mesmo saldo.
 */
class Estoque {
    private final Map<Produto, Integer> saldos = new HashMap<>();

    /** Soma a quantidade ao saldo do produto. */
    void adicionar(Produto produto, int quantidade) {
        saldos.put(produto, getSaldo(produto) + quantidade);
    }

    /** Devolve o saldo do produto, ou zero se ele nunca entrou no estoque. */
    int getSaldo(Produto produto) {
        return saldos.getOrDefault(produto, 0);
    }

    /**
     * Retira a quantidade do saldo do produto.
     * Lança IllegalStateException se o saldo for insuficiente.
     */
    void baixar(Produto produto, int quantidade) {
        int saldo = getSaldo(produto);
        if (quantidade > saldo) {
            throw new IllegalStateException("Saldo insuficiente para " + produto.getCodigo());
        }
        saldos.put(produto, saldo - quantidade);
    }
}

/** Forma de calcular o frete de um pedido. */
interface EstrategiaFrete {
    double calcular(double pesoTotal, double subtotal);
}

/** PAC: R$ 15,00 mais R$ 2,00 por kg. */
class FretePac implements EstrategiaFrete {
    @Override
    public double calcular(double pesoTotal, double subtotal) {
        return 15.0 + pesoTotal * 2.0;
    }
}

/** SEDEX: R$ 25,00 mais R$ 4,00 por kg. */
class FreteSedex implements EstrategiaFrete {
    @Override
    public double calcular(double pesoTotal, double subtotal) {
        return 25.0 + pesoTotal * 4.0;
    }
}

/** TRANSPORTADORA: grátis para subtotal a partir de R$ 300,00; R$ 40,00 abaixo disso. */
class FreteTransportadora implements EstrategiaFrete {
    @Override
    public double calcular(double pesoTotal, double subtotal) {
        if (subtotal >= 300.0) {
            return 0.0;
        }
        return 40.0;
    }
}

/**
 * Formas de entrega disponíveis, pelo nome.
 * Uma forma nova entra com uma classe nova e uma linha de cadastro.
 */
class TiposDeFrete {
    private static final Map<String, EstrategiaFrete> tipos = new HashMap<>();

    static {
        tipos.put("PAC", new FretePac());
        tipos.put("SEDEX", new FreteSedex());
        tipos.put("TRANSPORTADORA", new FreteTransportadora());
    }

    /** Devolve a estratégia do nome informado, ou null se ele não existir. */
    static EstrategiaFrete buscar(String nome) {
        return tipos.get(nome);
    }
}

/** Pedido de compra. Aceita produtos, kits e itens com adicionais. */
class Pedido {
    private final String tipoFrete;
    private final EstrategiaFrete estrategiaFrete;
    private final List<ItemPedido> itens = new ArrayList<>();
    private Cupom cupom;
    private boolean finalizado;

    /** @param tipoFrete PAC, SEDEX ou TRANSPORTADORA */
    Pedido(String tipoFrete) {
        this.tipoFrete = tipoFrete;
        this.estrategiaFrete = TiposDeFrete.buscar(tipoFrete);
    }

    Pedido(EstrategiaFrete estrategiaFrete) {
        this.tipoFrete = estrategiaFrete.getClass().getSimpleName();
        this.estrategiaFrete = estrategiaFrete;
    }

    void adicionar(ItemPedido item) {
        verificarAberto();
        itens.add(item);
    }

    private void verificarAberto() {
        if (finalizado) {
            throw new IllegalStateException("Pedido finalizado não aceita novos itens");
        }
    }

    /**
     * Devolve os itens do pedido. A lista devolvida não aceita alterações:
     * novos itens entram somente por adicionar().
     */
    List<Object> getItens() {
        return Collections.unmodifiableList(itens);
    }

    void aplicarCupom(Cupom cupom) {
        this.cupom = cupom;
    }

    void finalizar() {
        finalizado = true;
    }

    boolean isFinalizado() {
        return finalizado;
    }

    double calcularSubtotal() {
        double subtotal = 0.0;
        for (ItemPedido item : itens) {
            subtotal += item.getPreco();
        }
        return subtotal;
    }

    double calcularPesoTotal() {
        double peso = 0.0;
        for (ItemPedido item : itens) {
            peso += item.getPeso();
        }
        return peso;
    }

    /** Calcula o frete do pedido com a estratégia escolhida. */
    double calcularFrete() {
        if (estrategiaFrete == null) {
            throw new IllegalArgumentException("Tipo de frete inválido: " + tipoFrete);
        }
        return estrategiaFrete.calcular(calcularPesoTotal(), calcularSubtotal());
    }

    /** Subtotal com o desconto do cupom, quando houver, mais o frete. */
    double calcularTotal() {
        double subtotal = calcularSubtotal();
        if (cupom != null) {
            subtotal -= subtotal * cupom.getPercentual() / 100;
        }
        return subtotal + calcularFrete();
    }
}

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
 * Produto vendido individualmente.
 * Dois produtos são iguais quando têm o mesmo código.
 */
class Produto {
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

    String getDescricao() {
        return descricao;
    }

    double getPreco() {
        return preco;
    }

    /** Peso em quilogramas. */
    double getPeso() {
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
 * Conjunto de produtos vendido com desconto percentual.
 * A equipe de vendas pediu kits que contenham outros kits
 * e itens com adicionais, o que esta classe ainda não aceita.
 */
class Kit {
    private final String nome;
    private final double percentualDesconto;
    private final List<Produto> produtos = new ArrayList<>();

    Kit(String nome, double percentualDesconto) {
        this.nome = nome;
        this.percentualDesconto = percentualDesconto;
    }

    void adicionar(Produto produto) {
        produtos.add(produto);
    }

    /** Soma os preços de todos os produtos do kit e aplica o desconto percentual. */
    double getPreco() {
        double soma = 0.0;
        for (int i = 0; i < produtos.size(); i++) {
            soma += produtos.get(i).getPreco();
        }
        return soma * (1 - percentualDesconto / 100);
    }

    /** Soma os pesos de todos os produtos do kit, em quilogramas. */
    double getPeso() {
        double soma = 0.0;
        for (Produto produto : produtos) {
            soma += produto.getPeso();
        }
        return soma;
    }

    String getDescricao() {
        return "Kit " + nome;
    }
}

/**
 * Produto vendido com adicionais opcionais:
 * embalagem para presente (R$ 5,00), garantia estendida
 * (10% sobre o preço do produto já somado à embalagem, quando houver)
 * e seguro contra roubo (R$ 8,00).
 * O marketing planeja novos adicionais, como cartão de presente.
 */
class ItemComAdicionais {
    private final Produto produto;
    private final boolean embalagem;
    private final boolean garantia;
    private final boolean seguro;

    ItemComAdicionais(Produto produto, boolean embalagem, boolean garantia, boolean seguro) {
        this.produto = produto;
        this.embalagem = embalagem;
        this.garantia = garantia;
        this.seguro = seguro;
    }

    double getPreco() {
        double preco = produto.getPreco();
        if (embalagem) {
            preco += 5.0;
        }
        if (garantia) {
            preco += preco * 0.10;
        }
        if (seguro) {
            preco += 8.0;
        }
        return preco;
    }

    double getPeso() {
        return produto.getPeso();
    }

    String getDescricao() {
        String descricao = produto.getDescricao();
        if (embalagem) {
            descricao += " + embalagem";
        }
        if (garantia) {
            descricao += " + garantia";
        }
        if (seguro) {
            descricao += " + seguro";
        }
        return descricao;
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

/** Pedido de compra. Aceita produtos, kits e itens com adicionais. */
class Pedido {
    private final String tipoFrete;
    private final List<Object> itens = new ArrayList<>();
    private Cupom cupom;
    private boolean finalizado;

    /** @param tipoFrete PAC, SEDEX ou TRANSPORTADORA */
    Pedido(String tipoFrete) {
        this.tipoFrete = tipoFrete;
    }

    void adicionar(Produto produto) {
        verificarAberto();
        itens.add(produto);
    }

    void adicionar(Kit kit) {
        verificarAberto();
        itens.add(kit);
    }

    void adicionar(ItemComAdicionais item) {
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
        for (Object item : itens) {
            if (item instanceof Produto) {
                subtotal += ((Produto) item).getPreco();
            } else if (item instanceof Kit) {
                subtotal += ((Kit) item).getPreco();
            } else if (item instanceof ItemComAdicionais) {
                subtotal += ((ItemComAdicionais) item).getPreco();
            }
        }
        return subtotal;
    }

    double calcularPesoTotal() {
        double peso = 0.0;
        for (Object item : itens) {
            if (item instanceof Produto) {
                peso += ((Produto) item).getPeso();
            } else if (item instanceof Kit) {
                peso += ((Kit) item).getPeso();
            } else if (item instanceof ItemComAdicionais) {
                peso += ((ItemComAdicionais) item).getPeso();
            }
        }
        return peso;
    }

    /**
     * Calcula o frete do pedido.
     * PAC: R$ 15,00 mais R$ 2,00 por kg.
     * SEDEX: R$ 25,00 mais R$ 4,00 por kg.
     * TRANSPORTADORA: grátis para subtotal a partir de R$ 300,00; R$ 40,00 abaixo disso.
     * A loja negocia novas formas de entrega com frequência.
     */
    double calcularFrete() {
        double peso = calcularPesoTotal();
        switch (tipoFrete) {
            case "PAC":
                return 15.0 + peso * 2.0;
            case "SEDEX":
                return 25.0 + peso * 4.0;
            case "TRANSPORTADORA":
                if (calcularSubtotal() >= 300.0) {
                    return 0.0;
                }
                return 40.0;
            default:
                throw new IllegalArgumentException("Tipo de frete inválido: " + tipoFrete);
        }
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

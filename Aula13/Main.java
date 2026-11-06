import java.util.Locale;

interface Sorvete {
    String getDescricao();
    double getPreco();
}

class SorveteSimples implements Sorvete {
    @Override
    public String getDescricao() {
        return "Sorvete de baunilha";
    }

    @Override
    public double getPreco() {
        return 5.00;
    }
}

abstract class CoberturaDecorator implements Sorvete {
    protected Sorvete sorvete;

    public CoberturaDecorator(Sorvete sorvete) {
        this.sorvete = sorvete;
    }
}

class CoberturaChocolate extends CoberturaDecorator {
    public CoberturaChocolate(Sorvete sorvete) {
        super(sorvete);
    }

    @Override
    public String getDescricao() {
        return sorvete.getDescricao() + " + cobertura de chocolate";
    }

    @Override
    public double getPreco() {
        return sorvete.getPreco() + 1.50;
    }
}

class CoberturaCaramelo extends CoberturaDecorator {
    public CoberturaCaramelo(Sorvete sorvete) {
        super(sorvete);
    }

    @Override
    public String getDescricao() {
        return sorvete.getDescricao() + " + cobertura de caramelo";
    }

    @Override
    public double getPreco() {
        return sorvete.getPreco() + 1.20;
    }
}

class GranuladoColorido extends CoberturaDecorator {
    public GranuladoColorido(Sorvete sorvete) {
        super(sorvete);
    }

    @Override
    public String getDescricao() {
        return sorvete.getDescricao() + " + granulado colorido";
    }

    @Override
    public double getPreco() {
        return sorvete.getPreco() + 0.80;
    }
}

class ChantillyExtra extends CoberturaDecorator {
    public ChantillyExtra(Sorvete sorvete) {
        super(sorvete);
    }

    @Override
    public String getDescricao() {
        return sorvete.getDescricao() + " + chantilly extra";
    }

    @Override
    public double getPreco() {
        return sorvete.getPreco() + 1.00;
    }
}

public class Main {
    public static void main(String[] args) {
        Sorvete pedido1 = new SorveteSimples();
        pedido1 = new CoberturaChocolate(pedido1);
        pedido1 = new GranuladoColorido(pedido1);
        pedido1 = new ChantillyExtra(pedido1);
        imprimir(pedido1);

        Sorvete pedido2 = new SorveteSimples();
        pedido2 = new CoberturaCaramelo(pedido2);
        pedido2 = new ChantillyExtra(pedido2);
        imprimir(pedido2);

        Sorvete pedido3 = new SorveteSimples();
        pedido3 = new GranuladoColorido(pedido3);
        pedido3 = new CoberturaChocolate(pedido3);
        imprimir(pedido3);
    }

    public static void imprimir(Sorvete sorvete) {
        System.out.println(sorvete.getDescricao() + " - R$" + String.format(Locale.US, "%.2f", sorvete.getPreco()));
    }
}
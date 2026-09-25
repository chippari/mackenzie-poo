abstract class Veiculo {
    private String marca;
    private String modelo;
    private int ano;
    private double preco;
    private static int totalVeiculos = 0;

    public Veiculo(String marca, String modelo,  int ano, double preco) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.preco = preco;
        totalVeiculos++;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo(){
        return modelo;
    }

    public int getAno(){
        return ano;
    }

    public double getPreco(){
        return preco;
    }

    public int getTotalVeiculos(){
        return totalVeiculos;
    }

    public abstract String getDescricao();
    @Override
    public String toString(){
        return getDescricao();
    }

}

class VeiculoNovo extends Veiculo {
    private int garantiaAnos;

    public VeiculoNovo(String marca, String modelo,  int ano, double preco, int garantiaAnos) {
        super(marca, modelo, ano, preco);
        this.garantiaAnos = garantiaAnos;
    }

    @Override
    public abstract String getDescricao(){
        return (
            "marca: " + getMarca() +
            "modelo: " + getModelo() +
            "ano: " + getAno() +
            "preco: " + getPreco() +

        )
        return String.format("marca: %s %nmodelo: %s %nano: %d %npreco: %.2f", getMarca(), 
        getModelo(), getAno(), getPreco());

    }
}

class VeiculoUsado extends Veiculo {
    private int quilometragem;
    private boolean unicoDono;
}

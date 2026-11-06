public class CalculadoraFrete {
    private FreteStrategy estrategia;

    public void setEstrategia(FreteStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public double calcular(double peso) {
        return calcular(peso, 0);
    }

    public double calcular(double peso, double valor) {
        return estrategia.calcularFrete(peso, valor);
    }
}
public class PacFrete implements FreteStrategy {
    @Override
    public double calcularFrete(double peso, double valor) {
        return peso * 1.10;
    }
}
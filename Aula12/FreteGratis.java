public class FreteGratis implements FreteStrategy {
    @Override
    public double calcularFrete(double peso, double valor) {
        if(valor > 200) {
            return 0;
        }
        return peso * 1.10;
    }
}
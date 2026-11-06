public class Main {
    public static void main(String[] args) {
        CalculadoraFrete calculadora = new CalculadoraFrete();

        calculadora.setEstrategia(new SedexFrete());
        System.out.println("SEDEX (peso 10): R$ " + String.format("%.2f", calculadora.calcular(10)));

        calculadora.setEstrategia(new PacFrete());
        System.out.println("PAC (peso 10): R$ " + String.format("%.2f", calculadora.calcular(10)));

        calculadora.setEstrategia(new FreteGratis());
        System.out.println("Frete Grátis (peso 10, valor 250): R$ " + String.format("%.2f", calculadora.calcular(10, 250)));
    }
}
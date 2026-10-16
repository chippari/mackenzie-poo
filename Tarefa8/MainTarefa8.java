public class MainTarefa8 {
    public static void main(String[] args) {
        Desenvolvedor dev = new Desenvolvedor("Fabio", 101, 5000, "Java");
        Gerente ger = new Gerente("Nilton", 100, 10000, "POO");
        Estagiario est = new Estagiario("Felipe", 102, 1500, "Mackenzie");

        System.out.println(dev.getNome() + " - Remuneração: " + dev.calcularRemuneracao());
        System.out.println(ger.getNome() + " - Remuneração: " + ger.calcularRemuneracao());
        System.out.println(est.getNome() + " - Remuneração: " + est.calcularRemuneracao());
    }
}

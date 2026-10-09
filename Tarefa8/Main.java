public class Main {
    public static void main(String[] args) {
        Desenvolvedor dev = new Desenvolvedor("Ana", "001", 5000, "Java");
        Gerente ger = new Gerente("Carlos", "002", 8000, "TI");
        Estagiario est = new Estagiario("Bruno", "003", 1500, "Mackenzie");

        System.out.println(dev.getNome() + " - Remuneração: " + dev.calcularRemuneracao());
        System.out.println(ger.getNome() + " - Remuneração: " + ger.calcularRemuneracao());
        System.out.println(est.getNome() + " - Remuneração: " + est.calcularRemuneracao());
    }
}
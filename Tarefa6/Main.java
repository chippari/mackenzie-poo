import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        FastFood burger = new FastFood("Burger King", "Hamburguer");
        FineDining murakami = new FineDining("Murakami", 1);
        FineDining evvai = new FineDining("Evvai", 3);

        burger.adicionarAvaliacao(new Avaliacao("10/09/2026", 4, "Lanche muito bom"));
        burger.adicionarAvaliacao(new Avaliacao("12/09/2026", 3, "Demorou um pouco"));
        burger.adicionarAvaliacao(new Avaliacao("15/09/2026", 5, "Melhor da regiao"));

        murakami.adicionarAvaliacao(new Avaliacao("05/09/2026", 4, "Pratos elegantes"));
        murakami.adicionarAvaliacao(new Avaliacao("08/09/2026", 5, "Experiencia incrivel"));
        murakami.adicionarAvaliacao(new Avaliacao("20/09/2026", 3, "Porcoes pequenas"));

        evvai.adicionarAvaliacao(new Avaliacao("02/09/2026", 4, "Carta de vinhos otima"));
        evvai.adicionarAvaliacao(new Avaliacao("11/09/2026", 4, "Atendimento impecavel"));
        evvai.adicionarAvaliacao(new Avaliacao("18/09/2026", 5, "Inesquecivel"));

        ArrayList<Restaurante> lista = new ArrayList<>();
        lista.add(burger);
        lista.add(murakami);
        lista.add(evvai);

        for (Restaurante r : lista) {
            System.out.printf("%s - media: %.2f%n", r.getNome(), r.calcularMedia());
        }

        Restaurante melhor = Restaurante.melhorAvaliado(lista);
        System.out.println("Mais bem avaliado: " + melhor.getNome());

        System.out.println("Total de avaliacoes: " + Restaurante.getTotalAvaliacoes());
    }
}
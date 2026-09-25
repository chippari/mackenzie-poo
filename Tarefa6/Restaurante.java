import java.util.ArrayList;

public abstract class Restaurante {
    private String nome;
    private ArrayList<Avaliacao> avaliacoes;
    private static int totalAvaliacoes = 0;

    public Restaurante(String nome) {
        this.nome = nome;
        this.avaliacoes = new ArrayList<>();
    }

    public void adicionarAvaliacao(Avaliacao a) {
        avaliacoes.add(a);
        totalAvaliacoes++;
    }

    public double calcularMedia() {
        if (avaliacoes.isEmpty()) {
            return 0;
        }

        int soma = 0;
        for (Avaliacao a : avaliacoes) {
            soma += a.getNota();
        }

        double media = (double) soma / avaliacoes.size();
        return ajustarMedia(media);
    }

    protected abstract double ajustarMedia(double media);

    public String getNome() {
        return nome;
    }

    public ArrayList<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public static int getTotalAvaliacoes() {
        return totalAvaliacoes;
    }

    public static Restaurante melhorAvaliado(ArrayList<Restaurante> lista) {
        Restaurante melhor = lista.get(0);
        for (Restaurante r : lista) {
            if (r.calcularMedia() > melhor.calcularMedia()) {
                melhor = r;
            }
        }
        return melhor;
    }
}
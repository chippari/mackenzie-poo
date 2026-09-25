public class FineDining extends Restaurante {
    private int estrelasMichelin;

    public FineDining(String nome, int estrelasMichelin) {
        super(nome);
        this.estrelasMichelin = estrelasMichelin;
    }

    @Override
    protected double ajustarMedia(double media) {
        double mediaAjustada = media + 0.5 * estrelasMichelin;
        if (mediaAjustada > 5.0) {
            return 5.0;
        }
        return mediaAjustada;
    }
}
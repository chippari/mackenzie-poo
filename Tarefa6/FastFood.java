public class FastFood extends Restaurante {
    private String tipo;

    public FastFood(String nome, String tipo) {
        super(nome);
        this.tipo = tipo;
    }

    @Override
    protected double ajustarMedia(double media) {
        return media;
    }
}
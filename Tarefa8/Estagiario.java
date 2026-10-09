public class Estagiario extends Funcionario {
    private String instituicaoEnsino;

    public Estagiario(String nome, String matricula, double salarioBase, String instituicaoEnsino) {
        super(nome, matricula, salarioBase);
        this.instituicaoEnsino = instituicaoEnsino;
    }

    public String getInstituicaoEnsino() {
        return instituicaoEnsino;
    }

    @Override
    public double calcularRemuneracao() {
        return getSalarioBase();
    }
}
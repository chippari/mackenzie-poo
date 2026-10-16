public class Estagiario extends Funcionario {
    private String instituicaoEnsino;

    public Estagiario(String nome, int matricula, double salarioBase, String instituicaoEnsino){
        super(nome, matricula, salarioBase);
        this.instituicaoEnsino = instituicaoEnsino;
    }

    public String getInstituicaoEnsino(){
        return instituicaoEnsino;
    }

    public double calcularRemuneracao(){
        return getSalarioBase();
    }
}

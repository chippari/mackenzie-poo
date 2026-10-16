public class Gerente extends Funcionario implements Bonificacao {
    private String departamento;

    public Gerente(String nome, int matricula, double salarioBase, String departamento){
        super(nome, matricula, salarioBase);
        this.departamento = departamento;
    }

    public String getDepartamento(){
        return departamento;
    }

    @Override
    public double calcularBonus() {
        return getSalarioBase() * 0.30;
    }

    @Override
    public double calcularRemuneracao() {
        return getSalarioBase() + calcularBonus();
    }
}

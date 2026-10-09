public class Gerente extends Funcionario implements Bonificavel {
    private String departamento;

    public Gerente(String nome, String matricula, double salarioBase, String departamento) {
        super(nome, matricula, salarioBase);
        this.departamento = departamento;
    }

    public String getDepartamento() {
        return departamento;
    }

    @Override
    public double calcularBonus() {
        return getSalarioBase() * 0.20;
    }

    @Override
    public double calcularRemuneracao() {
        return getSalarioBase() + calcularBonus();
    }
}
public class Desenvolvedor extends Funcionario implements Bonificavel {
    private String linguagemPrincipal;

    public Desenvolvedor(String nome, String matricula, double salarioBase, String linguagemPrincipal) {
        super(nome, matricula, salarioBase);
        this.linguagemPrincipal = linguagemPrincipal;
    }

    public String getLinguagemPrincipal() {
        return linguagemPrincipal;
    }

    @Override
    public double calcularBonus() {
        return getSalarioBase() * 0.10;
    }

    @Override
    public double calcularRemuneracao() {
        return getSalarioBase() + calcularBonus();
    }
}
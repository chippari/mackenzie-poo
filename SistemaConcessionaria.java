class Concessionaria {
    private Map<String, Veiculo> veiculos;
    private List<Veiculo> vendas;

    public Concessionaria(){
        veiculos = new HashMap<>(); 
        vendas = new ArrayList<>();
    }

    public String getChave(Veiculo veiculo){
        return (
            veiculo.getMarca() + "-" +
            veiculo.getAno() + "-" +
            veiculo.getModelo() + "-"
        ).toLowCase();
    }

    public void cadastrarVeiculo(Veiculo veiculo){
        String chave = gerarChave(veiculo);
        if (veiculos.containsKey(chave)){
            System.out.println("Veiculo já cadastrado.")
        }

        veiculos.put(chave, veiculo);
    }
}



public class SistemaConcessionaria {
    public static void main(String[] args) {

    }
}

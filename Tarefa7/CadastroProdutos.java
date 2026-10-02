import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.Scanner;

public class CadastroProdutos {
    static final String ARQUIVO = "produtos.txt";
    static Scanner sc = new Scanner(System.in);
    static DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) {
        criarArquivoSeNaoExistir();

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n===== CADASTRO DE PRODUTOS =====");
            System.out.println("1 - Adicionar produto");
            System.out.println("2 - Listar todos os produtos");
            System.out.println("3 - Listar produtos com estoque baixo");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opcao invalida. Digite um numero.");
                continue;
            }

            switch (opcao) {
                case 1:
                    adicionarProduto();
                    break;
                case 2:
                    listarTodosProdutos();
                    break;
                case 3:
                    listarProdutosBaixoEstoque();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        }
        sc.close();
    }

    static void criarArquivoSeNaoExistir() {
        try {
            FileWriter fw = new FileWriter(ARQUIVO, true);
            fw.close();
        } catch (Exception e) {
            System.out.println("Erro ao criar o arquivo: " + e.getMessage());
        }
    }

    static void adicionarProduto() {
        try {
            System.out.print("Nome do produto: ");
            String nome = sc.nextLine();

            System.out.print("Preco: ");
            double preco = Double.parseDouble(sc.nextLine().replace(",", "."));

            System.out.print("Quantidade em estoque: ");
            int quantidade = Integer.parseInt(sc.nextLine());

            BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO, true));
            bw.write(nome + ";" + preco + ";" + quantidade);
            bw.newLine();
            bw.close();

            System.out.println("Produto cadastrado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Erro: preco ou quantidade invalidos. Produto nao cadastrado.");
        } catch (Exception e) {
            System.out.println("Erro ao gravar no arquivo: " + e.getMessage());
        }
    }

    static void listarTodosProdutos() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(ARQUIVO));
            String linha;
            int contador = 0;

            System.out.println("\n--- Todos os produtos ---");
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                String nome = partes[0];
                double preco = Double.parseDouble(partes[1]);
                int quantidade = Integer.parseInt(partes[2]);

                System.out.println("Nome: " + nome + " | Preco: R$ " + df.format(preco) + " | Quantidade: " + quantidade);
                contador++;
            }
            br.close();

            if (contador == 0) {
                System.out.println("Nenhum produto cadastrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    static void listarProdutosBaixoEstoque() {
        try {
            System.out.print("Informe o limite de estoque: ");
            int limite = Integer.parseInt(sc.nextLine());

            BufferedReader br = new BufferedReader(new FileReader(ARQUIVO));
            String linha;
            int contador = 0;

            System.out.println("\n--- Produtos com estoque menor ou igual a " + limite + " ---");
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                String nome = partes[0];
                double preco = Double.parseDouble(partes[1]);
                int quantidade = Integer.parseInt(partes[2]);

                if (quantidade <= limite) {
                    System.out.println("Nome: " + nome + " | Preco: R$ " + df.format(preco) + " | Quantidade: " + quantidade);
                    contador++;
                }
            }
            br.close();

            if (contador == 0) {
                System.out.println("Nenhum produto com estoque baixo.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: limite invalido. Digite um numero inteiro.");
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}

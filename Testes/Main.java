import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BancoLivros banco = new BancoLivros();
        banco.criarTabela();
        int opcao = -1;

        while(opcao != 0) {
            System.out.println("\n1 - Cadastrar livro");
            System.out.println("2 - Listar livros");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());

                switch(opcao) {
                    case 1:
                        System.out.print("Título: ");
                        String titulo = sc.nextLine();
                        System.out.print("Autor: ");
                        String autor = sc.nextLine();
                        banco.cadastrarLivro(titulo, autor);
                        break;
                    case 2:
                        banco.listarLivros();
                        break;
                    case 0:
                        System.out.println("Saindo do sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch(NumberFormatException e) {
                System.out.println("Opção inválida!");
            }
        }

        sc.close();
    }
}

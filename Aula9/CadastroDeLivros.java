//javac -cp ".;sqlite-jdbc-3.36.0.3.jar" CadastroDeLivros.java
//java -cp ".;sqlite-jdbc-3.36.0.3.jar" CadastroDeLivros
import java.sql.Connection;          // Representa a conexão com o banco
import java.sql.DriverManager;       // Cria conexões com o banco
import java.sql.PreparedStatement;   // Permite executar SQL com parâmetros
import java.sql.ResultSet;           // Guarda os resultados de um SELECT
import java.sql.SQLException;        // Exceção para erros de banco
import java.sql.Statement;           // Executa comandos SQL simples
import java.util.Scanner;            // Lê dados digitados pelo usuário

// Classe responsável pelas operações de banco de dados
class BancoLivros {

    // URL de conexão com o SQLite
    private static final String URL = "jdbc:sqlite:biblioteca.db";

    // Método para criar a tabela, se ela ainda não existir
    public void criarTabela() {
        // Comando SQL para criar a tabela
        String sql =
                "CREATE TABLE IF NOT EXISTS livros (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    titulo TEXT NOT NULL,\n" +
                "    autor TEXT NOT NULL\n" +
                ");";

        // try-with-resources fecha a conexão e o statement automaticamente
        try {
            Class.forName("org.sqlite.JDBC");
    
            try (Connection conn = DriverManager.getConnection(URL);
                 Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            }
    
        } catch (ClassNotFoundException e) {
            System.out.println("Driver SQLite nao encontrado no classpath.");
        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela: " + e.getMessage());
        }
    }

    // Método para inserir um novo livro
    public void cadastrarLivro(String titulo, String autor) {
        // SQL com parâmetros
        String sql = "INSERT INTO livros (titulo, autor) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Substitui os ? pelos valores recebidos
            pstmt.setString(1, titulo);
            pstmt.setString(2, autor);

            // Executa o INSERT
            pstmt.executeUpdate();

            System.out.println("Livro cadastrado com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar livro: " + e.getMessage());
        }
    }

    // Método para listar os livros cadastrados
    public void listarLivros() {
        // SQL de consulta
        String sql = "SELECT id, titulo, autor FROM livros ORDER BY id";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean encontrou = false;

            System.out.println("\n=== LIVROS CADASTRADOS ===");

            // Percorre o resultado linha por linha
            while (rs.next()) {
                encontrou = true;

                // Lê os valores de cada coluna
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");

                // Exibe o registro
                System.out.println(id + " - " + titulo + " | " + autor);
            }

            if (!encontrou) {
                System.out.println("Nenhum livro cadastrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar livros: " + e.getMessage());
        }
    }

    public void apagarLivros(int id) {
        String sql = "DELETE FROM livros WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Livro excluído com sucesso.");
            } else {
                System.out.println("Nenhum livro encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao excluir livro: " + e.getMessage());
        }
    }
}

// Classe principal do programa
public class CadastroDeLivros {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BancoLivros banco = new BancoLivros();

        // Garante que a tabela exista antes de começar
        banco.criarTabela();

        int opcao;

                // 2. Mude o menu e o switch dentro do método main para isto:
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Cadastrar livro");
            System.out.println("2 - Listar livros");
            System.out.println("3 - Excluir livro");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("Digite o título: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Digite o autor: ");
                    String autor = scanner.nextLine();
                    banco.cadastrarLivro(titulo, autor);
                    break;

                case 2:
                    banco.listarLivros();
                    break;

                case 3:
                    System.out.print("Digite o ID do livro que deseja excluir: ");
                    int idExcluir = Integer.parseInt(scanner.nextLine());
                    banco.apagarLivros(idExcluir);
                    break;

                case 4: // Mudou para 4
                    System.out.println("Programa encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 4); // Mudou para 4

        scanner.close();
    }
}
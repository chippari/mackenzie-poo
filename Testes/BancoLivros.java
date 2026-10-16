import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BancoLivros {
    private static final String URL = "jdbc:sqlite:biblioteca.db";

    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS livros ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "titulo TEXT NOT NULL, "
                + "autor TEXT NOT NULL)";

        try(Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch(SQLException e) {
            System.out.println("Não foi possível criar a tabela.\n" + e);
        }
    }

    public void cadastrarLivro(String titulo, String autor) {
        String sql = "INSERT INTO livros (titulo, autor) VALUES (?, ?)";

        try(Connection conn = DriverManager.getConnection(URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, titulo);
            pstmt.setString(2, autor);
            pstmt.executeUpdate();
            System.out.println("Livro cadastrado com sucesso!");
        } catch(SQLException e) {
            System.out.println("Não foi possível cadastrar o livro.\n" + e);
        }
    }

    public void listarLivros() {
        String sql = "SELECT id, titulo, autor FROM livros";

        try(Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            boolean vazio = true;
            while(rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " | Título: " + rs.getString("titulo") + " | Autor: " + rs.getString("autor"));
                vazio = false;
            }
            if(vazio) {
                System.out.println("Nenhum livro cadastrado.");
            }
        } catch(SQLException e) {
            System.out.println("Não foi possível listar os livros.\n" + e);
        }
    }
}

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ManipularArquivos arq = new ManipularArquivos("dados.csv");
        // arq.Gravar("Fabio;24;SP\n");
        // arq.Ler();
        arq.Apagar();
    }
}

class ManipularArquivos {
    String nome;

    public ManipularArquivos(String nome) {
        this.nome = nome;
    }

    public boolean Gravar(String texto) {
        try(FileWriter arquivo = new FileWriter(nome, true)) {
            arquivo.write(texto);
            return true;
        } catch(IOException e) {
            System.out.println("Não foi possível escrever no arquivo");
            return false;
        }
    }

    public void Ler() {
        try(BufferedReader arquivo = new BufferedReader(new FileReader(nome))){
            String linha;
            while((linha = arquivo.readLine()) != null) {
                System.out.println(linha);
            }
        } catch(IOException e) {
            System.out.println("Não foi possível ler o arquivo.\n" + e);
        }
    }

    public void Apagar() {
        File arquivo = new File(nome);
        if(arquivo.delete()) {
            System.out.println("arquivo exluído com sucesso");
        } else {
            System.out.println("Não foi possível deletar o arquivo!");
        }
    }
}

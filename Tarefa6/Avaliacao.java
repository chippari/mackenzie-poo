public class Avaliacao {
    private String data;
    private int nota;
    private String comentario;

    public Avaliacao(String data, int nota, String comentario) {
        this.data = data;
        this.nota = nota;
        this.comentario = comentario;
    }

    public String getData() {
        return data;
    }

    public int getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }
}
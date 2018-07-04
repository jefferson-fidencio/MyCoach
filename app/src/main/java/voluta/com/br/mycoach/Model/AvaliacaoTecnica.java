package voluta.com.br.mycoach.Model;

import java.io.Serializable;
import java.util.List;

public class AvaliacaoTecnica implements Serializable {
    private long id;
    private String nome;
    private List<Tecnica> tecnicas;

    public AvaliacaoTecnica(long id, String nome, List<Tecnica> tecnicas) {
        this.id = id;
        this.nome = nome;
        this.tecnicas = tecnicas;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<Tecnica> getTecnicas() {
        return tecnicas;
    }

}

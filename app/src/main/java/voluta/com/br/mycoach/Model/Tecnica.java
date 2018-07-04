package voluta.com.br.mycoach.Model;

import java.io.Serializable;
import java.util.List;

public class Tecnica implements Serializable {

    public void setId(long id) {
        this.id = id;
    }

    private long id;
    private int ordem;
    private String nome;
    private String conceito;
    private String observacao;
    private String videoURI;
    private List<SubTecnica> subtecnicas;

    public Tecnica(long id, int ordem, String nome, String conceito, List<SubTecnica> subTecnicas, String observacao, String videoURI) {
        this.id = id;
        this.ordem = ordem;
        this.nome = nome;
        this.conceito = conceito;
        this.subtecnicas = subTecnicas;
        this.observacao = observacao;
        this.videoURI = videoURI;
    }

    public long getId() {
        return id;
    }

    public int getOrdem() {
        return ordem;
    }

    public String getNome() {
        return nome;
    }

    public String getConceito() {
        return conceito;
    }

    public List<SubTecnica> getSubTecnicas() {
        return subtecnicas;
    }

    public String getObservacao() {
        return observacao;
    }

    public String getvideoURI() {
        return videoURI;
    }
}

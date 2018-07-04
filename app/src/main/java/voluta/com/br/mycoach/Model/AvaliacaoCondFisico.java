package voluta.com.br.mycoach.Model;

import java.io.Serializable;

public class AvaliacaoCondFisico  implements Serializable {
    private long id;
    private String nome;
    private String condicionamentoFisicoImg;

    public AvaliacaoCondFisico(long id, String nome, String condicionamentoFisicoImg) {
        this.id = id;
        this.nome = nome;
        this.condicionamentoFisicoImg = condicionamentoFisicoImg;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getcondicionamentoFisicoImg() {
        return condicionamentoFisicoImg;
    }
}

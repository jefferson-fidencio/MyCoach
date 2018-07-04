package voluta.com.br.mycoach.Model;

import java.io.Serializable;

public class Treino implements Serializable {

    private long id;
    private String dia;

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    private String descricao;

    public Treino(long id, String dia, String treino) {
        this.id = id;
        this.dia = dia;
        this.descricao = treino;
    }

    public String getdia() {
        return dia;
    }

    public String getDescricao() {
        return descricao;
    }

    public long getId() {
        return id;
    }
}

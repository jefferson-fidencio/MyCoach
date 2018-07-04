package voluta.com.br.mycoach.Model;

import java.io.Serializable;

/**
 * Created by jdfid on 09/06/2017.
 */

public class Execucao implements Serializable {
    private long id;
    private String nome;

    public Execucao(long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public long getId() {
        return id;
    }
}

package voluta.com.br.mycoach.Model;

import java.io.Serializable;

public class SubTecnica implements Serializable {

    private long id;
    private String nome;
    private Execucao execucao;

    public SubTecnica(long id, String nome, Execucao execucao) {
        this.id = id;
        this.nome = nome;
        this.execucao = execucao;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public voluta.com.br.mycoach.Enum.Execucao getExecucao() {
        switch (execucao.getNome()){
            case "Executa":
                return voluta.com.br.mycoach.Enum.Execucao.E;
            case "Não Executa":
                return voluta.com.br.mycoach.Enum.Execucao.NE;
            case "Executa Parcialmente":
                return voluta.com.br.mycoach.Enum.Execucao.EP;
            default:
                return voluta.com.br.mycoach.Enum.Execucao.E;
        }
    }
}

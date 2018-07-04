package voluta.com.br.mycoach.Model;

import java.io.Serializable;

public class FrequenciaMetragem implements Serializable {

    private long id;
    private String nome;
    private String frequenciaImg;
    private String metragemImg;

    public FrequenciaMetragem(long id, String _nome, String _frequenciaImg, String _metragemImg) {
        this.id = id;
        this.nome = _nome;
        this.frequenciaImg = _frequenciaImg;
        this.metragemImg = _metragemImg;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getfrequenciaImg() {
        return frequenciaImg;
    }

    public String getmetragemImg() {
        return metragemImg;
    }
}

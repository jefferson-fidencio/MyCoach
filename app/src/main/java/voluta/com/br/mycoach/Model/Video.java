package voluta.com.br.mycoach.Model;

import java.io.Serializable;

public class Video implements Serializable {

    private long id;
    private String nome;
    private String video;

    public Video(long id, String nome, String video) {
        this.id = id;
        this.nome = nome;
        this.video = video;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getvideo() {
        return video;
    }
}

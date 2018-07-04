package voluta.com.br.mycoach.Model;

import java.io.Serializable;
import java.util.List;

public class TreinoSemanal implements Serializable {

    private long id;
    private List<Treino> treinos;

    public TreinoSemanal(long id, List<Treino> treinos) {
        this.id = id;
        this.treinos = treinos;
    }

    public List<Treino> gettreino() {
        return treinos;
    }

    public long getId() {
        return id;
    }
}

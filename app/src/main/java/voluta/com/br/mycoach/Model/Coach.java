package voluta.com.br.mycoach.Model;

import java.util.List;

/**
 * Created by jdfid on 16/11/2017.
 */

public class Coach extends User{

    private List<Aluno> alunos;

    public Coach(long id, String nome, String email, String senha, String img, List<Aluno> alunos) {
        super(id, nome, email, senha, img, 0);
        this.alunos = alunos;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }
}

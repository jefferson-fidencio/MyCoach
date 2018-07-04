package voluta.com.br.mycoach.Model;

/**
 * Created by jdfid on 16/11/2017.
 */

public class User {

    public long id;
    public String nome;
    public String email;
    public String senha;
    public String img;
    public int categoria;

    public User(long id, String nome, String email, String senha, String img, int categoria) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.img = img;
        this.categoria = categoria;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
}

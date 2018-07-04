package voluta.com.br.mycoach.Model;

import java.io.Serializable;
import java.util.List;

import voluta.com.br.mycoach.Enum.Modality;

/**
 * Created by jdfid on 08/06/2017.
 */

public class Aluno extends User implements Serializable {
    private int idModalidade;
    private List<TreinoSemanal> treino_semanal;
    private List<FrequenciaMetragem> frequencias_metragens;
    private List<AvaliacaoCondFisico> avaliacoes_cond_fisico;
    private List<AvaliacaoTecnica> avaliacoes_tecnicas;
    private List<Video> videos;
    private List<Video> outros_videos;

    public Aluno(long id, String nome, String email, String senha, String alunoImg, int idModalidade, List<TreinoSemanal> treino_semanalList, List<FrequenciaMetragem> frequenciaMetragemList, List<AvaliacaoCondFisico> avaliacaoCondFisicoList, List<AvaliacaoTecnica> avaliacaoTecnicaList, List<Video> videos, List<Video> outros_videos) {
        super(id, nome, email, senha, alunoImg, 1);
        this.idModalidade = idModalidade;
        this.treino_semanal = treino_semanalList;
        this.frequencias_metragens = frequenciaMetragemList;
        this.avaliacoes_cond_fisico = avaliacaoCondFisicoList;
        this.avaliacoes_tecnicas = avaliacaoTecnicaList;
        this.videos = videos;
        this.outros_videos = outros_videos;
    }

    public List<TreinoSemanal> getTreino_semanalList() {
        return treino_semanal;
    }

    public List<FrequenciaMetragem> getFrequenciaMetragemList() {
        return frequencias_metragens;
    }

    public List<AvaliacaoCondFisico> getAvaliacaoCondFisicoList() {
        return avaliacoes_cond_fisico;
    }

    public List<AvaliacaoTecnica> getAvaliacaoTecnicaList() {
        return avaliacoes_tecnicas;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public List<Video> getOutros_videos() {
        return outros_videos;
    }

    public int getIdModalidade() {
        return idModalidade;
    }

    public void setIdModalidade(int idModalidade) {
        this.idModalidade = idModalidade;
    }
}

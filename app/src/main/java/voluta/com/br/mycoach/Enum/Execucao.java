package voluta.com.br.mycoach.Enum;

public enum Execucao {
    E("Executa", 0),
    NE("Não Executa", 1),
    EP("Executa Parcialmente", 2);

    private String execucaoString;
    Execucao(String execucaoString, int i) {
        this.execucaoString = execucaoString;
    }

    @Override
    public String toString() {
        return execucaoString;
    }
}

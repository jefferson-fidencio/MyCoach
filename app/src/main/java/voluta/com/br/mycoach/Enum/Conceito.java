package voluta.com.br.mycoach.Enum;

public enum Conceito {
    E("Excelente", 0),
    MB("Muito Bom", 1),
    B("Bom", 2),
    R("Regular",3),
    RU("Ruim", 4);

    private String conceitoString;
    Conceito(String conceitoString, int i) {
        this.conceitoString = conceitoString;
    }

    @Override
    public String toString() {
        return conceitoString;
    }
}

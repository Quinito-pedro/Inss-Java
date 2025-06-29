public class Beneficiario {
    String nome;
    private String nuit;
    private String telefone;
    private String endereco;
    private boolean isencao;

    public Beneficiario(String nome, String nuit, String telefone, String endereco, boolean isencao) {
        this.nome = nome;
        this.nuit = nuit;
        this.telefone = telefone;
        this.endereco = endereco;
        this.setIsencao(isencao);
    }

    public boolean isIsencao() {
        return isencao;
        
    }

    public String getNome() { return nome; }
    public String getNuit() { return nuit; }
    public String getTelefone() { return telefone; }
    public String getEndereco() { return endereco; }
    public boolean getIsencao() { return isIsencao(); }

    public void setNome(String nome) { this.nome = nome; }
    public void setNuit(String nuit) { this.nuit = nuit; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public void setIsencao(boolean isencao) { this.isencao = isencao; }

    @Override
    public String toString() {
        return String.format("Nome: %s\nNUIT: %s\nTelefone: %s\nEndereço: %s\nIsento: %s",
            nome, nuit, telefone, endereco, isIsencao() ? "Sim" : "Não");
    }
}

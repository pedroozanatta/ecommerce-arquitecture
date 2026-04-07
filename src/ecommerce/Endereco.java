package ecommerce;

public class Endereco {
    private String rua;
    private String bairro;
    private String cep;
    private String cidade;
    private String uf;


    public Endereco(String rua, String bairro, String cep, String cidade, String uf) {
        this.rua = rua;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.uf = uf;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public boolean validarEndereco() {
        return rua != null && !rua.isBlank()
                && cep != null && cep.matches("\\d{5}-?\\d{3}")
                && cidade != null && !cidade.isBlank()
                && uf != null && uf.length() == 2;
    }

    @Override
    public String toString() {
        return rua + ", " + bairro + " - " + cidade + "/" + uf + " CEP: " + cep;
    }
}
package ecommerce.model;

public class Endereco {

    private String rua;
    private String bairro;
    private String cep;
    private String cidade;
    private String uf;

    public Endereco(String rua, String bairro, String cep, String cidade, String uf) {
        this.rua    = rua;
        this.bairro = bairro;
        this.cep    = cep;
        this.cidade = cidade;
        this.uf     = uf;
    }

    public boolean validarEndereco() {
        return rua    != null && !rua.isBlank()
                && cep    != null && !cep.isBlank()
                && cidade != null && !cidade.isBlank()
                && uf     != null && uf.length() == 2;
    }

    public String getRua()    { return rua; }
    public String getBairro() { return bairro; }
    public String getCep()    { return cep; }
    public String getCidade() { return cidade; }
    public String getUf()     { return uf; }

    @Override
    public String toString() {
        return rua + ", " + bairro + " - " + cidade + "/" + uf + " | CEP: " + cep;
    }
}
package ecommerce.model;

public class Produto {

    private static int contador = 1;

    private int id;
    private String nome;
    private double preco;
    private int qntEstoque;

    public Produto(String nome, double preco, int qntEstoque) {
        this.id         = contador++;
        this.nome       = nome;
        this.preco      = preco;
        this.qntEstoque = qntEstoque;
    }

    public boolean checkEstoque(int quantidade) {
        return qntEstoque >= quantidade;
    }

    public void reduzirEstoque(int quantidade) {
        if (!checkEstoque(quantidade)) {
            throw new IllegalStateException("Estoque insuficiente para: " + nome);
        }
        this.qntEstoque -= quantidade;
    }

    public void atualizarProduto(String novoNome, double novoPreco, int novoEstoque) {
        if (novoNome  != null && !novoNome.isBlank()) this.nome  = novoNome;
        if (novoPreco > 0)                            this.preco = novoPreco;
        if (novoEstoque >= 0)                         this.qntEstoque = novoEstoque;
    }

    public int    getId()         { return id; }
    public String getNome()       { return nome; }
    public double getPreco()      { return preco; }
    public int    getQntEstoque() { return qntEstoque; }

    @Override
    public String toString() {
        return String.format("[%d] %s - R$ %.2f (estoque: %d)", id, nome, preco, qntEstoque);
    }
}
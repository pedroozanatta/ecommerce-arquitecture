package ecommerce;

import java.util.UUID;

public class Produto {

    private String id;
    private String nome;
    private double preco;
    private int qntEstoque;

    public Produto(String nome, double preco, int qntEstoque) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.preco = preco;
        this.qntEstoque = qntEstoque;
    }

    public boolean checkEstoque(int quantidade) {
        return qntEstoque >= quantidade;
    }

    public void reduzirEstoque(int quantidade) {
        if (!checkEstoque(quantidade)) {
            throw new IllegalStateException("Estoque insuficiente para o produto: " + nome);
        }
        this.qntEstoque -= quantidade;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public int getQntEstoque() { return qntEstoque; }

    @Override
    public String toString() {
        return String.format("Produto[%s] - R$ %.2f (estoque: %d)", nome, preco, qntEstoque);
    }
}

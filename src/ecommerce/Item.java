package ecommerce;

import java.util.UUID;

public class Item {

    private String id;
    private Produto produto;
    private int quantidade;
    private double precoUnitario;
    private double desconto;

    public Item(Produto produto, int quantidade) {
        this.id = UUID.randomUUID().toString();
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
        this.desconto = 0.0;
    }

    public double subtotal() {
        return (precoUnitario * quantidade) - desconto;
    }

    public void aplicarDesconto(double valorDesconto) {
        if (valorDesconto < 0 || valorDesconto > subtotal()) {
            throw new IllegalArgumentException("Desconto inválido: " + valorDesconto);
        }
        this.desconto = valorDesconto;
    }

    public Produto escolherProduto() {
        return produto;
    }

    public String getId() { return id; }
    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }
    public double getPrecoUnitario() { return precoUnitario; }
    public double getDesconto() { return desconto; }

    @Override
    public String toString() {
        return String.format("  - %s x%d  R$ %.2f (desconto: R$ %.2f) = R$ %.2f",
                produto.getNome(), quantidade, precoUnitario, desconto, subtotal());
    }
}

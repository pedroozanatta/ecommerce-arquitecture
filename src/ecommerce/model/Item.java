package ecommerce.model;

public class Item {

    private static int contador = 1;

    private int     id;
    private int     qnt;
    private double  desconto;
    private double  precoUnitario;
    private Produto produto;

    public Item(Produto produto, int qnt) {
        this.id           = contador++;
        this.produto      = produto;
        this.qnt          = qnt;
        this.precoUnitario = produto.getPreco();
        this.desconto     = 0.0;
    }

    public Produto escolherProduto() {
        return produto;
    }

    public void aplicarDesconto(double valor) {
        if (valor < 0 || valor > precoUnitario * qnt) {
            throw new IllegalArgumentException("Desconto inválido.");
        }
        this.desconto = valor;
    }

    public double subtotal() {
        return (precoUnitario * qnt) - desconto;
    }

    public int    getId()           { return id; }
    public int    getQnt()          { return qnt; }
    public double getDesconto()     { return desconto; }
    public double getPrecoUnitario(){ return precoUnitario; }
    public Produto getProduto()     { return produto; }

    @Override
    public String toString() {
        return String.format("  Item[%d] %s x%d  R$ %.2f (desc: R$ %.2f) = R$ %.2f",
                id, produto.getNome(), qnt, precoUnitario, desconto, subtotal());
    }
}
package ecommerce.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private static int contador = 1;

    private int id;
    private String email;
    private String senha;
    private Carrinho carrinho;
    private List<Pedido> pedidos;
    private List<Endereco> enderecos;

    public Usuario(String email, String senha) {
        this.id = contador++;
        this.email = email;
        this.senha = senha;
        this.carrinho = new Carrinho();
        this.pedidos = new ArrayList<>();
        this.enderecos = new ArrayList<>();
    }

    public void adicionarCarrinho(Produto produto, int quantidade) {
        carrinho.adicionarItem(produto, quantidade);
    }

    public Pedido finalizarCompra(Endereco endereco) {
        Pedido pedido = carrinho.criarPedido(endereco);
        pedidos.add(pedido);
        return pedido;
    }

    public void adicionarEndereco(Endereco endereco) {
        enderecos.add(endereco);
    }

    public boolean autenticar(String senhaDigitada) {
        return this.senha.equals(senhaDigitada);
    }

    public int getId() { return id; }
    public String getEmail() { return email; }
    public Carrinho getCarrinho() { return carrinho; }
    public List<Pedido> getPedidos() { return pedidos; }
    public List<Endereco> getEnderecos() { return enderecos; }

    @Override
    public String toString() {
        return "Usuario[#" + id + " - " + email + "]";
    }
}
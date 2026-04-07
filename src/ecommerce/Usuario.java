package ecommerce.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Usuario {

    private String id;
    private String email;
    private String passwordHash;
    private List<Pedido> pedidos;
    private List<Endereco> enderecos;
    private Carrinho carrinho;

    public Usuario(String email, String senha) {
        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.passwordHash = Integer.toHexString(senha.hashCode());
        this.pedidos = new ArrayList<>();
        this.enderecos = new ArrayList<>();
        this.carrinho = new Carrinho();
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

    public String getId() { return id; }
    public String getEmail() { return email; }
    public List<Pedido> getPedidos() { return pedidos; }
    public List<Endereco> getEnderecos() { return enderecos; }
    public Carrinho getCarrinho() { return carrinho; }

    @Override
    public String toString() {
        return "Usuario[" + email + "]";
    }
}
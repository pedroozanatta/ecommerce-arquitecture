package ecommerce.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Carrinho {

    private List<Item> itensCarrinho;

    public Carrinho() {
        this.itensCarrinho = new ArrayList<>();
    }

    public void adicionarItem(Produto produto, int quantidade) {
        if (!produto.checkEstoque(quantidade)) {
            throw new IllegalStateException("Estoque insuficiente para: " + produto.getNome());
        }
        itensCarrinho.stream()
                .filter(i -> i.getProduto().getId().equals(produto.getId()))
                .findFirst()
                .ifPresentOrElse(
                        i -> System.out.println("Produto já no carrinho. Atualize a quantidade manualmente."),
                        () -> itensCarrinho.add(new Item(produto, quantidade))
                );
    }

    public void removerItem(String produtoId) {
        itensCarrinho.removeIf(i -> i.getProduto().getId().equals(produtoId));
    }

    public double totalCarrinho() {
        return itensCarrinho.stream().mapToDouble(Item::subtotal).sum();
    }

    public Pedido criarPedido(Endereco endereco) {
        if (itensCarrinho.isEmpty()) {
            throw new IllegalStateException("Carrinho vazio. Adicione itens antes de criar o pedido.");
        }
        Pedido pedido = new Pedido(endereco);
        for (Item item : itensCarrinho) {
            pedido.adicionarItem(new Item(item.getProduto(), item.getQuantidade()));
        }
        itensCarrinho.clear();
        System.out.println("Pedido criado a partir do carrinho!");
        return pedido;
    }

    public List<Item> getItensCarrinho() {
        return Collections.unmodifiableList(itensCarrinho);
    }

    public void exibir() {
        System.out.println("\n--- CARRINHO ---");
        if (itensCarrinho.isEmpty()) {
            System.out.println("Carrinho vazio.");
        } else {
            itensCarrinho.forEach(System.out::println);
            System.out.printf("Total: R$ %.2f%n", totalCarrinho());
        }
        System.out.println("----------------\n");
    }
}
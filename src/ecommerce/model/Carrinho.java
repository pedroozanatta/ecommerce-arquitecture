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
        // Se produto já está no carrinho, não duplica
        boolean jaExiste = itensCarrinho.stream()
                .anyMatch(i -> i.getProduto().getId() == produto.getId());
        if (jaExiste) {
            System.out.println("  Produto já está no carrinho.");
            return;
        }
        itensCarrinho.add(new Item(produto, quantidade));
        System.out.println("  Adicionado: " + produto.getNome() + " x" + quantidade);
    }

    public void removerItem(int produtoId) {
        boolean removido = itensCarrinho.removeIf(i -> i.getProduto().getId() == produtoId);
        if (!removido) System.out.println("  Produto não encontrado no carrinho.");
    }

    public double totalCarrinho() {
        return itensCarrinho.stream().mapToDouble(Item::subtotal).sum();
    }

    public Pedido criarPedido(Endereco endereco) {
        if (itensCarrinho.isEmpty()) {
            throw new IllegalStateException("Carrinho vazio.");
        }
        Pedido pedido = new Pedido(endereco);
        for (Item item : itensCarrinho) {
            item.getProduto().reduzirEstoque(item.getQnt());
            pedido.adicionarItem(item);
        }
        itensCarrinho.clear();
        return pedido;
    }

    public List<Item> getItensCarrinho() {
        return Collections.unmodifiableList(itensCarrinho);
    }

    public boolean isEmpty() {
        return itensCarrinho.isEmpty();
    }

    public void exibir() {
        System.out.println("\n  --- CARRINHO ---");
        if (itensCarrinho.isEmpty()) {
            System.out.println("  Carrinho vazio.");
        } else {
            itensCarrinho.forEach(System.out::println);
            System.out.printf("  Subtotal: R$ %.2f%n", totalCarrinho());
        }
    }
}
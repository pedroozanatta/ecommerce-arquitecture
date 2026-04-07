package ecommerce.model;

import ecommerce.model.enums.OrderStatus;
import ecommerce.model.pagamento.Pagamento;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Pedido {

    private String id;
    private double precoTotal;
    private LocalDateTime createdAt;
    private OrderStatus status;
    private List<Item> itens;
    private Transporte transporte;
    private List<Pagamento> pagamentos;
    private Endereco enderecoEntrega;

    public Pedido(Endereco enderecoEntrega) {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.PENDENTE;
        this.itens = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
        this.enderecoEntrega = enderecoEntrega;
        this.precoTotal = 0.0;
    }

    public void adicionarItem(Item item) {
        if (status != OrderStatus.PENDENTE) {
            throw new IllegalStateException("Não é possível adicionar itens a um pedido " + status);
        }
        item.getProduto().reduzirEstoque(item.getQuantidade());
        itens.add(item);
        calcularTotal();
    }

    public void calcularTotal() {
        double subtotal = itens.stream().mapToDouble(Item::subtotal).sum();
        double frete = transporte != null ? transporte.getCusto() : 0.0;
        this.precoTotal = subtotal + frete;
    }

    public void definirTransporte(Transporte transporte) {
        this.transporte = transporte;
        calcularTotal();
    }

    public void adicionarPagamento(Pagamento pagamento) {
        pagamentos.add(pagamento);
    }

    public void confirmar() {
        if (itens.isEmpty()) {
            throw new IllegalStateException("Pedido sem itens não pode ser confirmado.");
        }
        this.status = OrderStatus.CONFIRMADO;
        System.out.println("Pedido " + id + " confirmado!");
    }

    public void cancelarPedido() {
        if (status == OrderStatus.ENVIADO || status == OrderStatus.ENTREGUE) {
            throw new IllegalStateException("Não é possível cancelar um pedido já enviado ou entregue.");
        }
        this.status = OrderStatus.CANCELADO;
        System.out.println("Pedido " + id + " cancelado.");
    }

    public String getId() { return id; }
    public double getPrecoTotal() { return precoTotal; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public OrderStatus getStatus() { return status; }
    public List<Item> getItens() { return Collections.unmodifiableList(itens); }
    public Transporte getTransporte() { return transporte; }
    public List<Pagamento> getPagamentos() { return Collections.unmodifiableList(pagamentos); }
    public Endereco getEnderecoEntrega() { return enderecoEntrega; }

    public void exibirResumo() {
        System.out.println("\n========== RESUMO DO PEDIDO ==========");
        System.out.println("ID: " + id);
        System.out.println("Status: " + status);
        System.out.println("Data: " + createdAt);
        System.out.println("Endereço: " + enderecoEntrega);
        System.out.println("Itens:");
        itens.forEach(System.out::println);
        if (transporte != null) System.out.println("Frete: " + transporte);
        System.out.printf("Total: R$ %.2f%n", precoTotal);
        if (!pagamentos.isEmpty()) {
            System.out.println("Pagamentos:");
            pagamentos.forEach(p -> System.out.println("  " + p));
        }
        System.out.println("======================================\n");
    }
}
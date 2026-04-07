package ecommerce.model;

import ecommerce.model.pagamento.Pagamento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {

    private static int contador = 1;

    private int id;
    private double precoTotal;
    private LocalDateTime createdAt;
    private String status;
    private List<Item> itens;
    private Transporte transporte;
    private List<Pagamento> pagamentos;
    private Endereco enderecoEntrega;

    public Pedido(Endereco enderecoEntrega) {
        this.id = contador++;
        this.createdAt = LocalDateTime.now();
        this.status = "PENDENTE";
        this.itens = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
        this.enderecoEntrega = enderecoEntrega;
        this.precoTotal = 0.0;
    }

    public void adicionarItem(Item item) {
        if (!status.equals("PENDENTE")) {
            throw new IllegalStateException("Pedido não está pendente. Status atual: " + status);
        }
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
        this.pagamentos.add(pagamento);
    }

    public void confirmar() {
        if (itens.isEmpty()) {
            throw new IllegalStateException("Pedido sem itens não pode ser confirmado.");
        }
        this.status = "CONFIRMADO";
        System.out.println("  Pedido #" + id + " CONFIRMADO!");
    }

    public void cancelarPedido() {
        if (status.equals("ENVIADO") || status.equals("ENTREGUE")) {
            throw new IllegalStateException("Não é possível cancelar: pedido já foi " + status);
        }
        this.status = "CANCELADO";
        System.out.println("  Pedido #" + id + " CANCELADO.");
    }

    public int getId() { return id; }
    public double getPrecoTotal() { return precoTotal; }
    public String getStatus() { return status; }
    public Transporte getTransporte() { return transporte; }
    public Endereco getEnderecoEntrega() { return enderecoEntrega; }
    public List<Item> getItens() { return Collections.unmodifiableList(itens); }
    public List<Pagamento> getPagamentos() { return Collections.unmodifiableList(pagamentos); }

    public void exibirResumo() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("       RESUMO DO PEDIDO #" + id);
        System.out.println("  Status   : " + status);
        System.out.println("  Data     : " + createdAt.format(fmt));
        System.out.println("  Endereço : " + enderecoEntrega);
        System.out.println("  Itens:");
        itens.forEach(System.out::println);
        if (transporte != null) {
            System.out.println("  Frete    : " + transporte);
        }
        System.out.printf("  TOTAL    : R$ %.2f%n", precoTotal);
        if (!pagamentos.isEmpty()) {
            System.out.println("  Pgto     : " + pagamentos.get(pagamentos.size() - 1));
        }
    }
}
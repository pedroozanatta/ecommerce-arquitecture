package ecommerce.model.pagamento;

import ecommerce.model.enums.PaymentStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Pagamento {

    protected String id;
    protected double valor;
    protected LocalDateTime pagoEm;
    protected PaymentStatus status;

    public Pagamento(double valor) {
        this.id = UUID.randomUUID().toString();
        this.valor = valor;
        this.status = PaymentStatus.PENDENTE;
    }

    public abstract void processarPagamento();

    public void reembolsar() {
        if (status != PaymentStatus.APROVADO) {
            throw new IllegalStateException("Só é possível reembolsar pagamentos aprovados.");
        }
        this.status = PaymentStatus.REEMBOLSADO;
        System.out.println("Reembolso de R$ " + String.format("%.2f", valor) + " processado.");
    }

    protected void aprovar() {
        this.status = PaymentStatus.APROVADO;
        this.pagoEm = LocalDateTime.now();
    }

    protected void falhar() {
        this.status = PaymentStatus.FALHO;
    }

    public String getId() { return id; }
    public double getValor() { return valor; }
    public PaymentStatus getStatus() { return status; }
    public LocalDateTime getPagoEm() { return pagoEm; }
}
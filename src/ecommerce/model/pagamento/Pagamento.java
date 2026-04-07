package ecommerce.model.pagamento;

import ecommerce.model.enums.PaymentStatus;
import java.time.LocalDateTime;

public abstract class Pagamento {

    private static int contador = 1;

    protected int           id;
    protected double        valor;
    protected LocalDateTime pagoEm;
    protected boolean       status;   // true = aprovado, false = não aprovado
    protected PaymentStatus paymentStatus;

    public Pagamento(double valor) {
        this.id            = contador++;
        this.valor         = valor;
        this.status        = false;
        this.paymentStatus = PaymentStatus.PENDENTE;
    }

    public abstract void processarPagamento();

    public void reembolsar() {
        if (paymentStatus != PaymentStatus.APROVADO) {
            throw new IllegalStateException("Só é possível reembolsar pagamentos aprovados.");
        }
        this.paymentStatus = PaymentStatus.REEMBOLSADO;
        this.status        = false;
        System.out.println("  Reembolso de R$ " + String.format("%.2f", valor) + " processado.");
    }

    protected void aprovar() {
        this.status        = true;
        this.paymentStatus = PaymentStatus.APROVADO;
        this.pagoEm        = LocalDateTime.now();
    }

    protected void recusar() {
        this.status        = false;
        this.paymentStatus = PaymentStatus.FALHO;
    }

    public int           getId()            { return id; }
    public double        getValor()         { return valor; }
    public boolean       getStatus()        { return status; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public LocalDateTime getPagoEm()        { return pagoEm; }
}
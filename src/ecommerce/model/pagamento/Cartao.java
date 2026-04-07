package ecommerce.model.pagamento;

public class Cartao extends Pagamento {

    private int cvv;
    private String bandeira;
    private int parcelas;

    public Cartao(double valor, String bandeira, int parcelas, int cvv) {
        super(valor);
        this.bandeira = bandeira;
        this.parcelas = Math.max(1, parcelas);
        this.cvv = cvv;
    }

    @Override
    public void processarPagamento() {
        System.out.println("\n  PAGAMENTO COM CARTÃO");
        System.out.println("  Bandeira  : " + bandeira);
        System.out.println("  Parcelas  : " + parcelas + "x de R$ " + String.format("%.2f", valorParcela()));
        System.out.printf("  Total     : R$ %.2f%n", valor);

        if (autorizarSimulado()) {
            aprovar();
            System.out.println("  Cartão autorizado! Status: " + paymentStatus);
        } else {
            recusar();
            System.out.println("  Cartão recusado.    Status: " + paymentStatus);
        }
    }

    private boolean autorizarSimulado() {
        return Math.random() > 0.10;
    }

    public double valorParcela() {
        return valor / parcelas;
    }

    public String getBandeira() { return bandeira; }
    public int getParcelas() { return parcelas; }

    @Override
    public String toString() {
        return String.format("Cartao[bandeira=%s, %dx, status=%s]", bandeira, parcelas, paymentStatus);
    }
}
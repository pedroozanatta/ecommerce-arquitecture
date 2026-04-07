package ecommerce.model.pagamento;

import java.util.UUID;

public class Cartao extends Pagamento {

    private String cardToken;
    private String bandeira;
    private int parcelas;

    public Cartao(double valor, String bandeira, int parcelas) {
        super(valor);
        this.bandeira = bandeira;
        this.parcelas = parcelas < 1 ? 1 : parcelas;
        this.cardToken = UUID.randomUUID().toString();
    }

    @Override
    public void processarPagamento() {
        System.out.println("=== PAGAMENTO COM CARTÃO ===");
        System.out.println("Bandeira: " + bandeira);
        System.out.println("Token: " + cardToken);
        System.out.println("Parcelas: " + parcelas + "x de R$ " + String.format("%.2f", valorParcela()));
        System.out.printf("Total: R$ %.2f%n", valor);

        boolean aprovado = simularAutorizacao();
        if (aprovado) {
            aprovar();
            System.out.println("Cartão autorizado! Status: " + status);
        } else {
            falhar();
            System.out.println("Cartão recusado. Status: " + status);
        }
    }

    private boolean simularAutorizacao() {
        return Math.random() > 0.1;
    }

    public double valorParcela() {
        return valor / parcelas;
    }

    public String getCardToken() { return cardToken; }
    public String getBandeira() { return bandeira; }
    public int getParcelas() { return parcelas; }

    @Override
    public String toString() {
        return String.format("Cartao[bandeira=%s, parcelas=%d, status=%s]", bandeira, parcelas, status);
    }
}
package ecommerce;

import ecommerce.model.pagamento.Pagamento;
import java.time.LocalDate;
import java.util.UUID;

public class Boleto extends Pagamento{

    private String codigoBarras;
    private LocalDate dataLimite;
    private String banco;

    public Boleto(double valor, String banco) {
        super(valor);
        this.banco = banco;
        this.dataLimite = LocalDate.now().plusDays(3);
        this.codigoBarras = gerarCodigoBarras();
    }

    private String gerarCodigoBarras() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 24).toUpperCase();
    }

    @Override
    public void processarPagamento() {
        System.out.println("=== BOLETO GERADO ===");
        System.out.println("Banco: " + banco);
        System.out.println("Código de barras: " + codigoBarras);
        System.out.println("Vencimento: " + dataLimite);
        System.out.printf("Valor: R$ %.2f%n", valor);
        aprovar();
        System.out.println("Status: " + status);
    }

    public String getCodigoBarras() { return codigoBarras; }
    public LocalDate getDataLimite() { return dataLimite; }
    public String getBanco() { return banco; }

    @Override
    public String toString() {
        return String.format("Boleto[banco=%s, venc=%s, status=%s]", banco, dataLimite, status);
    }
}
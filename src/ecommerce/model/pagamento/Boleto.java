package ecommerce.model.pagamento;

import java.time.LocalDate;

public class Boleto extends Pagamento {

    private String    codigoBarras;
    private LocalDate dataLimite;
    private String    banco;

    public Boleto(double valor, String banco) {
        super(valor);
        this.banco      = banco;
        this.dataLimite = LocalDate.now().plusDays(3);
        this.codigoBarras = gerarCodigo();
    }

    private String gerarCodigo() {
        return String.format("%04d.%05d %05d.%06d %05d.%06d %d %14d",
                id, (int)(Math.random() * 99999),
                (int)(Math.random() * 99999), (int)(Math.random() * 999999),
                (int)(Math.random() * 99999), (int)(Math.random() * 999999),
                (int)(Math.random() * 9),     (long)(Math.random() * 99999999999999L));
    }

    @Override
    public void processarPagamento() {
        System.out.println("\n  === BOLETO GERADO ===");
        System.out.println("  Banco       : " + banco);
        System.out.println("  Cód. Barras : " + codigoBarras);
        System.out.println("  Vencimento  : " + dataLimite);
        System.out.printf ("  Valor       : R$ %.2f%n", valor);
        aprovar();
        System.out.println("  Status      : " + paymentStatus);
    }

    public String    getCodigoBarras() { return codigoBarras; }
    public LocalDate getDataLimite()   { return dataLimite; }
    public String    getBanco()        { return banco; }

    @Override
    public String toString() {
        return String.format("Boleto[banco=%s, venc=%s, status=%s]", banco, dataLimite, paymentStatus);
    }
}
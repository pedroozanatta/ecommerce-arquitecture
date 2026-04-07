package ecommerce;

import ecommerce.model.pagamento.Pagamento;
import java.time.LocalDateTime;
import java.util.UUID;

public class Pix extends Pagamento {

    private String chave;
    private String qrCode;
    private LocalDateTime expiraEm;

    public Pix(double valor, String chave) {
        super(valor);
        this.chave = chave;
        this.expiraEm = LocalDateTime.now().plusMinutes(30);
        this.qrCode = gerarQrCode();
    }

    private String gerarQrCode() {
        return "PIX-QR-" + UUID.randomUUID().toString().toUpperCase();
    }

    @Override
    public void processarPagamento() {
        System.out.println("=== PIX GERADO ===");
        System.out.println("Chave Pix: " + chave);
        System.out.println("QR Code: " + qrCode);
        System.out.println("Expira em: " + expiraEm);
        System.out.printf("Valor: R$ %.2f%n", valor);

        if (LocalDateTime.now().isBefore(expiraEm)) {
            aprovar();
            System.out.println("Pix confirmado! Status: " + status);
        } else {
            falhar();
            System.out.println("Pix expirado. Status: " + status);
        }
    }

    public String getChave() { return chave; }
    public String getQrCode() { return qrCode; }
    public LocalDateTime getExpiraEm() { return expiraEm; }

    @Override
    public String toString() {
        return String.format("Pix[chave=%s, expira=%s, status=%s]", chave, expiraEm, status);
    }
}
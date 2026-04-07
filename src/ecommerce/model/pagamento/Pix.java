package ecommerce.model.pagamento;

import java.time.LocalDateTime;

public class Pix extends Pagamento {

    private String        chave;
    private String        qrCode;
    private LocalDateTime expiraEm;

    public Pix(double valor, String chave) {
        super(valor);
        this.chave    = chave;
        this.expiraEm = LocalDateTime.now().plusMinutes(30);
        this.qrCode   = gerarQrCode();
    }

    private String gerarQrCode() {
        return "00020126360014BR.GOV.BCB.PIX" + chave.replaceAll("[^a-zA-Z0-9]", "") + "520400005303986"
                + String.format("54%02d%.2f", String.format("%.2f", valor).length(), valor)
                + "5802BR5913ECOMMERCE6009SAO PAULO62070503***6304ABCD";
    }

    @Override
    public void processarPagamento() {
        System.out.println("\n  === PIX GERADO ===");
        System.out.println("  Chave Pix : " + chave);
        System.out.println("  QR Code   : " + qrCode.substring(0, Math.min(40, qrCode.length())) + "...");
        System.out.println("  Expira em : " + expiraEm);
        System.out.printf ("  Valor     : R$ %.2f%n", valor);

        if (LocalDateTime.now().isBefore(expiraEm)) {
            aprovar();
            System.out.println("  Pix confirmado! Status: " + paymentStatus);
        } else {
            recusar();
            System.out.println("  Pix expirado. Status: " + paymentStatus);
        }
    }

    public String        getChave()    { return chave; }
    public String        getQrCode()   { return qrCode; }
    public LocalDateTime getExpiraEm() { return expiraEm; }

    @Override
    public String toString() {
        return String.format("Pix[chave=%s, expira=%s, status=%s]", chave, expiraEm, paymentStatus);
    }
}
package ecommerce.model;

public class Transporte {

    private static int contador = 1;

    private int id;
    private String metodo;
    private double custo;
    private String codigoRastreio;
    private int qntDias;

    public Transporte(String metodo) {
        this.id = contador++;
        this.metodo = metodo.toUpperCase();
        calcularFrete();
    }

    private void calcularFrete() {
        switch (metodo) {
            case "PAC"      -> { this.custo = 15.90; this.qntDias = 10; }
            case "SEDEX"    -> { this.custo = 29.90; this.qntDias = 3;  }
            case "EXPRESS"  -> { this.custo = 49.90; this.qntDias = 1;  }
            case "RETIRADA" -> { this.custo = 0.0;   this.qntDias = 0;  }
            default         -> { this.custo = 20.00; this.qntDias = 7;  }
        }
    }

    public double calcularFrete(String destino) {
        System.out.println("  Calculando frete para: " + destino);
        return custo;
    }

    public String tempoEntrega() {
        return qntDias == 0 ? "Retirada na loja" : qntDias + " dia(s) úteis";
    }

    public void gerarRastreio() {
        this.codigoRastreio = "BR" + String.format("%06d", id) + metodo.substring(0, 2).toUpperCase();
        System.out.println("  Código de rastreio gerado: " + codigoRastreio);
    }

    public int getId() { return id; }
    public String getMetodo() { return metodo; }
    public double getCusto() { return custo; }
    public String getCodigoRastreio() { return codigoRastreio; }
    public int getQntDias() { return qntDias; }

    @Override
    public String toString() {
        return String.format("Transporte[%s] R$ %.2f | Prazo: %s | Rastreio: %s",
                metodo, custo, tempoEntrega(),
                codigoRastreio != null ? codigoRastreio : "não gerado");
    }
}
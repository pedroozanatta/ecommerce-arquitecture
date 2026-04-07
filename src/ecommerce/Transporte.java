package ecommerce.model;

import java.util.UUID;

public class Transporte {

    private String id;
    private String metodo;
    private double custo;
    private String codigoRastreio;
    private int qntDias;
    private String status;

    public Transporte(String metodo) {
        this.id = UUID.randomUUID().toString();
        this.metodo = metodo;
        this.codigoRastreio = null;
        this.status = "PENDENTE";
        calcularFrete();
    }

    private void calcularFrete() {
        if (metodo == null) {
            this.custo = 0.0;
            this.qntDias = 0;
            return;
        }

        switch (metodo.toUpperCase()) {
            case "PAC"      -> { this.custo = 15.90; this.qntDias = 10; }
            case "SEDEX"    -> { this.custo = 29.90; this.qntDias = 3;  }
            case "EXPRESS"  -> { this.custo = 49.90; this.qntDias = 1;  }
            case "RETIRADA" -> { this.custo = 0.0;   this.qntDias = 0;  }
            case "GRATIS"   -> { this.custo = 0.0;   this.qntDias = 7;  }
            default         -> { this.custo = 0.0;   this.qntDias = 0;  }
        }
    }

    public double getCusto() { return custo; }

    public int tempoEntrega() { return qntDias; }

    public void gerarRastreio() {
        this.codigoRastreio = "BR" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }

    public String getId() { return id; }
    public String getMetodo() { return metodo; }
    public String getCodigoRastreio() { return codigoRastreio; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("Transporte[%s] - R$ %.2f | Prazo: %d dia(s) | Rastreio: %s | Status: %s",
                metodo, custo, qntDias, codigoRastreio != null ? codigoRastreio : "não gerado", status);
    }
}


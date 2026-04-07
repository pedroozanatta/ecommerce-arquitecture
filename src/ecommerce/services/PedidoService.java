package ecommerce.service;

import ecommerce.model.Pedido;
import ecommerce.model.Transporte;
import ecommerce.model.enums.PaymentStatus;
import ecommerce.model.pagamento.Pagamento;

public class PedidoService {

    public void definirTransporte(Pedido pedido, Transporte transporte) {
        pedido.definirTransporte(transporte);
        System.out.println("  Frete aplicado: " + transporte);
    }

    public void processarPagamento(Pedido pedido, Pagamento pagamento) {
        System.out.println("\n>> Processando pagamento do pedido #" + pedido.getId());
        pagamento.processarPagamento();
        pedido.adicionarPagamento(pagamento);

        if (pagamento.getPaymentStatus() == PaymentStatus.APROVADO) {
            pedido.confirmar();
        } else {
            System.out.println("  Pagamento não aprovado. Pedido segue PENDENTE.");
        }
    }

    public void enviarPedido(Pedido pedido) {
        if (pedido.getTransporte() == null) {
            System.out.println("  Defina um transporte antes de enviar.");
            return;
        }
        pedido.getTransporte().gerarRastreio();
        System.out.println("  Pedido #" + pedido.getId() + " enviado! " + pedido.getTransporte());
    }
}
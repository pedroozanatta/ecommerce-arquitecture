package ecommerce.service;

import ecommerce.model.Pedido;
import ecommerce.model.Transporte;
import ecommerce.model.enums.PaymentStatus;
import ecommerce.model.pagamento.Pagamento;

public class PedidoService {

    public void processarPagamento(Pedido pedido, Pagamento pagamento) {
        System.out.println("\n>> Processando pagamento para o pedido: " + pedido.getId());
        pagamento.processarPagamento();
        pedido.adicionarPagamento(pagamento);

        if (pagamento.getStatus() == PaymentStatus.APROVADO) {
            pedido.confirmar();
        } else {
            System.out.println("Pagamento não aprovado. Pedido permanece " + pedido.getStatus());
        }
    }

    public void definirTransporte(Pedido pedido, Transporte transporte) {
        pedido.definirTransporte(transporte);
        System.out.println("Transporte definido: " + transporte);
    }

    public void enviarPedido(Pedido pedido) {
        pedido.getTransporte().gerarRastreio();
        System.out.println("Pedido enviado! " + pedido.getTransporte());
    }
}
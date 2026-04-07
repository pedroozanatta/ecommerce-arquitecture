package ecommerce.main;

import ecommerce.model.*;
import ecommerce.model.enums.ShippingMethod;
import ecommerce.model.pagamento.*;
import ecommerce.service.PedidoService;

public class Main {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   SISTEMA DE E-COMMERCE - POO JAVA    ");
        System.out.println("========================================\n");

        // --- Produtos ---
        Produto notebook = new Produto("Notebook Dell", 3500.00, 10);
        Produto mouse    = new Produto("Mouse Logitech", 120.00, 50);
        Produto teclado  = new Produto("Teclado Mecânico", 250.00, 30);

        System.out.println("Produtos cadastrados:");
        System.out.println(notebook);
        System.out.println(mouse);
        System.out.println(teclado);

        // --- Usuário ---
        Usuario usuario = new Usuario("joao@email.com", "senha123");
        System.out.println("\nUsuário criado: " + usuario);

        // --- Endereço ---
        Endereco endereco = new Endereco(
                "Rua das Flores, 123", "Centro",
                "12345-678", "São Paulo", "SP"
        );
        System.out.println("Endereço válido? " + endereco.validarEndereco());
        usuario.adicionarEndereco(endereco);

        // --- Carrinho (adicionar itens) ---
        System.out.println("\n--- Adicionando itens ao carrinho ---");
        usuario.adicionarCarrinho(notebook, 1);
        usuario.adicionarCarrinho(mouse, 2);
        usuario.adicionarCarrinho(teclado, 1);
        usuario.getCarrinho().exibir();

        // --- Criar Pedido a partir do Carrinho ---
        Pedido pedido = usuario.finalizarCompra(endereco);

        // --- Definir Transporte ---
        PedidoService pedidoService = new PedidoService();
        Transporte transporte = new Transporte(ShippingMethod.SEDEX);
        pedidoService.definirTransporte(pedido, transporte);

        pedido.exibirResumo();

        // ===== DEMONSTRAÇÃO DE POLIMORFISMO =====
        // Cada forma de pagamento é uma subclasse de Pagamento (herança + polimorfismo)
        System.out.println("\n======= DEMO POLIMORFISMO: PAGAMENTO =======");
        demonstrarPolimorfismo(pedido.getPrecoTotal());

        // --- Pagamento real do pedido (Pix) ---
        System.out.println("\n--- Realizando pagamento do pedido ---");
        Pagamento pagamentoPix = new Pix(pedido.getPrecoTotal(), "joao@email.com");
        pedidoService.processarPagamento(pedido, pagamentoPix);

        // --- Enviar pedido ---
        pedidoService.enviarPedido(pedido);

        // --- Resumo final ---
        pedido.exibirResumo();

        // --- Demonstrar cancelamento e reembolso ---
        System.out.println("--- Reembolso do pagamento Pix ---");
        pagamentoPix.reembolsar();
        System.out.println("Status do pagamento: " + pagamentoPix.getStatus());
    }

    /**
     * Demonstra polimorfismo: mesma chamada processarPagamento()
     * comporta-se de forma diferente dependendo da subclasse.
     */
    private static void demonstrarPolimorfismo(double valor) {
        // Array de Pagamento — tipo da referência é a classe abstrata
        Pagamento[] formasDePagamento = {
                new Boleto(valor, "Banco do Brasil"),
                new Pix(valor, "11999999999"),
                new Cartao(valor, "VISA", 3)
        };

        for (Pagamento p : formasDePagamento) {
            System.out.println("\nTipo: " + p.getClass().getSimpleName());
            p.processarPagamento(); // polimorfismo em ação
        }
    }
}
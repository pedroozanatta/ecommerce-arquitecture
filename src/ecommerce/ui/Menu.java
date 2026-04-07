package ecommerce.ui;

import ecommerce.model.*;
import ecommerce.model.pagamento.*;
import ecommerce.service.PedidoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final Scanner       scanner     = new Scanner(System.in);
    private final PedidoService pedidoService = new PedidoService();
    private final List<Produto> catalogo    = new ArrayList<>();
    private final List<Usuario> usuarios    = new ArrayList<>();

    private Usuario usuarioLogado = null;
    private Pedido  pedidoAtual  = null;

    public Menu() {
        carregarCatalogo();
    }

    private void carregarCatalogo() {
        catalogo.add(new Produto("Notebook Dell",      3500.00, 10));
        catalogo.add(new Produto("Mouse Logitech",      120.00, 50));
        catalogo.add(new Produto("Teclado Mecânico",    250.00, 30));
        catalogo.add(new Produto("Monitor 24\"",       1200.00, 15));
        catalogo.add(new Produto("Headset Gamer",       350.00, 20));
    }

    public void iniciar() {
        int opcao;
        do {
            if (usuarioLogado == null) {
                opcao = menuInicial();
            } else {
                opcao = menuPrincipal();
            }
        } while (opcao != 0);

        System.out.println("\nAté logo!");
        scanner.close();
    }

    private int menuInicial() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("       E-COMMERCE JAVA          ");
        System.out.println("╚══════════════════════════════╝");
        System.out.println("  1. Cadastrar usuário");
        System.out.println("  2. Fazer login");
        System.out.println("  0. Sair");
        System.out.print("  Opção: ");

        int opcao = lerInt();
        switch (opcao) {
            case 1 -> cadastrarUsuario();
            case 2 -> login();
            case 0 -> { }
            default -> System.out.println("  Opção inválida.");
        }
        return opcao;
    }

    private int menuPrincipal() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("  Olá, " + usuarioLogado.getEmail());
        System.out.println("╚══════════════════════════════╝");
        System.out.println("  1. Ver catálogo de produtos");
        System.out.println("  2. Adicionar produto ao carrinho");
        System.out.println("  3. Ver carrinho");
        System.out.println("  4. Remover item do carrinho");
        System.out.println("  5. Finalizar compra (gerar pedido)");
        System.out.println("  6. Ver meus pedidos");
        System.out.println("  7. Sair da conta");
        System.out.println("  0. Encerrar sistema");
        System.out.print("  Opção: ");

        int opcao = lerInt();
        switch (opcao) {
            case 1 -> listarCatalogo();
            case 2 -> adicionarAoCarrinho();
            case 3 -> usuarioLogado.getCarrinho().exibir();
            case 4 -> removerDoCarrinho();
            case 5 -> fluxoFinalizarCompra();
            case 6 -> listarPedidos();
            case 7 -> { usuarioLogado = null; pedidoAtual = null;
                System.out.println("  Logout realizado."); }
            case 0 -> { }
            default -> System.out.println("  Opção inválida.");
        }
        return opcao;
    }

    private void cadastrarUsuario() {
        System.out.println("\n  -- CADASTRO --");
        System.out.print("  E-mail : ");
        String email = scanner.nextLine().trim();

        boolean jaExiste = usuarios.stream().anyMatch(u -> u.getEmail().equals(email));
        if (jaExiste) { System.out.println("  E-mail já cadastrado."); return; }

        System.out.print("  Senha  : ");
        String senha = scanner.nextLine().trim();

        Usuario novo = new Usuario(email, senha);
        usuarios.add(novo);
        System.out.println("  Usuário cadastrado com sucesso!");
    }

    private void login() {
        System.out.println("\n  -- LOGIN --");
        System.out.print("  E-mail : ");
        String email = scanner.nextLine().trim();
        System.out.print("  Senha  : ");
        String senha = scanner.nextLine().trim();

        usuarioLogado = usuarios.stream()
                .filter(u -> u.getEmail().equals(email) && u.autenticar(senha))
                .findFirst()
                .orElse(null);

        if (usuarioLogado != null) {
            System.out.println("  Bem-vindo, " + usuarioLogado.getEmail() + "!");
        } else {
            System.out.println("  E-mail ou senha inválidos.");
        }
    }

    private void listarCatalogo() {
        System.out.println("\n  -- CATÁLOGO --");
        catalogo.forEach(System.out::println);
    }

    private void adicionarAoCarrinho() {
        listarCatalogo();
        System.out.print("\n  ID do produto: ");
        int id = lerInt();

        Produto produto = catalogo.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (produto == null) { System.out.println("  Produto não encontrado."); return; }

        System.out.print("  Quantidade: ");
        int qtd = lerInt();
        if (qtd <= 0) { System.out.println("  Quantidade inválida."); return; }

        try {
            usuarioLogado.adicionarCarrinho(produto, qtd);
        } catch (IllegalStateException e) {
            System.out.println("  Erro: " + e.getMessage());
        }
    }

    private void removerDoCarrinho() {
        usuarioLogado.getCarrinho().exibir();
        System.out.print("  ID do produto a remover: ");
        int id = lerInt();
        usuarioLogado.getCarrinho().removerItem(id);
    }

    private void fluxoFinalizarCompra() {
        if (usuarioLogado.getCarrinho().isEmpty()) {
            System.out.println("  Carrinho vazio. Adicione produtos antes.");
            return;
        }

        usuarioLogado.getCarrinho().exibir();

        System.out.println("\n  -- ENDEREÇO DE ENTREGA --");
        Endereco endereco = coletarEndereco();
        if (!endereco.validarEndereco()) {
            System.out.println("  Endereço inválido.");
            return;
        }

        try {
            pedidoAtual = usuarioLogado.finalizarCompra(endereco);
        } catch (IllegalStateException e) {
            System.out.println("  Erro: " + e.getMessage());
            return;
        }

        System.out.println("\n  -- FRETE --");
        System.out.println("  Opções: PAC | SEDEX | EXPRESS | RETIRADA");
        System.out.print("  Método: ");
        String metodo = scanner.nextLine().trim().toUpperCase();
        Transporte transporte = new Transporte(metodo);
        pedidoService.definirTransporte(pedidoAtual, transporte);

        pedidoAtual.exibirResumo();

        System.out.println("\n  -- PAGAMENTO --");
        System.out.println("  1. Boleto");
        System.out.println("  2. Pix");
        System.out.println("  3. Cartão de crédito");
        System.out.print("  Opção: ");
        int opcPgto = lerInt();

        Pagamento pagamento = coletarPagamento(opcPgto, pedidoAtual.getPrecoTotal());
        if (pagamento == null) { System.out.println("  Pagamento cancelado."); return; }

        pedidoService.processarPagamento(pedidoAtual, pagamento);
        pedidoAtual.exibirResumo();
    }

    private Endereco coletarEndereco() {
        System.out.print("  Rua     : ");
        String rua    = scanner.nextLine().trim();
        System.out.print("  Bairro  : ");
        String bairro = scanner.nextLine().trim();
        System.out.print("  CEP     : ");
        String cep    = scanner.nextLine().trim();
        System.out.print("  Cidade  : ");
        String cidade = scanner.nextLine().trim();
        System.out.print("  UF (ex: SP): ");
        String uf     = scanner.nextLine().trim().toUpperCase();
        return new Endereco(rua, bairro, cep, cidade, uf);
    }

    private Pagamento coletarPagamento(int opcao, double valor) {
        switch (opcao) {
            case 1 -> {
                System.out.print("  Banco (ex: Itaú): ");
                String banco = scanner.nextLine().trim();
                return new Boleto(valor, banco);
            }
            case 2 -> {
                System.out.print("  Chave Pix (CPF/e-mail/telefone): ");
                String chave = scanner.nextLine().trim();
                return new Pix(valor, chave);
            }
            case 3 -> {
                System.out.print("  Bandeira (VISA/MASTER/ELO): ");
                String bandeira = scanner.nextLine().trim().toUpperCase();
                System.out.print("  Parcelas (1-12): ");
                int parcelas = lerInt();
                System.out.print("  CVV: ");
                int cvv = lerInt();
                return new Cartao(valor, bandeira, parcelas, cvv);
            }
            default -> { return null; }
        }
    }

    private void listarPedidos() {
        List<Pedido> pedidos = usuarioLogado.getPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("\n  Nenhum pedido realizado.");
            return;
        }
        System.out.println("\n  -- MEUS PEDIDOS --");
        for (Pedido p : pedidos) {
            System.out.printf("  Pedido #%d | Status: %-12s | Total: R$ %.2f%n",
                    p.getId(), p.getStatus(), p.getPrecoTotal());
        }
        System.out.print("\n  Ver detalhes de qual pedido? (0 = voltar): ");
        int id = lerInt();
        if (id != 0) {
            pedidos.stream()
                    .filter(p -> p.getId() == id)
                    .findFirst()
                    .ifPresentOrElse(Pedido::exibirResumo,
                            () -> System.out.println("  Pedido não encontrado."));
        }
    }

    private int lerInt() {
        try {
            String linha = scanner.nextLine().trim();
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
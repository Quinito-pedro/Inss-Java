import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Database {

    private static Beneficiario beneficiario;
    public static Beneficiario getBeneficiario() {
        return beneficiario;
    }

    private static List<Contribuicao> contribuicoes = new ArrayList<>();
    private static List<Pagamento> pagamentos = new ArrayList<>();

    private static final DateTimeFormatter DATE_INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_OUTPUT_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void inicializarDados() {
        carregarBeneficiario();
        carregarContribuicoes();
        carregarPagamentos();

        if (beneficiario == null) {
            beneficiario = new Beneficiario("João Manuel", "123456789", "842222222", "Maputo", false);
            salvarBeneficiario();
        }
    }

    public static void mostrarMenuPrincipal() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Ver minhas informações");
        System.out.println("2. Consultar saldo da pensão");
        System.out.println("3. Estado de requerimentos");
        System.out.println("4. Histórico de benefícios");
        System.out.println("5. Atualizar CPF/BI");
        System.out.println("6. Notificações");
        System.out.println("7. Atualizar dados do beneficiário");
        System.out.println("8. Adicionar contribuição");
        System.out.println("9. Adicionar pagamento");
        System.out.println("10. Ver contribuições");
        System.out.println("11. Ver pagamentos");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public static <beneficiario> String getInfoTexto() {
        if (beneficiario == null) return "Beneficiário não encontrado.";

        StringBuilder sb = new StringBuilder();
        sb.append(beneficiario.toString()).append("\n");

        sb.append("Contribuinte: ").append(!contribuicoes.isEmpty() ? "Sim" : "Não").append("\n");
        sb.append("Beneficiário: ").append(!pagamentos.isEmpty() ? "Sim" : "Não").append("\n");

        if (!pagamentos.isEmpty()) {
            try {
                Pagamento ultimo = pagamentos.get(pagamentos.size() - 1);
                LocalDate data = LocalDate.parse(ultimo.getData(), DATE_INPUT_FORMAT);
                boolean emDia = !data.plusMonths(1).isBefore(LocalDate.now());
                sb.append("Pagamento Atualizado: ").append(emDia ? "Sim" : "Não").append("\n");
            } catch (DateTimeParseException e) {
                sb.append("Pagamento Atualizado: Data inválida\n");
            }
        } else {
            sb.append("Pagamento Atualizado: Não\n");
        }

        return sb.toString();
    }

    public static String getInfoTextoGUI() {
        return getInfoTexto();
    }

    public static String consultarSaldoPensaoGUI() {
        if (pagamentos.isEmpty()) return "Nenhum pagamento registrado.";
        double total = pagamentos.stream().mapToDouble(p -> p.getValor()).sum();
        return String.format("Seu saldo atual é de %.2f MT", total);
    }

    public static String estadoRequerimentosGUI() {
        return "Requerimento em análise. Última atualização: 10/05/2025.";
    }

    public static String historicoBeneficiosGUI() {
        if (pagamentos.isEmpty()) return "Nenhum benefício recebido ainda.";

        StringBuilder sb = new StringBuilder("Histórico de Benefícios:\n");
        for (Pagamento p : pagamentos) {
            sb.append(p.toString()).append("\n");
        }
        return sb.toString();
    }

    public static String atualizarCpfBiGUI(String novoDoc) {
        if (beneficiario != null) {
            beneficiario.setNuit(novoDoc);
            salvarBeneficiario();
        }
        return novoDoc;
    }

    public static String mostrarNotificacoesGUI() {
        return "Você tem 2 novas notificações:\n- Atualização de sistema\n- Novo pagamento liberado.";
    }

    public static void atualizarDadosGUI(String nome, String nuit, String telefone, String endereco, boolean isencao) {
        if (beneficiario != null) {
            beneficiario.nome = nome;
            beneficiario.setNuit(nuit);
            beneficiario.setTelefone(telefone);
            beneficiario.setEndereco(endereco);
            beneficiario.setIsencao(isencao);
            salvarBeneficiario();
        }
    }

    public static void adicionarContribuicaoGUI(String data, double valor) {
        contribuicoes.add(new Contribuicao(data, valor));
        salvarContribuicoes();
    }

    public static void adicionarPagamentoGUI(String data, double valor) {
        pagamentos.add(new Pagamento(data, valor));
        salvarPagamentos();
    }

    public static String mostrarContribuicoesGUI() {
        if (contribuicoes.isEmpty()) return "Nenhuma contribuição registrada.";
        StringBuilder sb = new StringBuilder("Contribuições:\n");
        for (Contribuicao c : contribuicoes) {
            sb.append(c.toString()).append("\n");
        }
        return sb.toString();
    }

    public static String mostrarPagamentosGUI() {
        if (pagamentos.isEmpty()) return "Nenhum pagamento registrado.";
        StringBuilder sb = new StringBuilder("Pagamentos:\n");
        for (Pagamento p : pagamentos) {
            sb.append(p.toString()).append("\n");
        }
        return sb.toString();
    }

    // ===== Arquivos =====
    private static void salvarBeneficiario() {
        try (PrintWriter writer = new PrintWriter("beneficiario.txt")) {
            writer.println(beneficiario.nome);
            writer.println(beneficiario.getNuit());
            writer.println(beneficiario.getTelefone());
            writer.println(beneficiario.getEndereco());
            writer.println(beneficiario.isIsencao());
        } catch (IOException e) {
            System.out.println("Erro ao salvar beneficiário: " + e.getMessage());
        }
    }

    private static void carregarBeneficiario() {
        File file = new File("beneficiario.txt");
        if (!file.exists()) return;
        try (Scanner sc = new Scanner(file)) {
            String nome = sc.nextLine();
            String nuit = sc.nextLine();
            String telefone = sc.nextLine();
            String endereco = sc.nextLine();
            boolean isencao = Boolean.parseBoolean(sc.nextLine());
            beneficiario = new Beneficiario(nome, nuit, telefone, endereco, isencao);
        } catch (Exception e) {
            System.out.println("Erro ao carregar beneficiário.");
        }
    }

    private static void salvarContribuicoes() {
        try (PrintWriter writer = new PrintWriter("contribuicoes.txt")) {
            for (Contribuicao c : contribuicoes) {
                writer.println(c.data + ";" + c.valor);
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar contribuições.");
        }
    }

    private static void carregarContribuicoes() {
        File file = new File("contribuicoes.txt");
        if (!file.exists()) {
            contribuicoes.add(new Contribuicao("2024-12", 3000));
            contribuicoes.add(new Contribuicao("2025-01", 3100));
            salvarContribuicoes();
            return;
        }
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String[] partes = sc.nextLine().split(";");
                contribuicoes.add(new Contribuicao(partes[0], Double.parseDouble(partes[1])));
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar contribuições.");
        }
    }

    private static void salvarPagamentos() {
        try (PrintWriter writer = new PrintWriter("pagamentos.txt")) {
            for (Pagamento p : pagamentos) {
                writer.println(p.getData() + ";" + p.getValor());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar pagamentos.");
        }
    }

    private static void carregarPagamentos() {
        File file = new File("pagamentos.txt");
        if (!file.exists()) {
            pagamentos.add(new Pagamento("2025-04-01", 3200));
            pagamentos.add(new Pagamento("2025-05-01", 3200));
            salvarPagamentos();
            return;
        }
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String[] partes = sc.nextLine().split(";");
                pagamentos.add(new Pagamento(partes[0], Double.parseDouble(partes[1])));
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar pagamentos.");
        }
    }

	public static void adicionarPagamento(String valor) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'adicionarPagamento'");
	}

    public static void adicionarContribuicao(String valor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'adicionarContribuicao'");
    }

    public static void atualizarDados(String nome, String telefone) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizarDados'");
    }

    public static String consultarSaldoPensao() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultarSaldoPensao'");
    }

    public static String mostrarMinhaInformacao() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarMinhaInformacao'");
    }

    public static String mostrarNotificacoes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarNotificacoes'");
    }

    public static String mostrarContribuicoes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarContribuicoes'");
    }

    public static String mostrarPagamentos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarPagamentos'");
    }

    public static String historicoBeneficios() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'historicoBeneficios'");
    }

    public static String estadoRequerimentos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'estadoRequerimentos'");
    }
}

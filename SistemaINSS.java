import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SistemaINSS {

    private JFrame frame;
    private JPanel panel;
    private JLabel displayLabel;
    private JTextField inputField;
    private JButton enviarButton;

    // Dados do beneficiário simulados
    private String nome = "";
    private String bi = "000000000";
    private String nuit = "000000000";
    private String contacto = "";
    private String endereco = "";

    // Simulação de contribuições e benefícios
    private List<String> beneficios = new ArrayList<>();
    private List<String> extratos = new ArrayList<>();
    private List<String> notificacoes = new ArrayList<>();
    private List<String> requerimentos = new ArrayList<>();
    private JLabel campoEntrada;
 
    public SistemaINSS() {
        configurarDadosExemplo();
        configurarJanela();
        configurarInterfaceInicial();
        frame.setVisible(true);
    }

    private void configurarDadosExemplo() {
        beneficios.add("Pensão por invalidez");
        beneficios.add("Subsídio de doença");

        extratos.add("2025-04-10: Pagamento de Pensão: 2000 MT");
        extratos.add("2025-03-15: Pagamento de Subsídio: 1500 MT");

        notificacoes.add("Notificação: Atualize seu contacto telefónico.");
        notificacoes.add("Notificação: Novo benefício disponível.");

        requerimentos.add("Requerimento 001 - Pensão - Em análise");
        requerimentos.add("Requerimento 002 - Subsídio - Aprovado");
    }
private void configurarJanela() {
    frame = new JFrame("INSS - Simulador USSD");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(320, 480); // Tamanho inicial pequeno
    frame.setMinimumSize(new Dimension(240, 320)); // Tamanho mínimo compatível com celulares
    frame.setLocationRelativeTo(null);
    frame.setResizable(true); // Permite o redimensionamento da janela
}

private void configurarInterfaceInicial() {
    panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    displayLabel = new JLabel("Digite o código USSD (*122#):", SwingConstants.CENTER);
    displayLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    displayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    inputField = new JTextField();
    inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

    enviarButton = new JButton("Enviar");
    enviarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    enviarButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
    enviarButton.addActionListener(e -> processarComando());

    panel.add(displayLabel);
    panel.add(Box.createRigidArea(new Dimension(0, 5)));
    panel.add(inputField);
    panel.add(Box.createRigidArea(new Dimension(0, 5)));
    panel.add(enviarButton);

    JScrollPane scrollPane = new JScrollPane(panel);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    frame.getContentPane().add(scrollPane);
}

    private void processarComando() {
        String comando = inputField.getText().trim();
        if (comando.equals("*122#")) {
            mostrarMenuPrincipal();
        } else {
            JOptionPane.showMessageDialog(frame, "Comando inválido. Tente *122#");
        }
    }

    private void mostrarMenuPrincipal() {
        panel.removeAll();

        JLabel titulo = new JLabel("<html><center><b>Bem-vindo ao INSS</b></center></html>", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(criarBotao("1. Painel do Beneficiário", () -> mostrarPainelBeneficiario()));
        panel.add(criarBotao("2. Extracto do Beneficiário", () -> mostrarExtracto()));
        panel.add(criarBotao("3. Dados Cadastrais", () -> mostrarDadosCadastrais()));
        panel.add(criarBotao("4. Requerimentos", this::mostrarRequerimentos));
        panel.add(criarBotao("5. Maternidade", this::mostrarMaternidade));
        panel.add(criarBotao("6. Mais Informações", this::abrirSubmenuMaisInformacoes));

        panel.add(criarBotao("0. Sair", this::sair));

        panel.revalidate();
        panel.repaint();
    }

    private JButton criarBotao(String texto, Runnable acao) {
        JButton botao = new JButton(texto);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.addActionListener(e -> acao.run());
        botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return botao;
    }

private void mostrarPainelBeneficiario() {
    panel.removeAll();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

    JLabel titulo = new JLabel(">> Painel do Beneficiário");
    titulo.setFont(new Font("Monospaced", Font.BOLD, 12));
    titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(titulo);
    panel.add(Box.createRigidArea(new Dimension(0, 5)));

    JTextArea beneficiosTextArea = new JTextArea();
    beneficiosTextArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
    beneficiosTextArea.setEditable(false);
    beneficiosTextArea.setLineWrap(true);
    beneficiosTextArea.setWrapStyleWord(true);
    beneficiosTextArea.setFocusable(false);
    beneficiosTextArea.setBackground(panel.getBackground());
    beneficiosTextArea.setMaximumSize(new Dimension(240, 90));

    StringBuilder sb = new StringBuilder();
    sb.append("Benefícios Ativos:\n\n");
    int i = 1;
    for (String beneficio : beneficios) {
        if (beneficio.length() > 20) {
            beneficio = beneficio.substring(0, 20) + "...";
        }
        sb.append(i++).append(". ").append(beneficio).append("\n");
    }

    beneficiosTextArea.setText(sb.toString());
    panel.add(beneficiosTextArea);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    JButton voltarButton = new JButton("Voltar");
    voltarButton.setFont(new Font("Monospaced", Font.PLAIN, 11));
    voltarButton.setMaximumSize(new Dimension(100, 25));
    voltarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    voltarButton.addActionListener(e -> mostrarMenuPrincipal());

    panel.add(voltarButton);

    panel.revalidate();
    panel.repaint();
}



private void mostrarExtracto() {
    panel.removeAll();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

    JLabel titulo = new JLabel(">> Extrato");
    titulo.setFont(new Font("Monospaced", Font.BOLD, 12));
    titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(titulo);
    panel.add(Box.createRigidArea(new Dimension(0, 5)));

    JTextArea texto = new JTextArea();
    texto.setFont(new Font("Monospaced", Font.PLAIN, 11));
    texto.setEditable(false);
    texto.setLineWrap(true);
    texto.setWrapStyleWord(true);
    texto.setFocusable(false);
    texto.setBackground(panel.getBackground());
    texto.setMaximumSize(new Dimension(240, 90)); // tamanho compacto

    StringBuilder sb = new StringBuilder();
    sb.append("Hist.Pagamentos:\n\n");
    for (String ext : extratos) {
        if (ext.length() > 20) {
            ext = ext.substring(0, 20) + "..."; // cortar textos longos
        }
        sb.append("- ").append(ext).append("\n");
    }
    texto.setText(sb.toString());

    panel.add(texto);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    JButton voltarButton = new JButton("Voltar");
    voltarButton.setFont(new Font("Monospaced", Font.PLAIN, 11));
    voltarButton.setMaximumSize(new Dimension(100, 25));
    voltarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    voltarButton.addActionListener(e -> mostrarMenuPrincipal());

    panel.add(voltarButton);

    panel.revalidate();
    panel.repaint();
}

    
private void mostrarDadosCadastrais() {
    panel.removeAll();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JLabel titulo = new JLabel(">> Atualizar Dados");
    titulo.setFont(new Font("Monospaced", Font.BOLD, 14));
    titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(titulo);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    // Área de instruções
    JLabel instrucoesLabel = new JLabel("<html><div style='text-align:center;'>"
        + "Tipos de Dados:<br>"
        + "1. Nome<br>"
        + "2. Número (+258)<br>"
        + "3. BI<br>"
        + "4. NUIT<br>"
        + "5. Endereço<br>"
        + "6. Salvar<br>"
        + "0. Voltar"
        + "</div></html>");
    instrucoesLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
    instrucoesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(instrucoesLabel);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    // Label para mostrar dados atuais
    JLabel dadosAtuaisLabel = new JLabel();
    dadosAtuaisLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
    dadosAtuaisLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    atualizarDadosAtuais(dadosAtuaisLabel);
    panel.add(dadosAtuaisLabel);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    // Campo de entrada para dados
    JTextField entradaField = new JTextField();
    entradaField.setFont(new Font("Monospaced", Font.PLAIN, 12));
    entradaField.setMaximumSize(new Dimension(200, 25));
    entradaField.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(entradaField);
    panel.add(Box.createRigidArea(new Dimension(0, 5)));

    // Botão confirmar
    JButton confirmarButton = new JButton("Confirmar");
    confirmarButton.setFont(new Font("Monospaced", Font.PLAIN, 12));
    confirmarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(confirmarButton);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    // Label status
    JLabel statusLabel = new JLabel("Digite uma opção (0-6):");
    statusLabel.setFont(new Font("Monospaced", Font.ITALIC, 11));
    statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(statusLabel);

    // Variável para controlar o estado (qual campo está sendo editado)
    final int[] estadoEdicao = {0}; // 0 = esperando escolha opção, 1-5 = edição do campo

    confirmarButton.addActionListener(e -> {
        String entrada = entradaField.getText().trim();

        if (estadoEdicao[0] == 0) {
            // Esperando opção do menu
            switch (entrada) {
                case "1":
                    statusLabel.setText("Atualize Nome:");
                    entradaField.setText(nome);
                    estadoEdicao[0] = 1;
                    break;
                case "2":
                    statusLabel.setText("Atualize Número (+258):");
                    entradaField.setText(contacto);
                    estadoEdicao[0] = 2;
                    break;
                case "3":
                    statusLabel.setText("Atualize BI:");
                    entradaField.setText(bi);
                    estadoEdicao[0] = 3;
                    break;
                case "4":
                    statusLabel.setText("Atualize NUIT:");
                    entradaField.setText(nuit);
                    estadoEdicao[0] = 4;
                    break;
                case "5":
                    statusLabel.setText("Atualize Endereço:");
                    entradaField.setText(endereco);
                    estadoEdicao[0] = 5;
                    break;
                case "6":
                    statusLabel.setText("Dados salvos com sucesso!");
                    // Aqui você pode colocar lógica real de salvar (ex: arquivo, banco, etc.)
                    break;
                case "0":
                    mostrarMenuPrincipal();
                    return;
                default:
                    statusLabel.setText("Opção inválida! Use 0-6.");
            }
            entradaField.selectAll();
        } else {
            // Estamos no modo edição - atualiza o dado e volta para menu
            switch (estadoEdicao[0]) {
                case 1:
                    nome = entrada;
                    break;
                case 2:
                    contacto = entrada;
                    break;
                case 3:
                    bi = entrada;
                    break;
                case 4:
                    nuit = entrada;
                    break;
                case 5:
                    endereco = entrada;
                    break;
            }
            atualizarDadosAtuais(dadosAtuaisLabel);
            statusLabel.setText("Atualizado! Digite uma opção (0-6):");
            entradaField.setText("");
            estadoEdicao[0] = 0;
        }
    });

    panel.revalidate();
    panel.repaint();
}

// Método para atualizar a exibição dos dados atuais
private void atualizarDadosAtuais(JLabel label) {
    String texto = "<html><div style='text-align:center;'>"
        + "Nome: " + (nome.isEmpty() ? "&lt;vazio&gt;" : nome) + "<br>"
        + "Número: " + (contacto.isEmpty() ? "&lt;vazio&gt;" : contacto) + "<br>"
        + "BI: " + (bi.isEmpty() ? "&lt;vazio&gt;" : bi) + "<br>"
        + "NUIT: " + (nuit.isEmpty() ? "&lt;vazio&gt;" : nuit) + "<br>"
        + "Endereço: " + (endereco.isEmpty() ? "&lt;vazio&gt;" : endereco)
        + "</div></html>";
    label.setText(texto);
}


    private void mostrarRequerimentos() { 
    TelaRequerimentosSimples.mostrar(frame, panel, this::mostrarMenuPrincipal);
}

    private void mostrarMaternidade() {
    panel.removeAll();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JLabel titulo = new JLabel(">> Benefício de Maternidade");
    titulo.setFont(new Font("Monospaced", Font.BOLD, 14));
    titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(titulo);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    String texto = "<html><div style='text-align:center; font-family:monospace; font-size:12px;'>" +
        "Este serviço permite:<br>" +
        "- Solicitar subsídio<br>" +
        "- Verificar pagamentos<br>" +
        "- Submeter documentos<br>" +
        "</div></html>";
    JLabel infoLabel = new JLabel(texto);
    infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(infoLabel);
    panel.add(Box.createRigidArea(new Dimension(0, 15)));

    JButton solicitarButton = new JButton("1. Solicitar Subsídio");
    solicitarButton.setFont(new Font("Monospaced", Font.PLAIN, 12));
    solicitarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    solicitarButton.setMaximumSize(new Dimension(200, 30));
    solicitarButton.addActionListener(e -> 
        JOptionPane.showMessageDialog(frame, "Pedido de subsídio de maternidade enviado com sucesso.")
    );
    panel.add(solicitarButton);
    panel.add(Box.createRigidArea(new Dimension(0, 8)));

    JButton verificarPagamentosButton = new JButton("2. Verificar Pagamentos");
    verificarPagamentosButton.setFont(new Font("Monospaced", Font.PLAIN, 12));
    verificarPagamentosButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    verificarPagamentosButton.setMaximumSize(new Dimension(200, 30));
    verificarPagamentosButton.addActionListener(e -> 
        JOptionPane.showMessageDialog(frame,
            "Pagamentos:\n" +
            "- 2025-01-10: Subsídio maternidade: 3.000 MT\n" +
            "- 2025-03-05: Complemento: 1.000 MT"
        )
    );
    panel.add(verificarPagamentosButton);
    panel.add(Box.createRigidArea(new Dimension(0, 8)));

    JButton submeterDocumentosButton = new JButton("3. Submeter Certidão");
    submeterDocumentosButton.setFont(new Font("Monospaced", Font.PLAIN, 12));
    submeterDocumentosButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    submeterDocumentosButton.setMaximumSize(new Dimension(200, 30));
    submeterDocumentosButton.addActionListener(e -> 
        JOptionPane.showMessageDialog(frame, "Documento submetido com sucesso.")
    );
    panel.add(submeterDocumentosButton);
    panel.add(Box.createRigidArea(new Dimension(0, 15)));

    JButton voltarButton = new JButton("0. Voltar");
    voltarButton.setFont(new Font("Monospaced", Font.PLAIN, 12));
    voltarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    voltarButton.setMaximumSize(new Dimension(200, 30));
    voltarButton.addActionListener(e -> mostrarMenuPrincipal());
    panel.add(voltarButton);

    panel.revalidate();
    panel.repaint();
}


    
private void abrirSubmenuMaisInformacoes() { 
    // Limpa o painel atual
    panel.removeAll();
    panel.revalidate();
    panel.repaint();

    // Cria a janela do submenu com configuração padronizada
    JFrame submenuFrame = configurarSubmenuJanela("Mais Informações - INSS");

    // Quando fechar, volta ao menu principal
    submenuFrame.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
            mostrarMenuPrincipal();
        }
    });

    // Painel do submenu
    JPanel submenuPanel = new JPanel();
    submenuPanel.setLayout(new BoxLayout(submenuPanel, BoxLayout.Y_AXIS));
    submenuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Botões do submenu
    submenuPanel.add(criarBotao("1. Doença", () -> {
        submenuFrame.dispose(); 
        mostrarDoenca();        
    }));
    submenuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    submenuPanel.add(criarBotao("2. Internamento", () -> {
        submenuFrame.dispose();
        mostrarInternamento();
    }));
    submenuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    submenuPanel.add(criarBotao("3. Pensão por Velhice", () -> {
        submenuFrame.dispose();
        mostrarPensaoPorVelhice();
    }));
    submenuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    submenuPanel.add(criarBotao("4. Pensão por Invalidez", () -> {
        submenuFrame.dispose();
        mostrarPensaoPorInvalidez();
    }));
    submenuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    submenuPanel.add(criarBotao("5. Reembolso", () -> {
        submenuFrame.dispose();
        mostrarReembolso();
    }));
    submenuPanel.add(Box.createRigidArea(new Dimension(0, 15)));

    submenuPanel.add(criarBotao("9. Voltar", () -> {
        submenuFrame.dispose();
        mostrarMenuPrincipal();
    }));

    // Scroll para garantir visibilidade em telas menores
    JScrollPane scrollPane = new JScrollPane(submenuPanel);
    submenuFrame.add(scrollPane);

    // Exibe a janela
    submenuFrame.setVisible(true);
}

// Método utilitário para configurar janelas secundárias
private JFrame configurarSubmenuJanela(String titulo) {
    JFrame frame = new JFrame(titulo);
    frame.setSize(320, 480); // Tamanho inicial
    frame.setMinimumSize(new Dimension(240, 320)); // Tamanho mínimo
    frame.setLocationRelativeTo(null); // Centraliza na tela
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setResizable(true); // Permite redimensionar
    return frame;
}

private void mostrarDoenca() {
    panel.removeAll(); // limpa o painel principal

    JLabel titulo = new JLabel("<html><center><b>Subsídio por Doença</b></center></html>", SwingConstants.CENTER);
    titulo.setFont(new Font("Arial", Font.BOLD, 18));
    titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(titulo);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    JTextArea descricao = new JTextArea(
        "Este benefício cobre a incapacidade temporária devido à doença.\n\n" +
        "O que permite:\n" +
        "- Solicitar o benefício com atestado médico;\n" +
        "- Acompanhar a análise do pedido;\n" +
        "- Ver datas de pagamento aprovadas."
    );
    descricao.setEditable(false);
    descricao.setLineWrap(true);
    descricao.setWrapStyleWord(true);
    descricao.setFont(new Font("Arial", Font.PLAIN, 14));
    descricao.setBackground(panel.getBackground());
    descricao.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    panel.add(new JScrollPane(descricao));
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    panel.add(criarBotao("Solicitar benefício", this::solicitarDoenca));
    panel.add(Box.createRigidArea(new Dimension(0, 5)));
    panel.add(criarBotao("Ver análise do pedido", this::verAnaliseDoenca));
    panel.add(Box.createRigidArea(new Dimension(0, 5)));
    panel.add(criarBotao("Ver datas de pagamento", this::verPagamentosDoenca));
    panel.add(Box.createRigidArea(new Dimension(0, 15)));

    JButton voltar = criarBotao("Voltar", () -> abrirSubmenuMaisInformacoes());
    panel.add(voltar);

    // MUITO IMPORTANTE
    panel.revalidate();  // Atualiza o layout
    panel.repaint();     // Repaint para mostrar as mudanças
}

private JButton criarBotaoRedimensionavel(String texto, Runnable acao) {
    JButton botao = new JButton(texto);
    botao.setAlignmentX(Component.CENTER_ALIGNMENT);
    botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Largura máxima
    botao.setPreferredSize(new Dimension(200, 40)); // Tamanho preferido
    botao.setFocusable(false);
    botao.addActionListener(e -> acao.run());
    return botao;
}


private void solicitarDoenca() {
    JOptionPane.showMessageDialog(frame, "Requisição enviada com sucesso.\n\nSeu atestado foi anexado para análise.");
    requerimentos.add("Requerimento 003 - Doença - Em análise");
}

private void verAnaliseDoenca() {
    StringBuilder sb = new StringBuilder("Análise de Requerimentos de Doença:\n");
    for (String req : requerimentos) {
        if (req.contains("Doença")) {
            sb.append("- ").append(req).append("\n");
        }
    }
    JOptionPane.showMessageDialog(frame, sb.toString());
}

private void verPagamentosDoenca() {
    StringBuilder sb = new StringBuilder("Pagamentos de Subsídio de Doença:\n");
    for (String ext : extratos) {
        if (ext.toLowerCase().contains("subsídio")) {
            sb.append("- ").append(ext).append("\n");
        }
    }
    JOptionPane.showMessageDialog(frame, sb.toString());
}
private void mostrarInternamento() {
    panel.removeAll();

    JLabel titulo = new JLabel("<html><center><b>Internamento Hospitalar</b></center></html>", SwingConstants.CENTER);
    titulo.setFont(new Font("Arial", Font.BOLD, 16));
    titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(titulo);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    JTextArea texto = new JTextArea();
    texto.setEditable(false);
    texto.setLineWrap(true);
    texto.setWrapStyleWord(true);
    texto.setText(
        "Função: Cobre benefícios relacionados ao internamento hospitalar.\n\n" +
        "O que permite:\n" +
        "- Solicitar ajuda financeira durante período de hospitalização.\n" +
        "- Submeter comprovativos de internamento.\n" +
        "- Acompanhar os apoios concedidos."
    );
    panel.add(new JScrollPane(texto));
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    // Botão para simular envio de comprovativo
    JButton enviarComprovativo = new JButton("Submeter Comprovativo");
    enviarComprovativo.setAlignmentX(Component.CENTER_ALIGNMENT);
    enviarComprovativo.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Comprovativo enviado com sucesso!"));
    panel.add(enviarComprovativo);

    // Botão para simular solicitação de ajuda
    JButton solicitarAjuda = new JButton("Solicitar Ajuda Financeira");
    solicitarAjuda.setAlignmentX(Component.CENTER_ALIGNMENT);
    solicitarAjuda.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Pedido submetido para análise."));
    panel.add(Box.createRigidArea(new Dimension(0, 5)));
    panel.add(solicitarAjuda);

    // Botão de voltar
    JButton voltarButton = new JButton("Voltar");
    voltarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    voltarButton.addActionListener(e -> abrirSubmenuMaisInformacoes());
    panel.add(Box.createRigidArea(new Dimension(0, 10)));
    panel.add(voltarButton);

    panel.revalidate();
    panel.repaint();
}
private void mostrarPensaoPorVelhice() {
    panel.removeAll();

    JLabel titulo = new JLabel("<html><center><b>Pensão por Velhice</b></center></html>", SwingConstants.CENTER);
    titulo.setFont(new Font("Arial", Font.BOLD, 16));
    titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(titulo);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    JTextArea texto = new JTextArea();
    texto.setEditable(false);
    texto.setLineWrap(true);
    texto.setWrapStyleWord(true);
    texto.setText(
        "Função: Área para gestão da pensão de reforma (aposentadoria).\n\n" +
        "O que permite:\n" +
        "- Solicitar a aposentadoria ao atingir a idade legal.\n" +
        "- Acompanhar o processo e os pagamentos mensais.\n" +
        "- Atualizar dados bancários para recebimento."
    );
    panel.add(new JScrollPane(texto));
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    JButton solicitarAposentadoria = new JButton("Solicitar Aposentadoria");
    solicitarAposentadoria.setAlignmentX(Component.CENTER_ALIGNMENT);
    solicitarAposentadoria.addActionListener(e -> solicitarAposentadoria());
    panel.add(solicitarAposentadoria);

    JButton acompanharProcesso = new JButton("Acompanhar Processo e Pagamentos");
    acompanharProcesso.setAlignmentX(Component.CENTER_ALIGNMENT);
    acompanharProcesso.addActionListener(e -> acompanharPensao());
    panel.add(Box.createRigidArea(new Dimension(0, 5)));
    panel.add(acompanharProcesso);

    JButton atualizarDadosBancarios = new JButton("Atualizar Dados Bancários");
    atualizarDadosBancarios.setAlignmentX(Component.CENTER_ALIGNMENT);
    atualizarDadosBancarios.addActionListener(e -> atualizarDadosBancarios());
    panel.add(Box.createRigidArea(new Dimension(0, 5)));
    panel.add(atualizarDadosBancarios);

    JButton voltarButton = new JButton("Voltar");
    voltarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    voltarButton.addActionListener(e -> abrirSubmenuMaisInformacoes());
    panel.add(Box.createRigidArea(new Dimension(0, 10)));
    panel.add(voltarButton);

    panel.revalidate();
    panel.repaint();
}


    private Object atualizarDadosBancarios() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'atualizarDadosBancarios'");
}

    private Object acompanharPensao() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'acompanharPensao'");
}

    private Object solicitarAposentadoria() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'solicitarAposentadoria'");
}

    private void mostrarPensaoPorInvalidez() {
    panel.removeAll();

    JLabel titulo = new JLabel("<html><center><b>Pensão por Invalidez</b></center></html>", SwingConstants.CENTER);
    titulo.setFont(new Font("Arial", Font.BOLD, 16));
    titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(titulo);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    JTextArea texto = new JTextArea();
    texto.setEditable(false);
    texto.setLineWrap(true);
    texto.setWrapStyleWord(true);
    texto.setText(
        "Função: Tratamento de benefício vitalício ou temporário por invalidez.\n\n" +
        "O que permite:\n" +
        "- Requerer a pensão apresentando laudo médico.\n" +
        "- Ver histórico e periodicidade dos pagamentos.\n" +
        "- Consultar decisões médicas e reavaliações."
    );
    panel.add(new JScrollPane(texto));
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    // Botão para simular requerimento da pensão
    JButton requererPensao = new JButton("Requerer Pensão com Laudo");
    requererPensao.setAlignmentX(Component.CENTER_ALIGNMENT);
    requererPensao.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Requerimento submetido com sucesso!"));
    panel.add(requererPensao);

    // Botão para ver histórico de pagamentos
    JButton verHistorico = new JButton("Ver Histórico de Pagamentos");
    verHistorico.setAlignmentX(Component.CENTER_ALIGNMENT);
    verHistorico.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Histórico de pagamentos:\n- Janeiro: 3.000MT\n- Fevereiro: 3.000MT"));
    panel.add(Box.createRigidArea(new Dimension(0, 5)));
    panel.add(verHistorico);

    // Botão para consultar decisões médicas
    JButton consultarDecisoes = new JButton("Consultar Decisões Médicas");
    consultarDecisoes.setAlignmentX(Component.CENTER_ALIGNMENT);
    consultarDecisoes.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Última decisão médica: Aprovado para pensão temporária.\nPróxima reavaliação: Julho 2025"));
    panel.add(Box.createRigidArea(new Dimension(0, 5)));
    panel.add(consultarDecisoes);

    // Botão de voltar
    JButton voltarButton = new JButton("Voltar");
    voltarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    voltarButton.addActionListener(e -> abrirSubmenuMaisInformacoes());
    panel.add(Box.createRigidArea(new Dimension(0, 10)));
    panel.add(voltarButton);

    panel.revalidate();
    panel.repaint();
}

    private void mostrarReembolso() {
    panel.removeAll();

    JLabel titulo = new JLabel("<html><center><b>Reembolso</b></center></html>", SwingConstants.CENTER);
    titulo.setFont(new Font("Arial", Font.BOLD, 16));
    titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(titulo);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    JTextArea texto = new JTextArea();
    texto.setEditable(false);
    texto.setLineWrap(true);
    texto.setWrapStyleWord(true);
    texto.setText(
        "Função: Referente a reembolso de despesas médicas ou outras cobertas pelo INSS.\n\n" +
        "O que permite:\n" +
        "- Solicitar reembolso com comprovativos de pagamento.\n" +
        "- Acompanhar a análise e decisão.\n" +
        "- Consultar pagamentos reembolsados."
    );
    panel.add(new JScrollPane(texto));
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    // Botão para simular envio de comprovativos
    JButton enviarComprovativo = new JButton("Submeter Comprovativo");
    enviarComprovativo.setAlignmentX(Component.CENTER_ALIGNMENT);
    enviarComprovativo.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Comprovativo submetido para reembolso."));
    panel.add(enviarComprovativo);

    // Botão para simular acompanhamento de análise
    JButton acompanharAnalise = new JButton("Acompanhar Análise");
    acompanharAnalise.setAlignmentX(Component.CENTER_ALIGNMENT);
    acompanharAnalise.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Seu pedido está em fase de análise."));
    panel.add(Box.createRigidArea(new Dimension(0, 5)));
    panel.add(acompanharAnalise);

    // Botão para consultar pagamentos reembolsados
    JButton consultarPagamentos = new JButton("Consultar Reembolsos");
    consultarPagamentos.setAlignmentX(Component.CENTER_ALIGNMENT);
    consultarPagamentos.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Pagamentos reembolsados disponíveis para visualização."));
    panel.add(Box.createRigidArea(new Dimension(0, 5)));
    panel.add(consultarPagamentos);

    // Botão de voltar
    JButton voltarButton = new JButton("Voltar");
    voltarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    voltarButton.addActionListener(e -> abrirSubmenuMaisInformacoes());
    panel.add(Box.createRigidArea(new Dimension(0, 10)));
    panel.add(voltarButton);

    panel.revalidate();
    panel.repaint();
}
    private void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(frame, mensagem);
    }

    private void sair() {
        int confirmacao = JOptionPane.showConfirmDialog(frame, "Deseja realmente sair?", "Sair", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SistemaINSS::new);
    }
 }
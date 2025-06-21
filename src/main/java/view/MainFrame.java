package view;

import controller.ClienteController;
import javax.swing.*;

public class MainFrame extends JFrame {

    private final JTabbedPane tabbedPane;

    public MainFrame() {
        super("Sistema de Locadora de Veículos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(950, 750);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        criarMenu();
        adicionarAbaInicial();

        add(tabbedPane);
    }

    private void criarMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuCadastro = new JMenu("Cadastros");

        JMenuItem menuCliente = new JMenuItem("Gerenciar Clientes");
        menuCliente.addActionListener(e -> abrirAba("Clientes", new ClientePanel(ClienteController.listarTodos(), this)));

        JMenuItem menuVeiculo = new JMenuItem("Incluir Novo Veículo");
        menuVeiculo.addActionListener(e -> abrirAba("Inclusão de Veículos", new VeiculoPanel(this)));

        // NOVO: Menu para gerenciar veículos existentes
        JMenuItem menuGerenciarVeiculo = new JMenuItem("Gerenciar Veículos");
        menuGerenciarVeiculo.addActionListener(e -> abrirAba("Gerenciar Frota", new GerenciarVeiculosPanel(this)));

        menuCadastro.add(menuCliente);
        menuCadastro.add(menuVeiculo);
        menuCadastro.add(menuGerenciarVeiculo); // Adicionado ao menu

        JMenu menuOperacoes = new JMenu("Operações");

        JMenuItem menuNovaLocacao = new JMenuItem("Realizar Locação");
        menuNovaLocacao.addActionListener(e -> abrirAba("Locação", new LocacaoPanel(this)));

        JMenuItem menuDevolucao = new JMenuItem("Realizar Devolução");
        menuDevolucao.addActionListener(e -> abrirAba("Devolução", new DevolucaoPanel(this)));

        JMenuItem menuVenda = new JMenuItem("Realizar Venda");
        menuVenda.addActionListener(e -> abrirAba("Venda", new VendaPanel(this)));

        menuOperacoes.add(menuNovaLocacao);
        menuOperacoes.add(menuDevolucao);
        menuOperacoes.add(menuVenda);

        menuBar.add(menuCadastro);
        menuBar.add(menuOperacoes);

        setJMenuBar(menuBar);
    }

    private void adicionarAbaInicial() {
        JLabel bemVindo = new JLabel("Bem-vindo ao Sistema da Locadora!", JLabel.CENTER);
        tabbedPane.addTab("Início", bemVindo);
    }

    public void abrirAba(String titulo, JPanel painel) {
        int index = tabbedPane.indexOfTab(titulo);
        if (index == -1) {
            tabbedPane.addTab(titulo, painel);
            tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
        } else {
            tabbedPane.setComponentAt(index, painel);
            tabbedPane.setSelectedIndex(index);
        }
    }

    // NOVO: Método para fechar uma aba específica
    public void fecharAba(JPanel panel) {
        int index = tabbedPane.indexOfComponent(panel);
        if (index != -1) {
            tabbedPane.remove(index);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
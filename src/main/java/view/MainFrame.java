/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;

public class MainFrame extends JFrame {

    private final JTabbedPane tabbedPane;

    public MainFrame() {
        super("Sistema de Locadora");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Centraliza a janela

        tabbedPane = new JTabbedPane();

        criarMenu();
        adicionarAbasIniciais();

        add(tabbedPane);
    }

    private void criarMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuCadastro = new JMenu("Cadastros");

        JMenuItem menuCliente = new JMenuItem("Clientes");
        menuCliente.addActionListener(e -> abrirAba("Clientes", new ClientePanel()));

        JMenuItem menuVeiculo = new JMenuItem("Veículos");
        menuVeiculo.addActionListener(e -> abrirAba("Veículos", new VeiculoPanel()));

        menuCadastro.add(menuCliente);
        menuCadastro.add(menuVeiculo);

        JMenu menuLocacao = new JMenu("Locações");

        JMenuItem menuNovaLocacao = new JMenuItem("Nova Locação");
        menuNovaLocacao.addActionListener(e -> abrirAba("Locação", new LocacaoPanel()));

        JMenuItem menuDevolucao = new JMenuItem("Devolução");
        menuDevolucao.addActionListener(e -> abrirAba("Devolução", new DevolucaoPanel()));

        JMenuItem menuVenda = new JMenuItem("Venda");
        menuVenda.addActionListener(e -> abrirAba("Venda", new VendaPanel()));

        menuLocacao.add(menuNovaLocacao);
        menuLocacao.add(menuDevolucao);
        menuLocacao.add(menuVenda);

        menuBar.add(menuCadastro);
        menuBar.add(menuLocacao);

        setJMenuBar(menuBar);
    }

    private void adicionarAbasIniciais() {
        JLabel bemVindo = new JLabel("Bem-vindo à Locadora!", JLabel.CENTER);
        tabbedPane.addTab("Início", bemVindo);
    }

    private void abrirAba(String titulo, JPanel painel) {
        int index = tabbedPane.indexOfTab(titulo);
        if (index == -1) {
            tabbedPane.addTab(titulo, painel);
            tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
        } else {
            tabbedPane.setSelectedIndex(index);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}


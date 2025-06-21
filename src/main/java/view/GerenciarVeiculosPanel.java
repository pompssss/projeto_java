package view;

import controller.VeiculoController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import model.Estado;
import model.Veiculo;
import tablemodel.VeiculoGerenciamentoTableModel;

public class GerenciarVeiculosPanel extends JPanel {
    private final JTable tabelaVeiculos;
    private final VeiculoGerenciamentoTableModel tableModel;
    private final JComboBox<Estado> estadoBox;
    private final JTextField valorCompraField;
    private final JButton btnAtualizar, btnExcluir, btnRefresh, btnFechar;
    private final MainFrame mainFrame;

    public GerenciarVeiculosPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 10));

        // --- PAINEL SUPERIOR COM AÇÕES ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRefresh = new JButton("Atualizar Lista");
        btnFechar = new JButton("Fechar Aba");
        topPanel.add(btnRefresh);
        topPanel.add(btnFechar);
        add(topPanel, BorderLayout.NORTH);

        // --- TABELA PRINCIPAL ---
        tableModel = new VeiculoGerenciamentoTableModel(VeiculoController.listarTodos());
        tabelaVeiculos = new JTable(tableModel);
        add(new JScrollPane(tabelaVeiculos), BorderLayout.CENTER);

        // --- PAINEL DE EDIÇÃO (SUL) ---
        JPanel editPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        estadoBox = new JComboBox<>(Estado.values());
        valorCompraField = new JTextField();
        btnAtualizar = new JButton("Salvar Alterações");
        btnExcluir = new JButton("Excluir Veículo");

        editPanel.add(new JLabel("Novo Estado:"));
        editPanel.add(estadoBox);
        editPanel.add(new JLabel("Novo Valor de Compra:"));
        editPanel.add(valorCompraField);
        editPanel.add(btnAtualizar);
        editPanel.add(btnExcluir);
        add(editPanel, BorderLayout.SOUTH);

        // --- LISTENERS ---
        tabelaVeiculos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaVeiculos.getSelectedRow() != -1) {
                preencherFormulario();
            }
        });

        btnAtualizar.addActionListener(e -> atualizarVeiculo());
        btnExcluir.addActionListener(e -> excluirVeiculo());
        btnRefresh.addActionListener(e -> refreshTable());
        btnFechar.addActionListener(e -> this.mainFrame.fecharAba(this));
    }

    private void preencherFormulario() {
        int selectedRow = tabelaVeiculos.convertRowIndexToModel(tabelaVeiculos.getSelectedRow());
        Veiculo veiculo = tableModel.getVeiculoAt(selectedRow);
        
        estadoBox.setSelectedItem(veiculo.getEstado());
        valorCompraField.setText(String.valueOf(veiculo.getValorDeCompra()));
    }

    private void atualizarVeiculo() {
        int selectedRow = tabelaVeiculos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Veiculo veiculo = tableModel.getVeiculoAt(tabelaVeiculos.convertRowIndexToModel(selectedRow));
            
            Estado novoEstado = (Estado) estadoBox.getSelectedItem();
            double novoValor = Double.parseDouble(valorCompraField.getText());

            veiculo.setEstado(novoEstado);
            veiculo.setValorDeCompra(novoValor);

            VeiculoController.atualizar(veiculo);
            JOptionPane.showMessageDialog(this, "Veículo atualizado com sucesso!");
            refreshTable();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O valor de compra deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirVeiculo() {
        int selectedRow = tabelaVeiculos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Veiculo veiculo = tableModel.getVeiculoAt(tabelaVeiculos.convertRowIndexToModel(selectedRow));

        if (veiculo.getEstado() == Estado.LOCADO) {
            JOptionPane.showMessageDialog(this, "Não é possível excluir um veículo que está locado.", "Erro de Exclusão", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o veículo " + veiculo.getPlaca() + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            VeiculoController.excluir(veiculo);
            JOptionPane.showMessageDialog(this, "Veículo excluído com sucesso!");
            refreshTable();
        }
    }

    private void refreshTable() {
        tableModel.setVeiculos(VeiculoController.listarTodos());
        limparCampos();
    }
    
    private void limparCampos() {
        valorCompraField.setText("");
        tabelaVeiculos.clearSelection();
    }
}
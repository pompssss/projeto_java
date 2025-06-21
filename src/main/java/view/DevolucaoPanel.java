package view;

import controller.VeiculoController;
import java.awt.*;
import javax.swing.*;
import model.Veiculo;
import tablemodel.VeiculoDevolucaoTableModel;

public class DevolucaoPanel extends JPanel {
    private final JTable tabelaLocados;
    private final VeiculoDevolucaoTableModel tableModel;
    private final JButton devolverBtn, btnRefresh, btnFechar;
    private final MainFrame mainFrame;

    public DevolucaoPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 10));

        // --- PAINEL DE AÇÕES SUPERIOR ---
        JPanel topActionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRefresh = new JButton("Atualizar Lista");
        btnFechar = new JButton("Fechar Aba");
        topActionsPanel.add(btnRefresh);
        topActionsPanel.add(btnFechar);
        add(topActionsPanel, BorderLayout.NORTH);

        // --- TABELA PRINCIPAL ---
        tableModel = new VeiculoDevolucaoTableModel(VeiculoController.listarLocados());
        tabelaLocados = new JTable(tableModel);
        add(new JScrollPane(tabelaLocados), BorderLayout.CENTER);

        // --- PAINEL DE AÇÃO INFERIOR ---
        JPanel southPanel = new JPanel();
        devolverBtn = new JButton("Devolver Veículo Selecionado");
        southPanel.add(devolverBtn);
        add(southPanel, BorderLayout.SOUTH);

        // --- LISTENERS ---
        devolverBtn.addActionListener(e -> devolverVeiculo());
        btnRefresh.addActionListener(e -> refreshTable());
        btnFechar.addActionListener(e -> this.mainFrame.fecharAba(this));
    }

    private void devolverVeiculo() {
        int selectedRow = tabelaLocados.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para devolver.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Veiculo veiculo = tableModel.getVeiculoAt(tabelaLocados.convertRowIndexToModel(selectedRow));
        int confirm = JOptionPane.showConfirmDialog(this, "Confirma a devolução do veículo " + veiculo.getPlaca() + "?", "Confirmar Devolução", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            veiculo.devolver();
            VeiculoController.atualizar(veiculo);
            JOptionPane.showMessageDialog(this, "Veículo devolvido com sucesso!");
            refreshTable();
        }
    }

    private void refreshTable() {
        tableModel.setVeiculos(VeiculoController.listarLocados());
    }
}
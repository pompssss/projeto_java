package view;

import controller.VeiculoController;
import java.awt.*;
import javax.swing.*;
import model.Veiculo;
import tablemodel.VeiculoVendaTableModel;

public class VendaPanel extends JPanel {
    private final JTable tabelaVenda;
    private final VeiculoVendaTableModel tableModel;
    private final JButton venderBtn, btnRefresh, btnFechar;
    private final MainFrame mainFrame;

    public VendaPanel(MainFrame mainFrame) {
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
        tableModel = new VeiculoVendaTableModel(VeiculoController.listarDisponiveis());
        tabelaVenda = new JTable(tableModel);
        add(new JScrollPane(tabelaVenda), BorderLayout.CENTER);

        // --- PAINEL DE AÇÃO INFERIOR ---
        JPanel southPanel = new JPanel();
        venderBtn = new JButton("Vender Veículo Selecionado");
        southPanel.add(venderBtn);
        add(southPanel, BorderLayout.SOUTH);

        // --- LISTENERS ---
        venderBtn.addActionListener(e -> venderVeiculo());
        btnRefresh.addActionListener(e -> refreshTable());
        btnFechar.addActionListener(e -> this.mainFrame.fecharAba(this));
    }

    private void venderVeiculo() {
        int selectedRow = tabelaVenda.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para vender.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Veiculo veiculo = tableModel.getVeiculoAt(tabelaVenda.convertRowIndexToModel(selectedRow));
        String precoVenda = String.format("R$%.2f", veiculo.getValorParaVenda());
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja vender o veículo " + veiculo.getPlaca() + " por " + precoVenda + "?", "Confirmar Venda", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            veiculo.vender();
            VeiculoController.atualizar(veiculo);
            JOptionPane.showMessageDialog(this, "Veículo vendido com sucesso!");
            refreshTable();
        }
    }
    
    private void refreshTable() {
        tableModel.setVeiculos(VeiculoController.listarDisponiveis());
    }
}
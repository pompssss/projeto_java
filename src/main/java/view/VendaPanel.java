/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import model.Veiculo;
import tablemodel.VeiculoVendaTableModel;

/**
 *
 * @author Pomps
 */
public class VendaPanel extends JPanel {
    private final JTable tabelaVenda;
    private final JButton venderBtn;

    public VendaPanel(List<Veiculo> veiculosDisponiveis) {
        setLayout(new BorderLayout());
        tabelaVenda = new JTable(new VeiculoVendaTableModel(veiculosDisponiveis));
        venderBtn = new JButton("Vender");

        add(new JScrollPane(tabelaVenda), BorderLayout.CENTER);
        add(venderBtn, BorderLayout.SOUTH);

        // Adicione listener para vender veículo selecionado
    }

    VendaPanel() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

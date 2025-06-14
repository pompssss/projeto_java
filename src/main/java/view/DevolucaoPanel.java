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
import tablemodel.VeiculoDevolucaoTableModel;

/**
 *
 * @author Pomps
 */
public class DevolucaoPanel extends JPanel {
    private JTable tabelaLocados;
    private JButton devolverBtn;

    public DevolucaoPanel(List<Veiculo> veiculosLocados) {
        setLayout(new BorderLayout());
        tabelaLocados = new JTable(new VeiculoDevolucaoTableModel(veiculosLocados));
        devolverBtn = new JButton("Devolver Veículo");

        add(new JScrollPane(tabelaLocados), BorderLayout.CENTER);
        add(devolverBtn, BorderLayout.SOUTH);

        // Adicione listener para devolver veículo selecionado
    }
}

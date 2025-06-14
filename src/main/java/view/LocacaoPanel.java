/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import model.Veiculo;
import tablemodel.VeiculoLocacaoTableModel;

/**
 *
 * @author Pomps
 */
public class LocacaoPanel extends JPanel {
    private JTextField clienteField, diasField, dataField;
    private JComboBox<String> tipoBox, marcaBox, categoriaBox;
    private JTable tabelaVeiculos;
    private JButton locarBtn;

    public LocacaoPanel(List<Veiculo> veiculosDisponiveis) {
        setLayout(new BorderLayout());

        JPanel filtros = new JPanel(new GridLayout(2, 4));
        clienteField = new JTextField();
        diasField = new JTextField();
        dataField = new JTextField();
        tipoBox = new JComboBox<>(new String[]{"Automóvel", "Van", "Motocicleta"});
        marcaBox = new JComboBox<>(); // Preencha com marcas disponíveis
        categoriaBox = new JComboBox<>(); // Preencha com categorias disponíveis

        filtros.add(new JLabel("Cliente:"));
        filtros.add(clienteField);
        filtros.add(new JLabel("Tipo:"));
        filtros.add(tipoBox);
        filtros.add(new JLabel("Marca:"));
        filtros.add(marcaBox);
        filtros.add(new JLabel("Categoria:"));
        filtros.add(categoriaBox);

        add(filtros, BorderLayout.NORTH);

        tabelaVeiculos = new JTable(new VeiculoLocacaoTableModel(veiculosDisponiveis));
        add(new JScrollPane(tabelaVeiculos), BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.add(new JLabel("Dias:"));
        southPanel.add(diasField);
        southPanel.add(new JLabel("Data:"));
        southPanel.add(dataField);
        locarBtn = new JButton("Locar");
        southPanel.add(locarBtn);

        add(southPanel, BorderLayout.SOUTH);

        // Adicione listeners para filtrar e locar veículos
    }
}

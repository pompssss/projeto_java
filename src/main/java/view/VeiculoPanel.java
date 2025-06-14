/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Categoria;
import model.Estado;
import model.Marca;

/**
 *
 * @author Pomps
 */
public class VeiculoPanel extends JPanel {
    private JComboBox<Marca> marcaBox;
    private JComboBox<Estado> estadoBox;
    private JComboBox<Categoria> categoriaBox;
    private JComboBox<Object> modeloBox; // Use o tipo correto conforme o veículo
    private JTextField valorDeCompraField, placaField, anoField;
    private JButton incluirBtn;

    public VeiculoPanel() {
        setLayout(new GridLayout(8, 2));

        marcaBox = new JComboBox<>(Marca.values());
        estadoBox = new JComboBox<>(Estado.values());
        categoriaBox = new JComboBox<>(Categoria.values());
        modeloBox = new JComboBox<>(); // Preencha conforme o tipo selecionado
        valorDeCompraField = new JTextField();
        placaField = new JTextField();
        anoField = new JTextField();
        incluirBtn = new JButton("Incluir Veículo");

        add(new JLabel("Marca:"));
        add(marcaBox);
        add(new JLabel("Estado:"));
        add(estadoBox);
        add(new JLabel("Categoria:"));
        add(categoriaBox);
        add(new JLabel("Modelo:"));
        add(modeloBox);
        add(new JLabel("Valor de Compra:"));
        add(valorDeCompraField);
        add(new JLabel("Placa:"));
        add(placaField);
        add(new JLabel("Ano:"));
        add(anoField);
        add(incluirBtn);

        // Adicione listeners para atualizar modeloBox conforme o tipo de veículo
    }
}

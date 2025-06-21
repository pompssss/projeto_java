/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.VeiculoController;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import javax.swing.*;

import model.*;

public class VeiculoPanel extends JPanel {
    private final JComboBox<String> tipoVeiculoBox;
    private final JComboBox<Marca> marcaBox;
    private final JComboBox<Estado> estadoBox;
    private final JComboBox<Categoria> categoriaBox;
    private final JComboBox<Object> modeloBox;
    private final JTextField valorDeCompraField;
    private final JTextField placaField;
    private final JTextField anoField;
    private final JButton incluirBtn;

    public VeiculoPanel() {
        setLayout(new GridLayout(9, 2)); // aumentou uma linha por causa do novo campo

        tipoVeiculoBox = new JComboBox<>(new String[] { "Automóvel", "Motocicleta", "Van" });
        marcaBox = new JComboBox<>(Marca.values());
        estadoBox = new JComboBox<>(Estado.values());
        categoriaBox = new JComboBox<>(Categoria.values());
        modeloBox = new JComboBox<>();
        valorDeCompraField = new JTextField();
        placaField = new JTextField();
        anoField = new JTextField();
        incluirBtn = new JButton("Incluir Veículo");

        incluirBtn.addActionListener((ActionEvent e) -> {
            try {
                String tipo = (String) tipoVeiculoBox.getSelectedItem();
                Marca marca = (Marca) marcaBox.getSelectedItem();
                Estado estado = (Estado) estadoBox.getSelectedItem();
                Categoria categoria = (Categoria) categoriaBox.getSelectedItem();
                Object modelo = modeloBox.getSelectedItem();
                double valorDeCompra = Double.parseDouble(valorDeCompraField.getText());
                String placa = placaField.getText();
                int ano = Integer.parseInt(anoField.getText());
                
                Veiculo veiculo = null;
                switch (tipo) {
                    case "Automóvel" -> veiculo = new Automovel(
                            marca, estado, categoria, valorDeCompra, placa, ano, (ModeloAutomovel) modelo
                    );
                    // Adicione cases para Motocicleta e Van
                }
                
                VeiculoController.salvar(veiculo);
                
                JOptionPane.showMessageDialog(VeiculoPanel.this, "Veículo incluído com sucesso!");
            } catch (HeadlessException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(VeiculoPanel.this, "Erro ao incluir veículo: " + ex.getMessage());
            }
        });
        add(new JLabel("Tipo de Veículo:"));
        add(tipoVeiculoBox);
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

        tipoVeiculoBox.addActionListener((ActionEvent e) -> {
            atualizarModelos();
        });

        // Atualiza os modelos no início
        atualizarModelos();
    
    }

    private void atualizarModelos() {
        modeloBox.removeAllItems();
        String tipoSelecionado = (String) tipoVeiculoBox.getSelectedItem();

        if (null != tipoSelecionado) switch (tipoSelecionado) {
            case "Automóvel" -> {
                for (ModeloAutomovel modelo : ModeloAutomovel.values()) {
                    modeloBox.addItem(modelo);
                }
            }
            case "Motocicleta" -> {
                for (ModeloMotocicleta modelo : ModeloMotocicleta.values()) {
                    modeloBox.addItem(modelo);
                }
            }
            case "Van" -> {
                for (ModeloVan modelo : ModeloVan.values()) {
                    modeloBox.addItem(modelo);
                }
            }
            default -> {
            }
        }
    }
    
}
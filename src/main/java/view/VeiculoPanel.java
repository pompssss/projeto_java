package view;

import controller.VeiculoController;
import java.awt.*;
import javax.swing.*;
import model.*;

public class VeiculoPanel extends JPanel {
    private final JComboBox<String> tipoVeiculoBox;
    private final JComboBox<Marca> marcaBox;
    private final JComboBox<Estado> estadoBox;
    private final JComboBox<Categoria> categoriaBox;
    private final JComboBox<Object> modeloBox;
    private final JTextField valorDeCompraField, placaField, anoField;
    private final JButton incluirBtn, btnLimpar, btnFechar;
    private final MainFrame mainFrame;

    public VeiculoPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 10));

        // --- PAINEL DE AÇÕES SUPERIOR ---
        JPanel topActionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLimpar = new JButton("Limpar Formulário");
        btnFechar = new JButton("Fechar Aba");
        topActionsPanel.add(btnLimpar);
        topActionsPanel.add(btnFechar);
        add(topActionsPanel, BorderLayout.NORTH);

        // --- PAINEL CENTRAL COM FORMULÁRIO E BOTÃO ---
        JPanel centerPanel = new JPanel(new BorderLayout(10,10));
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        
        tipoVeiculoBox = new JComboBox<>(new String[] { "Automóvel", "Motocicleta", "Van" });
        marcaBox = new JComboBox<>(Marca.values());
        estadoBox = new JComboBox<>(new Estado[]{Estado.NOVO, Estado.DISPONIVEL}); // Apenas estados válidos para inclusão
        categoriaBox = new JComboBox<>(Categoria.values());
        modeloBox = new JComboBox<>();
        valorDeCompraField = new JTextField();
        placaField = new JTextField();
        anoField = new JTextField();
        
        formPanel.add(new JLabel("Tipo de Veículo:"));
        formPanel.add(tipoVeiculoBox);
        formPanel.add(new JLabel("Marca:"));
        formPanel.add(marcaBox);
        formPanel.add(new JLabel("Estado:"));
        formPanel.add(estadoBox);
        formPanel.add(new JLabel("Categoria:"));
        formPanel.add(categoriaBox);
        formPanel.add(new JLabel("Modelo:"));
        formPanel.add(modeloBox);
        formPanel.add(new JLabel("Valor de Compra:"));
        formPanel.add(valorDeCompraField);
        formPanel.add(new JLabel("Placa (XXX-0000):"));
        formPanel.add(placaField);
        formPanel.add(new JLabel("Ano:"));
        formPanel.add(anoField);
        
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        incluirBtn = new JButton("Incluir Veículo");
        buttonWrapper.add(incluirBtn);

        centerPanel.add(formPanel, BorderLayout.CENTER);
        centerPanel.add(buttonWrapper, BorderLayout.SOUTH);
        
        // Adiciona um preenchimento nas laterais para centralizar o form
        add(new JPanel(), BorderLayout.WEST);
        add(new JPanel(), BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);


        // --- LISTENERS ---
        incluirBtn.addActionListener(e -> incluirVeiculo());
        btnLimpar.addActionListener(e -> limparCampos());
        btnFechar.addActionListener(e -> this.mainFrame.fecharAba(this));
        
        tipoVeiculoBox.addActionListener(e -> atualizarModelos());
        
        atualizarModelos();
    }
    
    private void incluirVeiculo(){
        try {
            String tipo = (String) tipoVeiculoBox.getSelectedItem();
            Marca marca = (Marca) marcaBox.getSelectedItem();
            Estado estado = (Estado) estadoBox.getSelectedItem();
            Categoria categoria = (Categoria) categoriaBox.getSelectedItem();
            Object modelo = modeloBox.getSelectedItem();
            double valorDeCompra = Double.parseDouble(valorDeCompraField.getText());
            String placa = placaField.getText();
            int ano = Integer.parseInt(anoField.getText());
            
            if (placa.trim().isEmpty() || VeiculoController.buscarPorPlaca(placa) != null) {
                JOptionPane.showMessageDialog(this, "Placa inválida ou já cadastrada.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Veiculo veiculo = switch (tipo) {
                case "Automóvel" -> new Automovel(marca, estado, categoria, valorDeCompra, placa, ano, (ModeloAutomovel) modelo);
                case "Motocicleta" -> new Motocicleta(marca, estado, categoria, valorDeCompra, placa, ano, (ModeloMotocicleta) modelo);
                case "Van" -> new Van(marca, estado, categoria, valorDeCompra, placa, ano, (ModeloVan) modelo);
                default -> null;
            };
            
            if (veiculo != null) {
                VeiculoController.salvar(veiculo);
                JOptionPane.showMessageDialog(this, "Veículo incluído com sucesso!");
                limparCampos();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor de Compra e Ano devem ser números válidos.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarModelos() {
        modeloBox.removeAllItems();
        String tipoSelecionado = (String) tipoVeiculoBox.getSelectedItem();

        if (null != tipoSelecionado) switch (tipoSelecionado) {
            case "Automóvel" -> {
                for (ModeloAutomovel modelo : ModeloAutomovel.values()) modeloBox.addItem(modelo);
            }
            case "Motocicleta" -> {
                for (ModeloMotocicleta modelo : ModeloMotocicleta.values()) modeloBox.addItem(modelo);
            }
            case "Van" -> {
                for (ModeloVan modelo : ModeloVan.values()) modeloBox.addItem(modelo);
            }
        }
    }
    
    private void limparCampos(){
        valorDeCompraField.setText("");
        placaField.setText("");
        anoField.setText("");
        tipoVeiculoBox.setSelectedIndex(0);
        marcaBox.setSelectedIndex(0);
        estadoBox.setSelectedIndex(0);
        categoriaBox.setSelectedIndex(0);
    }
}
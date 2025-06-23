package view;

import controller.VeiculoController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import model.*;
import tablemodel.VeiculoGerenciamentoTableModel;

/**
 * Painel da UI para gerenciar (CRUD) os Veículos da frota.
 */
public class GerenciarVeiculosPanel extends JPanel {
    
    // --- Componentes da Interface Gráfica ---
    private final JTable tabelaVeiculos;
    private final VeiculoGerenciamentoTableModel tableModel;
    
    private final JComboBox<String> tipoVeiculoBox;
    private final JComboBox<Marca> marcaBox;
    private final JComboBox<Estado> estadoBox;
    private final JComboBox<Categoria> categoriaBox;
    private final JComboBox<Object> modeloBox;
    private final JFormattedTextField valorDeCompraField;
    private final JFormattedTextField placaField;
    private final JTextField anoField;

    private final JButton btnAdicionar, btnAtualizar, btnExcluir, btnLimpar, btnRefresh, btnFechar;
    
    private final MainFrame mainFrame;

    /**
     * Construtor do painel de Gerenciamento de Veículos.
     * @param mainFrame A referência ao frame principal da aplicação.
     */
    public GerenciarVeiculosPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 10));

        JPanel northPanel = new JPanel(new BorderLayout(10, 10));

        JPanel topActionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRefresh = new JButton("Atualizar Lista");
        btnFechar = new JButton("Fechar Aba");
        topActionsPanel.add(btnRefresh);
        topActionsPanel.add(btnFechar);
        northPanel.add(topActionsPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(0, 4, 5, 5));
        
        tipoVeiculoBox = new JComboBox<>(new String[]{"Automóvel", "Motocicleta", "Van"});
        marcaBox = new JComboBox<>(Marca.values());
        estadoBox = new JComboBox<>(Estado.values());
        categoriaBox = new JComboBox<>(Categoria.values());
        modeloBox = new JComboBox<>();
        anoField = new JTextField();

        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Double.class);
        formatter.setMinimum(0.0);
        formatter.setMaximum(Double.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        valorDeCompraField = new JFormattedTextField(formatter);
        valorDeCompraField.setValue(0.0);

        JFormattedTextField tempPlacaField;
        try {
            MaskFormatter placaFormatter = new MaskFormatter("UUU-####");
            placaFormatter.setPlaceholderCharacter('_');
            tempPlacaField = new JFormattedTextField(placaFormatter);
        } catch (ParseException e) {
            System.err.println("Erro na formatação da máscara da placa: " + e.getMessage());
            tempPlacaField = new JFormattedTextField();
        }
        placaField = tempPlacaField;
        
        formPanel.add(new JLabel("Tipo:"));
        formPanel.add(tipoVeiculoBox);
        formPanel.add(new JLabel("Marca:"));
        formPanel.add(marcaBox);
        formPanel.add(new JLabel("Modelo:"));
        formPanel.add(modeloBox);
        formPanel.add(new JLabel("Categoria:"));
        formPanel.add(categoriaBox);
        formPanel.add(new JLabel("Placa:"));
        formPanel.add(placaField);
        formPanel.add(new JLabel("Ano:"));
        formPanel.add(anoField);
        formPanel.add(new JLabel("Valor de Compra:"));
        formPanel.add(valorDeCompraField);
        formPanel.add(new JLabel("Estado:"));
        formPanel.add(estadoBox);
        northPanel.add(formPanel, BorderLayout.CENTER);

        JPanel crudButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnAdicionar = new JButton("Adicionar Veículo");
        btnAtualizar = new JButton("Salvar Alterações");
        btnExcluir = new JButton("Excluir Veículo");
        btnLimpar = new JButton("Limpar Formulário");
        crudButtonsPanel.add(btnAdicionar);
        crudButtonsPanel.add(btnAtualizar);
        crudButtonsPanel.add(btnExcluir);
        crudButtonsPanel.add(btnLimpar);
        northPanel.add(crudButtonsPanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);

        tableModel = new VeiculoGerenciamentoTableModel(VeiculoController.listarTodos());
        tabelaVeiculos = new JTable(tableModel);
        add(new JScrollPane(tabelaVeiculos), BorderLayout.CENTER);
        
        btnAdicionar.addActionListener(e -> adicionarVeiculo());
        btnAtualizar.addActionListener(e -> atualizarVeiculo());
        btnExcluir.addActionListener(e -> excluirVeiculo());
        btnLimpar.addActionListener(e -> limparCampos());
        btnRefresh.addActionListener(e -> refreshTable());
        btnFechar.addActionListener(e -> this.mainFrame.fecharAba(this));
        
        tipoVeiculoBox.addActionListener(e -> atualizarModelos());
        
        tabelaVeiculos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaVeiculos.getSelectedRow() != -1) {
                preencherFormulario();
            }
        });
        
        atualizarModelos();
        limparCampos();
    }

    private void preencherFormulario() {
        int selectedRow = tabelaVeiculos.convertRowIndexToModel(tabelaVeiculos.getSelectedRow());
        if (selectedRow == -1) return;
        
        Veiculo veiculo = tableModel.getVeiculoAt(selectedRow);

        if (veiculo instanceof Automovel) {
            tipoVeiculoBox.setSelectedItem("Automóvel");
        } else if (veiculo instanceof Motocicleta) {
            tipoVeiculoBox.setSelectedItem("Motocicleta");
        } else if (veiculo instanceof Van) {
            tipoVeiculoBox.setSelectedItem("Van");
        }
        
        marcaBox.setSelectedItem(veiculo.getMarca());
        categoriaBox.setSelectedItem(veiculo.getCategoria());
        estadoBox.setSelectedItem(veiculo.getEstado());
        placaField.setText(veiculo.getPlaca());
        anoField.setText(String.valueOf(veiculo.getAno()));
        valorDeCompraField.setValue(veiculo.getValorDeCompra());

        atualizarModelos();
        modeloBox.setSelectedItem(veiculo.getModelo());

        placaField.setEditable(false);
        btnAdicionar.setEnabled(false);
    }

    private void adicionarVeiculo() {
        try {
            String placa = placaField.getText();
            if (placa.contains("_")) {
                JOptionPane.showMessageDialog(this, "A placa deve ser preenchida completamente.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (VeiculoController.buscarPorPlaca(placa) != null) {
                JOptionPane.showMessageDialog(this, "Esta placa já está cadastrada.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String tipo = (String) tipoVeiculoBox.getSelectedItem();
            Marca marca = (Marca) marcaBox.getSelectedItem();
            Estado estado = (Estado) estadoBox.getSelectedItem();
            Categoria categoria = (Categoria) categoriaBox.getSelectedItem();
            Object modelo = modeloBox.getSelectedItem();
            double valorDeCompra = ((Number) valorDeCompraField.getValue()).doubleValue();
            int ano = Integer.parseInt(anoField.getText());

            Veiculo veiculo = switch (tipo) {
                case "Automóvel" -> new Automovel(marca, estado, categoria, valorDeCompra, placa, ano, (ModeloAutomovel) modelo);
                case "Motocicleta" -> new Motocicleta(marca, estado, categoria, valorDeCompra, placa, ano, (ModeloMotocicleta) modelo);
                case "Van" -> new Van(marca, estado, categoria, valorDeCompra, placa, ano, (ModeloVan) modelo);
                default -> null;
            };

            if (veiculo != null) {
                VeiculoController.salvar(veiculo);
                JOptionPane.showMessageDialog(this, "Veículo adicionado com sucesso!");
                refreshTable();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O Ano deve ser um número válido.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Ação do botão "Salvar Alterações". Atualiza os dados do veículo selecionado.
     */
    private void atualizarVeiculo() {
        int selectedRow = tabelaVeiculos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Pega o objeto Veiculo da linha selecionada.
            Veiculo veiculo = tableModel.getVeiculoAt(tabelaVeiculos.convertRowIndexToModel(selectedRow));
            
            // Atualiza os dados do objeto Veiculo com os valores do formulário.
            veiculo.setEstado((Estado) estadoBox.getSelectedItem());
            veiculo.setValorDeCompra(((Number) valorDeCompraField.getValue()).doubleValue());
            
            // CORRIGIDO: Lê o valor do campo "anoField", converte para inteiro e atualiza o veículo.
            int ano = Integer.parseInt(anoField.getText());
            veiculo.setAno(ano);
            
            // Chama o controller para salvar as alterações na lista em memória.
            VeiculoController.atualizar(veiculo);
            JOptionPane.showMessageDialog(this, "Veículo atualizado com sucesso!");
            refreshTable();

        } catch (NumberFormatException ex) {
            // Trata erro caso o valor do ano ou do valor de compra não seja um número válido.
            JOptionPane.showMessageDialog(this, "O ano e o valor de compra devem ser números válidos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
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

    private void limparCampos() {
        placaField.setValue(null);
        anoField.setText("");
        valorDeCompraField.setValue(0.0);
        tipoVeiculoBox.setSelectedIndex(0);
        marcaBox.setSelectedIndex(0);
        estadoBox.setSelectedIndex(0);
        categoriaBox.setSelectedIndex(0);
        tabelaVeiculos.clearSelection();
        
        placaField.setEditable(true);
        btnAdicionar.setEnabled(true);
    }
    
    private void refreshTable() {
        tableModel.setVeiculos(VeiculoController.listarTodos());
        limparCampos();
    }
}
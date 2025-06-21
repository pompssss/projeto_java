package view;

import controller.ClienteController;
import controller.VeiculoController;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.swing.*;
import model.*;
import tablemodel.VeiculoLocacaoTableModel;

public class LocacaoPanel extends JPanel {
    private final JTextField clienteCpfField, diasField, dataField;
    private final JComboBox<String> tipoBox;
    private final JComboBox<Marca> marcaBox;
    private final JComboBox<Categoria> categoriaBox;
    private final JTable tabelaVeiculos;
    private final VeiculoLocacaoTableModel tableModel;
    private final JButton locarBtn, filtrarBtn, btnFechar;
    private final MainFrame mainFrame;

    public LocacaoPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 10));

        // --- PAINEL NORTE (FILTROS E AÇÕES) ---
        JPanel northPanel = new JPanel(new BorderLayout());
        JPanel topActionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filtrarBtn = new JButton("Atualizar / Filtrar");
        btnFechar = new JButton("Fechar Aba");
        topActionsPanel.add(filtrarBtn);
        topActionsPanel.add(btnFechar);
        northPanel.add(topActionsPanel, BorderLayout.NORTH);

        JPanel filtrosPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        tipoBox = new JComboBox<>(new String[]{"Todos", "Automóvel", "Van", "Motocicleta"});
        marcaBox = new JComboBox<>();
        categoriaBox = new JComboBox<>();
        
        marcaBox.addItem(null); // Representa "Todas"
        for(Marca m : Marca.values()) marcaBox.addItem(m);
        categoriaBox.addItem(null); // Representa "Todas"
        for(Categoria c : Categoria.values()) categoriaBox.addItem(c);

        filtrosPanel.add(new JLabel("Tipo Veículo:"));
        filtrosPanel.add(tipoBox);
        filtrosPanel.add(new JLabel("Marca:"));
        filtrosPanel.add(marcaBox);
        filtrosPanel.add(new JLabel("Categoria:"));
        filtrosPanel.add(categoriaBox);
        northPanel.add(filtrosPanel, BorderLayout.CENTER);
        
        add(northPanel, BorderLayout.NORTH);

        // --- TABELA DE VEÍCULOS ---
        tableModel = new VeiculoLocacaoTableModel(VeiculoController.listarDisponiveis());
        tabelaVeiculos = new JTable(tableModel);
        add(new JScrollPane(tabelaVeiculos), BorderLayout.CENTER);

        // --- PAINEL SUL (AÇÃO DE LOCAR) ---
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        clienteCpfField = new JTextField(10);
        diasField = new JTextField(5);
        dataField = new JTextField(10);
        locarBtn = new JButton("Locar Veículo Selecionado");

        southPanel.add(new JLabel("CPF Cliente:"));
        southPanel.add(clienteCpfField);
        southPanel.add(new JLabel("Dias:"));
        southPanel.add(diasField);
        southPanel.add(new JLabel("Data (dd/MM/yyyy):"));
        southPanel.add(dataField);
        southPanel.add(locarBtn);
        add(southPanel, BorderLayout.SOUTH);

        // --- LISTENERS ---
        filtrarBtn.addActionListener(e -> filtrarVeiculos());
        btnFechar.addActionListener(e -> this.mainFrame.fecharAba(this));
        locarBtn.addActionListener(e -> locarVeiculo());
    }
    
    private void filtrarVeiculos() {
        String tipo = (String) tipoBox.getSelectedItem();
        Marca marca = (Marca) marcaBox.getSelectedItem();
        Categoria categoria = (Categoria) categoriaBox.getSelectedItem();

        List<Veiculo> veiculosFiltrados = VeiculoController.listarDisponiveis().stream()
            .filter(v -> tipo.equals("Todos") || v.getClass().getSimpleName().equalsIgnoreCase(tipo))
            .filter(v -> marca == null || v.getMarca().equals(marca))
            .filter(v -> categoria == null || v.getCategoria().equals(categoria))
            .toList();
        
        tableModel.setVeiculos(veiculosFiltrados);
    }

    private void locarVeiculo() {
        int selectedRow = tabelaVeiculos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para locar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cpfCliente = clienteCpfField.getText();
        if (cpfCliente.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o CPF do cliente.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente cliente = ClienteController.buscarPorCpf(cpfCliente);
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Cliente com CPF " + cpfCliente + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int dias = Integer.parseInt(diasField.getText());
            if (dias <= 0) throw new NumberFormatException();
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Calendar dataLocacao = Calendar.getInstance();
            dataLocacao.setTime(sdf.parse(dataField.getText()));

            Veiculo veiculo = tableModel.getVeiculoAt(tabelaVeiculos.convertRowIndexToModel(selectedRow));
            veiculo.locar(dias, dataLocacao, cliente);
            VeiculoController.atualizar(veiculo);

            JOptionPane.showMessageDialog(this, "Veículo " + veiculo.getPlaca() + " locado para " + cliente.getNome() + " com sucesso!");

            clienteCpfField.setText("");
            diasField.setText("");
            dataField.setText("");
            filtrarVeiculos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O número de dias deve ser um inteiro positivo.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use dd/MM/yyyy.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}
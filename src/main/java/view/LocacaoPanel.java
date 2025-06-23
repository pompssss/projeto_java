package view;

import controller.ClienteController;
import controller.VeiculoController;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import model.*;
import tablemodel.VeiculoLocacaoTableModel;

/**
 * Painel da UI para a funcionalidade de Locação de Veículos.
 */
public class LocacaoPanel extends JPanel {
    // --- Componentes da Interface Gráfica ---
    private final JTextField clienteCpfField, diasField, dataField;
    private final JComboBox<String> tipoBox;
    private final JComboBox<Marca> marcaBox;
    private final JComboBox<Categoria> categoriaBox;
    private final JTable tabelaVeiculos;
    private final VeiculoLocacaoTableModel tableModel;
    private final JButton locarBtn, filtrarBtn, btnFechar;
    private final MainFrame mainFrame;

    /**
     * Construtor do painel de Locação.
     * @param mainFrame A referência ao frame principal da aplicação.
     */
    public LocacaoPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 10));

        // --- PAINEL NORTE (FILTROS E AÇÕES) ---
        JPanel northPanel = new JPanel(new BorderLayout());
        
        // Subpainel para botões no topo.
        JPanel topActionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filtrarBtn = new JButton("Atualizar / Filtrar");
        btnFechar = new JButton("Fechar Aba");
        topActionsPanel.add(filtrarBtn);
        topActionsPanel.add(btnFechar);
        northPanel.add(topActionsPanel, BorderLayout.NORTH);

        // Subpainel para os campos de filtro.
        JPanel filtrosPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        tipoBox = new JComboBox<>(new String[]{"Todos", "Automóvel", "Van", "Motocicleta"});
        marcaBox = new JComboBox<>();
        categoriaBox = new JComboBox<>();
        
        // Adiciona a opção "Todas" (representada por null) e depois os valores do enum.
        marcaBox.addItem(null);
        for(Marca m : Marca.values()) marcaBox.addItem(m);
        categoriaBox.addItem(null);
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
    
    /**
     * Filtra os veículos com base nos valores selecionados nos ComboBoxes.
     */
    private void filtrarVeiculos() {
        // Coleta os valores dos filtros.
        String tipo = (String) tipoBox.getSelectedItem();
        Marca marca = (Marca) marcaBox.getSelectedItem();
        Categoria categoria = (Categoria) categoriaBox.getSelectedItem();

        // REATORADO: Usa o novo método centralizado do VeiculoController.
        tableModel.setVeiculos(VeiculoController.listarDisponiveisComFiltro(tipo, marca, categoria));
    }

    /**
     * Executa a lógica para locar um veículo selecionado.
     */
    private void locarVeiculo() {
        // Valida se um veículo foi selecionado.
        int selectedRow = tabelaVeiculos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para locar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Valida se o CPF do cliente foi informado.
        String cpfCliente = clienteCpfField.getText();
        if (cpfCliente.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o CPF do cliente.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Busca o cliente pelo CPF.
        Cliente cliente = ClienteController.buscarPorCpf(cpfCliente);
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Cliente com CPF " + cpfCliente + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Converte e valida os dias e a data.
            int dias = Integer.parseInt(diasField.getText());
            if (dias <= 0) throw new NumberFormatException();
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false); // Impede datas inválidas como 32/01/2025
            Calendar dataLocacao = Calendar.getInstance();
            dataLocacao.setTime(sdf.parse(dataField.getText()));

            // Obtém o veículo, executa a locação e atualiza no controller.
            Veiculo veiculo = tableModel.getVeiculoAt(tabelaVeiculos.convertRowIndexToModel(selectedRow));
            veiculo.locar(dias, dataLocacao, cliente);
            VeiculoController.atualizar(veiculo);

            JOptionPane.showMessageDialog(this, "Veículo " + veiculo.getPlaca() + " locado para " + cliente.getNome() + " com sucesso!");

            // Limpa os campos e atualiza a tabela.
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
package view;

import controller.VeiculoController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Categoria;
import model.Marca;
import model.Veiculo;
import tablemodel.VeiculoVendaTableModel;

/**
 * Painel da UI para a funcionalidade de Venda de Veículos.
 */
public class VendaPanel extends JPanel {
    // --- Componentes da Interface Gráfica ---
    private JTable tabelaVenda;
    private VeiculoVendaTableModel tableModel;
    private final JButton venderBtn, btnRefresh, btnFechar;
    private final JComboBox<String> tipoBox;
    private final JComboBox<Marca> marcaBox;
    private final JComboBox<Categoria> categoriaBox;
    
    private final MainFrame mainFrame;

    /**
     * Construtor do painel de Venda.
     * @param mainFrame A referência ao frame principal da aplicação.
     */
    public VendaPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 10));

        // --- PAINEL NORTE: Contém filtros e botões ---
        JPanel northPanel = new JPanel(new BorderLayout(5, 5));

        // Subpainel para os botões "Atualizar" e "Fechar".
        JPanel topActionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRefresh = new JButton("Atualizar / Filtrar");
        btnFechar = new JButton("Fechar Aba");
        topActionsPanel.add(btnRefresh);
        topActionsPanel.add(btnFechar);
        northPanel.add(topActionsPanel, BorderLayout.NORTH);
        
        // Subpainel para os campos de filtro.
        JPanel filtrosPanel = new JPanel(new GridLayout(1, 6, 5, 5));
        tipoBox = new JComboBox<>(new String[]{"Todos", "Automóvel", "Van", "Motocicleta"});
        marcaBox = new JComboBox<>();
        categoriaBox = new JComboBox<>();
        
        // Adiciona a opção "Todas" (null) e depois os valores dos enums.
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

        // --- TABELA PRINCIPAL: Exibe os veículos disponíveis ---
        tableModel = new VeiculoVendaTableModel(VeiculoController.listarDisponiveis());
        tabelaVenda = new JTable(tableModel);
        add(new JScrollPane(tabelaVenda), BorderLayout.CENTER);

        // --- PAINEL SUL: Contém o botão "Vender" ---
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        venderBtn = new JButton("Vender Veículo Selecionado");
        southPanel.add(venderBtn);
        add(southPanel, BorderLayout.SOUTH);

        // --- LISTENERS: Definem as ações para cada botão ---
        venderBtn.addActionListener(e -> venderVeiculo());
        btnRefresh.addActionListener(e -> refreshTableWithFilters()); 
        btnFechar.addActionListener(e -> this.mainFrame.fecharAba(this));
        
        // Carrega os dados na tabela ao iniciar.
        refreshTableWithFilters();
    }
    
    /**
     * Executa a lógica para vender um veículo selecionado na tabela.
     */
    private void venderVeiculo() {
        int selectedRow = tabelaVenda.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para vender.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtém o veículo selecionado.
        Veiculo veiculo = tableModel.getVeiculoAt(tabelaVenda.convertRowIndexToModel(selectedRow));
        String precoVenda = String.format("R$%.2f", veiculo.getValorParaVenda());
        
        // Pede confirmação ao usuário.
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja vender o veículo " + veiculo.getPlaca() + " por " + precoVenda + "?", "Confirmar Venda", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Muda o estado do veículo para VENDIDO e atualiza no controller.
            veiculo.vender();
            VeiculoController.atualizar(veiculo);
            JOptionPane.showMessageDialog(this, "Veículo vendido com sucesso!");
            refreshTableWithFilters(); // Atualiza a tabela.
        }
    }
    
    /**
     * Atualiza a tabela de veículos, aplicando os filtros selecionados.
     */
    private void refreshTableWithFilters() {
        // Coleta os valores dos filtros.
        String tipo = (String) tipoBox.getSelectedItem();
        Marca marca = (Marca) marcaBox.getSelectedItem();
        Categoria categoria = (Categoria) categoriaBox.getSelectedItem();

        // REATORADO: Usa o novo método centralizado do VeiculoController.
        List<Veiculo> veiculosFiltrados = VeiculoController.listarDisponiveisComFiltro(tipo, marca, categoria);
        
        // Atualiza o modelo da tabela com a lista filtrada.
        tableModel.setVeiculos(veiculosFiltrados);
    }
}
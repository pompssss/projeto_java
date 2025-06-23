package view;

import controller.ClienteController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Cliente;
import tablemodel.ClienteTableModel;

/**
 * Painel da interface gráfica para gerenciar (CRUD) os Clientes.
 */
public class ClientePanel extends JPanel {
    
    // --- Componentes da Interface ---
    private final JTable tabelaClientes;
    private final ClienteTableModel modelo;
    private final JTextField nomeField, sobrenomeField, rgField, cpfField, enderecoField;
    private final JButton adicionarBtn, atualizarBtn, excluirBtn, btnRefresh, btnFechar;
    private final MainFrame mainFrame; // Referência ao frame principal para fechar a aba

    /**
     * Construtor do painel de Clientes.
     * @param clientes A lista inicial de clientes a ser exibida.
     * @param mainFrame A referência ao frame principal da aplicação.
     */
    public ClientePanel(List<Cliente> clientes, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 10));

        // --- PAINEL DE AÇÕES SUPERIOR (Atualizar, Fechar Aba) ---
        JPanel topActionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRefresh = new JButton("Atualizar");
        btnFechar = new JButton("Fechar Aba");
        topActionsPanel.add(btnRefresh);
        topActionsPanel.add(btnFechar);

        // --- PAINEL DE FORMULÁRIO E BOTÕES DE CRUD ---
        JPanel formAndCrudPanel = new JPanel(new BorderLayout(10, 5));
        
        // Painel do formulário para preenchimento dos dados do cliente.
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        nomeField = new JTextField();
        sobrenomeField = new JTextField();
        rgField = new JTextField();
        cpfField = new JTextField();
        enderecoField = new JTextField();
        
        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("Sobrenome:"));
        formPanel.add(sobrenomeField);
        formPanel.add(new JLabel("RG:"));
        formPanel.add(rgField);
        formPanel.add(new JLabel("CPF:"));
        formPanel.add(cpfField);
        formPanel.add(new JLabel("Endereço:"));
        formPanel.add(enderecoField);

        // Painel com os botões de ação (Adicionar, Atualizar, Excluir).
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        adicionarBtn = new JButton("Adicionar");
        atualizarBtn = new JButton("Atualizar");
        excluirBtn = new JButton("Excluir");
        
        buttonPanel.add(adicionarBtn);
        buttonPanel.add(atualizarBtn);
        buttonPanel.add(excluirBtn);
        
        // Agrupa o formulário e os botões.
        formAndCrudPanel.add(formPanel, BorderLayout.CENTER);
        formAndCrudPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // --- PAINEL NORTE (Agrupa o painel de ações e o painel de formulário) ---
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(topActionsPanel, BorderLayout.NORTH);
        northPanel.add(formAndCrudPanel, BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);

        // --- TABELA PRINCIPAL (Exibe os clientes) ---
        modelo = new ClienteTableModel(clientes);
        tabelaClientes = new JTable(modelo);
        add(new JScrollPane(tabelaClientes), BorderLayout.CENTER);

        // --- LISTENERS (Ações dos botões e da seleção da tabela) ---
        adicionarBtn.addActionListener(e -> adicionarCliente());
        atualizarBtn.addActionListener(e -> atualizarCliente());
        excluirBtn.addActionListener(e -> excluirCliente());
        btnRefresh.addActionListener(e -> refreshData());
        btnFechar.addActionListener(e -> this.mainFrame.fecharAba(this));

        // Listener para preencher o formulário quando um cliente é selecionado na tabela.
        tabelaClientes.getSelectionModel().addListSelectionListener(e -> {
            // Garante que o código só seja executado ao final da seleção.
            if (!e.getValueIsAdjusting() && tabelaClientes.getSelectedRow() != -1) {
                // Obtém a linha selecionada na tabela (considerando o modelo).
                int selectedRow = tabelaClientes.convertRowIndexToModel(tabelaClientes.getSelectedRow());
                Cliente clienteSelecionado = modelo.getClienteAt(selectedRow);
                
                // Preenche os campos do formulário com os dados do cliente.
                nomeField.setText(clienteSelecionado.getNome());
                sobrenomeField.setText(clienteSelecionado.getSobrenome());
                rgField.setText(clienteSelecionado.getRg());
                cpfField.setText(clienteSelecionado.getCpf());
                enderecoField.setText(clienteSelecionado.getEndereco());
            }
        });
    }

    /**
     * Ação do botão "Adicionar". Valida os campos, cria um novo cliente e o salva através do controller.
     */
    private void adicionarCliente() {
        if (camposInvalidos()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Cliente novoCliente = new Cliente(nomeField.getText(), sobrenomeField.getText(), rgField.getText(), cpfField.getText(), enderecoField.getText());
        ClienteController.salvar(novoCliente);
        JOptionPane.showMessageDialog(this, "Cliente adicionado com sucesso!");
        refreshData(); // Atualiza a tabela e limpa os campos.
    }
    
    /**
     * Ação do botão "Atualizar". Valida os campos e atualiza o cliente selecionado através do controller.
     */
    private void atualizarCliente() {
        int selectedRow = tabelaClientes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (camposInvalidos()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Pega o CPF original do cliente selecionado para saber qual registro atualizar.
        Cliente clienteSelecionado = modelo.getClienteAt(tabelaClientes.convertRowIndexToModel(selectedRow));
        String cpfOriginal = clienteSelecionado.getCpf();

        // Cria um novo objeto cliente com todos os dados dos campos de texto (incluindo o novo CPF).
        Cliente clienteAtualizado = new Cliente(
            nomeField.getText(), 
            sobrenomeField.getText(), 
            rgField.getText(), 
            cpfField.getText(), // Agora pega o valor do campo de texto.
            enderecoField.getText()
        );
        
        // Envia o CPF original e os novos dados para o controller fazer a atualização.
        ClienteController.atualizar(cpfOriginal, clienteAtualizado);
        JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
        refreshData();
    }
    
    /**
     * Ação do botão "Excluir". Pede confirmação e solicita a exclusão ao controller.
     * O controller agora é responsável pela validação.
     */
    private void excluirCliente() {
        int selectedRow = tabelaClientes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Cliente clienteSelecionado = modelo.getClienteAt(selectedRow);
        
        // Pede confirmação ao usuário.
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este cliente?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Tenta excluir através do controller.
                ClienteController.excluir(clienteSelecionado);
                JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
                refreshData();
            } catch (IllegalStateException ex) {
                // Se o controller lançar uma exceção (erro de regra de negócio), exibe a mensagem.
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Exclusão", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Atualiza os dados da tabela buscando a lista mais recente do controller e limpa os campos do formulário.
     */
    private void refreshData() {
        modelo.setClientes(ClienteController.listarTodos());
        limparCampos();
    }
    
    /**
     * Limpa todos os campos do formulário e a seleção da tabela.
     */
    private void limparCampos() {
        nomeField.setText("");
        sobrenomeField.setText("");
        rgField.setText("");
        cpfField.setText("");
        enderecoField.setText("");
        tabelaClientes.clearSelection();
    }

    /**
     * Verifica se algum dos campos obrigatórios do formulário está vazio.
     * @return true se algum campo estiver inválido, false caso contrário.
     */
    private boolean camposInvalidos() {
        return nomeField.getText().trim().isEmpty() 
            || sobrenomeField.getText().trim().isEmpty() 
            || rgField.getText().trim().isEmpty() 
            || cpfField.getText().trim().isEmpty() 
            || enderecoField.getText().trim().isEmpty();
    }
}
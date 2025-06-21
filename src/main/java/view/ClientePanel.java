package view;

import controller.ClienteController;
import controller.VeiculoController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Cliente;
import model.Estado;
import model.Veiculo;
import tablemodel.ClienteTableModel;

public class ClientePanel extends JPanel {
    private final JTable tabelaClientes;
    private final ClienteTableModel modelo;
    private final JTextField nomeField, sobrenomeField, rgField, cpfField, enderecoField;
    private final JButton adicionarBtn, atualizarBtn, excluirBtn, btnRefresh, btnFechar;
    private final MainFrame mainFrame;

    public ClientePanel(List<Cliente> clientes, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 10));

        // --- PAINEL DE AÇÕES SUPERIOR ---
        JPanel topActionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRefresh = new JButton("Atualizar");
        btnFechar = new JButton("Fechar Aba");
        topActionsPanel.add(btnRefresh);
        topActionsPanel.add(btnFechar);

        // --- PAINEL DE FORMULÁRIO E BOTÕES DE CRUD ---
        JPanel formAndCrudPanel = new JPanel(new BorderLayout(10, 5));
        
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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        adicionarBtn = new JButton("Adicionar");
        atualizarBtn = new JButton("Atualizar");
        excluirBtn = new JButton("Excluir");
        
        buttonPanel.add(adicionarBtn);
        buttonPanel.add(atualizarBtn);
        buttonPanel.add(excluirBtn);
        
        formAndCrudPanel.add(formPanel, BorderLayout.CENTER);
        formAndCrudPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // --- PAINEL NORTE (AGRUPA AÇÕES E FORMULÁRIO) ---
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(topActionsPanel, BorderLayout.NORTH);
        northPanel.add(formAndCrudPanel, BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);

        // --- TABELA PRINCIPAL ---
        modelo = new ClienteTableModel(clientes);
        tabelaClientes = new JTable(modelo);
        add(new JScrollPane(tabelaClientes), BorderLayout.CENTER);

        // --- LISTENERS ---
        adicionarBtn.addActionListener(e -> adicionarCliente());
        atualizarBtn.addActionListener(e -> atualizarCliente());
        excluirBtn.addActionListener(e -> excluirCliente());
        btnRefresh.addActionListener(e -> refreshData());
        btnFechar.addActionListener(e -> this.mainFrame.fecharAba(this));

        tabelaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaClientes.getSelectedRow() != -1) {
                int selectedRow = tabelaClientes.convertRowIndexToModel(tabelaClientes.getSelectedRow());
                Cliente clienteSelecionado = modelo.getClienteAt(selectedRow);
                
                nomeField.setText(clienteSelecionado.getNome());
                sobrenomeField.setText(clienteSelecionado.getSobrenome());
                rgField.setText(clienteSelecionado.getRg());
                cpfField.setText(clienteSelecionado.getCpf());
                enderecoField.setText(clienteSelecionado.getEndereco());
            }
        });
    }

    private void adicionarCliente() {
        if (camposInvalidos()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Cliente novoCliente = new Cliente(nomeField.getText(), sobrenomeField.getText(), rgField.getText(), cpfField.getText(), enderecoField.getText());
        ClienteController.salvar(novoCliente);
        JOptionPane.showMessageDialog(this, "Cliente adicionado com sucesso!");
        refreshData();
    }
    
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
        Cliente clienteAtualizado = new Cliente(nomeField.getText(), sobrenomeField.getText(), rgField.getText(), cpfField.getText(), enderecoField.getText());
        ClienteController.atualizar(clienteAtualizado);
        JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
        refreshData();
    }
    
    private void excluirCliente() {
        int selectedRow = tabelaClientes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Cliente clienteSelecionado = modelo.getClienteAt(selectedRow);
        
        boolean temLocacao = VeiculoController.listarLocados().stream().anyMatch(v -> v.getLocacao().getCliente().equals(clienteSelecionado));
        
        if (temLocacao) {
            JOptionPane.showMessageDialog(this, "Este cliente não pode ser excluído pois possui veículos locados.", "Erro de Exclusão", JOptionPane.ERROR_MESSAGE);
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este cliente?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ClienteController.excluir(clienteSelecionado);
                JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
                refreshData();
            }
        }
    }
    
    private void refreshData() {
        modelo.setClientes(ClienteController.listarTodos());
        limparCampos();
    }
    
    private void limparCampos() {
        nomeField.setText("");
        sobrenomeField.setText("");
        rgField.setText("");
        cpfField.setText("");
        enderecoField.setText("");
        tabelaClientes.clearSelection();
    }

    private boolean camposInvalidos() {
        return nomeField.getText().trim().isEmpty() || sobrenomeField.getText().trim().isEmpty() || rgField.getText().trim().isEmpty() || cpfField.getText().trim().isEmpty() || enderecoField.getText().trim().isEmpty();
    }
}
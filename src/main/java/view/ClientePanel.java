/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author Pomps
 */
import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Cliente;
import tablemodel.ClienteTableModel;

public class ClientePanel extends JPanel {
    private JTable tabelaClientes;
    private ClienteTableModel modelo;
    private JTextField nomeField, sobrenomeField, rgField, cpfField, enderecoField;
    private JButton adicionarBtn, atualizarBtn, excluirBtn;

    public ClientePanel(List<Cliente> clientes) {
        setLayout(new BorderLayout());

        modelo = new ClienteTableModel(clientes);
        tabelaClientes = new JTable(modelo);

        JPanel form = new JPanel(new GridLayout(6, 2));
        nomeField = new JTextField();
        sobrenomeField = new JTextField();
        rgField = new JTextField();
        cpfField = new JTextField();
        enderecoField = new JTextField();

        form.add(new JLabel("Nome:"));
        form.add(nomeField);
        form.add(new JLabel("Sobrenome:"));
        form.add(sobrenomeField);
        form.add(new JLabel("RG:"));
        form.add(rgField);
        form.add(new JLabel("CPF:"));
        form.add(cpfField);
        form.add(new JLabel("Endereço:"));
        form.add(enderecoField);

        adicionarBtn = new JButton("Adicionar");
        atualizarBtn = new JButton("Atualizar");
        excluirBtn = new JButton("Excluir");

        form.add(adicionarBtn);
        form.add(atualizarBtn);
        form.add(excluirBtn);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(tabelaClientes), BorderLayout.CENTER);

        // Adicione os listeners para os botões conforme a lógica do seu projeto
    }
}

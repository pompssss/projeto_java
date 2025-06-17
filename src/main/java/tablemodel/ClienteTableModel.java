/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tablemodel;

/**
 *
 * @author Pomps
 */
public class ClienteTableModel extends AbstractTableModel {
    private List<Cliente> clientes;
    private final String[] colunas = {"Nome", "Sobrenome", "RG", "CPF", "Endereço"};

    public ClienteTableModel(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public int getRowCount() { return clientes.size(); }

    @Override
    public int getColumnCount() { return colunas.length; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente c = clientes.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> c.getNome();
            case 1 -> c.getSobrenome();
            case 2 -> c.getRg();
            case 3 -> c.getCpf();
            case 4 -> c.getEndereco();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
}


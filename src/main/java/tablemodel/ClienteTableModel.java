package tablemodel;

import controller.ClienteController;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Cliente;

public class ClienteTableModel extends AbstractTableModel {
    private List<Cliente> clientes;
    private final String[] colunas = {"Nome", "Sobrenome", "RG", "CPF", "Endereço"};

    public ClienteTableModel(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    // NOVO: Método para atualizar a lista de clientes e notificar a tabela
    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
        fireTableDataChanged();
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
    
    public Cliente getClienteAt(int rowIndex) {
        return clientes.get(rowIndex);
    }
}
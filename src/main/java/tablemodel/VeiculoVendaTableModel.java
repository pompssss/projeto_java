package tablemodel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Veiculo;

/**
 * TableModel para a tabela de Venda de Veículos.
 * Exibe os veículos disponíveis para venda. 
 */
public class VeiculoVendaTableModel extends AbstractTableModel {
    private List<Veiculo> veiculos;
    private final String[] colunas = {"Placa", "Marca", "Modelo", "Ano", "Preço para venda"};

    public VeiculoVendaTableModel(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }
    
    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return veiculos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Veiculo v = veiculos.get(rowIndex);
        // Retorna o valor correto com base na coluna.
        return switch (columnIndex) {
            case 0 -> v.getPlaca();
            case 1 -> v.getMarca();
            // REATORADO: Usa polimorfismo.
            case 2 -> v.getModelo();
            // Formata o ano com 4 dígitos. 
            case 3 -> String.format("%04d", v.getAno());
            // Formata o preço para venda. 
            case 4 -> String.format("R$%.2f", v.getValorParaVenda());
            default -> "";
        };
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
    
    public Veiculo getVeiculoAt(int rowIndex) {
        return veiculos.get(rowIndex);
    }
}
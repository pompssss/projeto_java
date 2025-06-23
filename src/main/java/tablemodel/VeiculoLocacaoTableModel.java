package tablemodel;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import model.Veiculo;

/**
 * TableModel para a tabela de Locação de Veículos. 
 * Exibe os veículos disponíveis para locação. 
 */
public class VeiculoLocacaoTableModel extends AbstractTableModel {

    private List<Veiculo> veiculos;
    private final String[] colunas = {"Placa", "Marca", "Modelo", "Ano", "Preço Diária"}; 

    public VeiculoLocacaoTableModel(List<Veiculo> veiculos) {
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
            case 3 -> v.getAno();
            // Formata o preço para o padrão R$XXX,XX 
            case 4 -> String.format("R$%.2f", v.getValorDiariaLocacao());
            default -> null;
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
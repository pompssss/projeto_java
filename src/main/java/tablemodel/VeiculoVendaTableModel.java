package tablemodel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Automovel;
import model.Motocicleta;
import model.Van;
import model.Veiculo;

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
        return switch (columnIndex) {
            case 0 -> v.getPlaca();
            case 1 -> v.getMarca();
            case 2 -> {
                if (v instanceof Automovel automovel) yield automovel.getModelo();
                else if (v instanceof Motocicleta motocicleta) yield motocicleta.getModelo();
                else if (v instanceof Van van) yield van.getModelo();
                else yield "-";
            }
            case 3 -> String.format("%04d", v.getAno());
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
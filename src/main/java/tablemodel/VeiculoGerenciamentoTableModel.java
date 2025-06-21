package tablemodel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Automovel;
import model.Motocicleta;
import model.Van;
import model.Veiculo;

public class VeiculoGerenciamentoTableModel extends AbstractTableModel {

    private List<Veiculo> veiculos;
    private final String[] colunas = {"Placa", "Marca", "Modelo", "Ano", "Estado", "Valor de Compra"};

    public VeiculoGerenciamentoTableModel(List<Veiculo> veiculos) {
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
                if (v instanceof Automovel a) yield a.getModelo();
                else if (v instanceof Motocicleta m) yield m.getModelo();
                else if (v instanceof Van va) yield va.getModelo();
                else yield "-";
            }
            case 3 -> v.getAno();
            case 4 -> v.getEstado();
            case 5 -> String.format("R$%.2f", v.getValorDeCompra());
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
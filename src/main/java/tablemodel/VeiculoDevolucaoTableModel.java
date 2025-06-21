package tablemodel;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.List;
import model.Automovel;
import model.Locacao;
import model.Motocicleta;
import model.Van;
import model.Veiculo;

public class VeiculoDevolucaoTableModel extends AbstractTableModel {

    private List<Veiculo> veiculos;
    private final String[] colunas = {
        "Nome do Cliente", "Placa", "Marca", "Modelo", "Ano", 
        "Data Locação", "Preço Diária", "Dias Locados", "Valor Locação"
    };

    public VeiculoDevolucaoTableModel(List<Veiculo> veiculos) {
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
        Locacao loc = v.getLocacao();
        if (loc == null) return null; // Segurança
        
        return switch (columnIndex) {
            case 0 -> loc.getCliente().getNome() + " " + loc.getCliente().getSobrenome();
            case 1 -> v.getPlaca();
            case 2 -> v.getMarca();
            case 3 -> {
                if (v instanceof Automovel a) yield a.getModelo();
                else if (v instanceof Motocicleta m) yield m.getModelo();
                else if (v instanceof Van va) yield va.getModelo();
                else yield "-";
            }
            case 4 -> v.getAno();
            case 5 -> new SimpleDateFormat("dd/MM/yyyy").format(loc.getData().getTime());
            case 6 -> String.format("R$%.2f", v.getValorDiariaLocacao());
            case 7 -> loc.getDias();
            case 8 -> String.format("R$%.2f", loc.getValor());
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
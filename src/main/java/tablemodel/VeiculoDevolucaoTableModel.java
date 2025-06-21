/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tablemodel;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import model.Automovel;
import model.Locacao;
import model.Motocicleta;
import model.Van;
import model.Veiculo;

public class VeiculoDevolucaoTableModel extends AbstractTableModel {

    private final List<Veiculo> veiculos;
    private final String[] colunas = {
        "Nome do Cliente", "Placa", "Marca", "Modelo", "Ano", 
        "Data Locação", "Preço Diária", "Dias Locados", "Valor Locação"
    };

    public VeiculoDevolucaoTableModel(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
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
        return switch (columnIndex) {
            case 0 -> loc.getCliente().getNome(); // Adapte para nome completo se necessário
            case 1 -> v.getPlaca();
            case 2 -> v.getMarca();
            case 3 -> {
                if (!(v instanceof Automovel a)) if (v instanceof Motocicleta m) yield m.getModelo();
                else if (v instanceof Van va) yield va.getModelo();
                else yield "-";
                else yield a.getModelo();
            }
            case 4 -> v.getAno();
            case 5 -> loc.getData().getTime(); // ou formatado com SimpleDateFormat
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
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tablemodel;

/**
 *
 * @author Pomps
 */

import javax.swing.table.AbstractTableModel;
import java.util.List;
import model.Automovel;
import model.Motocicleta;
import model.Van;
import model.Veiculo;

public class VeiculoLocacaoTableModel extends AbstractTableModel {

    private final List<Veiculo> veiculos;
    private final String[] colunas = {"Placa", "Marca", "Modelo", "Ano", "Preço Diária"};

    public VeiculoLocacaoTableModel(List<Veiculo> veiculos) {
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
            case 4 -> String.format("R$%.2f", v.getValorDiariaLocacao());
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
}


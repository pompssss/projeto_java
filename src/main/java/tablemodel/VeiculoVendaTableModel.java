/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tablemodel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Automovel;
import model.Motocicleta;
import model.Van;
import model.Veiculo;

/**
 *
 * @author Pomps
 */
public class VeiculoVendaTableModel extends AbstractTableModel {
    private List<Veiculo> veiculos;
    private String[] colunas = {"Placa", "Marca", "Modelo", "Ano", "Preço para venda"};

    public VeiculoVendaTableModel(List<Veiculo> veiculos) {
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
        switch (columnIndex) {
            case 0 -> {
                return v.getPlaca();
            }
            case 1 -> {
                return v.getMarca();
            }
            case 2 -> {
                if (v instanceof Automovel automovel) return automovel.getModelo();
                if (v instanceof Motocicleta motocicleta) return motocicleta.getModelo();
                if (v instanceof Van van) return van.getModelo();
                return "";
            }
            case 3 -> {
                return String.format("%04d", v.getAno());
            }
            case 4 -> {
                return String.format("R$%.2f", v.getValorParaVenda());
            }
            default -> {
                return "";
            }
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
}

package tablemodel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Veiculo;

/**
 * TableModel para a tabela de Gerenciamento de Veículos.
 */
public class VeiculoGerenciamentoTableModel extends AbstractTableModel {

    private List<Veiculo> veiculos;
    // Define os nomes das colunas da tabela.
    private final String[] colunas = {"Placa", "Marca", "Modelo", "Ano", "Estado", "Valor de Compra"};

    public VeiculoGerenciamentoTableModel(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }
    
    /**
     * Permite atualizar a lista de veículos exibida na tabela.
     * @param veiculos A nova lista de veículos.
     */
    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
        fireTableDataChanged(); // Notifica a JTable que os dados mudaram.
    }

    @Override
    public int getRowCount() {
        return veiculos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    /**
     * Retorna o valor a ser exibido em uma célula específica da tabela.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Veiculo v = veiculos.get(rowIndex);
        // Retorna o valor correto com base na coluna.
        return switch (columnIndex) {
            case 0 -> v.getPlaca();
            case 1 -> v.getMarca();
            // REATORADO: Usa polimorfismo para obter o modelo.
            case 2 -> v.getModelo();
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
    
    /**
     * Retorna o objeto Veiculo completo de uma linha da tabela.
     * @param rowIndex O índice da linha.
     * @return O objeto Veiculo.
     */
    public Veiculo getVeiculoAt(int rowIndex) {
        return veiculos.get(rowIndex);
    }
}
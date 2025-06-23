package tablemodel;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.List;
import model.Locacao;
import model.Veiculo;

/**
 * TableModel para a tabela de Devolução de Veículos.
 */
public class VeiculoDevolucaoTableModel extends AbstractTableModel {

    private List<Veiculo> veiculos;
    private final String[] colunas = {
        "Nome do Cliente", "Placa", "Marca", "Modelo", "Ano", 
        "Data Locação", "Preço Diária", "Dias Locados", "Valor Locação"
    };

    public VeiculoDevolucaoTableModel(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }
    
    /**
     * Atualiza a lista de veículos exibida na tabela.
     * @param veiculos A nova lista de veículos.
     */
    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
        // Notifica a JTable que os dados mudaram, para que ela se redesenhe.
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

    /**
     * Retorna o valor a ser exibido em uma célula específica da tabela.
     * @param rowIndex O índice da linha.
     * @param columnIndex O índice da coluna.
     * @return O objeto a ser exibido na célula.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // Pega o veículo e a locação da linha correspondente.
        Veiculo v = veiculos.get(rowIndex);
        Locacao loc = v.getLocacao();
        if (loc == null) return null; // Garante que não haverá erro se a locação for nula.
        
        // Retorna o valor apropriado com base no índice da coluna.
        return switch (columnIndex) {
            case 0 -> loc.getCliente().getNome() + " " + loc.getCliente().getSobrenome();
            case 1 -> v.getPlaca();
            case 2 -> v.getMarca();
            // REATORADO: Usa polimorfismo, sem precisar de "instanceof".
            // O método getModelo() correto será chamado automaticamente.
            case 3 -> v.getModelo();
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
    
    /**
     * Retorna o objeto Veiculo completo de uma linha específica.
     * @param rowIndex O índice da linha.
     * @return O objeto Veiculo.
     */
    public Veiculo getVeiculoAt(int rowIndex) {
        return veiculos.get(rowIndex);
    }
}
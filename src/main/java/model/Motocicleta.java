package model;

/**
 * Representa uma Motocicleta, que é uma especialização da classe Veiculo. 
 * Herda os atributos e métodos comuns e implementa o cálculo de diária específico para motocicletas.
 */
public class Motocicleta extends Veiculo {
    
    // Atributo específico para o modelo da motocicleta.
    private final ModeloMotocicleta modelo;

    /**
     * Construtor da classe Motocicleta. 
     * @param marca A marca da motocicleta.
     * @param estado O estado inicial da motocicleta.
     * @param categoria A categoria da motocicleta.
     * @param valorDeCompra O valor de compra.
     * @param placa A placa de identificação.
     * @param ano O ano de fabricação/modelo.
     * @param modelo O modelo específico da motocicleta (ex: CG_125, CBR_500). 
     */
    public Motocicleta(Marca marca, Estado estado, Categoria categoria, double valorDeCompra, String placa, int ano, ModeloMotocicleta modelo) {
        // Chama o construtor da classe pai (Veiculo) para inicializar os atributos comuns.
        super(marca, estado, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    /**
     * Retorna o modelo específico da motocicleta.
     * Sobrescreve o método abstrato da classe pai 'Veiculo'.
     * @return O enum ModeloMotocicleta.
     */
    @Override
    public ModeloMotocicleta getModelo() {
        return modelo;
    }

    /**
     * Calcula e retorna o valor da diária de locação com base na categoria da motocicleta.
     * Implementa a tabela de preços específica para este tipo de veículo. 
     * @return O valor da diária de locação.
     */
    @Override
    public double getValorDiariaLocacao() {
        return switch (super.categoria) {
            case POPULAR -> 70.0; 
            case INTERMEDIARIO -> 200.0;
            case LUXO -> 350.0;
        };
    }
}
package model;

/**
 * Representa uma Van, que é uma especialização da classe Veiculo. 
 * Herda os atributos e métodos comuns e implementa o cálculo de diária específico para vans.
 */
public class Van extends Veiculo {

    // Atributo específico da van, para definir seu modelo.
    private final ModeloVan modelo;

    /**
     * Construtor da classe Van. 
     * @param marca A marca da van.
     * @param estado O estado inicial da van.
     * @param categoria A categoria da van.
     * @param valorDeCompra O valor de compra.
     * @param placa A placa de identificação.
     * @param ano O ano de fabricação/modelo.
     * @param modelo O modelo específico da van (ex: KOMBI, SPRINTER). 
     */
    public Van(Marca marca, Estado estado, Categoria categoria, double valorDeCompra, String placa, int ano, ModeloVan modelo) {
        // Chama o construtor da classe pai (Veiculo) para inicializar os atributos comuns.
        super(marca, estado, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    /**
     * Retorna o modelo específico da van.
     * Sobrescreve o método abstrato 'getModelo' da classe pai 'Veiculo'.
     * @return O enum ModeloVan.
     */
    @Override
    public ModeloVan getModelo() {
        return modelo;
    }

    /**
     * Calcula e retorna o valor da diária de locação com base na categoria da van.
     * Implementa a tabela de preços específica para este tipo de veículo. 
     * @return O valor da diária de locação.
     */
    @Override
    public double getValorDiariaLocacao() {
        return switch (super.categoria) {
            case POPULAR -> 200.0;
            case INTERMEDIARIO -> 400.0;
            case LUXO -> 600.0;
        };
    }
}
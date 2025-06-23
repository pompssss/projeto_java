package model;

/**
 * Representa um Automóvel, que é uma especialização da classe Veiculo. 
 * Herda os atributos e métodos comuns e implementa o cálculo de diária específico para automóveis.
 */
public class Automovel extends Veiculo {
    
    // Atributo específico para o modelo do automóvel.
    private final ModeloAutomovel modelo; 

    /**
     * Construtor da classe Automovel. 
     * @param marca A marca do automóvel.
     * @param estado O estado inicial do automóvel.
     * @param categoria A categoria do automóvel.
     * @param valorDeCompra O valor de compra.
     * @param placa A placa de identificação.
     * @param ano O ano de fabricação/modelo.
     * @param modelo O modelo específico do automóvel (ex: GOL, PALIO). 
     */
    public Automovel(Marca marca, Estado estado, Categoria categoria, double valorDeCompra, String placa, int ano, ModeloAutomovel modelo) {
        // Chama o construtor da classe pai (Veiculo) para inicializar os atributos comuns.
        super(marca, estado, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    /**
     * Retorna o modelo específico do automóvel.
     * Sobrescreve o método abstrato da classe Veiculo.
     * @return O enum ModeloAutomovel.
     */
    @Override
    public ModeloAutomovel getModelo() { 
        return modelo; 
    }

    /**
     * Calcula e retorna o valor da diária de locação com base na categoria.
     * Implementa a tabela de preços para Automóvel. 
     * @return O valor da diária.
     */
    @Override
    public double getValorDiariaLocacao() {
        return switch (super.categoria) {
            case POPULAR -> 100.0;
            case INTERMEDIARIO -> 300.0;
            case LUXO -> 450.0;
        };
    }
}
package model;

import java.util.Calendar;

/**
 * Classe abstrata que representa um Veículo genérico no sistema.
 * Contém atributos e implementações comuns a todos os tipos de veículos (Automóvel, Motocicleta, Van).
 * Implementa a interface VeiculoI, garantindo que todas as subclasses terão os métodos de negócio necessários.
 */
public abstract class Veiculo implements VeiculoI {
    // Atributos protegidos para serem acessíveis pelas subclasses diretas.
    protected Marca marca;
    protected Estado estado;
    protected Locacao locacao; // Armazena os dados da locação (será nulo se o veículo não estiver locado).
    protected Categoria categoria;
    protected double valorDeCompra;
    protected String placa;
    protected int ano;

    /**
     * Construtor da classe Veiculo, chamado pelas subclasses.
     * @param marca A marca do veículo.
     * @param estado O estado inicial do veículo (NOVO, DISPONIVEL, etc.).
     * @param categoria A categoria do veículo (POPULAR, INTERMEDIARIO, LUXO).
     * @param valorDeCompra O valor pago na aquisição do veículo.
     * @param placa A placa de identificação do veículo.
     * @param ano O ano de fabricação/modelo do veículo.
     */
    public Veiculo(Marca marca, Estado estado, Categoria categoria, double valorDeCompra, String placa, int ano) {
        this.marca = marca;
        this.estado = estado;
        this.categoria = categoria;
        this.valorDeCompra = valorDeCompra;
        this.placa = placa;
        this.ano = ano;
        this.locacao = null; // Garante que um veículo novo sempre começa sem uma locação.
    }

    // --- MÉTODOS DE NEGÓCIO DA INTERFACE VEICULO_I ---

    /**
     * Realiza a locação de um veículo se ele estiver disponível.
     * Muda o estado para LOCADO e cria uma nova instância de Locacao. 
     * @param dias O número de dias da locação.
     * @param data A data em que a locação foi realizada.
     * @param cliente O cliente que está locando o veículo.
     */
    @Override
    public void locar(int dias, Calendar data, Cliente cliente) {
        if (this.estado == Estado.DISPONIVEL) {
            double valorTotal = this.getValorDiariaLocacao() * dias;
            this.locacao = new Locacao(dias, valorTotal, data, cliente);
            this.estado = Estado.LOCADO;
        }
    }

    /**
     * Realiza a venda de um veículo.
     * Muda o estado para VENDIDO e remove qualquer informação de locação. 
     */
    @Override
    public void vender() {
        this.estado = Estado.VENDIDO;
        this.locacao = null; 
    }

    /**
     * Realiza a devolução de um veículo que estava locado.
     * Muda o estado para DISPONIVEL e remove a informação da locação. 
     */
    @Override
    public void devolver() {
        this.locacao = null;
        this.estado = Estado.DISPONIVEL;
    }

    /**
     * Calcula o valor de venda do veículo com base em sua idade e valor de compra.
     * A fórmula de depreciação é de 15% do valor de compra por ano de uso. 
     * O valor de venda não pode ser inferior a 10% do valor de compra. 
     * @return O valor calculado para a venda.
     */
    @Override
    public double getValorParaVenda() {
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        int idade = anoAtual - this.ano;
        if (idade < 0) idade = 0; // Garante que a idade não seja negativa.

        double valorVenda = valorDeCompra - (idade * 0.15 * valorDeCompra);
        double valorMinimo = valorDeCompra * 0.1;

        // Se o valor calculado for menor que o mínimo, o valor de venda será o mínimo.
        if (valorVenda < valorMinimo) {
            return valorMinimo;
        }
        return valorVenda;
    }
    
    // --- MÉTODOS ABSTRATOS (a serem implementados pelas subclasses) ---

    /**
     * Método abstrato para obter o modelo específico do veículo (ex: GOL, CBR 500, KOMBI).
     * O uso de 'Object' como tipo de retorno permite que cada subclasse retorne seu próprio enum de modelo.
     * @return O modelo do veículo.
     */
    @Override
    public abstract Object getModelo();
    
    /**
     * Método abstrato para obter o valor da diária de locação.
     * Força as subclasses (Automovel, Motocicleta, Van) a implementarem sua própria tabela de preços. 
     * @return O valor da diária de locação.
     */
    @Override
    public abstract double getValorDiariaLocacao();


    // --- GETTERS E SETTERS ---
    // Embora o requisito  pedisse para evitar setters, eles são necessários para a funcionalidade de "Atualizar Veículo".

    @Override
    public Estado getEstado() { return estado; }
    @Override
    public Marca getMarca() { return marca; }
    @Override
    public Categoria getCategoria() { return categoria; }
    @Override
    public Locacao getLocacao() { return locacao; }
    @Override
    public String getPlaca() { return placa; }
    @Override
    public int getAno() { return ano; }
    
    public double getValorDeCompra() { return valorDeCompra; }
    
    /**
     * Altera o estado atual do veículo.
     * @param estado O novo estado.
     */
    public void setEstado(Estado estado) { this.estado = estado; }

    /**
     * Altera o valor de compra do veículo.
     * @param valorDeCompra O novo valor de compra.
     */
    public void setValorDeCompra(double valorDeCompra) { this.valorDeCompra = valorDeCompra; }

    /**
     * Altera o ano do veículo.
     * @param ano O novo ano do veículo.
     */
    public void setAno(int ano) {
        this.ano = ano;
    }
}
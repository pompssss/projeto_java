package model;

import java.util.Calendar;

public abstract class Veiculo implements VeiculoI {
    protected Marca marca;
    protected Estado estado;
    protected Locacao locacao;
    protected Categoria categoria;
    protected double valorDeCompra;
    protected String placa;
    protected int ano;

    public Veiculo(Marca marca, Estado estado, Categoria categoria, double valorDeCompra, String placa, int ano) {
        this.marca = marca;
        this.estado = estado;
        this.categoria = categoria;
        this.valorDeCompra = valorDeCompra;
        this.placa = placa;
        this.ano = ano;
    }

    // --- MÉTODOS DE NEGÓCIO DA INTERFACE ---

    @Override
    public void locar(int dias, Calendar data, Cliente cliente) {
        if (estado == Estado.DISPONIVEL) {
            this.locacao = new Locacao(dias, getValorDiariaLocacao() * dias, data, cliente);
            this.estado = Estado.LOCADO;
        }
    }

    @Override
    public void vender() {
        this.estado = Estado.VENDIDO;
        this.locacao = null;
    }

    @Override
    public void devolver() {
        this.locacao = null;
        this.estado = Estado.DISPONIVEL;
    }

    // --- GETTERS DA INTERFACE ---

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
    public double getValorDeCompra() { return valorDeCompra; } // Getter adicional útil

    // --- SETTERS (NECESSÁRIOS PARA A TELA DE GERENCIAMENTO) ---
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setValorDeCompra(double valorDeCompra) {
        this.valorDeCompra = valorDeCompra;
    }


    @Override
    public double getValorParaVenda() {
        int idade = Calendar.getInstance().get(Calendar.YEAR) - this.ano;
        double valor = valorDeCompra - (idade * 0.15 * valorDeCompra);
        if (valor < valorDeCompra * 0.1 || valor <= 0) {
            valor = valorDeCompra * 0.1;
        }
        return valor;
    }
    
    @Override
    public abstract double getValorDiariaLocacao();
}
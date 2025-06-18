/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package model;

import java.util.Calendar;

/**
 *
 * @author Pomps
 */
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

    @Override
    public void locar(int dias, Calendar data, Cliente cliente) {
        if (estado != Estado.DISPONIVEL) return; {
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

    @Override
    public double getValorParaVenda() {
        int idade = Calendar.getInstance().get(Calendar.YEAR) - this.ano;
        double valor = valorDeCompra - (idade * 0.15 * valorDeCompra);
        if (valor < 0.1 * valorDeCompra) {
            valor = 0.1 * valorDeCompra;
        }
        return valor;
    }
    
    @Override
    public abstract double getValorDiariaLocacao();
}

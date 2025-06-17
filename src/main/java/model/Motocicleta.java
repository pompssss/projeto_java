/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Pomps
 */
public class Motocicleta extends Veiculo {
    private ModeloMotocicleta modelo;

    public Motocicleta(Marca marca, Estado estado, Categoria categoria, double valorDeCompra, String placa, int ano, ModeloMotocicleta modelo) {
        super(marca, estado, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    public ModeloMotocicleta getModelo() { return modelo; }

    @Override
    public double getValorDiariaLocacao() {
        return switch (categoria) {
            case POPULAR -> 70.0;
            case INTERMEDIARIO -> 200.0;
            case LUXO -> 350.0;
            default -> 0.0;
        };
    }
}

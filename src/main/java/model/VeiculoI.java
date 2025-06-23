package model;

import java.util.Calendar;

/**
 * Interface que define o contrato para todas as entidades Veículo. 
 * Especifica os métodos de negócio e getters obrigatórios. 
 */
public interface VeiculoI {
    void locar(int dias, Calendar data, Cliente cliente);
    void vender();
    void devolver();
    Estado getEstado();
    Marca getMarca();
    Categoria getCategoria();
    Locacao getLocacao();
    String getPlaca();
    int getAno();
    Object getModelo(); // ADICIONADO PARA POLIMORFISMO
    double getValorParaVenda();
    double getValorDiariaLocacao();
}
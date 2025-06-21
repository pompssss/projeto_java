package model;

/**
 * Representa os possíveis estados de um veículo no sistema.
 * Conforme requisito 2.b.iii.
 * @author Pomps
 */
public enum Estado {
    NOVO,       // Estado inicial de um veículo comprado
    DISPONIVEL, // Disponível para locação ou venda
    LOCADO,     // Atualmente locado por um cliente
    VENDIDO     // Já foi vendido e saiu da frota
}
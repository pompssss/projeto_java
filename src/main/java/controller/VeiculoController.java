/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Veiculo;
import model.Categoria;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VeiculoController {
    // Simula um banco de dados em memória
    private static final List<Veiculo> veiculos = new ArrayList<>();

    public static void salvar(Veiculo v) {
        if (v == null) throw new IllegalArgumentException("Veículo não pode ser nulo");
        veiculos.add(v);
    }

    public static List<Veiculo> listarTodos() {
        return new ArrayList<>(veiculos); // Retorna uma cópia para evitar modificações externas
    }

    // Buscar veículo por placa
    public static Veiculo buscarPorPlaca(String placa) {
        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v;
            }
        }
        return null; // ou Optional<Veiculo>
    }

    // Atualizar veículo (procura por placa e substitui)
    public static boolean atualizar(Veiculo novoVeiculo) {
        for (int i = 0; i < veiculos.size(); i++) {
            if (veiculos.get(i).getPlaca().equalsIgnoreCase(novoVeiculo.getPlaca())) {
                veiculos.set(i, novoVeiculo);
                return true;
            }
        }
        return false;
    }

    // Buscar veículos por categoria
    public static List<Veiculo> buscarPorCategoria(Categoria categoria) {
        return veiculos.stream()
                .filter(v -> v.getCategoria() == categoria)
                .collect(Collectors.toList());
    }

    private static Object vPlaca() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
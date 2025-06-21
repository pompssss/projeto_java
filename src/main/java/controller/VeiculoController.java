package controller;

import model.Veiculo;
import model.Categoria;
import model.Estado;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VeiculoController {
    private static final List<Veiculo> veiculos = new ArrayList<>();

    public static void salvar(Veiculo v) {
        if (v == null) throw new IllegalArgumentException("Veículo não pode ser nulo");
        veiculos.add(v);
    }

    // NOVO: Método para excluir um veículo
    public static void excluir(Veiculo v) {
        veiculos.remove(v);
    }

    public static List<Veiculo> listarTodos() {
        return new ArrayList<>(veiculos);
    }

    public static List<Veiculo> listarDisponiveis() {
        return veiculos.stream()
                .filter(v -> v.getEstado() == Estado.DISPONIVEL)
                .collect(Collectors.toList());
    }
    
    public static List<Veiculo> listarLocados() {
        return veiculos.stream()
                .filter(v -> v.getEstado() == Estado.LOCADO)
                .collect(Collectors.toList());
    }

    public static Veiculo buscarPorPlaca(String placa) {
        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v;
            }
        }
        return null;
    }

    public static boolean atualizar(Veiculo novoVeiculo) {
        for (int i = 0; i < veiculos.size(); i++) {
            if (veiculos.get(i).getPlaca().equalsIgnoreCase(novoVeiculo.getPlaca())) {
                veiculos.set(i, novoVeiculo);
                return true;
            }
        }
        return false;
    }

    public static List<Veiculo> buscarPorCategoria(Categoria categoria) {
        return veiculos.stream()
                .filter(v -> v.getCategoria() == categoria)
                .collect(Collectors.toList());
    }
}
package controller;

import model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClienteController {
    private static final List<Cliente> clientes = new ArrayList<>();

    public static void salvar(Cliente cliente) {
        clientes.add(cliente);
    }

    public static List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }

    public static void atualizar(Cliente clienteAtualizado) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCpf().equals(clienteAtualizado.getCpf())) {
                clientes.set(i, clienteAtualizado);
                return;
            }
        }
    }

    public static void excluir(Cliente cliente) {
        // A lógica de verificação se o cliente tem locações pendentes
        // deve ser adicionada aqui antes de remover.
        clientes.remove(cliente);
    }
    
    public static Cliente buscarPorCpf(String cpf) {
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }
        return null;
    }
}
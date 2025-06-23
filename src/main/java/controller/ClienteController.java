package controller;

import model.Cliente;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsável por gerenciar as operações de negócio relacionadas aos Clientes.
 * Funciona como um "banco de dados em memória" para a entidade Cliente.
 */
public class ClienteController {
    
    // Lista estática para armazenar todos os clientes em memória.
    private static final List<Cliente> clientes = new ArrayList<>();

    /**
     * Adiciona um novo cliente à lista de clientes.
     * @param cliente O objeto Cliente a ser salvo.
     */
    public static void salvar(Cliente cliente) {
        clientes.add(cliente);
    }

    /**
     * Retorna uma cópia da lista de todos os clientes cadastrados. 
     * @return Uma nova lista contendo todos os clientes.
     */
    public static List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }

    /**
     * Atualiza os dados de um cliente existente na lista.
     * @param cpfOriginal O CPF original do cliente para encontrá-lo na lista.
     * @param clienteAtualizado O objeto Cliente com os novos dados.
     */
    public static void atualizar(String cpfOriginal, Cliente clienteAtualizado) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCpf().equals(cpfOriginal)) {
                // Substitui o objeto antigo pelo novo com os dados atualizados.
                clientes.set(i, clienteAtualizado);
                return; 
            }
        }
    }

    /**
     * Exclui um cliente da lista.
     * Aplica a regra de negócio que impede a exclusão de clientes com veículos locados. 
     * @param cliente O objeto Cliente a ser excluído.
     * @throws IllegalStateException se o cliente tiver veículos locados, com a mensagem de erro apropriada. 
     */
    public static void excluir(Cliente cliente) throws IllegalStateException {
        // Verifica se o cliente a ser excluído possui algum veículo locado.
        boolean temLocacao = VeiculoController.listarLocados().stream()
                .anyMatch(v -> v.getLocacao().getCliente().equals(cliente));
        
        // Se tiver, lança uma exceção para ser tratada pela View.
        if (temLocacao) {
            throw new IllegalStateException("Este cliente não pode ser excluído pois possui veículos locados.");
        }
        
        // Se a validação passar, remove o cliente da lista.
        clientes.remove(cliente);
    }
    
    /**
     * Busca e retorna um cliente específico pelo seu CPF.
     * @param cpf O CPF do cliente a ser buscado.
     * @return O objeto Cliente encontrado, ou null se não for encontrado.
     */
    public static Cliente buscarPorCpf(String cpf) {
        return clientes.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }
}
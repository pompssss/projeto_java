package controller;

import model.Veiculo;
import model.Categoria;
import model.Estado;
import model.Marca;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller responsável por gerenciar as operações de negócio relacionadas aos Veículos.
 * Funciona como um "banco de dados em memória" para a entidade Veiculo.
 */
public class VeiculoController {
    
    // Lista estática para armazenar todos os veículos da aplicação.
    private static final List<Veiculo> veiculos = new ArrayList<>();

    /**
     * Adiciona um novo veículo à lista em memória. 
     * @param v O objeto Veiculo a ser salvo.
     */
    public static void salvar(Veiculo v) {
        if (v == null) {
            throw new IllegalArgumentException("Veículo não pode ser nulo");
        }
        veiculos.add(v);
    }

    /**
     * Remove um veículo da lista em memória.
     * @param v O objeto Veiculo a ser excluído.
     */
    public static void excluir(Veiculo v) {
        veiculos.remove(v);
    }

    /**
     * Retorna uma cópia da lista de todos os veículos cadastrados.
     * @return Uma nova lista contendo todos os veículos.
     */
    public static List<Veiculo> listarTodos() {
        // Retorna uma nova ArrayList para evitar modificações externas na lista original.
        return new ArrayList<>(veiculos);
    }

    /**
     * Retorna uma lista de veículos que estão com o estado DISPONIVEL.
     * @return Uma lista de veículos disponíveis para locação ou venda.
     */
    public static List<Veiculo> listarDisponiveis() {
        // Reutiliza o método de filtro mais completo, sem passar critérios específicos.
        return listarDisponiveisComFiltro("Todos", null, null);
    }
    
    /**
     * Filtra a lista de veículos disponíveis com base nos critérios fornecidos. 
     * Este método é um ótimo exemplo de refatoração, pois centraliza a lógica
     * que é usada tanto na tela de Locação quanto na de Venda.
     * @param tipo O nome da classe do veículo ("Automóvel", "Van", "Motocicleta") ou "Todos".
     * @param marca A marca para filtrar, ou null para ignorar este filtro.
     * @param categoria A categoria para filtrar, ou null para ignorar este filtro.
     * @return Uma lista de veículos filtrada.
     */
    public static List<Veiculo> listarDisponiveisComFiltro(String tipo, Marca marca, Categoria categoria) {
        return veiculos.stream()
            // 1. Garante que apenas veículos com estado DISPONIVEL sejam considerados. 
            .filter(v -> v.getEstado() == Estado.DISPONIVEL)
            // 2. Filtra por tipo, ignorando o filtro se for "Todos".
            .filter(v -> tipo == null || tipo.equals("Todos") || v.getClass().getSimpleName().equalsIgnoreCase(tipo))
            // 3. Filtra por marca, ignorando o filtro se a marca for nula.
            .filter(v -> marca == null || v.getMarca().equals(marca))
            // 4. Filtra por categoria, ignorando o filtro se a categoria for nula.
            .filter(v -> categoria == null || v.getCategoria().equals(categoria))
            // Coleta os resultados do filtro em uma nova lista.
            .collect(Collectors.toList());
    }
    
    /**
     * Retorna uma lista de veículos que estão com o estado LOCADO. 
     * @return Uma lista de veículos locados.
     */
    public static List<Veiculo> listarLocados() {
        return veiculos.stream()
                .filter(v -> v.getEstado() == Estado.LOCADO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um veículo específico pela sua placa.
     * @param placa A placa do veículo a ser buscado.
     * @return O objeto Veiculo encontrado, ou null se não for encontrado.
     */
    public static Veiculo buscarPorPlaca(String placa) {
        return veiculos.stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst() // Retorna o primeiro que encontrar
                .orElse(null); // Retorna null se não encontrar nenhum
    }

    /**
     * Atualiza os dados de um veículo existente na lista.
     * @param veiculoAtualizado O objeto Veiculo com os novos dados.
     * @return true se a atualização foi bem-sucedida, false caso contrário.
     */
    public static boolean atualizar(Veiculo veiculoAtualizado) {
        for (int i = 0; i < veiculos.size(); i++) {
            // Procura o veículo na lista pela placa, que é um identificador único.
            if (veiculos.get(i).getPlaca().equalsIgnoreCase(veiculoAtualizado.getPlaca())) {
                // Substitui o objeto antigo pelo novo na mesma posição da lista.
                veiculos.set(i, veiculoAtualizado);
                return true;
            }
        }
        return false; // Retorna falso se não encontrou o veículo para atualizar.
    }
}
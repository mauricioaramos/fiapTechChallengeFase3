package br.com.resturante.reservas.usecases.service;

import br.com.resturante.reservas.entities.Cliente;

import java.util.List;

public interface ClienteService {
    Cliente cadastrarCliente(Cliente cliente);
    Cliente atualizarCliente(String id, Cliente cliente);
    Cliente listarClientePorId(String id) throws Exception;
    void deletarCliente(String id);

    Cliente buscarClientePorNome(String nome) throws Exception;

    Cliente buscarClientePorEndereco(String endereco) throws Exception;

    Cliente buscarClientePorTelefone(String telefone) throws Exception;

    List<Cliente> getAllClientes();
}

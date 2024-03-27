package br.com.resturante.reservas.usecases.service.impl;

import br.com.resturante.reservas.entities.Cliente;
import br.com.resturante.reservas.external.ClienteRepository;
import br.com.resturante.reservas.usecases.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {
    public static final String CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado";
    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public Cliente cadastrarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);

    }

    @Override
    public Cliente atualizarCliente(String id, Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente listarClientePorId(String id) throws Exception {
        return clienteRepository.findById(id).orElseThrow(() -> new Exception(CLIENTE_NAO_ENCONTRADO));
    }

    @Override
    public void deletarCliente(String id) {
        clienteRepository.deleteById(id);

    }

    @Override
    public Cliente buscarClientePorNome(String nome) throws Exception {
        return clienteRepository.findByNome(nome).orElseThrow(() -> new Exception(CLIENTE_NAO_ENCONTRADO));
    }

    @Override
    public Cliente buscarClientePorEndereco(String endereco) throws Exception {
        return clienteRepository.findByEndereco(endereco).orElseThrow(() -> new Exception(CLIENTE_NAO_ENCONTRADO));
    }

    @Override
    public Cliente buscarClientePorTelefone(String telefone) throws Exception {
        return clienteRepository.findByTelefone(telefone).orElseThrow(() -> new Exception(CLIENTE_NAO_ENCONTRADO));
    }

    @Override
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }
}

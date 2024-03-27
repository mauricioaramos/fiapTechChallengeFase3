package br.com.resturante.reservas.adapter.controller;

import br.com.resturante.reservas.entities.Cliente;
import br.com.resturante.reservas.usecases.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
        Cliente clienteCriado = clienteService.cadastrarCliente(cliente);
        if (clienteCriado == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(clienteCriado, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        return new ResponseEntity<>(clienteService.getAllClientes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable("id") String id) {
         Cliente cliente = null;
        try {
            cliente = clienteService.listarClientePorId(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Cliente> buscarClientePorNome(@PathVariable("nome") String nome)  {
        Cliente cliente = null;
        try {
            cliente = clienteService.buscarClientePorNome(nome);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping("/endereco/{endereco}")
    public ResponseEntity<Cliente> buscarClientePorEndereco(@PathVariable("endereco") String endereco)  {
        Cliente cliente = null;
        try {
            cliente = clienteService.buscarClientePorEndereco(endereco);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping("/telefone/{telefone}")
    public ResponseEntity<Cliente> buscarClientePorTelefone(@PathVariable("telefone") String telefone)  {
        Cliente cliente = null;
        try {
            cliente = clienteService.buscarClientePorTelefone(telefone);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);

    }


}

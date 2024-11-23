package com.sunnymeter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sunnymeter.model.Cliente;
import com.sunnymeter.dto.ClienteDTO;
import com.sunnymeter.repository.ClienteRepository;

import java.util.stream.Collectors;
import java.util.Optional;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // POST - Adicionar cliente
    @PostMapping("/clientes")
    public ResponseEntity<Cliente> adicionarCliente(@RequestBody Cliente cliente) {
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    // GET - Listar todos os clientes
    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteDTO>> listarClientes() {
        List<ClienteDTO> clientesDTO = clienteRepository.findAll()
                .stream()
                .map(ClienteDTO::new) // Construtor que aceita Cliente
                .collect(Collectors.toList());

        return ResponseEntity.ok(clientesDTO);
    }

    // GET - Buscar um cliente pelo cliente_uuid
    @GetMapping("/clientes/{cliente_uuid}")
    public ResponseEntity<List<ClienteDTO>> buscarClientePorUuid(@PathVariable("cliente_uuid") String clienteUuid) {
        Optional<Cliente> clienteOptional = clienteRepository.findByClienteUuid(clienteUuid);

        if (clienteOptional.isPresent()) {
            ClienteDTO clienteDTO = new ClienteDTO(clienteOptional.get());
            return ResponseEntity.ok(Collections.singletonList(clienteDTO));
        }

        return ResponseEntity.ok(Collections.emptyList());
    }

    // DELETE - Deletar cliente logicamente
    @DeleteMapping("/clientes/{clienteUuid}")
    public ResponseEntity<ClienteDTO> deletarCliente(@PathVariable String clienteUuid) {
        Optional<Cliente> clienteOptional = clienteRepository.findByClienteUuid(clienteUuid);

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            cliente.setAtivo(false);
            clienteRepository.save(cliente);

            ClienteDTO clienteDTO = new ClienteDTO(cliente);
            return ResponseEntity.ok(clienteDTO);
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 se não encontrado
        }
    }

    // PUT - Atualizar cliente existente
    @PutMapping("/clientes/{clienteUuid}")
    public ResponseEntity<Cliente> atualizarCliente(
            @PathVariable String clienteUuid,
            @RequestBody Cliente clienteAtualizado) {

        Optional<Cliente> clienteOptional = clienteRepository.findByClienteUuid(clienteUuid);

        if (clienteOptional.isPresent()) {
            Cliente clienteExistente = clienteOptional.get();
            clienteExistente.setNome(clienteAtualizado.getNome());
            clienteExistente.setEndereco(clienteAtualizado.getEndereco());
            clienteExistente.setDocumento(clienteAtualizado.getDocumento());
            clienteExistente.setTipo(clienteAtualizado.getTipo());
            clienteExistente.setCep(clienteAtualizado.getCep());
            clienteExistente.setAtivo(clienteAtualizado.isAtivo());
            clienteRepository.save(clienteExistente);

            return ResponseEntity.ok(clienteExistente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

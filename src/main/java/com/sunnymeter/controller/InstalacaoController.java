package com.sunnymeter.controller;

import com.sunnymeter.model.Instalacao;
import com.sunnymeter.dto.InstalacaoDTO;
import com.sunnymeter.repository.InstalacaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
public class InstalacaoController {

    @Autowired
    private InstalacaoRepository instalacaoRepository;

    // POST - Criar nova instalação
    @PostMapping("/instalacoes")
    public ResponseEntity<InstalacaoDTO> criarInstalacao(@RequestBody Instalacao novaInstalacao) {
        novaInstalacao.setInstalacaoUuid(UUID.randomUUID()); // Gera um UUID único
        Instalacao instalacaoSalva = instalacaoRepository.save(novaInstalacao);
        return ResponseEntity.ok(new InstalacaoDTO(instalacaoSalva));
    }

    // GET - Listar todas as instalações
    @GetMapping("/instalacoes")
    public ResponseEntity<List<InstalacaoDTO>> listarInstalacoes() {
        List<Instalacao> instalacoes = instalacaoRepository.findAll();
        List<InstalacaoDTO> instalacoesDTO = instalacoes.stream()
                .map(InstalacaoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(instalacoesDTO);
    }

    // GET - Buscar instalação por UUID
    @GetMapping("/instalacoes/{instalacaoUuid}")
    public ResponseEntity<InstalacaoDTO> buscarInstalacaoPorUuid(@PathVariable UUID instalacaoUuid) {
        Optional<Instalacao> instalacaoOptional = instalacaoRepository.findByInstalacaoUuid(instalacaoUuid);

        if (instalacaoOptional.isPresent()) {
            return ResponseEntity.ok(new InstalacaoDTO(instalacaoOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/instalacoes/{instalacaoUuid}")
    public ResponseEntity<InstalacaoDTO> deletarInstalacao(@PathVariable UUID instalacaoUuid) {
        Optional<Instalacao> instalacaoOptional = instalacaoRepository.findByInstalacaoUuid(instalacaoUuid);

        if (instalacaoOptional.isPresent()) {
            Instalacao instalacao = instalacaoOptional.get();
            instalacao.setAtivo(false);
            instalacaoRepository.save(instalacao);
            return ResponseEntity.ok(new InstalacaoDTO(instalacao));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

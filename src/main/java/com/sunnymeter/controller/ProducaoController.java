package com.sunnymeter.controller;

import com.sunnymeter.dto.ProducaoDTO;
import com.sunnymeter.model.Producao;
import com.sunnymeter.repository.ProducaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/producao")
public class ProducaoController {

    @Autowired
    private ProducaoRepository producaoRepository;

    @PostMapping
    public ProducaoDTO createProducao(@RequestBody ProducaoDTO producaoDTO) {
        UUID registroProducaoUuid = UUID.randomUUID();
        UUID instalacaoUuid = UUID.fromString(producaoDTO.getInstalacaoUuid());

        Producao producao = new Producao();
        producao.setRegistroProducaoUuid(registroProducaoUuid);
        producao.setInstalacaoUuid(instalacaoUuid);
        producao.setProducaoKwh(producaoDTO.getProducaoKwh());
        producao.setMedicaoTimestamp(producaoDTO.getMedicaoTimestamp());
        producao.setCreatedAt(System.currentTimeMillis() / 1000); // Armazena em segundos

        producao = producaoRepository.save(producao);

        return convertToDTO(producao);
    }

    @GetMapping
    public List<ProducaoDTO> getAllProducao() {
        List<Producao> producoes = producaoRepository.findAll();

        return producoes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/custom-format")
    public String getAllProducaoCustomFormat() {
        List<Producao> producoes = producaoRepository.findAll();

        StringBuilder response = new StringBuilder("[\n");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss a", new Locale("pt", "BR"));

        for (Producao producao : producoes) {
            String medicaoFormatted = dateFormat.format(new Date(producao.getMedicaoTimestamp() * 1000L));
            String createdAtFormatted = dateFormat.format(new Date(producao.getCreatedAt() * 1000L));

            response.append("    {\n")
                    .append("        \"registro_producao_uuid\": \"").append(producao.getRegistroProducaoUuid()).append("\",\n")
                    .append("        \"instalacao_uuid\": \"").append(producao.getInstalacaoUuid()).append("\",\n")
                    .append("        \"producao_kwh\": ").append(producao.getProducaoKwh()).append(",\n")
                    .append("        \"medicao_timestamp\": ").append(producao.getMedicaoTimestamp()).append(" // ").append(medicaoFormatted).append(",\n")
                    .append("        \"created_at\": ").append(producao.getCreatedAt()).append(" // ").append(createdAtFormatted).append("\n")
                    .append("    },\n");
        }

        if (!producoes.isEmpty()) {
            response.setLength(response.length() - 2); // Remove a última vírgula
            response.append("\n");
        }
        response.append("]");

        return response.toString();
    }

    private ProducaoDTO convertToDTO(Producao producao) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss a");

        String medicaoFormatted = dateFormat.format(new Date(producao.getMedicaoTimestamp() * 1000L));
        String createdAtFormatted = dateFormat.format(new Date(producao.getCreatedAt() * 1000L));

        return new ProducaoDTO(
                producao.getRegistroProducaoUuid().toString(),
                producao.getInstalacaoUuid().toString(),
                producao.getProducaoKwh(),
                producao.getMedicaoTimestamp(),
                producao.getCreatedAt(),
                medicaoFormatted,
                createdAtFormatted
        );
    }
}

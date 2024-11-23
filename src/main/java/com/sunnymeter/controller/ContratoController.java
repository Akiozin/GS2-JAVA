package com.sunnymeter.controller;

import com.sunnymeter.model.Contrato;
import com.sunnymeter.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("contratos")
public class ContratoController {

    @Autowired
    private ContratoRepository contratoRepository;

    private static final Set<Integer> TIMEFRAMES_VALIDOS = Set.of(90, 180, 270, 360, 540, 720, 810);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @PostMapping
    public ResponseEntity<Object> criarContrato(@RequestBody Map<String, Object> contratoRequest) {
        try {
            UUID clienteUuid = UUID.fromString((String) contratoRequest.get("cliente_uuid"));
            UUID instalacaoUuid = UUID.fromString((String) contratoRequest.get("instalacao_uuid"));
            int timeframe = (int) contratoRequest.get("timeframe");

            if (!TIMEFRAMES_VALIDOS.contains(timeframe)) {
                // Criar resposta de erro formatada corretamente
                Map<String, Object> error = new LinkedHashMap<>();
                error.put("error_code", "INVALID_TIMEFRAME");
                error.put("error_description", "Invalid timeframe used! Please select a valid timeframe!\nInput timeframe: "
                        + timeframe + "\nAvailable timeframes: " + TIMEFRAMES_VALIDOS);

                Map<String, Object> errorResponse = new LinkedHashMap<>();
                errorResponse.put("errors", List.of(error));
                return ResponseEntity.badRequest().body(errorResponse);
            }

            LocalDateTime now = LocalDateTime.now();

            // Criar contrato
            Contrato contrato = new Contrato();
            contrato.setContratoUuid(UUID.randomUUID());
            contrato.setClienteUuid(clienteUuid);
            contrato.setInstalacaoUuid(instalacaoUuid);
            contrato.setDataInicio(now.toLocalDate());
            contrato.setDuracaoContrato(timeframe);
            contrato.setStatus("Ativo");
            contrato.setHoraCriacao(now); // Adicionando o horário de criação

            // Salvar contrato no banco
            contratoRepository.save(contrato);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("instalacao_uuid", contrato.getInstalacaoUuid().toString());
            response.put("cliente_uuid", contrato.getClienteUuid().toString());
            response.put("contrato_uuid", contrato.getContratoUuid().toString());
            response.put("timeframe", contrato.getDuracaoContrato());
            response.put("status", contrato.getStatus());
            response.put("contrato_inicio_timestamp", now.atZone(ZoneId.systemDefault()).toEpochSecond());
            response.put("contrato_inicio_datahora", now.format(DATE_TIME_FORMATTER));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("error_code", "ERROR_CREATING_CONTRACT");
            error.put("error_description", "An error occurred while creating the contract: " + e.getMessage());

            Map<String, Object> errorResponse = new LinkedHashMap<>();
            errorResponse.put("errors", List.of(error));
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllContratos() {
        try {
            List<Map<String, Object>> contratosResponse = contratoRepository.findAll().stream().map(contrato -> {
                Map<String, Object> response = new LinkedHashMap<>();
                response.put("instalacao_uuid", contrato.getInstalacaoUuid().toString());
                response.put("cliente_uuid", contrato.getClienteUuid().toString());
                response.put("contrato_uuid", contrato.getContratoUuid().toString());
                response.put("timeframe", contrato.getDuracaoContrato());
                response.put("status", contrato.getStatus());
                response.put("contrato_inicio_timestamp", contrato.getHoraCriacao()
                        .atZone(ZoneId.systemDefault())
                        .toEpochSecond());
                response.put("contrato_inicio_datahora", contrato.getHoraCriacao()
                        .format(DATE_TIME_FORMATTER));
                return response;
            }).toList();

            return ResponseEntity.ok(contratosResponse);
        } catch (Exception e) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("error_code", "ERROR_FETCHING_CONTRACTS");
            error.put("error_description", "An error occurred while fetching contracts: " + e.getMessage());

            Map<String, Object> errorResponse = new LinkedHashMap<>();
            errorResponse.put("errors", List.of(error));
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/{contratoUuid}")
    public ResponseEntity<Object> getContrato(@PathVariable("contratoUuid") UUID contratoUuid) {
        try {
            Optional<Contrato> contratoOptional = contratoRepository.findByContratoUuid(contratoUuid);

            if (contratoOptional.isEmpty()) {
                Map<String, Object> error = new LinkedHashMap<>();
                error.put("error_code", "CONTRACT_NOT_FOUND");
                error.put("error_description", "No contract found with contrato_uuid: " + contratoUuid);
                Map<String, Object> errorResponse = new LinkedHashMap<>();
                errorResponse.put("errors", List.of(error));
                return ResponseEntity.status(404).body(errorResponse);
            }

            Contrato contrato = contratoOptional.get();
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("instalacao_uuid", contrato.getInstalacaoUuid().toString());
            response.put("cliente_uuid", contrato.getClienteUuid().toString());
            response.put("contrato_uuid", contrato.getContratoUuid().toString());
            response.put("timeframe", contrato.getDuracaoContrato());
            response.put("status", contrato.getStatus());
            response.put("contrato_inicio_timestamp", contrato.getHoraCriacao()
                    .atZone(ZoneId.systemDefault())
                    .toEpochSecond());
            response.put("contrato_inicio_datahora", contrato.getHoraCriacao().format(DATE_TIME_FORMATTER));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("error_code", "ERROR_FETCHING_CONTRACT");
            error.put("error_description", "An error occurred while fetching the contract: " + e.getMessage());
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            errorResponse.put("errors", List.of(error));
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @DeleteMapping("/{contratoUuid}")
    public ResponseEntity<Object> cancelContrato(@PathVariable("contratoUuid") UUID contratoUuid) {
        try {
            Optional<Contrato> contratoOptional = contratoRepository.findByContratoUuid(contratoUuid);

            if (contratoOptional.isEmpty()) {
                Map<String, Object> error = new LinkedHashMap<>();
                error.put("error_code", "CONTRACT_NOT_FOUND");
                error.put("error_description", "No contract found with contrato_uuid: " + contratoUuid);
                Map<String, Object> errorResponse = new LinkedHashMap<>();
                errorResponse.put("errors", List.of(error));
                return ResponseEntity.status(404).body(errorResponse);
            }

            Contrato contrato = contratoOptional.get();
            contrato.setStatus("Cancelado");
            contratoRepository.save(contrato);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("instalacao_uuid", contrato.getInstalacaoUuid().toString());
            response.put("cliente_uuid", contrato.getClienteUuid().toString());
            response.put("contrato_uuid", contrato.getContratoUuid().toString());
            response.put("timeframe", contrato.getDuracaoContrato());
            response.put("status", contrato.getStatus());
            response.put("contrato_inicio_timestamp", contrato.getHoraCriacao()
                    .atZone(ZoneId.systemDefault())
                    .toEpochSecond());
            response.put("contrato_inicio_datahora", contrato.getHoraCriacao().format(DATE_TIME_FORMATTER));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("error_code", "ERROR_UPDATING_CONTRACT");
            error.put("error_description", "An error occurred while canceling the contract: " + e.getMessage());
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            errorResponse.put("errors", List.of(error));
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }


}

package com.sunnymeter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("consumo")
public class ConsumoController {

    private final List<Map<String, Object>> consumos = new ArrayList<>();

    private final Map<UUID, List<RegistroConsumo>> registrosConsumo = new HashMap<>();

    @PostMapping
    public ResponseEntity<Object> criarConsumo(@RequestBody Map<String, Object> consumoRequest) {
        try {
            UUID instalacaoUuid = UUID.fromString((String) consumoRequest.get("instalacao_uuid"));
            double consumoKwh = ((Number) consumoRequest.get("consumo_kwh")).doubleValue();
            long medicaoTimestamp = ((Number) consumoRequest.get("medicao_timestamp")).longValue();

            UUID registroConsumoUuid = UUID.randomUUID();
            long createdAtTimestamp = Instant.now().getEpochSecond();

            Map<String, Object> novoConsumo = Map.of(
                    "registro_consumo_uuid", registroConsumoUuid.toString(),
                    "instalacao_uuid", instalacaoUuid.toString(),
                    "consumo_kwh", consumoKwh,
                    "medicao_timestamp", medicaoTimestamp,
                    "created_at", createdAtTimestamp
            );
            consumos.add(novoConsumo);

            List<RegistroConsumo> registros = registrosConsumo.computeIfAbsent(instalacaoUuid, k -> new ArrayList<>());
            registros.add(new RegistroConsumo(medicaoTimestamp, consumoKwh));

            return ResponseEntity.ok(formatarSaida(novoConsumo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error_code", "ERROR_CREATING_CONSUMO",
                    "error_description", e.getMessage()
            ));
        }
    }

    @PostMapping("/adicionar")
    public ResponseEntity<Object> adicionarRegistroConsumo(@RequestBody RegistroConsumoRequest request) {
        try {
            UUID instalacaoUuid = request.getInstalacaoUuid();
            List<RegistroConsumo> registros = registrosConsumo.computeIfAbsent(instalacaoUuid, k -> new ArrayList<>());

            // Adiciona novo registro
            registros.add(new RegistroConsumo(request.getMedicaoTimestamp(), request.getConsumoKwh()));

            return ResponseEntity.ok(Map.of(
                    "message", "Registro adicionado com sucesso.",
                    "instalacao_uuid", instalacaoUuid.toString()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error_code", "ERROR_ADDING_CONSUMO",
                    "error_description", e.getMessage()
            ));
        }
    }

    @GetMapping
    public ResponseEntity<Object> listarConsumos() {
        List<Map<String, Object>> respostaFormatada = consumos.stream()
                .map(this::formatarSaida)
                .toList();

        return ResponseEntity.ok(respostaFormatada);
    }

    @GetMapping("/{instalacao_uuid}")
    public ResponseEntity<Object> getConsumoMensal(@PathVariable("instalacao_uuid") UUID instalacaoUuid) {
        List<RegistroConsumo> registros = registrosConsumo.get(instalacaoUuid);

        if (registros == null || registros.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of(
                    "instalacao_uuid", instalacaoUuid.toString(),
                    "message", "Nenhum registro encontrado para a instalação."
            ));
        }

        LocalDate hoje = LocalDate.now();
        List<RegistroConsumo> registrosDoMes = registros.stream()
                .filter(r -> {
                    LocalDate dataRegistro = Instant.ofEpochSecond(r.getMedicaoTimestamp())
                            .atZone(ZoneOffset.UTC)
                            .toLocalDate();
                    return dataRegistro.getMonth() == hoje.getMonth() && dataRegistro.getYear() == hoje.getYear();
                })
                .sorted(Comparator.comparingLong(RegistroConsumo::getMedicaoTimestamp))
                .collect(Collectors.toList());

        if (registrosDoMes.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of(
                    "instalacao_uuid", instalacaoUuid.toString(),
                    "message", "Nenhum registro encontrado para o mês atual."
            ));
        }

        double consumoMensal = 0.0;
        double consumoDiarioMedio = 0.0;
        long diasEntre = 1;

        RegistroConsumo ultimoRegistro = registrosDoMes.get(registrosDoMes.size() - 1);
        consumoMensal = ultimoRegistro.getConsumoKwh();

        // Calcular o consumo diário médio
        diasEntre = ChronoUnit.DAYS.between(
                Instant.ofEpochSecond(0)
                        .atZone(ZoneOffset.UTC)
                        .toLocalDate(),
                Instant.ofEpochSecond(ultimoRegistro.getMedicaoTimestamp())
                        .atZone(ZoneOffset.UTC)
                        .toLocalDate()
        );
        diasEntre = Math.max(diasEntre, 1);
        consumoDiarioMedio = consumoMensal / diasEntre;

        // Calcular o consumo mensal estimado
        double consumoMensalEstimado = consumoDiarioMedio * hoje.lengthOfMonth();

        Map<String, Object> resultado = new LinkedHashMap<>();
        resultado.put("instalacao_uuid", instalacaoUuid.toString());
        resultado.put("timestamp_calculo", Instant.now().getEpochSecond());
        resultado.put("dia_referencia", String.valueOf(hoje.getDayOfMonth()));
        resultado.put("mes_referencia", hoje.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pt")));
        resultado.put("ano_referencia", String.valueOf(hoje.getYear()));
        resultado.put("dias_para_acabar_o_mes", String.valueOf(hoje.lengthOfMonth() - hoje.getDayOfMonth()));
        resultado.put("consumo_mensal_medio_kwh", consumoMensal);
        resultado.put("consumo_diario_medio_kwh", consumoDiarioMedio);
        resultado.put("consumo_mensal_estimado_kwh", consumoMensalEstimado);

        return ResponseEntity.ok(resultado);
    }

    private Map<String, Object> formatarSaida(Map<String, Object> consumo) {
        long medicaoTimestamp = (long) consumo.get("medicao_timestamp");
        long createdAtTimestamp = (long) consumo.get("created_at");

        String medicaoTimestampFormatted = formatarTimestamp(medicaoTimestamp);
        String createdAtFormatted = formatarTimestamp(createdAtTimestamp);

        Map<String, Object> saida = new LinkedHashMap<>();
        saida.put("registro_consumo_uuid", consumo.get("registro_consumo_uuid"));
        saida.put("instalacao_uuid", consumo.get("instalacao_uuid"));
        saida.put("consumo_kwh", consumo.get("consumo_kwh"));
        saida.put("medicao_timestamp", medicaoTimestamp + " // " + medicaoTimestampFormatted);
        saida.put("created_at", createdAtTimestamp + " // " + createdAtFormatted);

        return saida;
    }

    private String formatarTimestamp(long timestamp) {
        return Instant.ofEpochSecond(timestamp)
                .atZone(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private static class RegistroConsumo {
        private long medicaoTimestamp;
        private double consumoKwh;

        public RegistroConsumo(long medicaoTimestamp, double consumoKwh) {
            this.medicaoTimestamp = medicaoTimestamp;
            this.consumoKwh = consumoKwh;
        }

        public long getMedicaoTimestamp() {
            return medicaoTimestamp;
        }

        public double getConsumoKwh() {
            return consumoKwh;
        }
    }

    private static class RegistroConsumoRequest {
        private UUID instalacaoUuid;
        private long medicaoTimestamp;
        private double consumoKwh;

        public UUID getInstalacaoUuid() {
            return instalacaoUuid;
        }

        public long getMedicaoTimestamp() {
            return medicaoTimestamp;
        }

        public double getConsumoKwh() {
            return consumoKwh;
        }

    }
}

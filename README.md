
# Global Solution FIAP S2/2024 - SunnyMeter

**Integrantes:**


*   RM97631 - Fabrício Saavedra
*   RM98582 - Guilherme Akio

O projeto **SunnyMeter API** é uma solução desenvolvida para gerenciar dados de clientes, instalações, contratos, registros de consumo e produção de energia solar. Esta API foi construída para facilitar a integração e manipulação de informações relacionadas a sistemas de energia sustentável.

## Visão Geral

A API permite:
- Gerenciar informações de clientes e suas instalações.
- Registrar contratos relacionados às instalações.
- Monitorar consumo e produção de energia.

O **SunnyMeter** utiliza o banco de dados H2 para gerenciar e armazenar os dados capturados pelos sensores solares. A escolha do H2 se deu pela sua leveza, facilidade de configuração e alto desempenho, o que o torna ideal tanto para ambientes de desenvolvimento quanto para protótipos.
### **Configuração do Banco de Dados H2**
1. **Modo de Operação:**
    - O H2 é utilizado no modo **embutido**, armazenando os dados diretamente no disco local do dispositivo onde a aplicação está sendo executada.
    - Também é possível configurá-lo no modo **servidor**, permitindo conexões remotas para maior flexibilidade.

2. **URL de Conexão Padrão:**
   ```plaintext
   http://localhost:8080/h2-console/login.jsp?jsessionid=7be331f7de995a3191774b2fa9b95e9d
A API estará disponível no endereço `http://localhost:8080`.
`login:`
`Senha:`

---

## Cliente http://localhost:8080/clientes

### GET /clientes

**Saída:**

```json
[
  {
    "clienteUuid": "af915f08-acee-40f6-bb6d-d2491592ba51",
    "nome": "joão da silva",
    "endereco": "Rua das Flores, 41",
    "documento": "966.351.800-60",
    "tipo": "PF",
    "cep": "05534-120",
    "ativo": true
  }
]
```

### GET /clientes/{uuid}

**Saída:**

```json
[
  {
    "clienteUuid": "af915f08-acee-40f6-bb6d-d2491592ba51",
    "nome": "joão da silva",
    "endereco": "Rua das Flores, 41",
    "documento": "966.351.800-60",
    "tipo": "PF",
    "cep": "05534-120",
    "ativo": true
  }
]
```

### POST /clientes

**Entrada:**

```json
{
  "nome": "joão da silva",
  "endereco": "Rua das Flores, 41",
  "documento": "966.351.800-60",
  "tipo": "PF",
  "cep": "05534-120"
}
```

**Saída:**

```json
{
  "id": 1,
  "clienteUuid": "af915f08-acee-40f6-bb6d-d2491592ba51",
  "nome": "joão da silva",
  "endereco": "Rua das Flores, 41",
  "documento": "966.351.800-60",
  "cep": "05534-120",
  "tipo": "PF",
  "ativo": true
}
```

### DELETE /cliente/{uuid}

**Saída:**

```json
{
  "clienteUuid": "af915f08-acee-40f6-bb6d-d2491592ba51",
  "nome": "joão da silva",
  "endereco": "Rua das Flores, 41",
  "documento": "966.351.800-60",
  "tipo": "PF",
  "cep": "05534-120",
  "ativo": false
}
```

---

## Instalações http://localhost:8080/instalacoes


### GET /instalacoes 

**Saída:**

```json
[
  {
    "instalacaoUuid": "4a14e02a-d514-405c-9512-1fabd7150982",
    "endereco": "Rua das Flores, 41",
    "cep": "05535-120",
    "ativo": true
  }
]
```

### GET /instalacao/{uuid}

**Saída:**

```json
[
  {
    "instalacaoUuid": "4a14e02a-d514-405c-9512-1fabd7150982",
    "endereco": "Rua das Flores, 41",
    "cep": "05535-120",
    "ativo": true
  }
]
```

### POST /instalacao

**Entrada:**

```json
{
  "endereco": "Rua das Flores, 41",
  "cep": "05535-120"
}
```

**Saída:**

```json
{
  "instalacaoUuid": "4a14e02a-d514-405c-9512-1fabd7150982",
  "endereco": "Rua das Flores, 41",
  "cep": "05535-120",
  "ativo": true
}
```

### DELETE /instalacao/{uuid}

**Saída:**

```json
{
  "instalacaoUuid": "4a14e02a-d514-405c-9512-1fabd7150982",
  "endereco": "Rua das Flores, 41",
  "cep": "05535-120",
  "ativo": false
}
```

---
## Contratos http://localhost:8080/contratos


### GET /contratos

**Saída:**

```json
[
  {
    "instalacao_uuid": "84b4b063-58a4-4dab-bf4f-fd13954c328c",
    "cliente_uuid": "84b4b063-58a4-4dab-bf4f-fd13954c328c",
    "contrato_uuid": "7a451415-13ab-4537-b5f4-4e359433fea7",
    "timeframe": 180,
    "status": "Ativo",
    "contrato_inicio_timestamp": 1732321634,
    "contrato_inicio_datahora": "22/11/2024 21:27:14"
  }
]
```

### GET /contratos/{uuid}

**Saída:**

```json
[
  {
    "instalacao_uuid": "84b4b063-58a4-4dab-bf4f-fd13954c328c",
    "cliente_uuid": "84b4b063-58a4-4dab-bf4f-fd13954c328c",
    "contrato_uuid": "7a451415-13ab-4537-b5f4-4e359433fea7",
    "timeframe": 180,
    "status": "Ativo",
    "contrato_inicio_timestamp": 1732321634,
    "contrato_inicio_datahora": "22/11/2024 21:27:14"
  }
]
```

### POST /contratos

**Entrada:**

```json
{
  "instalacao_uuid": "84b4b063-58a4-4dab-bf4f-fd13954c328c",
  "cliente_uuid": "84b4b063-58a4-4dab-bf4f-fd13954c328c",
  "timeframe": 180
}
```

**Saída:**

```json
{
  "instalacaoUuid": "4a14e02a-d514-405c-9512-1fabd7150982",
  "endereco": "Rua das Flores, 41",
  "cep": "05535-120",
  "ativo": true
}
```

### POST /contratos

**Entrada:**

```json
{
  "instalacao_uuid": "84b4b063-58a4-4dab-bf4f-fd13954c328c",
  "cliente_uuid": "84b4b063-58a4-4dab-bf4f-fd13954c328c",
  "timeframe": 18
}
```

**Saída:**

```json
{
  "errors": [
    {
      "error_code": "INVALID_TIMEFRAME",
      "error_description": "Invalid timeframe used! Please select a valid timeframe!\nInput timeframe: 18\nAvailable timeframes: [180, 810, 270, 90, 720, 540, 360]"
    }
  ]
}
```

### DELETE /instalacao/{uuid}

**Saída:**

```json
{
  "instalacao_uuid": "84b4b063-58a4-4dab-bf4f-fd13954c328c",
  "cliente_uuid": "84b4b063-58a4-4dab-bf4f-fd13954c328c",
  "contrato_uuid": "7a451415-13ab-4537-b5f4-4e359433fea7",
  "timeframe": 180,
  "status": "Cancelado",
  "contrato_inicio_timestamp": 1732321634,
  "contrato_inicio_datahora": "22/11/2024 21:27:14"
}
```

---

## Consumo http://localhost:8080/contratos


### GET /consumo

**Saída:**

```json
[
  {
    "registro_consumo_uuid": "49881c9d-ec6d-4ae1-a414-cc5ccb2ccf15",
    "instalacao_uuid": "7da41106-5109-45f4-8d09-9ca405c33e5c",
    "consumo_kwh": 100.0,
    "medicao_timestamp": "1730486400 // 2024-11-01 18:40:00",
    "created_at": "1732322118 // 2024-11-23 00:35:18"
  }
]
```

### GET /consumo/{instalacao_uuid}

**Saída:**

```json
[
  {
    "instalacao_uuid": "7da41106-5109-45f4-8d09-9ca405c33e5c",
    "timestamp_calculo": 1732322202,
    "dia_referencia": "22",
    "mes_referencia": "novembro",
    "ano_referencia": "2024",
    "dias_para_acabar_o_mes": "8",
    "consumo_mensal_medio_kwh": 100.0,
    "consumo_diario_medio_kwh": 0.004993009786299181,
    "consumo_mensal_estimado_kwh": 0.14979029358897542
  }
]
```

### POST /contratos

**Entrada:**

```json
{
  "instalacao_uuid": "7da41106-5109-45f4-8d09-9ca405c33e5c",
  "consumo_kwh": 100.0,
  "medicao_timestamp": 1730486400
}
```

**Saída:**

```json
{
  "registro_consumo_uuid": "f37e8147-5b6f-450d-9738-ff1659b846bd",
  "instalacao_uuid": "7da41106-5109-45f4-8d09-9ca405c33e5c",
  "consumo_kwh": 100.0,
  "medicao_timestamp": "1730486400 // 2024-11-01 18:40:00",
  "created_at": "1732322238 // 2024-11-23 00:37:18"
}
```

---

## Produção http://localhost:8080/producao


### GET /producao

**Saída:**

```json
[
  {
    "registro_producao_uuid": "44cc976b-17ca-4f47-8e8b-1aac1160c355",
    "instalacao_uuid": "7da41106-5109-45f4-8d09-9ca405c33e5c",
    "producao_kwh": 10.47,
    "medicao_timestamp": 1731284100,
    "created_at": 1732322416,
    "medicao_timestamp_horario": "novembro 10, 2024 21:15:00 PM",
    "created_at_horario": "novembro 22, 2024 21:40:16 PM"
  }
]
```

### POST /contratos

**Entrada:**

```json
{
  "instalacaoUuid": "7da41106-5109-45f4-8d09-9ca405c33e5c",
  "producaoKwh": 10.47,
  "medicaoTimestamp": 1731284100
}
```

**Saída:**

```json
{
  "registro_producao_uuid": "44cc976b-17ca-4f47-8e8b-1aac1160c355",
  "instalacao_uuid": "7da41106-5109-45f4-8d09-9ca405c33e5c",
  "producao_kwh": 10.47,
  "medicao_timestamp": 1731284100,
  "created_at": 1732322416,
  "medicao_timestamp_horario": "novembro 10, 2024 21:15:00 PM",
  "created_at_horario": "novembro 22, 2024 21:40:16 PM"
}
```

---
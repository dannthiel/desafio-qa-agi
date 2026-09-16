# Relatório de execução - Performance

## Critério de aceitação

- vazão: pelo menos 250 requisições por segundo;
- latência: percentil 90 inferior a 2.000 ms;
- cenário funcional: compra confirmada com sucesso.

## Ambiente da execução

| Item | Valor |
|---|---|
| Data/hora UTC | A preencher após execução controlada |
| JMeter | 5.6.3 |
| Origem da carga | A preencher |
| Plano de carga | `load-test.jmx` |
| Plano de pico | `spike-test.jmx` |
| Ambiente-alvo | `https://blazedemo.com` |

## Resultado do smoke funcional

| Métrica | Resultado |
|---|---|
| Validação técnica do plano de carga | 16 requisições, 0 erros |
| Validação técnica do plano de pico | 25 requisições, 0 erros |
| Correlação e assertions | Aprovadas no ambiente controlado |
| Compra no ambiente público | Pendente de execução controlada |

As validações técnicas foram realizadas contra um mock local determinístico para comprovar carregamento dos JMX, correlação dos campos do voo, envio das quatro requisições e assertions. Esses números **não** representam o desempenho do BlazeDemo e não são usados para decidir o critério de aceitação.

## Resultado - carga sustentada

| Métrica | Resultado | Critério | Status |
|---|---:|---:|---|
| Throughput | A preencher | ≥ 250 req/s | Pendente |
| p90 | A preencher | < 2.000 ms | Pendente |
| Taxa de erros | A preencher | Informativo | Pendente |
| Total de requisições | A preencher | Informativo | Pendente |

## Resultado - pico

| Fase | Throughput | p90 | Erros | Observação |
|---|---:|---:|---:|---|
| Linha de base | A preencher | A preencher | A preencher | Pendente |
| Pico | A preencher | A preencher | A preencher | Pendente |
| Recuperação | A preencher | A preencher | A preencher | Pendente |

## Conclusão

**O critério ainda não deve ser classificado como aprovado ou reprovado antes da execução controlada dos dois planos.** O relatório HTML e o JTL deverão ser anexados, e a decisão deverá considerar simultaneamente throughput e p90.

Mesmo após a execução, a conclusão será válida somente para o horário, origem de carga e ambiente público observados. Sem acesso à infraestrutura e à telemetria do servidor, não é possível atribuir causa raiz apenas pelo teste de caixa-preta.

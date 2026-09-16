# Relatório de execução - Performance

## Critério de aceitação

- vazão: pelo menos 250 requisições por segundo;
- latência: percentil 90 inferior a 2.000 ms;
- cenário funcional: compra confirmada com sucesso.

## Ambiente da execução

| Item | Valor |
|---|---|
| Data | 16/09/2026 |
| JMeter | 5.6.3 |
| Origem da carga | GitHub Actions - ubuntu-latest |
| Plano de carga | `load-test.jmx` |
| Plano de pico | `spike-test.jmx` |
| Ambiente-alvo | `https://blazedemo.com` |
| Execução carga | GitHub Actions #4 |
| Execução pico | GitHub Actions #5 |

## Resultado do smoke funcional

| Métrica | Resultado |
|---|---|
| Validação técnica do plano de carga | 16 requisições, 0 erros |
| Validação técnica do plano de pico | 25 requisições, 0 erros |
| Correlação e assertions | Aprovadas |
| Fluxo de compra | Validado até a confirmação |

As validações iniciais foram realizadas com baixa carga antes da execução dos cenários de performance. O objetivo foi confirmar o funcionamento do fluxo, correlação dos dados e assertions antes de aumentar a concorrência.

## Resultado - carga sustentada

| Métrica | Resultado | Critério | Status |
|---|---:|---:|---|
| Throughput | 247,97 req/s | >= 250 req/s | Não atingiu |
| p90 | 308 ms | < 2.000 ms | Atingiu |
| Taxa de erros | 0,107% (48/44.695) | Informativo | Observado |
| Total de requisições | 44.695 | Informativo | Executado |
| Duração aproximada | 180 s | 180 s | Executado |

O throughput observado ficou aproximadamente 2,03 req/s abaixo da meta estabelecida, diferença de cerca de 0,81%.

O percentil 90 permaneceu em 308 ms, significativamente abaixo do limite de 2.000 ms.

Foram registradas 48 falhas entre 44.695 requisições. As falhas observadas foram associadas principalmente a `Connection reset` / `Connection reset by peer`, distribuídas entre as etapas do fluxo.

Como o ambiente é público e externo ao projeto, sem acesso à infraestrutura ou telemetria do servidor, não é possível atribuir causa raiz exclusivamente a partir desse teste de caixa-preta.

## Resultado - pico

| Fase | Throughput | p90 | Erros | Observação |
|---|---:|---:|---:|---|
| Linha de base | 50,78 req/s | 288 ms | 0 | Comportamento estável |
| Pico | 221,95 req/s | 2.456 ms | 0 | Aumento significativo da latência |
| Recuperação | 50,83 req/s | 300 ms | 0 | Retorno próximo à linha de base |

Durante a linha de base, o sistema apresentou aproximadamente 50,78 req/s e p90 de 288 ms.

Na fase de pico houve degradação perceptível: o throughput observado foi de 221,95 req/s e o p90 aumentou para 2.456 ms.

Após a retirada do pico, o sistema apresentou recuperação, retornando para aproximadamente 50,83 req/s e p90 de 300 ms, valores próximos aos observados antes do aumento de carga.

Nenhum erro foi registrado durante as três fases do teste de pico.

## Conclusão

No teste de carga sustentada, o critério de latência foi atendido, com p90 de 308 ms. Entretanto, o critério de vazão não foi completamente atingido, pois foram observadas 247,97 req/s frente à meta de pelo menos 250 req/s.

Portanto, considerando simultaneamente os dois critérios definidos para o cenário LOAD-001, a execução não atende integralmente ao critério de aceitação.

O teste de pico demonstrou degradação de desempenho durante o aumento súbito de carga, principalmente na latência, cujo p90 chegou a 2.456 ms. Após o pico, houve recuperação para valores próximos à linha de base, sem erros registrados nessa execução.

Os resultados representam exclusivamente as condições observadas nas execuções realizadas em 16/09/2026 a partir de um runner do GitHub Actions contra o ambiente público BlazeDemo.

Por se tratar de um ambiente externo, sem controle da infraestrutura, concorrência, rede ou telemetria do servidor, os resultados não devem ser interpretados como benchmark definitivo nem permitem determinar causa raiz de eventuais limitações de desempenho.

Os arquivos `results.jtl`, logs do JMeter e relatórios HTML foram preservados como artefatos das execuções no GitHub Actions para rastreabilidade e evidência.

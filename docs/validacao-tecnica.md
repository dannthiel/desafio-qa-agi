# Validação técnica da entrega

## Situação final

A entrega foi validada considerando execução local, pipeline e testes de performance.

## Validações Web e API

| Verificação | Resultado |
|---|---|
| Clone novo do repositório | Validado |
| Ambiente local | Windows |
| Java | 17 |
| Maven | Maven Wrapper do projeto |
| Testes de API | BUILD SUCCESS |
| Testes Web | BUILD SUCCESS |
| Execução completa Web + API | BUILD SUCCESS |
| Pipeline GitHub Actions | Sucesso |
| Relatórios Allure | Gerados e preservados como artefatos |
| Screenshots de falha Web | Configurados como evidência |

A execução local foi realizada a partir de uma nova cópia clonada diretamente do GitHub, simulando o processo que poderá ser realizado pelo avaliador.

O Maven Wrapper permite executar o projeto sem instalação prévia do Maven. O projeto disponibiliza `mvnw` para Linux/macOS e `mvnw.cmd` para Windows.

## Validação da API

Foram automatizados os três endpoints obrigatórios:

- `GET /breeds/list/all`
- `GET /breed/{breed}/images`
- `GET /breeds/image/random`

Além dos cenários obrigatórios, foram adicionadas validações de:

- status HTTP;
- Content-Type;
- regras básicas de negócio;
- schemas JSON;
- formato das URLs das imagens;
- cenário negativo para raça inexistente.

## Validação Web

Foram automatizados três cenários para a pesquisa do Blog do Agi:

- pesquisa por termo existente;
- pesquisa por termo inexistente;
- abertura de um artigo retornado pela busca.

Os testes utilizam Selenium WebDriver, Page Object, JUnit 5 e evidências no Allure.

## Validação local de performance

O smoke de performance foi executado localmente com Apache JMeter 5.6.2 e Java 17.

Configuração utilizada:

- 1 thread;
- ramp-up de 1 segundo;
- duração de 20 segundos;
- baixa vazão para validação funcional.

Resultado:

- 20 requisições;
- 0 erros;
- relatório HTML gerado com sucesso.

O objetivo do smoke local foi comprovar que o projeto baixado do GitHub pode executar o plano JMeter e gerar suas evidências em uma máquina local.

## Execuções de performance no GitHub Actions

As execuções de acceptance utilizaram Apache JMeter 5.6.3.

### Carga sustentada

| Métrica | Resultado |
|---|---:|
| Throughput | 247,97 req/s |
| p90 | 308 ms |
| Erros | 48 / 44.695 |
| Taxa de erros | 0,107% |
| Duração aproximada | 180 s |

O critério de latência foi atendido, porém a vazão observada ficou aproximadamente 2,03 req/s abaixo da meta de 250 req/s.

Portanto, o cenário de carga sustentada não atende integralmente ao critério de aceitação definido.

### Teste de pico

| Fase | Throughput | p90 | Erros |
|---|---:|---:|---:|
| Linha de base | 50,78 req/s | 288 ms | 0 |
| Pico | 221,95 req/s | 2.456 ms | 0 |
| Recuperação | 50,83 req/s | 300 ms | 0 |

Durante o pico houve aumento significativo da latência. Após a retirada da carga elevada, os indicadores retornaram para valores próximos aos observados na linha de base.

## Evidências

As execuções preservam:

- `results.jtl`;
- `jmeter.log`;
- relatório HTML do JMeter;
- resultados Allure;
- screenshots Web quando aplicável;
- artefatos das execuções no GitHub Actions.

## Conclusão

A automação Web, API e Performance está versionada em repositório público e possui instruções para execução local.

Web e API foram executados com sucesso tanto localmente quanto no GitHub Actions.

O plano de performance foi validado localmente em smoke e também executado em carga e pico pelo GitHub Actions.

Os resultados de performance foram documentados de forma transparente. O critério de latência da carga sustentada foi atendido, mas a vazão ficou ligeiramente abaixo dos 250 req/s definidos no desafio. O teste de pico evidenciou degradação de latência durante o aumento de carga e recuperação posterior.

Como o BlazeDemo é um ambiente público e externo ao projeto, sem acesso à infraestrutura e à telemetria do servidor, os resultados representam exclusivamente as condições observadas durante as execuções realizadas.

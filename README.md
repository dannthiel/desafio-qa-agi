# Desafio técnico QA - Agi

Projeto de qualidade que cobre os três blocos solicitados no desafio:

- **Web:** pesquisa de artigos do Blog do Agi;
- **API:** endpoints públicos da Dog API;
- **Performance:** compra de passagem no BlazeDemo com carga e pico.

Além da automação, a entrega explicita estratégia, riscos, cenários exploratórios, critérios de aceite, limitações e evidências. A intenção é demonstrar o raciocínio de QA que orientou o código, e não apenas a quantidade de testes.

## Tecnologias

- Java 17
- Maven Wrapper
- Selenium WebDriver
- REST Assured
- JUnit 5
- Hamcrest e JSON Schema
- Allure Report
- Apache JMeter 5.6.x
- GitHub Actions

## Estrutura

```text
desafio-qa-agi/
├── api-tests/                 # Testes REST Assured e schemas
├── web-tests/                 # Selenium, Page Object e evidências
├── performance-tests/
│   ├── jmeter/test-plans/     # Carga, pico e fluxo reutilizável
│   ├── scripts/               # Execução Linux/macOS e Windows
│   └── results/               # Saída local ignorada pelo Git
├── docs/                      # Estratégia, riscos e relatórios
├── .github/workflows/         # CI funcional e performance manual
└── pom.xml                    # Agregador Maven
```

## Pré-requisitos

### Web e API

- Java 17 ou superior;
- Google Chrome instalado para os testes Web;
- acesso à internet para os ambientes públicos.

Não é necessário instalar o Maven: o projeto inclui o Maven Wrapper.

### Performance

- Apache JMeter 5.6.x no `PATH`;
- máquina compatível com a quantidade de threads usada;
- execução previamente autorizada contra o ambiente-alvo.

## Como executar

### Baixando o projeto em uma máquina local

Clone o repositório e acesse a pasta do projeto:

```bash
git clone https://github.com/dannthiel/desafio-qa-agi.git
cd desafio-qa-agi
```

A execução local foi validada a partir de uma cópia nova do repositório em ambiente Windows. Os testes Web e API foram executados com Java 17 utilizando o Maven Wrapper. O smoke de performance foi validado localmente com Apache JMeter 5.6.2, enquanto o pipeline do GitHub Actions utiliza JMeter 5.6.3.


### Todos os testes Web e API

Linux/macOS:

```bash
./mvnw clean test
```

Windows:

```powershell
.\mvnw.cmd clean test
```

### Somente API

```bash
./mvnw -pl api-tests clean test
```

Para apontar para outro ambiente:

```bash
./mvnw -pl api-tests test -Ddog.api.baseUrl=https://dog.ceo/api
```

### Somente Web

Execução headless, padrão para CI:

```bash
./mvnw -pl web-tests clean test -Dheadless=true
```

Execução com navegador visível:

```bash
./mvnw -pl web-tests test -Dheadless=false
```

O Selenium Manager resolve o driver compatível com o navegador. Screenshots de falhas ficam em `web-tests/target/screenshots` e também são anexados ao Allure.

### Relatório Allure

Após executar um módulo:

```bash
./mvnw -pl api-tests allure:serve
./mvnw -pl web-tests allure:serve
```

Os resultados brutos ficam em `target/allure-results` e são publicados como artefatos no pipeline.

### JMeter - smoke funcional

Antes de aplicar carga, valide o fluxo com 1 usuário e baixa vazão:

```bash
bash performance-tests/scripts/run-performance.sh load-test \
  -Jthreads=1 -JrampUp=1 -Jduration=20 -JtargetRpm=60
```

PowerShell:

```powershell
.\performance-tests\scripts\run-performance.ps1 `
  -Plan load-test `
  -JMeterProperties "-Jthreads=1", "-JrampUp=1", "-Jduration=20", "-JtargetRpm=60"
```

### JMeter - carga sustentada

```bash
bash performance-tests/scripts/run-performance.sh load-test
```

Configuração padrão:

- 600 threads disponíveis;
- ramp-up de 60 segundos;
- duração de 180 segundos;
- meta global de 15.000 requisições/minuto, equivalente a 250 req/s.

### JMeter - pico

```bash
bash performance-tests/scripts/run-performance.sh spike-test
```

Perfil padrão:

1. 60 segundos a 50 req/s;
2. 60 segundos a 250 req/s;
3. 60 segundos de recuperação a 50 req/s.

Cada execução gera `results.jtl`, `jmeter.log` e relatório HTML em uma pasta com data/hora dentro de `performance-tests/results`.

> **Importante:** 250 usuários não significam 250 req/s. Os planos usam `Constant Throughput Timer` em requisições por minuto e threads suficientes para sustentar a vazão. O fluxo possui quatro requisições HTTP por compra.

## Cenários automatizados

### Web

| ID | Cenário | Prioridade |
|---|---|---:|
| WEB-001 | Pesquisa por termo existente (`INSS`) | P0 |
| WEB-002 | Pesquisa por termo inexistente | P0 |
| WEB-003 | Abertura de artigo retornado | P1 |

Os testes validam elementos e comportamentos específicos, sem depender de um título editorial fixo. O endereço do desafio, `blogdoagi.com.br`, redireciona atualmente para `blog.agibank.com.br`; isso foi tratado como observação de ambiente.

### API

| ID | Cenário | Principais validações |
|---|---|---|
| API-001 | Listar todas as raças | 200, negócio e coleção não vazia |
| API-002 | Listar imagens de `hound` | 200, lista e URLs coerentes |
| API-003 | Obter imagem aleatória | 200, contrato e URL |
| API-004 | Consultar raça inexistente | 404 e contrato de erro |
| API-005 | Validar schemas | campos, tipos e obrigatoriedade |
| API-006 | Validar Content-Type | JSON nos três endpoints |

### Performance

| ID | Cenário | Critério |
|---|---|---|
| LOAD-001 | Compra sob carga sustentada | ≥ 250 req/s e p90 < 2 s |
| SPIKE-001 | Linha de base, pico e recuperação | observar degradação e recuperação |

## Documentação de QA

- [Estratégia de testes](docs/estrategia-de-testes.md)
- [Matriz de riscos](docs/matriz-de-riscos.md)
- [Testes exploratórios](docs/testes-exploratorios.md)
- [Relatório de performance](docs/relatorio-performance.md)
- [Validação técnica da entrega](docs/validacao-tecnica.md)

## CI/CD

O workflow principal executa API e Web em jobs separados, conserva relatórios Allure e screenshots como artefatos. Os testes de performance ficam em workflow manual para evitar carga involuntária em um serviço público.

## Limitações conhecidas

- Os três alvos são ambientes públicos e externos ao projeto; indisponibilidade, latência de rede, rate limit ou mudança de layout/contrato podem afetar a execução.
- Resultados de performance não devem ser generalizados para produção, pois não há controle sobre infraestrutura, concorrência externa ou telemetria do servidor.
- O teste de carga deve ser executado de forma controlada e com autorização do responsável pelo ambiente.

## Melhorias futuras

- execução Web em matriz de navegadores;
- publicação automática do relatório Allure;
- baseline histórico de performance;
- teste de acessibilidade e responsividade;
- validação dos arquivos de imagem retornados pela API, além do formato das URLs.

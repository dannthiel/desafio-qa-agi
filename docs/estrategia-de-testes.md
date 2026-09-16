# Estratégia de testes

## Objetivo

Validar os comportamentos mais críticos solicitados no desafio e tornar explícita a relação entre requisito, risco, cenário e evidência.

## Abordagem

1. leitura dos requisitos obrigatórios;
2. exploração dos ambientes atuais;
3. levantamento e priorização dos riscos;
4. definição dos cenários positivos, negativos e de contrato;
5. automação das jornadas prioritárias;
6. execução independente por camada;
7. geração de relatório e análise das limitações.

## Pirâmide aplicada

- **API:** maior cobertura de contrato e regras por serem testes rápidos e estáveis;
- **Web:** três jornadas de alto valor, evitando replicar toda a cobertura da API;
- **Performance:** fluxo E2E no protocolo HTTP, sem navegador, medindo vazão, latência e erros.

## Critérios de entrada

- endpoints e sites acessíveis;
- Java 17 disponível;
- Chrome disponível para Web;
- JMeter 5.6.3 para performance;
- autorização para executar a carga planejada.

## Critérios de saída

- todos os endpoints obrigatórios cobertos;
- pelo menos dois cenários Web relevantes automatizados;
- fluxo de compra validado funcionalmente antes da carga;
- relatório com throughput, p90 e taxa de erros;
- falhas com evidências suficientes para reprodução.

## Decisões técnicas

- Page Object separa interação e asserção no Web;
- `WebDriverWait` substitui esperas fixas;
- Selenium Manager reduz dependência de drivers locais;
- `RequestSpecification` centraliza a configuração da Dog API;
- JSON Schema verifica campos obrigatórios e tipos;
- Allure recebe passos, requests/responses e screenshots;
- fragmento JMeter reutilizável evita duplicação do fluxo entre carga e pico;
- performance não roda automaticamente no CI para impedir carga involuntária.

## Dados de teste

- termo válido Web: `INSS`, estável como tema do blog;
- termo inválido Web: `qaautomacao999999`, reduz risco de colisão;
- raça válida API: `hound`, listada no catálogo e com sub-raças;
- raça inválida API: `qaautomacao999999`;
- dados do formulário BlazeDemo são fictícios e não representam cartão ou pessoa real.

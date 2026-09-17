# Testes exploratórios

Data: 16/09/2026

Os testes exploratórios foram realizados antes e durante a automação para identificar riscos, comportamentos não explícitos nos requisitos e cenários relevantes.

A exploração também ajudou a decidir quais cenários deveriam ser automatizados e quais poderiam permanecer como backlog por apresentarem menor risco.

## Blog do Agi

| Cenário explorado | Resultado observado | Decisão |
|---|---|---|
| Pesquisa por `INSS` | Retorna artigos relacionados | Automatizado - WEB-001 |
| Pesquisa por termo inexistente | Exibe ausência de resultados | Automatizado - WEB-002 |
| Abertura de artigo retornado | Artigo é carregado corretamente | Automatizado - WEB-003 |
| URL informada no desafio | Redireciona para `blog.agibank.com.br` | Comportamento documentado |
| Pesquisa vazia | Cenário complementar identificado | Backlog |
| Caracteres especiais | Cenário complementar identificado | Backlog |

A automação priorizou o fluxo principal da pesquisa e um cenário negativo, evitando dependência de títulos específicos de artigos, pois o conteúdo editorial pode mudar.

## Dog API

| Cenário explorado | Resultado observado | Decisão |
|---|---|---|
| Listagem de raças | HTTP 200 e coleção preenchida | Automatizado |
| Imagens da raça `hound` | HTTP 200 e URLs retornadas | Automatizado |
| Imagem aleatória | HTTP 200 e URL válida | Automatizado |
| Raça inexistente | HTTP 404 e contrato de erro | Automatizado |
| Contrato JSON | Estrutura conforme esperado | Automatizado com JSON Schema |
| Content-Type | JSON nos endpoints obrigatórios | Automatizado |
| Método ou endpoint inválido | Cenário negativo complementar | Backlog |

Além dos endpoints obrigatórios, foram priorizadas validações de contrato, conteúdo e tratamento de erro para aumentar a cobertura sem criar testes redundantes.

## BlazeDemo

| Cenário explorado | Resultado observado | Decisão |
|---|---|---|
| Fluxo completo de compra | Compra confirmada com sucesso | Automatizado no JMeter |
| Dados do voo | `flight`, `price` e `airline` são obtidos durante o fluxo | Correlação dinâmica |
| Smoke antes da carga | Fluxo executado com baixa carga e sem erros | Validado antes dos testes de performance |
| Carga sustentada | Vazão ficou ligeiramente abaixo da meta | Documentado no relatório |
| Pico de carga | Houve aumento significativo da latência | Documentado no relatório |
| Recuperação após pico | Indicadores retornaram próximos à linha de base | Documentado |

A validação funcional do fluxo foi executada antes da carga para evitar que um problema no script fosse confundido com um problema de performance.

## Segurança básica

Durante a exploração também foram considerados alguns cuidados básicos:

- utilização apenas de dados fictícios;
- nenhuma credencial ou token real armazenado no projeto;
- execução dos testes de performance de forma manual e controlada;
- ausência de dados pessoais reais nos testes e relatórios;
- nenhum teste invasivo de segurança foi realizado.

## Conclusão

Os testes exploratórios ajudaram a identificar riscos e orientar a priorização da automação.

Os cenários de maior impacto foram automatizados, enquanto cenários complementares de menor risco foram registrados como backlog. Dessa forma, a automação foi baseada em risco e valor para o negócio, e não apenas na quantidade de testes.

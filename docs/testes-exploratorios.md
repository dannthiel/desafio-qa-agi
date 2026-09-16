# Registro de testes exploratórios

Data da exploração: 16/09/2026.

## Blog do Agi

| Verificação | Resultado observado | Encaminhamento |
|---|---|---|
| Abrir URL fornecida | Redireciona para `blog.agibank.com.br` | Documentado; não classificado como bug |
| Pesquisar `INSS` | Exibe vários artigos | Automatizado em WEB-001 |
| Pesquisar termo único inexistente | Exibe mensagem de ausência de resultados | Automatizado em WEB-002 |
| Abrir resultado | Artigo é carregado fora da URL de busca | Automatizado em WEB-003 |
| Pesquisa vazia | Requer acompanhamento por ter menor risco | Backlog exploratório |
| Caracteres especiais | Requer acompanhamento por ter menor risco | Backlog exploratório |

## Dog API

| Verificação | Resultado observado | Encaminhamento |
|---|---|---|
| `GET /breeds/list/all` | 200, `status=success`, objeto de raças | API-001 e API-005 |
| `GET /breed/hound/images` | 200 e coleção de URLs | API-002 e API-005 |
| `GET /breeds/image/random` | 200 e uma URL | API-003 e API-005 |
| Raça inexistente | 404, `status=error`, `code=404` | API-004 |
| Content-Type | JSON nos endpoints verificados | API-006 |

## BlazeDemo

| Verificação | Resultado observado | Encaminhamento |
|---|---|---|
| Página inicial | Origem, destino e botão `Find Flights` | Assertion no JMeter |
| Pesquisa de voo | Exibe opções e campos ocultos do voo | Correlação por regex |
| Formulário de compra | Campos de passageiro e cartão disponíveis | Massa fictícia |
| Confirmação | Exibe `Thank you for your purchase today!` | Assertion final |

## Pontos de segurança básicos

- todos os alvos usam HTTPS;
- nenhum segredo ou token é necessário;
- a massa de cartão é fictícia;
- relatórios não devem registrar dados pessoais reais;
- performance deve ser iniciada manualmente e com autorização.

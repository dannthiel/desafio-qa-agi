# Validação técnica da entrega

## Verificações concluídas

| Verificação | Resultado |
|---|---|
| Leitura dos três módulos Maven | `BUILD SUCCESS` em `mvn validate` |
| Estrutura XML dos POMs | Válida |
| Estrutura XML dos JMX | Válida |
| Schemas JSON | Válidos |
| Workflows YAML | Válidos |
| Plano JMeter de carga em smoke controlado | 16 requisições, 0 erros |
| Plano JMeter de pico em smoke controlado | 25 requisições, 0 erros |
| Correlação de `flight`, `price` e `airline` | Validada |
| Assertions das quatro etapas da compra | Validadas |

O smoke controlado dos JMX utilizou respostas determinísticas equivalentes ao contrato HTML observado no BlazeDemo. Seu objetivo foi validar a integridade do script, a correlação e as assertions sem gerar carga no serviço público.

## Verificações pelo pipeline

O GitHub Actions executa os testes Web e API contra os ambientes públicos e conserva as evidências como artefatos. A performance permanece em workflow manual, com perfis `smoke` e `acceptance`, para impedir disparos involuntários.

## Pendente antes da submissão final

1. publicar o projeto em um repositório GitHub público;
2. confirmar que o workflow Web/API ficou verde;
3. executar os planos JMeter em ambiente autorizado e adequado;
4. anexar o HTML/JTL da execução;
5. substituir os campos pendentes de `relatorio-performance.md` pelos números reais;
6. concluir se o critério de 250 req/s e p90 menor que 2 segundos foi atendido.

# Matriz de riscos

Escala: probabilidade e impacto de 1 a 3. Exposição = probabilidade × impacto.

| ID | Área | Risco | Prob. | Impacto | Exposição | Prioridade | Tratamento |
|---|---|---|---:|---:|---:|---|---|
| R01 | Web | Busca válida não retornar artigos | 2 | 3 | 6 | Alta | WEB-001 |
| R02 | Web | Busca inexistente exibir conteúdo incompatível | 2 | 2 | 4 | Alta | WEB-002 |
| R03 | Web | Mudança de layout quebrar seletores | 3 | 2 | 6 | Alta | Page Object e seletores semânticos |
| R04 | Web | Conteúdo editorial mudar e tornar teste instável | 3 | 2 | 6 | Alta | Não fixar título de artigo |
| R05 | API | HTTP 200 com contrato inválido | 2 | 3 | 6 | Alta | Schemas e assertions de corpo |
| R06 | API | Lista de raças vazia | 1 | 3 | 3 | Média | API-001 |
| R07 | API | Imagens incompatíveis com a raça | 2 | 3 | 6 | Alta | API-002 |
| R08 | API | Erro de raça inexistente inconsistente | 2 | 2 | 4 | Alta | API-004 |
| R09 | Performance | Vazão inferior a 250 req/s | 3 | 3 | 9 | Crítica | LOAD-001 |
| R10 | Performance | p90 igual ou superior a 2 s | 3 | 3 | 9 | Crítica | Relatório JMeter |
| R11 | Performance | Pico elevar taxa de erros | 3 | 3 | 9 | Crítica | SPIKE-001 |
| R12 | Ambiente | Rede pública distorcer resultados | 3 | 3 | 9 | Crítica | Registrar data, local e limitações |
| R13 | Segurança | Carga involuntária contra terceiro | 2 | 3 | 6 | Alta | Workflow manual e autorização |

## Regra de priorização

- **Crítica:** exposição 9;
- **Alta:** exposição entre 4 e 6;
- **Média:** exposição até 3;
- **Baixa:** cenário de baixo impacto e baixa probabilidade, mantido como exploração manual.

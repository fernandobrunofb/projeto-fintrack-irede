# FinTrack

Sistema de controle de finanças pessoais em Java, com interface gráfica em JavaFX e dados salvos em SQLite. Projeto de estudo.

## Funcionalidades

- Cadastrar, editar e remover transações (entrada ou saída, avulsa ou mensal)
- Listar as transações numa tabela
- Relatório com total de entradas, total de saídas e saldo

## Tecnologias

Java 21, JavaFX 21, SQLite (JDBC), JUnit 5, Maven

## Como rodar

```bash
mvn javafx:run
```

No IntelliJ: aba Maven → Plugins → javafx → `javafx:run`.

O banco `fintrack.db` é criado automaticamente na primeira execução.

## Testes

```bash
mvn test
```

Os testes do DAO usam um banco SQLite em memória, então não mexem nos dados reais.

## Versões

- `v1.0-console`: versão inicial, via console
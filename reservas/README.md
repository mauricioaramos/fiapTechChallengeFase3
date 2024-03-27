## POST TECH FIAP
### SOLUÇÃO DO TEC CHALLENGE FASE 3

#### Foi desenvolvida uma API REST com os endpoints necessários para realização do fluxo de trabalho, salvando as informações em um banco de dados NOSQL(mongoDB). A estrutura do Document foi pensada de maneira a facilitar sua leitura e evitar a realização de JOIN.

#### A aplicação está dockerizada, utilizando docker-compose, foram criados dois contêineres, um para a API e outro para o BD, permitindo e facilitando sua escalabilidade, podendo ser facilmente implantada em um ambiente Cloud.

#### Durante o desenvolvimento, a dificuldade encontrada foi sobre como seria rodado os testes de integracao e como seria feito o acesso ao banco de dados. A solucao desenvolvida utiliza um container docker para subir um banco de dados Mongo DB e rodar os testes de forma mais isolada e quando os testes terminam o conteiner e encerrado e os dados sao apagados, sendo assim nao ha necessidade de se preocupar com volume de dados gerado pelos testes.


#### O projeto faz uso de clean architecture e está implementado pensando em isolar as regras de negócio e separar as responsabilidades do sistema, garantindo assim, sua fácil manutenção e evolução, seguindo conceitos como Responsabilidade única, Segregação por interface e Inversão de dependência.

#### Foram implementados teste unitarios, de integracao, de performance e nao funcionais


### Execução
executar o comando:
make docker-start

demais comandos disponiveis no arquivo Makefile

## Documentação
http://localhost:80/swagger-ui.htm

#### Código fonte
https://github.com/mauricioaramos/fiapTechChallengeFase3


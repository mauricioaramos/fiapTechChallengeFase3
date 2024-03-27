# POS TECH Fiap - Tech Challenge Fase3
## Desafio
Criar um sistema de Reserva e Avaliação de
Restaurantes.
Funcionalidades Básicas:
1. Cadastro de Restaurantes: Os restaurantes podem se
   cadastrar no sistema, fornecendo informações como
   nome, localização, tipo de cozinha, horários de
   funcionamento e capacidade.
2. Reserva de Mesas: Os usuários podem fazer reservas
   para datas e horários específicos.
3. Avaliações e Comentários: Após a visita, os usuários
   podem avaliar o restaurante e deixar comentários
   sobre sua experiência.
4. Busca de Restaurantes: Os usuários podem buscar
   restaurantes por nome, localização ou tipo de
   cozinha.

## Solucao
#### Foi utilizado na implementação da solução para o desafio do Tec Challenge, Spring boot, java 17, MongoDB, Docker, lombok, swagger, junit, gatlin, jacoco, .
#### Foi desenvolvida uma API REST com os endpoints necessários para realização do fluxo de trabalho, salvando as informações em um banco de dados NOSQL(mongoDB). A estrutura do Document foi pensada de maneira a facilitar sua leitura e evitar a realização de JOIN.
#### A aplicação está dockerizada, utilizando docker-compose, foram criados dois contêineres, um para a API e outro para o BD, permitindo e facilitando sua escalabilidade, podendo ser facilmente implantada em um ambiente Cloud.
#### Durante o desenvolvimento, surgiu uma dúvida de como seria a automatização do processo de monitoramento do tempo das vagas, que foi resolvido utilizando o Scheduled do Spring, para rodar um job que faz a varredura no banco procurando por reservas ativas.
#### Outra questão foi sobre a forma de pagamento Pix ou Cartão de débito ou crédito que foi resolvido fazendo uso do padrão de projeto Strategy.
O projeto faz uso do padrão MVC e está implementado pensando em isolar as regras de negócio e separar as responsabilidades do sistema, garantindo assim, sua fácil manutenção e evolução, seguindo conceitos como Responsabilidade única, Segregação por interface e Inversão de dependência.

## Execução
executar o comando:
make docker-start

demais comandos no arquivo Makefile

## Documentação
http://localhost:80/swagger-ui.html

## Repositório






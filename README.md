# SoftDesign - Avaliação Técnica

Projeto no Github: https://github.com/edsandrof/technical_evaluation_softdesign

Aplicação no Heroku: https://softdesign-edsandrof.herokuapp.com/swagger-ui.html

# Descrição do projeto
O projeto foi desenvolvido usando Java, Spring Boot, banco de dados mongodb, Swagger para documentação e gerenciamento 
de dependências com gradle.
Foram utilizados profiles para auxiliar a migração entre ambiente de desenvolvimento e produção;

Foi implementado: 
- as Tarefas Bônus 1 e 4;
- o cadastro de propostas para votação (chamadas aqui de proposal) com descrição, com as suas opções e data de criação;
- o cadastro de membros votantes, com nome e cpf;
- a listagem de membros cadastrados;
- a listagem de propostas cadastradas;
- a busca de uma proposta por id;
- a abertura de uma proposta para votação, podendo ser informado ou não o tempo que a mesma permanecerá aberta;
- o envio de votos para uma proposta;
- o total de votos de uma proposta;
- a API foi descrita com Swagger e possui versionamento na URL, como /api/v1/ (Tarefa bônus 4). Foi escolhido este formato por ser mais fácil 
para o desenvolvedor identificar e alterar a versão na chamada, evitando também a necessidade de inclusão da mesma no cabeçalho.   
 
Foram feitas validações como:
- se a proposta está aberta para votação ou se já foi encerrada;
- se um membro já votou na proposta; 
- se a opção de voto enviada pelo membro é válida dentro da proposta;  
- se um membro pode votar de acordo com validação do cpf (Tarefa bônus 1);
- entre outras;

# Testes
Foram implementados testes unitários, testando as camadas web e de serviço, e executados testes funcionais.

# Dificuldades encontradas
A descrição da atividade sem maiores detalhes acaba deixando o desenvolvimento mais subjetivo e passível de divergências
do que era esperado.

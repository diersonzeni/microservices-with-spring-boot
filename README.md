Este é um projeto simples de micro serviços utilizando Spring Boot, Service Discovery, WebFlux, Circuit Breaker com Resilience4J

O primeiro serviço que deve subir é o eureka-server, que será o service discovery.

Em seguida subir o micro serviço postal-service, responsável por retornar os endereços.

Em seguida subir o micro serviço user-service, que é um cadastro de usuários. Neste exemplo simples, um usuário armazena o id do endereço (que é fornecido pelo postal-service).

Chamadas REST de exemplo:

1- Para incluir um usuário:
  POST http://localhost:8080/users
  BODY:
  {
    "email": "aaaa@test.com",
    "name": "Usuário 1",
    "streetId": 1
  }
  
2- Para consultar os usuários:
  GET http://localhost:8080/users
Ao consultar o usuário, o user-service acessará o postal-service para buscar os dados do endereço.
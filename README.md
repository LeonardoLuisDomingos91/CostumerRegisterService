# Costumer Record Service
 Aplicação para persistir dados de clientes
 
## Stack
- Java 8
- Maven
- Spring Boot
- Spring Data
- Spring Security
- Autenticação com JWT

## Documentação

### Executando a aplicação
```
java -jar costumer-register-service.jar
ou execute a classe CustomerRegisterServiceApplication
```
### Login no sistema
Envie um POST para: (https://localhost:8080/login) colocando no payload alguma credencial listada abaixo

- Para logar-se como ADMIN, envie no payload os seguintes dados
```
{
    user: admin
    senha: admin
}
```
- Para logar-se como Gerente, envie no payload os seguintes dados
```
{
    user: gerente
    senha: gerente
}
```

- Para logar-se como funcionário, envie no payload os seguintes dados
```
{
    user: funcionario
    senha: funcionario
}
```
#### Modelo de respostas
- Caso o usuário e a senha estejam incorretos:
```
Status: 403 (Forbidden)
Body de resposta: vazio
```

- Caso o usuário e a senha estejam corretos:
```
Status: 200 (Ok)
Body de resposta: {
    token: "TOKENTESTE"
}
```

### Endpoints da API
##### Persistindo um novo cliente

- Todos os perfis de usuário podem acessar esse recurso
```
URI: https://localhost:8080/costumers
Método: POST
Authorization: token gerado no login
Payload: {
    name: 
    lastName:
    cpf:
    extraInformation:
}
```

##### Obtendo os clientes cadastrados

- Todos os perfis de usuário podem acessar esse recurso

```
URI: https://localhost:8080/costumers/{costumerId}
Método: GET
Authorization: token gerado no login
```

##### Atualizando dados de um cliente 

- Apenas os perfis ADMIN e GERENTE tem acesso a esse recurso

```
URI: https://localhost:8080/costumers/{costumerId}
Método: PUT
Authorization: token gerado no login
Payload: {
    name: 
    lastName:
    cpf:
    extraInformation:
}
```

##### Excluindo um cliente 

- Apenas o perfil ADMIN tem acesso a esse recurso

```
URI: https://localhost:8080/costumers/{costumerId}
Método: DELETE
Authorization: token gerado no login
```

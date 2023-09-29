# API de Gerenciamento de Pessoas

Esta é uma API de Gerenciamento de Pessoas desenvolvida usando Spring Boot. O projeto foi criado com o objetivo de fornecer funcionalidades essenciais para a gestão de informações pessoais. 
Este projeto adota os princípios da arquitetura REST e segue o **Nível 4 de Maturidade de Richardson**. Isso significa que a API fornece uma representação completa e interligada dos recursos, permitindo uma experiência de cliente mais flexível e autônoma.


**Arquitetura de Camadas**

O projeto é construído com uma arquitetura de camadas bem definidas:

- **Controller**: Responsável pela exposição dos endpoints da API e pela manipulação das requisições HTTP.
- **Service**: Lida com a lógica de negócios da aplicação, coordenando as operações e a lógica de validação.
- **Repository**: Responsável pelo acesso ao banco de dados H2, onde os dados das pessoas e endereços são armazenados.

## Documentação Postman
A documentação desta API está disponível no Postman, e pode ser encontrada neste [link](https://www.postman.com/mrdev-team-corp/workspace/publicworkspace/collection/13158450-59fdb5e2-dc1d-4ef6-93c8-a7c1c3ca3709?action=share&creator=13158450)

## Funcionalidades Implementadas

O projeto inclui as seguintes funcionalidades:

- **Cadastro de Pessoas**: Permite a criação de registros para indivíduos com informações como nome, data de nascimento e endereço.

- **Edição de Pessoas**: Oferece a possibilidade de atualizar informações de pessoas já cadastradas.

- **Consulta de Pessoas**: Permite a recuperação dos detalhes de uma pessoa específica com base em seu ID.

- **Listagem de Pessoas**: Fornece uma lista completa de todas as pessoas registradas no sistema.

- **Cadastro de Endereços para Pessoas**: Possibilita a associação de endereços a uma pessoa, incluindo campos como logradouro, CEP, número e cidade.

- **Listagem de Endereços de Pessoas**: Retorna uma lista de todos os endereços associados a uma pessoa específica.

- **Definição de Endereço Principal**: Permite marcar um endereço como o principal para uma pessoa.

## Detalhes das Informações de Pessoa

Cada registro de pessoa inclui os seguintes campos:

- **Nome**: O nome completo da pessoa.
- **Data de Nascimento**: A data de nascimento da pessoa.
- **Endereço(s)**: Um conjunto de informações que inclui logradouro, CEP, número e cidade.

## Implementações e Boas Práticas

- Todas as respostas da API são retornadas no formato JSON.
- Utilização do banco de dados H2 para armazenamento de dados.
- Inclusão de testes unitários para garantir a integridade do código.
- Adoção de princípios de Clean Code para manter o código limpo e de fácil manutenção.

## Conclusão

Este projeto foi meticulosamente desenvolvido, demonstrando domínio da linguagem Java e do ecossistema. Elevando-se ao Nível 4 de Maturidade de Richardson, nossa API REST oferece uma experiência sofisticada e autônoma, permitindo uma completa e interligada representação dos recursos.


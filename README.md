# Teste Desenvolvedor Backend Stoom

Projeto de testes de desenvolvedor Backend da Stoom. Neste `README` serão abordados as instruções de como rodar a
aplicação localmente com o docker.

## Docker-compose

Este projeto possui dois docker-compose. O `docker-compose.local.yaml` é para subir os recursos de forma local somente 
para a aplicação funcionar em tempo de desenvolvimento, neste caso, o banco de dados. Já o `docker-compose.yaml` é
focado em subir a aplicação por inteiro: desde o build da aplicação spring, até o banco de dados. Para subir basta
rodar o seguinte comando:

```
docker-compose up -d
```

Ou caso queira subir somente o banco de dados e rodar a aplicação direto da IDE

```
docker-compose -f docker-compose.local.yaml up -d
```

## Postman Collection

Esta aplicação também possui uma collection do postman para fins de teste. O arquivo 
`Teste stoom.postman_collection.json` possui todas os endpoints devidamente configurados para tal finalidade. Basta
importar esta coleção em seu cliente **Postman**.

A aplicação está disponível no endereço local `http://localhost:8080`.

## Informações adicionais

O endpoint de listagem de produtos `/api/products` teve seu comportamente modificado. O mesmo agora aceita queryParams
para filtragem de produtos. Exemplos:

Buscar produtos de uma determinada marca: `/api/products?brand={id}`

Buscar produtos de uma determinada categoria: `/api/products?category={id}`

Há endpoints para busca de produtos, conforme o enunciado:

Por categoria: `/api/categories/{id}/products`

Por marca: ``/api/brands/{id}/products``
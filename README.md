**Seja bem-vindo candidato!**

Como um desenvolvedor Back-End na Stoom uma das maiores responsabilidades que você vai ter é desenvolver funcionalidades e corrigir bugs em sistemas de e-commerce de larga escala que utilizam Spring Boot. Com base nisso, precisamos de sua ajuda para construir a nossa loja Stoom, que deve conter as seguintes funcionalidades:

1. Deve ser desenvolvida uma API de CRUD de produtos
2. Os produtos devem ser enriquecidos com as informações que você julgar relevante para o funcionamento em uma loja, algumas são obrigatórias:
    - Categorias
    - Marca
    - Preços
3. Deve existir um endpoint na API para a busca de produtos que será utilizada na loja
4. Deve existir um endpoint que lista os produtos de uma determinada Marca
5. Deve existir um endpoint que lista os produtos de uma determinada Categoria
6. Produtos podem ser inativados para não aparecerem na busca ou nas listagens sem a necessidade de serem deletados para poderem ser reativados posteriormente
7. Marcas e categorias também podem ser inativados para não aparecerem na loja

**Informações relevantes**:
- Atente-se à todos os pré-requisitos estabelecidos, porém não limite-se a eles, ideias novas ou melhorias são sempre bem-vindas :smiley:
- Você tem total liberdade para fazer qualquer tipo de alteração em qualquer ponto do código (contanto que não alterem a maneira de execução da aplicação)
- Se possível, adicione uma collection do Postman no repositório para conseguirmos testar o código da mesma forma que você
- Boas práticas, legibilidade, testes e performance são alguns dos pontos que serão considerados durante a avaliação

**Boa sorte!**

### O que foi implementado ##
**Endpoints da API**
**Produtos**

### Criar Produto ###
Endpoint: /api/products
HTTP Method: POST
Description: Create a new product
Responses:
201 Created: Produto criado com sucesso.
400 Bad Request: Detalhes do produto inválido

### Ativar Produto ###

Endpoint: /api/products/activate/{productId}
HTTP Method: PUT
Description: Activate a product
Parameters: productId (integer)
Responses:
204 No Content: Produto ativado com sucesso.
404 Not Found: Produto não encontrado.

### Desativar Produto ###

Endpoint: /api/products/deactivate/{productId}
HTTP Method: PUT
Description: Deactivate a product
Parameters: productId (integer)
Responses:
204 No Content: Produto desativado com sucesso.
404 Not Found: Produto não encontrado.


### Exportar Produtos para PDF ###

Endpoint: /api/products/export/pdf
HTTP Method: GET
Parameters: true or false (boolean)
Description: Export all products to a PDF file
Responses:
200 OK: Produtos exportados com sucesso.
500 Internal Server Error: Erro ao exportar produtos.

### Exportar Produtos para Excel ###

Endpoint: /api/products/export/excel
HTTP Method: GET
Description: Export all products to an Excel file
Parameters: true or false (boolean)
Responses:
200 OK: Produtos exportados com sucesso.
500 Internal Server Error: Erro ao exportar produtos.

### Buscar Produtos por Categoria ###

Endpoint: /api/products/category/{categoryName}
HTTP Method: GET
Description: Get products by category (paginated)
Parameters: categoryName (string)
Responses:
200 OK: Produtos encontrados com sucesso.
404 Not Found: Categoria não encontrada.

### Buscar Produtos por Marca ### 

Endpoint: /api/products/brand/{brandName}
HTTP Method: GET
Description: Get products by brand (paginated)
Parameters: brandName (string)
Responses:
200 OK: Produtos encontrados com sucesso.
404 Not Found: Marca não encontrada.

### Buscar Todos os Produtos Ativos ###

Endpoint: /api/products/active
HTTP Method: GET
Description: Get all active products (paginated)
Query Parameter: status
Responses:
200 OK: Lista de todos os produtos.

### Buscar Todos os Produtos ###
Endpoint: /api/products
HTTP Method: GET
Description: Get all products (paginated)
Responses:
200: Success

### Categorias ###

**Ativar Categoria**

Endpoint: /api/categories/{categoryId}/activate
HTTP Method: PATCH
Parameters: categoryId (integer)
Responses:
204 No Content: Categoria ativada com sucesso.
404 Not Found: Categoria não encontrada.

### Desativar Categoria ###

Endpoint: /api/categories/{categoryId}/deactivate
HTTP Method: PATCH
Parameters: categoryId (integer)
Responses:
204 No Content: Categoria desativada com sucesso.
404 Not Found: Categoria não encontrada.
Marcas
### Ativar Marca ###

Endpoint:/api/brands/{brandId}/activate
Parameters: brandId (integer)
HTTP Method: PATCH
Responses:
204 No Content: Marca ativada com sucesso.
404 Not Found: Marca não encontrada.

### Desativar Marca ###

Endpoint:/api/brands/{brandId}/deactivate
Parameters: brandId (integer)
HTTP Method: PATCH
Responses:
204 No Content: Marca desativada com sucesso.
404 Not Found: Marca não encontrada.

<p> Entre as funcionalidades implementadas, estão endpoints para exportar as informações de produto para Excel e PDF. Achei que seria interessante, já que pode ser impresso para melhorar na tomada de decisão sobre quais produtos ativar e desativar. Devido ao tempo curto, fiz somente para os produtos, mas deixei de uma forma de fácil mudança e replicação para categorias e marcas caso necessário.

Também utilizei o Swagger para documentar a API e facilitar os testes. Foram implementados testes unitários para a prevenção de erros. Acima, foram detalhados os endpoints implementados. Utilizei também o Flyway, uma biblioteca para facilitar o versionamento do banco de dados.

Além disso, utilizei o ModelMapper para transformar objetos, garantindo que os dados sejam corretamente mapeados entre as entidades e os DTOs, o que melhora a manutenção e clareza do código. <p>




# TreinamentoAutomcaoAPI
Get

Tarefa: Verificar se a API está online

•	O status code retornado é 201 Created, mas como nada é criado o retorno deveria ser 200 OK.

Tarefa: Visualizar erro de servidor 500 Internal Server Error quando enviar filtro mal formatado

•	Quando há um parâmetro mal formato em firstname ou lastname ele retorna a lista com todas as reservas e o status code 200 OK. Quando há um parâmetro mal formatado em checkin ou checkout o status code retornado é 500 Internal Server Error, mas como ambos são um erro do usuário o status code retornado deveria ser 400 Bad Request.

Tarefa: Validar erro na pesquisa com filtro inválido

•	O status code retornado é 200 OK, porém como é uma requisição errada do usuário, seria mais correto o status code 400 Bad Request.

Tarefa: Validar erro na pesquisa com filtro firstname mal formatado

•	O status code retornado é 200 OK, porém como é uma requisição errada do usuário, seria mais correto o status code 400 Bad Request.

Tarefa: Validar retorno da pesquisa utilizando o parâmetro checkin

•	O retorno é´200 OK porém a reserva procurada não consta na lista pois o retorno do checkin é somente datas maiores do que a informada, na documentação indica que ele deveria buscar datas iguais ou maiores.

Tarefa: Validar o retorno da pesquisa utilizando os parâmetros primeiro nome, último sobrenome, data de checkin e data de checkout

•	O teste retorna 200 OK, porém está errado pois o parâmetro checkin está buscando somente datas maiores do que a informada, na documentação indica que ele deveria buscar datas iguais ou maiores.

Tarefa: Validar o retorno da pesquisa utilizando o parâmetro checkout duas vezes

•	O status code retornado é 500 Internal Server Error, mas por ser uma requisição errada do usuário, se encaixaria melhor o status code 400 Bad Request. Como a documentação 
informa que a busca por checkout retorna datas maiores ou iguais a informada, caso necessário, poderia ser implementado a busca somente pela menor data informada.

Delete

Tarefa: Validar erro na exclusão ao utilizar ID inválido

•	O status code retornado é 405 Method Not Allowed, mas como a requisição é por um ID que não existe o status code retornado deveria ser 404 not found.

Tarefa: Validar exclusão de reserva do booking

•	O status code retornado é 201 Created, porém nessa situação se encaixaria melhor o status code 200 OK, já que nada é criado.

Tarefa: Validar exclusão da reserva no booking com parâmetro Basic Auth

•	O status code retornado é 201 Created, porém nessa situação se encaixaria melhor o status code 200 OK, já que nada é criado.

Post

Tarefa: Criar uma reserva com mais parâmetros

•	O status code retornado é 200 OK, mas cria a reserva somente com os parâmetros definidos na documentação, os parâmetros adicionais são ignorados.

Tarefa: Criar uma reserva com Header inválido

•	O status code retornado, e o pedido na documentação, é o 418 I’m a teapot, porém deveria retornar 400 Bad Request por ser uma requisição errada.

Tarefa: Validar erro ao criar reserva com payload inválido

•		O status code retornado é 500 Internal Server Error, porém se tratando de um erro do usuário o mais correto seria o retorno 400 Bad Request.

Put

Tarefa: Alterar uma reserva inexistente

•	O status code retornado é 405 Method Not Allowed, mas por tratar de algo que não existe, deveria retornar 404 not found.

Observações

•	Na documentação da API está escrito Authorisation, o que está errado, o correto é Authorization.

•	Como mencionado anteriormente o filtro checkin está retornando somente datas maiores do que a informada e consta na documentação que o retorno deveria apresentar datas maiores ou iguais.

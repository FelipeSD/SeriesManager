# SeriesManager

O projeto consiste no desenvolvimento de um aplicativo simples de lista com uso de persistência. O aplicativo permite criar, apagar e gerenciar séries, 
temporadas e seus episódios. 
O aplicativo deve possuir uma opção para inserção de novas séries que vão aparecer em lista na tela principal. 
Na tela principal deve haver opções para que sejam adicionadas novas, para visualizar detalhes (isto é, temporadas cadastradas) e para apagar séries cadastradas. 
Cada série deve possuir um nome (que deve ser único), ano de lançamento, e uma emissora e um gênero que pode ser romance, aventura, terror etc. 
Não é obrigatório, mas seria apreciado se o usuário também pudesse cadastrar e remover outros gêneros para categorizar as suas séries.

Clicando na célula correspondente a série na tela principal será possível ver a lista de temporadas (outra tela). Para cada série será possível cadastrar e gerenciar temporadas.
Cada temporada deve ter um número sequencial, ano de lançamento, e quantidade de episódios.

Para cada temporada é possível visualizar (outra tela) a lista de episódios. Será possível cadastrar os episódios que compõem a temporada. 
Cada episódio deve um número sequencial, um nome, um tempo de duração (em minutos), uma flag (para indicar se o episódio já foi assistido ou não). 
Clicando num episódio específico o usuário poderá gerenciá-lo (editá-lo ou marcá-lo como já assistido).

Deve-se manter observância aos conceitos de desenvolvimento de um banco de dados. A persistência deve ser implementada usando SQLite 
e o uso de uma arquitetura de software (mesmo que MVC) é indicado.

A entrega final consiste na entrega do projeto e de um vídeo mostrando o aplicativo em uso, narrado e demonstrando as principais funcionalidades (no máximo 5 minutos).
A entrega do deverá ser feita por compartilhamento de repositório no GitHub mantendo nome de projeto e repositório. O vídeo deverá ser entregue junto do repositório. 

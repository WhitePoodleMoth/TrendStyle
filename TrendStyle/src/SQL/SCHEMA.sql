DELIMITER //

CREATE PROCEDURE registrarCliente(
    IN p_usuario VARCHAR(255),
    IN p_senha VARCHAR(255),
    IN p_cpf VARCHAR(255),
    IN p_nome VARCHAR(255),
    IN p_sobrenome VARCHAR(255),
    IN p_email VARCHAR(255),
    IN p_telefone VARCHAR(255),
    IN p_cep VARCHAR(255),
    IN p_rua VARCHAR(255),
    IN p_numero VARCHAR(255),
    IN p_cidade VARCHAR(255),
    IN p_estado VARCHAR(255)
)
BEGIN
    INSERT INTO CLIENTE (saldo, usuario, senha, CPF, nome, sobrenome, email, telefone, CEP, rua, numero, cidade, estado)
    VALUES (0, p_usuario, p_senha, p_cpf, p_nome, p_sobrenome, p_email, p_telefone, p_cep, p_rua, p_numero, p_cidade, p_estado);
    SELECT LAST_INSERT_ID() AS 'ID';
END //

CREATE PROCEDURE atualizarCliente(
    IN p_id INTEGER,
    IN p_usuario VARCHAR(255),
    IN p_cpf VARCHAR(255),
    IN p_nome VARCHAR(255),
    IN p_sobrenome VARCHAR(255),
    IN p_email VARCHAR(255),
    IN p_telefone VARCHAR(255),
    IN p_CEP VARCHAR(255),
    IN p_rua VARCHAR(255),
    IN p_numero VARCHAR(255),
    IN p_cidade VARCHAR(255),
    IN p_estado VARCHAR(255)
)
BEGIN
    UPDATE CLIENTE
    SET usuario = p_usuario,
        CPF = p_cpf,
        nome = p_nome,
        sobrenome = p_sobrenome,
        email = p_email,
        telefone = p_telefone,
        CEP = p_CEP,
        rua = p_rua,
        numero = p_numero,
        cidade = p_cidade,
        estado = p_estado
    WHERE ID = p_id;
END //

CREATE PROCEDURE atualizarCadastroCliente(
    IN p_id INTEGER,
    IN p_email VARCHAR(255),
    IN p_telefone VARCHAR(255),
    IN p_CEP VARCHAR(255),
    IN p_rua VARCHAR(255),
    IN p_numero VARCHAR(255),
    IN p_cidade VARCHAR(255),
    IN p_estado VARCHAR(255)
)
BEGIN
    UPDATE CLIENTE
    SET email = p_email,
        telefone = p_telefone,
        CEP = p_CEP,
        rua = p_rua,
        numero = p_numero,
        cidade = p_cidade,
        estado = p_estado
    WHERE ID = p_id;
END //

CREATE PROCEDURE atualizarSenhaCliente(
    IN p_id INTEGER,
    IN p_senha VARCHAR(255)
)
BEGIN
    UPDATE CLIENTE
    SET senha = p_senha
    WHERE ID = p_id;
END //

CREATE PROCEDURE apagarCliente(
    IN p_id INTEGER
)
BEGIN
    -- Deletar registros relacionados em CARRINHO_PRODUTO
    DELETE cp
    FROM CARRINHO_PRODUTO cp
    INNER JOIN CARRINHO c ON cp.id_carrinho = c.ID
    WHERE c.id_cliente = p_id;

    -- Deletar registros relacionados em CARRINHO
    DELETE FROM CARRINHO WHERE id_cliente = p_id;

    -- Deletar registros relacionados em DEPOSITO
    DELETE FROM DEPOSITO WHERE id_cliente = p_id;

    -- Deletar registros relacionados em PEDIDO_PRODUTO
    DELETE pp
    FROM PEDIDO_PRODUTO pp
    INNER JOIN PEDIDO p ON pp.id_pedido = p.ID
    WHERE p.id_cliente = p_id;

    -- Deletar registros relacionados em PEDIDO
    DELETE FROM PEDIDO WHERE id_cliente = p_id;

    -- Deletar o cliente
    DELETE FROM CLIENTE WHERE ID = p_id;
END //

CREATE PROCEDURE atualizarCarrinho(IN p_cliente_id INTEGER)
BEGIN
    DECLARE finished INTEGER DEFAULT 0;
    DECLARE v_produto_id INTEGER;
    DECLARE v_quantidade_no_carrinho INTEGER;
    DECLARE v_estoque_disponivel INTEGER;
    
    -- Declarar cursor para itens no carrinho
    DECLARE carrinho_cursor CURSOR FOR 
        SELECT id_produto, quantidade 
        FROM CARRINHO_PRODUTO
        WHERE id_carrinho = (SELECT ID FROM CARRINHO WHERE id_cliente = p_cliente_id);

    -- Declarar manipulador para não encontrar linhas
    DECLARE CONTINUE HANDLER 
        FOR NOT FOUND SET finished = 1;
    
    OPEN carrinho_cursor;

    get_carrinho: LOOP
        FETCH carrinho_cursor INTO v_produto_id, v_quantidade_no_carrinho;

        IF finished = 1 THEN 
            LEAVE get_carrinho;
        END IF;

        -- Obter quantidade em estoque
        SELECT estoque INTO v_estoque_disponivel FROM PRODUTO WHERE ID = v_produto_id;

        -- Verificar se a quantidade no carrinho é maior que o estoque disponível
        IF v_quantidade_no_carrinho > v_estoque_disponivel THEN
            -- Se for maior, atualizar a quantidade no carrinho para ser igual ao estoque disponível
            UPDATE CARRINHO_PRODUTO
            SET quantidade = v_estoque_disponivel
            WHERE id_carrinho = (SELECT ID FROM CARRINHO WHERE id_cliente = p_cliente_id) AND id_produto = v_produto_id;
        END IF;
    END LOOP get_carrinho;
    
    CLOSE carrinho_cursor;
END //

CREATE PROCEDURE adicionarProdutoNoCarrinho(IN p_cliente_id INTEGER, IN p_produto_id INTEGER, IN p_quantidade INTEGER)
BEGIN
    DECLARE v_carrinho_id INTEGER;
    DECLARE v_quantidade_no_carrinho INTEGER;
    DECLARE v_estoque_disponivel INTEGER;

    -- Verificando se o carrinho já existe para o cliente
    SELECT ID INTO v_carrinho_id FROM CARRINHO WHERE id_cliente = p_cliente_id;

    -- Se o carrinho não existir, cria um
    IF v_carrinho_id IS NULL THEN
        INSERT INTO CARRINHO (id_cliente) VALUES (p_cliente_id);
        SET v_carrinho_id = LAST_INSERT_ID();
    END IF;

    -- Verificando a quantidade do produto no carrinho
    SELECT IFNULL(quantidade, 0) INTO v_quantidade_no_carrinho
    FROM CARRINHO_PRODUTO
    WHERE id_carrinho = v_carrinho_id AND id_produto = p_produto_id;

    -- Verificando a quantidade disponível no estoque
    SELECT estoque INTO v_estoque_disponivel FROM PRODUTO WHERE ID = p_produto_id;

    -- Verificando se a quantidade total no carrinho não excede o estoque disponível
    IF v_quantidade_no_carrinho + p_quantidade > v_estoque_disponivel THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Quantidade excede o estoque disponível';
    ELSE
        -- Verificando se o produto já existe no carrinho
        IF EXISTS (SELECT 1 FROM CARRINHO_PRODUTO WHERE id_carrinho = v_carrinho_id AND id_produto = p_produto_id) THEN
            -- Se existir, aumenta a quantidade
            UPDATE CARRINHO_PRODUTO
            SET quantidade = quantidade + p_quantidade
            WHERE id_carrinho = v_carrinho_id AND id_produto = p_produto_id;
        ELSE
            -- Se não existir, adiciona o produto ao carrinho
            INSERT INTO CARRINHO_PRODUTO (quantidade, id_carrinho, id_produto)
            VALUES (p_quantidade, v_carrinho_id, p_produto_id);
        END IF;
    END IF;

END //

CREATE PROCEDURE alterarQuantidadeProdutoNoCarrinho(IN p_cliente_id INTEGER, IN p_produto_id INTEGER, IN p_nova_quantidade INTEGER)
BEGIN
    DECLARE v_carrinho_id INTEGER;
    DECLARE v_estoque_disponivel INTEGER;

    -- Verificando se o carrinho já existe para o cliente
    SELECT ID INTO v_carrinho_id FROM CARRINHO WHERE id_cliente = p_cliente_id;

    -- Verificando a quantidade disponível no estoque
    SELECT estoque INTO v_estoque_disponivel FROM PRODUTO WHERE ID = p_produto_id;

    -- Verificando se a nova quantidade não excede o estoque disponível
    IF p_nova_quantidade > v_estoque_disponivel THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Quantidade excede o estoque disponível';
    ELSE
        -- Verificando se o produto já existe no carrinho
        IF EXISTS (SELECT 1 FROM CARRINHO_PRODUTO WHERE id_carrinho = v_carrinho_id AND id_produto = p_produto_id) THEN
            -- Se existir, altera a quantidade para a nova quantidade
            UPDATE CARRINHO_PRODUTO
            SET quantidade = p_nova_quantidade
            WHERE id_carrinho = v_carrinho_id AND id_produto = p_produto_id;
        ELSE
            -- Se não existir, adiciona o produto ao carrinho com a nova quantidade
            INSERT INTO CARRINHO_PRODUTO (quantidade, id_carrinho, id_produto)
            VALUES (p_nova_quantidade, v_carrinho_id, p_produto_id);
        END IF;
    END IF;
END //

CREATE PROCEDURE removerProdutoDoCarrinho(IN p_cliente_id INT, IN p_produto_id INT)
BEGIN
    DELETE FROM CARRINHO_PRODUTO
    WHERE id_carrinho = (SELECT ID FROM CARRINHO WHERE id_cliente = p_cliente_id) AND id_produto = p_produto_id;
END //

CREATE PROCEDURE apagarCarrinho(IN p_cliente_id INTEGER)
BEGIN
    -- Deletar produtos do carrinho
    DELETE FROM CARRINHO_PRODUTO
    WHERE id_carrinho = (SELECT ID FROM CARRINHO WHERE id_cliente = p_cliente_id);

    -- Deletar o carrinho
    DELETE FROM CARRINHO WHERE id_cliente = p_cliente_id;
END //

CREATE PROCEDURE realizarPedido(IN p_cliente_id INTEGER)
BEGIN
    DECLARE v_carrinho_id INTEGER;
    DECLARE v_valor_total FLOAT;
    DECLARE v_saldo_cliente FLOAT;

    -- Obtendo o ID do carrinho do cliente
    SELECT ID INTO v_carrinho_id FROM CARRINHO WHERE id_cliente = p_cliente_id;
    
    -- Calculando o valor total dos produtos no carrinho
    SELECT SUM(PRODUTO.valor * CARRINHO_PRODUTO.quantidade) INTO v_valor_total
    FROM PRODUTO
    INNER JOIN CARRINHO_PRODUTO ON PRODUTO.ID = CARRINHO_PRODUTO.id_produto
    WHERE CARRINHO_PRODUTO.id_carrinho = v_carrinho_id;

    -- Obtendo o saldo do cliente
    SELECT saldo INTO v_saldo_cliente FROM CLIENTE WHERE ID = p_cliente_id;

    -- Verificando se a quantidade de produtos no carrinho é menor ou igual ao estoque
    IF EXISTS (SELECT 1 FROM CARRINHO_PRODUTO 
               INNER JOIN PRODUTO ON CARRINHO_PRODUTO.id_produto = PRODUTO.ID 
               WHERE CARRINHO_PRODUTO.id_carrinho = v_carrinho_id AND CARRINHO_PRODUTO.quantidade > PRODUTO.estoque) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Quantidade de produtos maior do que disponível em estoque';
    ELSEIF v_saldo_cliente < v_valor_total THEN
        -- Verificando se o saldo do cliente é suficiente
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Saldo insuficiente para realizar o pedido';
    ELSE
        -- Criando o pedido
        INSERT INTO PEDIDO (valor, data_criacao, entregue, id_cliente)
        VALUES (v_valor_total, NOW(), 0, p_cliente_id);

        -- Obtendo o ID do pedido criado
        SET @v_pedido_id = LAST_INSERT_ID();

        -- Adicionando produtos ao pedido
        INSERT INTO PEDIDO_PRODUTO (quantidade, id_pedido, id_produto)
        SELECT quantidade, @v_pedido_id, id_produto
        FROM CARRINHO_PRODUTO
        WHERE id_carrinho = v_carrinho_id;

        -- Reduzindo a quantidade em estoque de cada produto
        UPDATE PRODUTO
        INNER JOIN CARRINHO_PRODUTO ON PRODUTO.ID = CARRINHO_PRODUTO.id_produto
        SET PRODUTO.estoque = PRODUTO.estoque - CARRINHO_PRODUTO.quantidade
        WHERE CARRINHO_PRODUTO.id_carrinho = v_carrinho_id;

        -- Atualizando o saldo do cliente
        UPDATE CLIENTE SET saldo = saldo - v_valor_total WHERE ID = p_cliente_id;

        -- Excluindo os produtos do carrinho
        DELETE FROM CARRINHO_PRODUTO WHERE id_carrinho = v_carrinho_id;

        -- Excluindo o carrinho
        DELETE FROM CARRINHO WHERE ID = v_carrinho_id;
    END IF;
END //

CREATE PROCEDURE realizarDeposito(IN p_cliente_id INT, IN p_valor FLOAT)
BEGIN
    -- Verificar a existência do cliente
    IF NOT EXISTS (SELECT 1 FROM CLIENTE WHERE ID = p_cliente_id) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cliente não encontrado';
    ELSE
        -- Inserir o registro de depósito
        INSERT INTO DEPOSITO (valor, data_criacao, id_cliente)
        VALUES (p_valor, CURDATE(), p_cliente_id);

        -- Atualizar o saldo do cliente
        UPDATE CLIENTE
        SET saldo = saldo + p_valor
        WHERE ID = p_cliente_id;
    END IF;
END //

CREATE PROCEDURE registrarAdmin(
    IN p_usuario VARCHAR(255),
    IN p_senha VARCHAR(255),
    IN p_cpf VARCHAR(255),
    IN p_nome VARCHAR(255),
    IN p_sobrenome VARCHAR(255),
    IN p_email VARCHAR(255),
    IN p_telefone VARCHAR(255)
)
BEGIN
    INSERT INTO ADMINISTRADOR (usuario, senha, CPF, nome, sobrenome, email, telefone)
    VALUES (p_usuario, p_senha, p_cpf, p_nome, p_sobrenome, p_email, p_telefone);
    SELECT LAST_INSERT_ID() AS 'ID';
END //

CREATE PROCEDURE atualizarAdmin(
    IN p_id INTEGER,
    IN p_usuario VARCHAR(255),
    IN p_CPF VARCHAR(255),
    IN p_nome VARCHAR(255),
    IN p_sobrenome VARCHAR(255),
    IN p_email VARCHAR(255),
    IN p_telefone VARCHAR(255)
)
BEGIN
    UPDATE ADMINISTRADOR
    SET usuario = p_usuario,
        CPF = p_CPF,
        nome = p_nome,
        sobrenome = p_sobrenome,
        email = p_email,
        telefone = p_telefone
    WHERE ID = p_id;
END //

CREATE PROCEDURE atualizarSenhaAdmin(
    IN p_id INTEGER,
    IN p_senha VARCHAR(255)
)
BEGIN
    UPDATE ADMINISTRADOR
    SET senha = p_senha
    WHERE ID = p_id;
END //

CREATE PROCEDURE apagarAdmin(
    IN p_id INTEGER
)
BEGIN
    DELETE FROM ADMINISTRADOR WHERE ID = p_id;
END //

CREATE PROCEDURE registrarFornecedor(
    IN p_CNPJ VARCHAR(255),
    IN p_razaoSocial VARCHAR(255),
    IN p_nomeFantasia VARCHAR(255),
    IN p_email VARCHAR(255),
    IN p_telefone VARCHAR(255),
    IN p_CEP VARCHAR(255),
    IN p_rua VARCHAR(255),
    IN p_numero VARCHAR(255),
    IN p_cidade VARCHAR(255),
    IN p_estado VARCHAR(255)
)
BEGIN
    INSERT INTO FORNECEDOR (CNPJ, razaoSocial, nomeFantasia, email, telefone, CEP, rua, numero, cidade, estado)
    VALUES (p_CNPJ, p_razaoSocial, p_nomeFantasia, p_email, p_telefone, p_CEP, p_rua, p_numero, p_cidade, p_estado);
END //

CREATE PROCEDURE atualizarFornecedor(
    IN p_id INTEGER,
    IN p_CNPJ VARCHAR(255),
    IN p_razaoSocial VARCHAR(255),
    IN p_nomeFantasia VARCHAR(255),
    IN p_email VARCHAR(255),
    IN p_telefone VARCHAR(255),
    IN p_CEP VARCHAR(255),
    IN p_rua VARCHAR(255),
    IN p_numero VARCHAR(255),
    IN p_cidade VARCHAR(255),
    IN p_estado VARCHAR(255)
)
BEGIN
    UPDATE FORNECEDOR
    SET CNPJ = p_CNPJ,
        razaoSocial = p_razaoSocial,
        nomeFantasia = p_nomeFantasia,
        email = p_email,
        telefone = p_telefone,
        CEP = p_CEP,
        rua = p_rua,
        numero = p_numero,
        cidade = p_cidade,
        estado = p_estado
    WHERE ID = p_id;
END //

CREATE PROCEDURE apagarFornecedor(
    IN p_id INTEGER
)
BEGIN
    -- Definir o fornecedor como NULL nos produtos relacionados
    UPDATE PRODUTO
    SET id_fornecedor = NULL
    WHERE id_fornecedor = p_id;

    -- Remover o fornecedor
    DELETE FROM FORNECEDOR WHERE ID = p_id;
END //

CREATE PROCEDURE registrarTipoProduto(
    IN p_nome VARCHAR(255),
    IN p_descricao VARCHAR(255)
)
BEGIN
    INSERT INTO PRODUTO_TIPO (nome, descricao)
    VALUES (p_nome, p_descricao);
END //

CREATE PROCEDURE alterarTipoProduto(
    IN p_id INT,
    IN p_nome VARCHAR(255),
    IN p_descricao VARCHAR(255)
)
BEGIN
    UPDATE PRODUTO_TIPO
    SET nome = p_nome,
        descricao = p_descricao
    WHERE ID = p_id;
END //

CREATE PROCEDURE excluirTipoProduto(
    IN p_id INT
)
BEGIN
    DELETE FROM PRODUTO_TIPO
    WHERE ID = p_id;
END //


CREATE PROCEDURE registrarProduto(
    IN p_nome VARCHAR(255),
    IN p_descricao VARCHAR(255),
    IN p_imagem_url VARCHAR(255),
    IN p_valor FLOAT,
    IN p_estoque INTEGER,
    IN p_creation DATE,
    IN p_id_fornecedor INTEGER,
    IN p_id_produto_tipo INTEGER
)
BEGIN
    INSERT INTO PRODUTO (nome, descricao, imagem_url, valor, estoque, creation, id_fornecedor, id_produto_tipo)
    VALUES (p_nome, p_descricao, p_imagem_url, p_valor, p_estoque, p_creation, p_id_fornecedor, p_id_produto_tipo);
END //

CREATE PROCEDURE atualizarProduto(
    IN p_id INTEGER,
    IN p_nome VARCHAR(255),
    IN p_descricao VARCHAR(255),
    IN p_imagem_url VARCHAR(255),
    IN p_valor FLOAT,
    IN p_estoque INTEGER,
    IN p_creation DATE,
    IN p_id_fornecedor INTEGER,
    IN p_id_produto_tipo INTEGER
)
BEGIN
    UPDATE PRODUTO
    SET nome = p_nome,
        descricao = p_descricao,
        imagem_url = p_imagem_url,
        valor = p_valor,
        estoque = p_estoque,
        creation = p_creation,
        id_fornecedor = p_id_fornecedor,
        id_produto_tipo = p_id_produto_tipo
    WHERE ID = p_id;
END //

CREATE PROCEDURE apagarProduto(
    IN p_id INTEGER
)
BEGIN
    -- Excluir produtos do carrinho que possuem o produto
    DELETE FROM CARRINHO_PRODUTO
    WHERE id_produto = p_id;

    -- Excluir produtos dos pedidos que possuem o produto
    DELETE FROM PEDIDO_PRODUTO
    WHERE id_produto = p_id;

    -- Excluir o produto
    DELETE FROM PRODUTO
    WHERE ID = p_id;
END //

DELIMITER ;

CREATE VIEW adminData AS
SELECT a.ID, CONCAT(a.nome, ' ', a.sobrenome) AS nome, a.CPF AS cpf, a.email, COALESCE(SUM(p.valor), 0) AS vendas
FROM ADMINISTRADOR a
LEFT JOIN CLIENTE c ON a.ID = c.ID
LEFT JOIN PEDIDO pe ON c.ID = pe.id_cliente
LEFT JOIN PEDIDO_PRODUTO pp ON pe.ID = pp.id_pedido
LEFT JOIN PRODUTO p ON pp.id_produto = p.ID
GROUP BY a.ID;

CREATE VIEW adminDataPainel AS
SELECT
    COALESCE((SELECT SUM(PRODUTO.estoque) FROM PRODUTO), 0) AS produtos,
    (SELECT COUNT(PEDIDO.ID) FROM PEDIDO WHERE PEDIDO.entregue = FALSE OR PEDIDO.entregue IS NULL) AS pedidos,
    COALESCE((SELECT SUM(PEDIDO.valor) FROM PEDIDO), 0) AS vendas,
    (SELECT COUNT(DISTINCT(PEDIDO.id_cliente)) FROM PEDIDO) AS clientes,
    (SELECT COUNT(*) FROM ADMINISTRADOR) AS administradores;

CREATE VIEW clienteData AS
SELECT ID, CONCAT(nome, ' ', sobrenome) AS nome, CPF as cpf, email, saldo
FROM CLIENTE;

CREATE VIEW clienteDataPainel AS
SELECT 
    CLIENTE.ID,
    CONCAT(CLIENTE.nome, ' ', CLIENTE.sobrenome) AS nome,
    CLIENTE.CPF AS cpf,
    CLIENTE.saldo AS saldo,
    COUNT(PEDIDO.ID) AS pedidos
FROM CLIENTE 
LEFT JOIN PEDIDO ON CLIENTE.ID = PEDIDO.id_cliente
GROUP BY CLIENTE.ID;

CREATE VIEW todosDepositos AS
SELECT d.ID AS IDDeposito, CONCAT(c.nome, ' ', c.sobrenome) AS nomeCliente, c.CPF AS cpfCliente, d.valor AS valorDeposito, d.data_criacao AS dataDeposito
FROM CLIENTE c
INNER JOIN DEPOSITO d ON c.ID = d.id_cliente;

CREATE VIEW depositosCliente AS
SELECT c.ID AS id_cliente, d.ID AS ID, CONCAT(c.nome, ' ', c.sobrenome) AS nome, c.CPF AS cpf, d.valor AS valor, d.data_criacao AS data_deposito
FROM CLIENTE c
INNER JOIN DEPOSITO d ON c.ID = d.id_cliente;


CREATE VIEW visualizarCarrinho AS
SELECT
    PRODUTO.ID AS id_produto,
    PRODUTO.nome AS nome,
    PRODUTO.valor AS valor,
    CARRINHO_PRODUTO.quantidade,
    (PRODUTO.valor * CARRINHO_PRODUTO.quantidade) AS total,
    CARRINHO.id_cliente
FROM
    CARRINHO_PRODUTO
    INNER JOIN PRODUTO ON CARRINHO_PRODUTO.id_produto = PRODUTO.ID
    INNER JOIN CARRINHO ON CARRINHO_PRODUTO.id_carrinho = CARRINHO.ID;

CREATE VIEW resumoCarrinho AS
SELECT
    id_cliente,
    SUM(cp.quantidade) AS quantidadeTotal,
    SUM(p.valor * cp.quantidade) AS valorTotal
FROM
    CARRINHO_PRODUTO cp
    INNER JOIN CARRINHO c ON cp.id_carrinho = c.ID
    INNER JOIN PRODUTO p ON cp.id_produto = p.ID
GROUP BY
    c.id_cliente;
    
CREATE VIEW visualizarPedidosPorCliente AS
SELECT p.ID as id_pedido,
       c.ID AS id_cliente,
       CONCAT(c.nome, ' ', c.sobrenome) AS nome,
       c.CPF AS cpf,
       SUM(pp.quantidade) AS volumes,
       SUM(pp.quantidade * pr.valor) AS valor,
       p.data_criacao AS data,
       p.entregue AS status
FROM PEDIDO p
JOIN CLIENTE c ON p.id_cliente = c.ID
JOIN PEDIDO_PRODUTO pp ON p.ID = pp.id_pedido
JOIN PRODUTO pr ON pp.id_produto = pr.ID
GROUP BY p.ID, c.ID
ORDER BY c.ID, p.ID;

CREATE VIEW visualizarPedido AS
SELECT p.ID AS id_pedido,
       pr.ID AS id_produto,
       CONCAT(pr.nome, ' (', pt.nome, ')') AS nome,
       f.nomeFantasia AS fabricante,
       pr.valor AS valorProduto,
       pp.quantidade AS quantidade,
       (pr.valor * pp.quantidade) AS valorTotal,
       pr.imagem_url AS imagemProduto
FROM PEDIDO p
JOIN PEDIDO_PRODUTO pp ON p.ID = pp.id_pedido
JOIN PRODUTO pr ON pp.id_produto = pr.ID
JOIN FORNECEDOR f ON pr.id_fornecedor = f.ID
JOIN PRODUTO_TIPO pt ON pr.id_produto_tipo = pt.ID
ORDER BY p.ID, pr.ID;
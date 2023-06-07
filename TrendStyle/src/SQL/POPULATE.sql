SET SQL_SAFE_UPDATES = 0;

CALL registrarAdmin('lena',     'cc967443070ab409a57a455dc8c2405a10b6ba96ca75f87bcf18ca368bb090a8', '93687284026', 'Lena',     'Smith',     'lena.smith@gmail.com',     '11987654321');
CALL registrarAdmin('kane',     'f57dbb83bc838bda3c0cf7d6091e9a3c16f824cef7ebef8faad554bf5ebc7ad4', '94069198008', 'Kane',     'Johnson',   'kane.johnson@gmail.com',   '21987654322');
CALL registrarAdmin('ventress', '0dd0cbbd6983a0d726786afc1e5927db466599a759c34914f03cbecbc9cd43e7', '77066964055', 'Ventress', 'Davis',     'ventress.davis@gmail.com', '31987654321');
CALL registrarAdmin('josie',    'd118e41faea4e000917a87021e4945508af18168985cb7d9dce1bba3ba390e43', '24402259088', 'Josie',    'Radek',     'josie.radek@gmail.com',    '41987654321');
CALL registrarAdmin('anya',     '33939d07e25f54e6432ad3b382e8d3d9e68522b6c3ef868f5c00410308fb6805', '16002565035', 'Anya',     'Thorensen', 'anya.thorensen@gmail.com', '51987654321');
    
CALL atualizarAdmin(1, 'admin', '93687284026', 'Lena', 'Smith', 'lena.smith@gmail.com', '11987654321');
CALL atualizarSenhaAdmin(1, '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918');
CALL apagarAdmin(5);

CALL registrarFornecedor('000000000000', 'Random Temporary',         'Random Temporary', 'invalid@gmail.com',      '00000000000', '00000000', 'Rua Gucci',     '000', 'São Paulo', 'SP');
CALL registrarFornecedor('169536700001', 'Versace S.p.A',            'Versace',          'versace@gmail.com',      '31987654321', '80020180', 'Via Versace',   '456', 'São Paulo', 'SP');
CALL registrarFornecedor('149592930001', 'Dolce & Gabbana S.r.l.',   'Dolce & Gabbana',  'dolcegabbana@gmail.com', '51987654321', '30140010', 'Via D&G',       '789', 'São Paulo', 'SP');
CALL registrarFornecedor('438120070001', 'Chanel S.A.',              'Chanel',           'chanel@gmail.com',       '61987654321', '88010400', 'Rue de Chanel', '321', 'São Paulo', 'SP');
CALL registrarFornecedor('136332710001', 'Prada S.p.A.',             'Prada',            'prada@gmail.com',        '19987654330', '40100160', 'Via Prada',     '654', 'São Paulo', 'SP');

CALL atualizarFornecedor(1, '083389860001', 'Gucci International', 'Gucci', 'gucci@gmail.com', '11987654321', '01020000', 'Rua Gucci', '123', 'São Paulo', 'SP');
CALL apagarFornecedor(4);

CALL registrarCliente('lena',     'cc967443070ab409a57a455dc8c2405a10b6ba96ca75f87bcf18ca368bb090a8', '93687284026', 'Lena',     'Smith',      'temporary.invalid@gmail.com',    '00000000000', '00000000', '',                              '',          '',         '');
CALL registrarCliente('kane',     'f57dbb83bc838bda3c0cf7d6091e9a3c16f824cef7ebef8faad554bf5ebc7ad4', '94069198008', 'Kane',     'Johnson',    'kane.johnson@gmail.com',         '21987654322', '40100160', 'Rua Conselheiro Pedro Luiz', '173',  'Salvador',       'BA');
CALL registrarCliente('ventress', '0dd0cbbd6983a0d726786afc1e5927db466599a759c34914f03cbecbc9cd43e7', '77066964055', 'Ventress', 'Davis',      'ventress.davis@gmail.com',       '31987654321', '69005140', 'Rua Miranda Leão',           '825',  'Manaus',         'AM');
CALL registrarCliente('josie',    'd118e41faea4e000917a87021e4945508af18168985cb7d9dce1bba3ba390e43', '24402259088', 'Josie',    'Radek',      'josie.radek@gmail.com',          '41987654321', '88010400', 'Rua Felipe Schmidt',         '303',  'Florianópolis',  'SC');
CALL registrarCliente('anya',     '33939d07e25f54e6432ad3b382e8d3d9e68522b6c3ef868f5c00410308fb6805', '16002565035', 'Anya',     'Thorensen',  'anya.thorensen@gmail.com',       '51987654321', '70040907', 'Esplanada dos Ministérios',  '4',    'Brasília',       'DF');
CALL registrarCliente('cassie',   'a3441069e422a14127a80e5f3323cffb5972097b39353462337c951006879bb9', '51516554000', 'Cassie',   'Sheppard',   'cassie.sheppard@gmail.com',      '61987654321', '80020180', 'Rua XV de Novembro',         '1299', 'Curitiba',       'PR');
CALL registrarCliente('lomax',    '001934c3d6ff6e7faa27053a4cc996ae1600233073b90d2fb06ad6916329c578', '15494800067', 'Lomax',    'Bennett',    'lomax.bennett@gmail.com',        '71987654321', '90540000', 'Avenida Carlos Gomes',       '222',  'Porto Alegre',   'RS');
CALL registrarCliente('danforth', 'b1b4c4675fd107c42cfe6fba286de44f7485e6c278150b63223af5ad5db16857', '78971897082', 'Danforth', 'Cunningham', 'danforth.cunningham@gmail.com',  '81987654321', '20021000', 'Avenida Rio Branco',         '1',    'Rio de Janeiro', 'RJ');
CALL registrarCliente('sheppard', 'b0c32913418c131845c6e0308c2929d4ba7c270289cc14615033590b204e21dd', '44614521070', 'Sheppard', 'Beck',       'sheppard.beck@gmail.com',        '91987654329', '30140010', 'Rua da Bahia',               '1148', 'Belo Horizonte', 'MG');
CALL registrarCliente('meyer',    'd4a83e68937b72940d9ac63bf86cb9db9710087dba05f406feafe598e1eae7d5', '01986842070', 'Meyer',    'Gilbert',    'meyer.gilbert@gmail.com',        '19987654330', '50020000', 'Rua do Bom Jesus',           '197',  'Recife',         'PE');

CALL atualizarCliente(1,'client', '93687284026', 'Lena', 'Smith', 'lena.smith@gmail.com', '11987654321', '01020000', 'Rua Direita', '200', 'São Paulo', 'SP');
CALL atualizarSenhaCliente(1, '948fe603f61dc036b5c596dc09fe3ce3f3d30dc90f024c85f3c82db2ccab679d');
CALL atualizarCadastroCliente(1, 'client@gmail.com', '11987654321', '01020000', 'Rua Direita', '200', 'São Paulo', 'SP');
CALL apagarCliente(10);

CALL registrarTipoProduto('Tenis',          'Calçado esportivo para diversas atividades físicas');
CALL registrarTipoProduto('Mochila',        'Bolsa com alças para transporte de objetos pessoais');
CALL registrarTipoProduto('Pulseira',       'Acessório de moda para o pulso');
CALL registrarTipoProduto('Cinto',          'Faixa de couro ou tecido para ajustar roupas');
CALL registrarTipoProduto('Bolsa',          '');
CALL registrarTipoProduto('Nova Categoria', '');

CALL alterarTipoProduto(5, 'Bolsa', 'Acessório de moda versátil para transportar itens pessoais com estilo e praticidade');
CALL excluirTipoProduto(6);

CALL registrarProduto('GG low-top sneakers',                '', 'https://cdn-images.farfetch-contents.com/20/27/32/42/20273242_50259070_1000.jpg', 5670.00,  50, '2023-01-05', 1, 1);
CALL registrarProduto('Mochila Jumbo GG de couro',          '', 'https://cdn-images.farfetch-contents.com/20/25/83/00/20258300_50203391_1000.jpg', 22890.00, 15, '2023-02-10', 1, 2);
CALL registrarProduto('Pulseira com logo Medusa',           '', 'https://cdn-images.farfetch-contents.com/12/97/27/68/12972768_13620002_1000.jpg', 4414.00,  10, '2023-03-15', 2, 3);
CALL registrarProduto('Tênis Portofino com patch de logo',  '', 'https://cdn-images.farfetch-contents.com/20/22/93/76/20229376_50226707_1000.jpg', 4500.00,  25, '2023-04-20', 3, 1);
CALL registrarProduto('Cinto de couro Saffiano dupla face', '', 'https://cdn-images.farfetch-contents.com/19/99/58/27/19995827_44934096_1000.jpg', 4700.00,  5 , '2023-05-25', 5, 4);
CALL registrarProduto('Temporario',                         '', '',                                                                                   0.00,  1 , '2023-05-30', 1, 1);
CALL registrarProduto('Produto Invalido',                   '', '',                                                                                   0.00,  1 , '2023-05-31', 1, 1);

CALL atualizarProduto(5, 'Nome', 'Descricao', 'Imagem', 100.00, 50, '2023-05-31', 1, 1);
CALL apagarProduto(6);

CALL realizarDeposito(1,45000);
CALL realizarDeposito(1,40000);

CALL adicionarProdutoNoCarrinho(1, 4, 5);
CALL adicionarProdutoNoCarrinho(1, 5, 1);
CALL alterarQuantidadeProdutoNoCarrinho(1,4,10);
CALL removerProdutoDoCarrinho(1,5);

CALL atualizarCarrinho(1);
CALL realizarPedido(1);

CALL adicionarProdutoNoCarrinho(1, 1, 1);
CALL adicionarProdutoNoCarrinho(1, 2, 1);
CALL adicionarProdutoNoCarrinho(1, 3, 1);
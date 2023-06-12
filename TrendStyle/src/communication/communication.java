/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package communication;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Unknown Account
 */
public class communication {
    MySQL mysql = new MySQL();
    
    public boolean registerAdmin(String username, String password, String cpf, String nome, String sobrenome, String email, String telefone) {
        mysql.conectaBanco();

        String consulta = "CALL registrarAdmin('" + username + "', '" + password + "', '" + cpf + "', '" + nome + "', '" + sobrenome + "', '" + email + "', '" + telefone + "')";

        mysql.executarSQL(consulta);

        boolean success = (mysql.getResultSet() != null);

        mysql.fechaBanco();

        return success;
    }
    
    public int checkAdminLogin(String username, String password) {
        mysql.conectaBanco();

        String consulta = "SELECT * FROM ADMINISTRADOR WHERE usuario = '" + username + "' AND senha = '" + password + "'";
        
        mysql.executarSQL(consulta);

        ResultSet resultSet = mysql.getResultSet();

        try {
            if (resultSet.next()) {
                int userId = resultSet.getInt("ID");
                mysql.fechaBanco();
                return userId;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mysql.fechaBanco();
        }
        return 0;
    }
    
    public boolean registerVendor(String cnpj, String nomeFantasia, String razaoSocial, String email, String telefone, String cep, String rua, String numero, String cidade, String estado) {
        mysql.conectaBanco();

        String consulta = "CALL registrarFornecedor('" + cnpj + "', '" + nomeFantasia + "', '" + razaoSocial + "', '" + email + "', '" + telefone + "', '" + cep + "', '" + rua + "', '" + numero + "', '" + cidade + "', '" + estado + "')";

        mysql.executarSQL(consulta);

        boolean success = (mysql.getResultSet() != null);

        mysql.fechaBanco();

        return success;
    }

    public boolean updateVendor(int id, String cnpj, String nomeFantasia, String razaoSocial, String email, String telefone, String cep, String rua, String numero, String cidade, String estado) {
        mysql.conectaBanco();

        String consulta = "CALL atualizarFornecedor(" + id + ", '" + cnpj + "', '" + nomeFantasia + "', '" + razaoSocial + "', '" + email + "', '" + telefone + "', '" + cep + "', '" + rua + "', '" + numero + "', '" + cidade + "', '" + estado + "')";

        mysql.executarSQL(consulta);

        boolean success = (mysql.getResultSet() != null);

        mysql.fechaBanco();

        return success;
    }
    
    public boolean deleteVendor(int id) {
        mysql.conectaBanco();
        
        String consulta = "CALL apagarFornecedor('" + id + "')";

        mysql.executarSQL(consulta);

        boolean success = (mysql.getResultSet() != null);

        mysql.fechaBanco();

        return success;
    }
    
    public ArrayList<ArrayList<?>> collectVendors() {
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<String> cnpjs = new ArrayList<>();
        ArrayList<String> razoes = new ArrayList<>();
        ArrayList<String> fantasias = new ArrayList<>();
        ArrayList<String> emails = new ArrayList<>();
        ArrayList<String> telefones = new ArrayList<>();
        ArrayList<String> ceps = new ArrayList<>();
        ArrayList<String> ruas = new ArrayList<>();
        ArrayList<String> numeros = new ArrayList<>();
        ArrayList<String> cidades = new ArrayList<>();
        ArrayList<String> estados = new ArrayList<>();

        try {
            mysql.conectaBanco();

            String consulta = "SELECT * FROM FORNECEDOR";

            mysql.executarSQL(consulta);

            ResultSet resultSet = mysql.getResultSet();

            while (resultSet.next()) {
                try {
                    ids.add(resultSet.getString("id"));
                    cnpjs.add(resultSet.getString("CNPJ"));
                    razoes.add(resultSet.getString("razaoSocial"));
                    fantasias.add(resultSet.getString("nomeFantasia"));
                    emails.add(resultSet.getString("email"));
                    telefones.add(resultSet.getString("telefone"));
                    ceps.add(resultSet.getString("CEP"));
                    ruas.add(resultSet.getString("rua"));
                    numeros.add(resultSet.getString("numero"));
                    cidades.add(resultSet.getString("cidade"));
                    estados.add(resultSet.getString("estado"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mysql.fechaBanco();
        }

        ArrayList<ArrayList<?>> vendorsData = new ArrayList<>();
        vendorsData.add(ids);
        vendorsData.add(cnpjs);
        vendorsData.add(razoes);
        vendorsData.add(fantasias);
        vendorsData.add(emails);
        vendorsData.add(telefones);
        vendorsData.add(ceps);
        vendorsData.add(ruas);
        vendorsData.add(numeros);
        vendorsData.add(cidades);
        vendorsData.add(estados);

        return vendorsData;
    }
    
    public ArrayList collectVendorData(int id) {
        ArrayList vendorData = new ArrayList<>();
        try {
            mysql.conectaBanco();

            String consulta = "SELECT * FROM FORNECEDOR WHERE ID = "+id;
            
            mysql.executarSQL(consulta);
            
            ResultSet resultSet = mysql.getResultSet();

            if (resultSet.next()) {
                try {
                    vendorData.add(resultSet.getString("nomeFantasia"));
                    vendorData.add(resultSet.getString("razaoSocial"));
                    vendorData.add(resultSet.getString("CNPJ"));
                    vendorData.add(resultSet.getString("email"));
                    vendorData.add(resultSet.getString("telefone"));
                    vendorData.add(resultSet.getString("estado"));
                    vendorData.add(resultSet.getString("CEP"));
                    vendorData.add(resultSet.getString("cidade"));
                    vendorData.add(resultSet.getString("rua"));
                    vendorData.add(resultSet.getString("numero"));
                } catch (Exception ex) {}
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mysql.fechaBanco();
        }
        return vendorData;
    }
    
    public ArrayList<ArrayList<?>> collectCategories() {
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<String> nomes = new ArrayList<>();
        ArrayList<String> descricoes = new ArrayList<>();

        try {
            mysql.conectaBanco();

            String consulta = "SELECT * FROM PRODUTO_TIPO";

            mysql.executarSQL(consulta);

            ResultSet resultSet = mysql.getResultSet();

            while (resultSet.next()) {
                try {
                    ids.add(resultSet.getString("id"));
                    nomes.add(resultSet.getString("nome"));
                    descricoes.add(resultSet.getString("descricao"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mysql.fechaBanco();
        }

        ArrayList<ArrayList<?>> categoriesData = new ArrayList<>();
        categoriesData.add(ids);
        categoriesData.add(nomes);
        categoriesData.add(descricoes);

        return categoriesData;
    }
    
    public ArrayList<ArrayList<?>> collectProducts() {
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<String> nomes = new ArrayList<>();
        ArrayList<String> descricoes = new ArrayList<>();
        ArrayList<String> imagens = new ArrayList<>();
        ArrayList<String> valores = new ArrayList<>();
        ArrayList<String> estoques = new ArrayList<>();
        ArrayList<String> criacoes = new ArrayList<>();
        ArrayList<String> categorias = new ArrayList<>();
        ArrayList<String> fornecedores = new ArrayList<>();
        ArrayList<String> categoriasID = new ArrayList<>();
        ArrayList<String> fornecedoresID = new ArrayList<>();
        

        try {
            mysql.conectaBanco();

            String consulta = "SELECT * FROM produtosAdmin";

            mysql.executarSQL(consulta);

            ResultSet resultSet = mysql.getResultSet();

            while (resultSet.next()) {
                try {
                    ids.add(resultSet.getString("id"));
                    nomes.add(resultSet.getString("nome"));
                    descricoes.add(resultSet.getString("descricao"));
                    imagens.add(resultSet.getString("imagem"));
                    valores.add(resultSet.getString("valor"));
                    estoques.add(resultSet.getString("estoque"));
                    criacoes.add(resultSet.getString("creation"));
                    categorias.add(resultSet.getString("categoria"));
                    fornecedores.add(resultSet.getString("fornecedor"));
                    categoriasID.add(resultSet.getString("categoriaID"));
                    fornecedoresID.add(resultSet.getString("fornecedorID"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mysql.fechaBanco();
        }

        ArrayList<ArrayList<?>> productsData = new ArrayList<>();
        productsData.add(ids);
        productsData.add(nomes);
        productsData.add(descricoes);
        productsData.add(imagens);
        productsData.add(valores);
        productsData.add(estoques);
        productsData.add(criacoes);
        productsData.add(categorias);
        productsData.add(fornecedores);
        productsData.add(categoriasID);
        productsData.add(fornecedoresID);

        return productsData;
    }
    
    public ArrayList<ArrayList<?>> collectOrders() {
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<String> nomes = new ArrayList<>();
        ArrayList<String> cpfs = new ArrayList<>();
        ArrayList<String> enderecos = new ArrayList<>();
        ArrayList<String> valores = new ArrayList<>();
        ArrayList<String> volumess = new ArrayList<>();
        ArrayList<String> entregas = new ArrayList<>();

        try {
            mysql.conectaBanco();

            String consulta = "SELECT * FROM pedidosAdmin";

            mysql.executarSQL(consulta);

            ResultSet resultSet = mysql.getResultSet();

            while (resultSet.next()) {
                try {
                    ids.add(resultSet.getString("id"));
                    nomes.add(resultSet.getString("nome"));
                    cpfs.add(resultSet.getString("CPF"));
                    enderecos.add(resultSet.getString("endereco"));
                    valores.add(resultSet.getString("valor"));
                    volumess.add(resultSet.getString("volumes"));
                    entregas.add(resultSet.getString("entrega"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mysql.fechaBanco();
        }

        ArrayList<ArrayList<?>> ordersData = new ArrayList<>();
        ordersData.add(ids);
        ordersData.add(nomes);
        ordersData.add(cpfs);
        ordersData.add(enderecos);
        ordersData.add(valores);
        ordersData.add(volumess);
        ordersData.add(entregas);

        return ordersData;
    }
    
    public ArrayList<ArrayList<?>> collectAdmins() {
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<String> usuarios = new ArrayList<>();
        ArrayList<String> cpfs = new ArrayList<>();
        ArrayList<String> nomes = new ArrayList<>();
        ArrayList<String> emails = new ArrayList<>();
        ArrayList<String> telefones = new ArrayList<>();

        try {
            mysql.conectaBanco();

            String consulta = "SELECT * FROM administradoresAdmin";

            mysql.executarSQL(consulta);

            ResultSet resultSet = mysql.getResultSet();

            while (resultSet.next()) {
                try {
                    ids.add(resultSet.getString("id"));
                    usuarios.add(resultSet.getString("usuario"));
                    cpfs.add(resultSet.getString("CPF"));
                    nomes.add(resultSet.getString("nome"));
                    emails.add(resultSet.getString("email"));
                    telefones.add(resultSet.getString("telefone"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mysql.fechaBanco();
        }

        ArrayList<ArrayList<?>> adminsData = new ArrayList<>();
        adminsData.add(ids);
        adminsData.add(usuarios);
        adminsData.add(cpfs);
        adminsData.add(nomes);
        adminsData.add(emails);
        adminsData.add(telefones);

        return adminsData;
    }
    
    public boolean registerClient(String username, String password, String cpf, String nome, String sobrenome, String email, String telefone, String cep, String rua, String numero, String cidade, String estado) {
        mysql.conectaBanco();

        String consulta = "CALL registrarCliente('" + username + "', '" + password + "', '" + cpf + "', '" + nome + "', '" + sobrenome + "', '" + email + "', '" + telefone + "', '" + cep + "', '" + rua + "', '" + numero + "', '" + cidade + "', '" + estado + "')";

        mysql.executarSQL(consulta);

        boolean success = (mysql.getResultSet() != null);

        mysql.fechaBanco();

        return success;
    }
    
    public int checkClientLogin(String username, String password) {
        mysql.conectaBanco();

        String consulta = "SELECT * FROM CLIENTE WHERE usuario = '" + username + "' AND senha = '" + password + "'";
        
        mysql.executarSQL(consulta);

        ResultSet resultSet = mysql.getResultSet();

        try {
            if (resultSet.next()) {
                int userId = resultSet.getInt("ID");
                mysql.fechaBanco();
                return userId;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mysql.fechaBanco();
        }
        return 0;
    }
    
    public ArrayList colletClientDetails(int id) {
        ArrayList<String> userInfo = new ArrayList<>();
        
        mysql.conectaBanco();

        String consulta = "SELECT * FROM CLIENTE WHERE id = '" + id + "'";
        
        mysql.executarSQL(consulta);

        ResultSet resultSet = mysql.getResultSet();

        try {
            if (resultSet.next()) {
                userInfo.add(resultSet.getString("nome"));
                userInfo.add(resultSet.getString("sobrenome"));
                userInfo.add(resultSet.getString("CPF"));
                userInfo.add(resultSet.getString("usuario"));
                
                userInfo.add(resultSet.getString("email"));
                userInfo.add(resultSet.getString("telefone"));
                userInfo.add(resultSet.getString("CEP"));
                userInfo.add(resultSet.getString("estado"));
                userInfo.add(resultSet.getString("cidade"));
                userInfo.add(resultSet.getString("rua"));
                userInfo.add(resultSet.getString("numero"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mysql.fechaBanco();
        }
        return userInfo;
    }
    
    public boolean updateClientDetails(int id, String email, String celular, String CEP, String estate, String city, String address, String addressNumber) {
        mysql.conectaBanco();
            
        String consulta = "CALL atualizarCadastroCliente('" + id + "', '" + email +"', '" + celular +"', '" + CEP +"', '" + address +"', '" + addressNumber +"', '" + city +"', '" + estate +"')";

        mysql.executarSQL(consulta);

        boolean success = (mysql.getResultSet() != null);

        mysql.fechaBanco();

        return success;
    }
    
    public boolean updateClientPassword(int id, String password) {
        mysql.conectaBanco();
        
        String consulta = "CALL atualizarSenhaCliente('" + id + "', '" + password +"')";

        mysql.executarSQL(consulta);

        boolean success = (mysql.getResultSet() != null);

        mysql.fechaBanco();

        return success;
    }
    
    public boolean deleteClient(int id) {
        mysql.conectaBanco();
        
        String consulta = "CALL apagarCliente('" + id + "')";

        mysql.executarSQL(consulta);

        boolean success = (mysql.getResultSet() != null);

        mysql.fechaBanco();

        return success;
    }
    
    public ArrayList collectClientData(int id) {
        ArrayList clientData = new ArrayList<>();
        try {
            mysql.conectaBanco();

            String consulta = "SELECT * FROM clienteData WHERE ID = "+id;
            
            mysql.executarSQL(consulta);
            
            ResultSet resultSet = mysql.getResultSet();

            if (resultSet.next()) {
                try {
                    clientData.add(resultSet.getString("nome"));
                    clientData.add(resultSet.getString("cpf"));
                    clientData.add(resultSet.getString("email"));
                    clientData.add(resultSet.getDouble("saldo"));
                } catch (Exception ex) {
                    clientData = new ArrayList<>();
                    clientData.add("Nome");
                    clientData.add("CPF");
                    clientData.add("Email");
                    clientData.add("0");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mysql.fechaBanco();
        }
        return clientData;
    }
    
    public ArrayList<ArrayList<?>> collectClientDeposits(int id) {
        ArrayList<Integer> idClientes = new ArrayList<>();
        ArrayList<Integer> IDs = new ArrayList<>();
        ArrayList<String> nomes = new ArrayList<>();
        ArrayList<String> cpfs = new ArrayList<>();
        ArrayList<Double> valores = new ArrayList<>();
        ArrayList<String> datas = new ArrayList<>();

        try {
            mysql.conectaBanco();

            String consulta = "SELECT * FROM depositosCliente WHERE id_cliente = " + id;

            mysql.executarSQL(consulta);

            ResultSet resultSet = mysql.getResultSet();

            while (resultSet.next()) {
                try {
                    idClientes.add(resultSet.getInt("id_cliente"));
                    IDs.add(resultSet.getInt("ID"));
                    nomes.add(resultSet.getString("nome"));
                    cpfs.add(resultSet.getString("cpf"));
                    valores.add(resultSet.getDouble("valor"));
                    datas.add(resultSet.getTimestamp("data_deposito").toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mysql.fechaBanco();
        }

        ArrayList<ArrayList<?>> depositsData = new ArrayList<>();
        depositsData.add(idClientes);
        depositsData.add(IDs);
        depositsData.add(nomes);
        depositsData.add(cpfs);
        depositsData.add(valores);
        depositsData.add(datas);

        return depositsData;
    }
    
    public boolean makeDeposit(int id, double value) {
        mysql.conectaBanco();

        String consulta = "CALL realizarDeposito('" + id + "', '" + value + "')";

        mysql.executarSQL(consulta);

        boolean success = (mysql.getResultSet() != null);

        mysql.fechaBanco();

        return success;
    }
    
    public ArrayList<ArrayList<?>> collectShoppingProducts() {
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<String> nomes = new ArrayList<>();
        ArrayList<String> descricoes = new ArrayList<>();
        ArrayList<String> imagens = new ArrayList<>();
        ArrayList<String> fornecedores  = new ArrayList<>();
        ArrayList<String> categorias = new ArrayList<>();
        ArrayList<Integer> estoques = new ArrayList<>();
        ArrayList<Double> valores = new ArrayList<>();
        
        try {
            mysql.conectaBanco();

            String consulta = "SELECT * FROM produtosLoja";

            mysql.executarSQL(consulta);

            ResultSet resultSet = mysql.getResultSet();

            while (resultSet.next()) {
                try {
                    ids.add(resultSet.getInt("id"));
                    nomes.add(resultSet.getString("nome"));
                    descricoes.add(resultSet.getString("descricao"));
                    imagens.add(resultSet.getString("imagem"));
                    fornecedores.add(resultSet.getString("fornecedor"));
                    categorias.add(resultSet.getString("categoria"));
                    estoques.add(resultSet.getInt("estoque"));
                    valores.add(resultSet.getDouble("valor"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mysql.fechaBanco();
        }
        
        ArrayList<ArrayList<?>> shopData = new ArrayList<>();
        shopData.add(ids);
        shopData.add(nomes);
        shopData.add(descricoes);
        shopData.add(imagens);
        shopData.add(fornecedores);
        shopData.add(categorias);
        shopData.add(estoques);
        shopData.add(valores);

        return shopData;
    }
    
    public ArrayList<ArrayList<?>> collectShoppingCartItems(int id) {
        ArrayList<Integer> idClientes = new ArrayList<>();
        ArrayList<Integer> idProdutos = new ArrayList<>();
        ArrayList<String> nomes = new ArrayList<>();
        ArrayList<Double> valores = new ArrayList<>();
        ArrayList<Integer> quantidades = new ArrayList<>();
        ArrayList<Double> totais = new ArrayList<>();
        ArrayList<String> imagens = new ArrayList<>();

        try {
            mysql.conectaBanco();

            String consulta = "SELECT * FROM visualizarCarrinho WHERE id_cliente = " + id;

            mysql.executarSQL(consulta);

            ResultSet resultSet = mysql.getResultSet();

            while (resultSet.next()) {
                try {
                    idClientes.add(resultSet.getInt("id_cliente"));
                    idProdutos.add(resultSet.getInt("id_produto"));
                    nomes.add(resultSet.getString("nome"));
                    valores.add(resultSet.getDouble("valor"));
                    quantidades.add(resultSet.getInt("quantidade"));
                    totais.add(resultSet.getDouble("total"));
                    imagens.add(resultSet.getString("imagem"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mysql.fechaBanco();
        }

        ArrayList<ArrayList<?>> cartData = new ArrayList<>();
        cartData.add(idClientes);
        cartData.add(idProdutos);
        cartData.add(nomes);
        cartData.add(valores);
        cartData.add(quantidades);
        cartData.add(totais);
        cartData.add(imagens);

        return cartData;
    }
    
    public ArrayList collectCartSummary(int id) {
        ArrayList cartSummary = new ArrayList<>();
        try {
            mysql.conectaBanco();

            String consulta = "SELECT * FROM resumoCarrinho WHERE id_cliente = "+id;

            mysql.executarSQL(consulta);

            ResultSet resultSet = mysql.getResultSet();

            if (resultSet.next()) {
                try {
                    cartSummary.add(resultSet.getInt("quantidadeTotal"));
                    cartSummary.add(resultSet.getDouble("valorTotal"));
                } catch (Exception ex) {
                    cartSummary = new ArrayList<>();
                    cartSummary.add("0");
                    cartSummary.add("0.0");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mysql.fechaBanco();
        }
        return cartSummary;
    }
    
    public boolean addCartProduct(int id, int id_product, int amount) {
        mysql.conectaBanco();

        if (amount<=0) {
            return false;
        }
        
        String consulta = "CALL adicionarProdutoNoCarrinho('" + id + "', '" + id_product + "', '" + amount + "')";

        mysql.executarSQL(consulta);

        boolean success = (mysql.getResultSet() != null);

        mysql.fechaBanco();

        return success;
    }
    
    public boolean updateCartProduct(int id, int id_product, int new_amount) {
        mysql.conectaBanco();
        
        String consulta = "";
        if (new_amount>0) {
            consulta = "CALL alterarQuantidadeProdutoNoCarrinho('" + id + "', '" + id_product + "', '" + new_amount + "')";
        } else {
            consulta = "CALL removerProdutoDoCarrinho('" + id + "', '" + id_product + "')";
        }

        mysql.executarSQL(consulta);

        boolean success = (mysql.getResultSet() != null);

        mysql.fechaBanco();

        return success;
    }
    
    public boolean deleteCart(int id) {
        mysql.conectaBanco();

        String consulta = "CALL apagarCarrinho('" + id + "')";

        mysql.executarSQL(consulta);

        boolean success = (mysql.getResultSet() != null);

        mysql.fechaBanco();

        return success;
    }
    
    private boolean updateCart(int id) {
        mysql.conectaBanco();

        String consulta = "CALL atualizarCarrinho('" + id + "')";

        mysql.executarSQL(consulta);

        boolean success = (mysql.getResultSet() != null);

        mysql.fechaBanco();

        return success;
    }
    
    public boolean makeOrder(int id) {
        if (updateCart(id)) {
            mysql.conectaBanco();
        
            String consulta = "CALL realizarPedido('" + id + "')";

            mysql.executarSQL(consulta);

            boolean success = (mysql.getResultSet() != null);

            mysql.fechaBanco();

            return success;
        } else {
            return false;
        }
    }
    
    public ArrayList<ArrayList<?>> collectClientOrder(int id) {
        ArrayList<Integer> idPedidos = new ArrayList<>();
        ArrayList<Integer> idClientes = new ArrayList<>();
        ArrayList<String> nomes = new ArrayList<>();
        ArrayList<String> cpfs = new ArrayList<>();
        ArrayList<Integer> volumes = new ArrayList<>();
        ArrayList<Double> valores = new ArrayList<>();
        ArrayList<String> datas = new ArrayList<>();
        ArrayList<Boolean> status = new ArrayList<>();

        try {
            mysql.conectaBanco();

            String consulta = "SELECT * FROM visualizarPedidosPorCliente WHERE id_cliente = " + id;

            mysql.executarSQL(consulta);

            ResultSet resultSet = mysql.getResultSet();

            while (resultSet.next()) {
                try {
                    idPedidos.add(resultSet.getInt("id_pedido"));
                    idClientes.add(resultSet.getInt("id_cliente"));
                    nomes.add(resultSet.getString("nome"));
                    cpfs.add(resultSet.getString("cpf"));
                    volumes.add(resultSet.getInt("volumes"));
                    valores.add(resultSet.getDouble("valor"));
                    datas.add(resultSet.getString("data"));
                    status.add(resultSet.getBoolean("status"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mysql.fechaBanco();
        }

        ArrayList<ArrayList<?>> cartData = new ArrayList<>();
        cartData.add(idPedidos);
        cartData.add(idClientes);
        cartData.add(nomes);
        cartData.add(cpfs);
        cartData.add(volumes);
        cartData.add(valores);
        cartData.add(datas);
        cartData.add(status);
        

        return cartData;
    }
    
    public ArrayList<ArrayList<?>> collectSingleOrder(int idPedido) {
        ArrayList<Integer> idPedidos = new ArrayList<>();
        ArrayList<Integer> idProdutos = new ArrayList<>();
        ArrayList<String> nomes = new ArrayList<>();
        ArrayList<String> fabricantes = new ArrayList<>();
        ArrayList<Double> valoresProduto = new ArrayList<>();
        ArrayList<Integer> quantidades = new ArrayList<>();
        ArrayList<Double> valoresTotal = new ArrayList<>();
        ArrayList<String> imagensProduto = new ArrayList<>();

        try {
            mysql.conectaBanco();

            String consulta = "SELECT * FROM visualizarPedido WHERE id_pedido = " + idPedido;

            mysql.executarSQL(consulta);

            ResultSet resultSet = mysql.getResultSet();

            while (resultSet.next()) {
                try {
                    idPedidos.add(resultSet.getInt("id_pedido"));
                    idProdutos.add(resultSet.getInt("id_produto"));
                    nomes.add(resultSet.getString("nome"));
                    fabricantes.add(resultSet.getString("fabricante"));
                    valoresProduto.add(resultSet.getDouble("valorProduto"));
                    quantidades.add(resultSet.getInt("quantidade"));
                    valoresTotal.add(resultSet.getDouble("valorTotal"));
                    imagensProduto.add(resultSet.getString("imagemProduto"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mysql.fechaBanco();
        }

        ArrayList<ArrayList<?>> orderData = new ArrayList<>();
        orderData.add(idPedidos);
        orderData.add(idProdutos);
        orderData.add(nomes);
        orderData.add(fabricantes);
        orderData.add(valoresProduto);
        orderData.add(quantidades);
        orderData.add(valoresTotal);
        orderData.add(imagensProduto);

        return orderData;
    }
}

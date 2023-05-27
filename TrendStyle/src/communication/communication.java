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
    
    public ArrayList<ArrayList<?>> collectShoppingCartItems(int id) {
        ArrayList<Integer> idClientes = new ArrayList<>();
        ArrayList<Integer> idProdutos = new ArrayList<>();
        ArrayList<String> nomes = new ArrayList<>();
        ArrayList<Double> valores = new ArrayList<>();
        ArrayList<Integer> quantidades = new ArrayList<>();
        ArrayList<Double> totais = new ArrayList<>();

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

        return cartData;
    }

}

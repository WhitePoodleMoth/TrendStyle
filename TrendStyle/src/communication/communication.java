/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package communication;
import java.sql.ResultSet;

/**
 *
 * @author Unknown Account
 */
public class communication {
    MySQL mysql = new MySQL();
    
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

        String consulta = "CALL registerClient('" + username + "', '" + password + "', '" + cpf + "', '" + nome + "', '" + sobrenome + "', '" + email + "', '" + telefone + "', '" + cep + "', '" + rua + "', '" + numero + "', '" + cidade + "', '" + estado + "')";

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
}

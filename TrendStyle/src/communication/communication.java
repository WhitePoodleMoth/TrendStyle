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
    }
    
    public boolean checkClientLogin(String username, String password) {
        return ("client".equals(username) && "client".equals(password));
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package communication;

/**
 *
 * @author Unknown Account
 */
public class communication {
    
    public boolean checkAdminLogin(String username, String password) {
        return ("admin".equals(username) && "admin".equals(password));
    }
    
    public boolean checkClientLogin(String username, String password) {
        return ("client".equals(username) && "client".equals(password));
    }
}

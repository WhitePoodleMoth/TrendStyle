/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trendstyle;

import views.SplashScreen;
import views.client.ClientOrderScreen;

/**
 *
 * @author Unknown Account
 */
public class TrendStyle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //SplashScreen page = new SplashScreen();
        ClientOrderScreen page = new ClientOrderScreen(6);
        page.setVisible(true);
    }
    
}

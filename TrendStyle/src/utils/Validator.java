/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.regex.Pattern;

/**
 *
 * @author Unknown Account
 */
public class Validator {
    
    public static boolean isValidEmail(String email) {
        // Verifica o formato do e-mail usando uma expressão regular
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);

        return pattern.matcher(email).matches();
    }
    
    public static boolean isValidCPF(String cpf) {
        // Remove caracteres especiais do CPF
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF possui 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (CPF inválido)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calcula e compara os dígitos verificadores
        try {
            int[] digits = new int[11];
            for (int i = 0; i < 11; i++) {
                digits[i] = Integer.parseInt(cpf.substring(i, i + 1));
            }

            int sum = 0;
            int factor = 10;
            for (int i = 0; i < 9; i++) {
                sum += digits[i] * factor;
                factor--;
            }

            int remainder = sum % 11;
            int firstVerifier = (remainder < 2) ? 0 : 11 - remainder;

            if (digits[9] != firstVerifier) {
                return false;
            }

            sum = 0;
            factor = 11;
            for (int i = 0; i < 10; i++) {
                sum += digits[i] * factor;
                factor--;
            }

            remainder = sum % 11;
            int secondVerifier = (remainder < 2) ? 0 : 11 - remainder;

            return digits[10] == secondVerifier;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

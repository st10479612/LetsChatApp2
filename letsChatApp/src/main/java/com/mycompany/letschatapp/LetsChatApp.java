/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.letschatapp;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class LetsChatApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
      


    

        // 1. COLLECT ALL INPUTS FIRST
        System.out.println("Enter username (min 5 chars + underscore):");
        String username = scanner.nextLine();

        System.out.println("Enter user password (must have Upper, Number, Special):");
        String password = scanner.nextLine();

        System.out.print("Enter cellphone number (e.g., +27677844509): ");
        String phone = scanner.nextLine();

        // 2. VALIDATION LOGIC
        
        // Check Username
        if (username.length() < 5 || !username.contains("_")) {
            System.out.println("Error: Username must be at least 5 letters and include an underscore.");
        } 
        // Check Password
        else if (!password.matches(".*[A-Z].*") || 
                 !password.matches(".*[0-9].*") || 
                 !password.matches(".*[!@#$%^&*(),.?/:{}|<>].*")) {
            System.out.println("Error: Password must contain a capital letter, number, and a special character.");
        } 
        // Check Phone
        else if (phone.isEmpty()) {
            System.out.println("Error: Phone cannot be empty.");
        } 
        else if (!phone.startsWith("+27")) {
            System.out.println("Error: Number must start with the national code +27");
        } 
        else if (phone.length() != 12) {
            System.out.println("Error: Invalid length for a South African number (must be 12 chars).");
        } 
        // If everything above is skipped, it's a success!
        else {
            System.out.println("\nSuccess! Account created for " + username + "!");
            System.out.println("Phone number saved: " + phone);
        }

        scanner.close();
    } // Closes Main
}
package com.example.practica1.modelos;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    private static String server = "localhost";
    private static String user = "topicos";
    private static String pass = "1234567890";
    private static String db = "restaurante";
    public static Connection conexion = null;
    public static void  createConnextion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://" + server + ":3306/" + db, user, pass);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
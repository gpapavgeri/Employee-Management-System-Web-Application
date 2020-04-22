package com.company.myproject.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbc {

    public static void main(String[] args) {

//        String jdbcURL = "jdbc:sqlserver://dbserver.local.cite.gr:5433;instanceName=devel;databaseName=Playground.gpapavgeri";
//        String user = "gpap";
//        String pass = "gpap";

        String jdbcURL = "jdbc:mysql://localhost:3306/cite?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String pass = "mySQL2019!";

        try {
            System.out.println("Connecting to database: " + jdbcURL);

            Connection myConn = DriverManager.getConnection(jdbcURL, user, pass);

            System.out.println("Connection successfull!");

        }
        catch (Exception exc){
            exc.printStackTrace();
        }
    }
}


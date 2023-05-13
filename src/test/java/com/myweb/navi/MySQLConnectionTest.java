package com.myweb.navi;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.jupiter.api.Test;

public class MySQLConnectionTest {
	
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "";
    private static final String USER = "";
    private static final String PASSWORD = "";

    @Test
    public void testConnection() throws Exception {
        Class.forName(DRIVER);
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

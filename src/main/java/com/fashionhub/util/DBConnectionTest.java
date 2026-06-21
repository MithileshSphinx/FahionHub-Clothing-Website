package com.fashionhub.util;

import java.sql.Connection;

public class DBConnectionTest {


public static void main(String[] args) {

    Connection connection = DBConnection.getConnection();

    if (connection != null) {
        System.out.println("Connection Test Successful");
    } else {
        System.out.println("Connection Test Failed");
    }
}


}

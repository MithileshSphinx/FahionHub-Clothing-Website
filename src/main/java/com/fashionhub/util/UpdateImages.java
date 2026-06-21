package com.fashionhub.util;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateImages {
    public static void main(String[] args) {
        String[][] updates = {
            {"assets/images/men-black-shirts.jpg", "1"},
            {"assets/images/men-blue-jeans.jpg", "2"},
            {"assets/images/men-white-polo.jpg", "3"},
            {"assets/images/women-floral-dress.jpg", "4"},
            {"assets/images/women-pink-kurti.jpg", "5"},
            {"assets/images/women-black-jeans.jpg", "6"},
            {"assets/images/running-shoes.jpg", "7"},
            {"assets/images/casual-sneakers.jpg", "8"},
            {"assets/images/leather-belt.jpg", "9"},
            {"assets/images/women-handbag.jpg", "10"}
        };

        try (Connection conn = DBConnection.getConnection()) {
            for (String[] update : updates) {
                String sql = "UPDATE products SET image_url = ? WHERE product_id = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, update[0]);
                    ps.setInt(2, Integer.parseInt(update[1]));
                    ps.executeUpdate();
                }
            }
            System.out.println("Images updated successfully in database!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

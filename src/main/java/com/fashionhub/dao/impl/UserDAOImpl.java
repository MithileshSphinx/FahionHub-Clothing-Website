package com.fashionhub.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fashionhub.dao.UserDAO;
import com.fashionhub.model.User;
import com.fashionhub.util.DBConnection;

public class UserDAOImpl implements UserDAO {


private static final String INSERT_USER_SQL =
        "INSERT INTO users (full_name, email, phone, password, address, city, state, pincode) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

private static final String LOGIN_USER_SQL =
        "SELECT * FROM users WHERE email = ? AND password = ?";

private static final String GET_USER_BY_ID_SQL =
        "SELECT * FROM users WHERE user_id = ?";

private static final String GET_USER_BY_EMAIL_SQL =
        "SELECT * FROM users WHERE email = ?";

private static final String UPDATE_USER_SQL =
        "UPDATE users SET full_name = ?, phone = ?, address = ?, city = ?, state = ?, pincode = ? " +
        "WHERE user_id = ?";

private static final String UPDATE_PASSWORD_SQL =
        "UPDATE users SET password = ? WHERE user_id = ?";

private static final String DELETE_USER_SQL =
        "DELETE FROM users WHERE user_id = ?";

private static final String EMAIL_EXISTS_SQL =
        "SELECT 1 FROM users WHERE email = ?";

private static final String PHONE_EXISTS_SQL =
        "SELECT 1 FROM users WHERE phone = ?";

@Override
public boolean registerUser(User user) {

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement preparedStatement =
                 connection.prepareStatement(INSERT_USER_SQL)) {

        preparedStatement.setString(1, user.getFullName());
        preparedStatement.setString(2, user.getEmail());
        preparedStatement.setString(3, user.getPhone());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, user.getAddress());
        preparedStatement.setString(6, user.getCity());
        preparedStatement.setString(7, user.getState());
        preparedStatement.setString(8, user.getPincode());

        return preparedStatement.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}

@Override
public User loginUser(String email, String password) {

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement preparedStatement =
                 connection.prepareStatement(LOGIN_USER_SQL)) {

        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return mapResultSetToUser(resultSet);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}

@Override
public User getUserById(int userId) {

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement preparedStatement =
                 connection.prepareStatement(GET_USER_BY_ID_SQL)) {

        preparedStatement.setInt(1, userId);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return mapResultSetToUser(resultSet);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}

@Override
public User getUserByEmail(String email) {

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement preparedStatement =
                 connection.prepareStatement(GET_USER_BY_EMAIL_SQL)) {

        preparedStatement.setString(1, email);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return mapResultSetToUser(resultSet);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}

@Override
public boolean updateUser(User user) {

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement preparedStatement =
                 connection.prepareStatement(UPDATE_USER_SQL)) {

        preparedStatement.setString(1, user.getFullName());
        preparedStatement.setString(2, user.getPhone());
        preparedStatement.setString(3, user.getAddress());
        preparedStatement.setString(4, user.getCity());
        preparedStatement.setString(5, user.getState());
        preparedStatement.setString(6, user.getPincode());
        preparedStatement.setInt(7, user.getUserId());

        return preparedStatement.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}

@Override
public boolean updatePassword(int userId, String password) {

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement preparedStatement =
                 connection.prepareStatement(UPDATE_PASSWORD_SQL)) {

        preparedStatement.setString(1, password);
        preparedStatement.setInt(2, userId);

        return preparedStatement.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}

@Override
public boolean deleteUser(int userId) {

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement preparedStatement =
                 connection.prepareStatement(DELETE_USER_SQL)) {

        preparedStatement.setInt(1, userId);

        return preparedStatement.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}

@Override
public boolean emailExists(String email) {

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement preparedStatement =
                 connection.prepareStatement(EMAIL_EXISTS_SQL)) {

        preparedStatement.setString(1, email);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            return resultSet.next();
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}

@Override
public boolean phoneExists(String phone) {

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement preparedStatement =
                 connection.prepareStatement(PHONE_EXISTS_SQL)) {

        preparedStatement.setString(1, phone);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            return resultSet.next();
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}

private User mapResultSetToUser(ResultSet resultSet) throws SQLException {

    User user = new User();

    user.setUserId(resultSet.getInt("user_id"));
    user.setFullName(resultSet.getString("full_name"));
    user.setEmail(resultSet.getString("email"));
    user.setPhone(resultSet.getString("phone"));
    user.setPassword(resultSet.getString("password"));
    user.setAddress(resultSet.getString("address"));
    user.setCity(resultSet.getString("city"));
    user.setState(resultSet.getString("state"));
    user.setPincode(resultSet.getString("pincode"));
    user.setCreatedAt(resultSet.getTimestamp("created_at"));

    return user;
}


}

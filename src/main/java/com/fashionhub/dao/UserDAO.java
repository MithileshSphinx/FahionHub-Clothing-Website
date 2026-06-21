package com.fashionhub.dao;

import com.fashionhub.model.User;

public interface UserDAO {


boolean registerUser(User user);

User loginUser(String email, String password);

User getUserById(int userId);

User getUserByEmail(String email);

boolean updateUser(User user);

boolean updatePassword(int userId, String password);

boolean deleteUser(int userId);

boolean emailExists(String email);

boolean phoneExists(String phone);


}

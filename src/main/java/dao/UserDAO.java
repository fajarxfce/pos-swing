package dao;

import model.User;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserDAO {
    User findByUsername(String username) throws SQLException;
    boolean authenticate(String username, String password) throws SQLException;
    User getUserByCredentials(String username, String password) throws SQLException;
}
package dao;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private String jdbcURL = "jdbc:mysql://localhost:3306/user_managment";
    private String username = "root";
    private String password = "root";

    //JDBC
    protected Connection getConnect(){
      Connection connection = null;
      try{
          Class.forName("com.mysql.jdbc.Driver");
          connection = DriverManager.getConnection(jdbcURL, username, password);
      }catch (SQLException ex){
          ex.printStackTrace();
      }catch (ClassNotFoundException ex){
          ex.printStackTrace();
      }
      return connection;
    }

    //Sql query
    private static final String INSERT_USER = "INSERT INTO users(name, email, country) VALUES(?, ?, ?);";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id=?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users;";
    private static final String DELETE_USER = "DELETE from users WHERE id=?;";
    private static final String UPDATE_USER = "UPDATE users SET name=?, email=?, country=? WHERE id=?;";


    //Create or insert user
    public void insertUser(User user){
        try(Connection connect = getConnect();
            PreparedStatement statement = connect.prepareStatement(INSERT_USER)){
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());

            statement.executeUpdate();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    //Update user
    public boolean updateUser(User user){
        boolean userUpdated = false;
        try(Connection connect = getConnect();
            PreparedStatement statement = connect.prepareStatement(UPDATE_USER)){
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());
            statement.setInt(4, user.getId());

            userUpdated = statement.executeUpdate()>0;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return userUpdated;
    }

    //Select user by id
    public User selectUser(int id){
        User user = null;
        try(Connection connect = getConnect();
            PreparedStatement statement = connect.prepareStatement(SELECT_USER_BY_ID)){
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");

                user = new User(id, name, email, country);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }

        return user;
    }
    //Select users
    public List<User> selectAllUser(){
        List<User> users = new ArrayList<>();
        try(Connection connect = getConnect();
            PreparedStatement statement = connect.prepareStatement(SELECT_ALL_USERS)){

            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");

                User user = new User(id, name, email, country);
                users.add(user);
            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }

        return users;
    }

    //Delete user
    public boolean deleteUser(Integer id){
        boolean deletedUser = false;
        try(Connection connect = getConnect();
            PreparedStatement statement = connect.prepareStatement(DELETE_USER)){
            statement.setInt(1, id);

            deletedUser = statement.executeUpdate() > 0;
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return deletedUser;
    }
}

















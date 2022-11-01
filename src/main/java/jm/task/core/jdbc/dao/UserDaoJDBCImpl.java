package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable()   { // создание таблицы

        String sql = "create table if not exists users (id int auto_increment, name varchar(45), " +
                "lastName varchar(45), age int, primary key (id))";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() { // удаление таблицы

        String sql = "drop table if exists users";

        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) { // добавление юзера
        String sql = "insert into users (name, lastName, age) values (?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("User с именем - " + name + " " + lastName + " добавлен в базу данных.");
    }

    public void removeUserById(long id) {  // удаление по iD
        String sql = "delete from users where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers()   { // получение всех кто есть в таблице
        List<User> users = new ArrayList<>();
        String sql = "select id, name, lastName, age from users";
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;

    }
        public void cleanUsersTable() { //очищение таблицы
        String sql = "delete from users";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                connection.setAutoCommit(false);
                preparedStatement.executeUpdate();
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


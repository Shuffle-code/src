package HW_3_2;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Main {
    private static Connection connection;
    public static void main(String[] args) {
        findAll();
        System.out.println(getNickByLoginAndPass("nick3", "pass3"));;// write your code here
    }
    public static Connection connectionM(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:users.db");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
    public static Connection connectionM1(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Evgeniy\\Documents\\Java\\SQL\\HW\\users.db");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static class Entry {
        String name;
        String login;
        String password;

        Entry(String name, String login, String password) {
            this.name = name;
            this.login = login;
            this.password = password;
        }



        String getName() {
            return name;
        }

        String getLogin() {
            return login;
        }

        String getPassword() {
            return password;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry entry = (Entry) o;
            return Objects.equals(name, entry.name) && Objects.equals(login, entry.login) && Objects.equals(password, entry.password);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, login, password);
        }
    }
    public static List<Entry> findAll() {
        Connection connection = connectionM();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM user");

            List<Entry> users = new ArrayList<>();
            while (rs.next()) {
                System.out.println(rs.getString("name") + " " + rs.getString("login") + " " + rs.getString("password"));

                users.add(
                        new Entry(
                                rs.getString("name"),
                                rs.getString("login"),
                                rs.getString("password")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }

        return Collections.emptyList();
    }
    public static String getNickByLoginAndPass(String login, String pass) {
        Connection connection = connectionM1();
        String sql = String.format("SELECT * FROM user WHERE login = '%s' and password = '%s';", login, pass);

        try {

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

//            PreparedStatement statement = connection.prepareStatement(sql);
//            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                System.out.println(rs.getString("name") + " " + rs.getString("login") + " " + rs.getString("password"));
                return rs.getString(1);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }


            return null;

    }

}

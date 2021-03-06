package HW_8.current.server;

import java.sql.*;
import java.util.Objects;
import java.util.Optional;

public class AuthService {
    private static Connection connection;


    public static Connection connectionM(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:users.db");
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

    public Optional<Entry> getNickByLoginAndPass(String login, String pass) {
        Connection connection = connectionM();
        String sql = String.format("SELECT * FROM user WHERE login = '%s' and password = '%s';", login, pass);

        try {

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                System.out.println(rs.getString("name") + " " + rs.getString("login") + " " + rs.getString("password"));
                return Optional.of(
                        new Entry (
                                rs.getString("name"),
                                rs.getString("login"),
                                rs.getString("password")
                )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }

            return Optional.empty();

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

}

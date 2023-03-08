package socialnetwork.repository.database;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class PrietenieDataBase implements Repository<Tuple<String>, Prietenie> {

    private final String url;
    private final String username;
    private final String password;

    public PrietenieDataBase(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Prietenie findOne(Tuple<String> mail) {
        String sql = "select * from prietenii where ((user1 = (?) and user2 = (?)) or (user1 = (?) and user2 = (?)))";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1,mail.getLeft());
            statement.setString(2,mail.getRight());

            statement.setString(3,mail.getRight());
            statement.setString(4,mail.getLeft());
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                LocalDate date = resultSet.getDate("data").toLocalDate();

                return new Prietenie(mail, date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Prietenie> findAll() {
        String sql = "select * from prietenii";
        Set<Prietenie> prieteni = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {

                String user1 = resultSet.getString("user1");
                String user2 = resultSet.getString("user2");
                LocalDate date = resultSet.getDate("data").toLocalDate();

                Prietenie pr =  new Prietenie(new Tuple<>(user1, user2), date);
                prieteni.add(pr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prieteni;
    }

    @Override
    public Prietenie save(Prietenie entity) {

        String sql = "insert into prietenii  (user1,user2,data) values (?,?,?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1,entity.getId().getLeft());
            statement.setString(2,entity.getId().getRight());
            statement.setDate(3,Date.valueOf(entity.getDate()));



            if(statement.executeUpdate() == 0) {
                return entity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Prietenie delete(Tuple<String> mail) {

        String sql = "delete from prietenii where ((user1 = (?) and user2 = (?)) or (user1 = (?) and user2 = (?)))";
        Prietenie pr = this.findOne(mail);
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1,mail.getLeft());
            statement.setString(2,mail.getRight());

            statement.setString(3,mail.getRight());
            statement.setString(4,mail.getLeft());

            if(statement.executeUpdate() == 0) {
                return pr;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Prietenie update(Prietenie entity) {

        String sql = "update prietenii set data = (?) where user1 = (?) and user2 = (?)";
        Tuple<String > mail = entity.getId();
        Prietenie pr = this.findOne(mail);
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(2,mail.getLeft());
            statement.setString(3,mail.getRight());
            statement.setDate(1,Date.valueOf(entity.getDate()));

            if(statement.executeUpdate() == 0) {
                return pr;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

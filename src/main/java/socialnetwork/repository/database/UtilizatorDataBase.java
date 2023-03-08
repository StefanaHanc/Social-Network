package socialnetwork.repository.database;

import socialnetwork.domain.Utilizator;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UtilizatorDataBase implements Repository<String, Utilizator> {
    private final String url;
    private final String username;
    private final String password;

    public UtilizatorDataBase(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    @Override
    public Utilizator findOne(String email) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from utilizatori where email = (?)")
        ) {
            statement.setString(1,email);
             ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String password = resultSet.getString("password");

                return new Utilizator(email, firstName, lastName, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Utilizator> findAll() {
        Set<Utilizator> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from utilizatori");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String password = resultSet.getString("password");

                Utilizator utilizator = new Utilizator(email, firstName, lastName, password);
                users.add(utilizator);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Utilizator save(Utilizator entity) {

        String sql = "insert into utilizatori (email, firstname, lastname, password ) values (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1,entity.getId());
            ps.setString(2, entity.getFirstName());
            ps.setString(3, entity.getLastName());
            ps.setString(4, entity.getPassword());

            if(ps.executeUpdate() == 0){
                return entity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Utilizator delete(String email) {

        String sql = "delete from utilizatori where email=?";

        Utilizator user = this.findOne(email);
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1,email);
            if(ps.executeUpdate() == 0){
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public Utilizator update(Utilizator entity) {
        Utilizator  user = this.findOne(entity.getId());

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("update utilizatori set lastname = (?) ,firstname = (?) where email = (?)")
        ) {
            statement.setString(1,entity.getLastName());
            statement.setString(2,entity.getFirstName());
            statement.setString(3,entity.getId());
            statement.executeUpdate();

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}

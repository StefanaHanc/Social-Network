package socialnetwork.repository.database;

import socialnetwork.domain.CererePrietenie;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class CereriPrietenieDataBase {
    private final String url;
    private final String username;
    private final String password;

    public CereriPrietenieDataBase(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public CererePrietenie findOne(String fromUser, String toUser) {
        String sql = "select * from cereri_prietenie where (from_user = (?) and to_user = (?)) or (to_user = (?) and from_user = (?))";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1,fromUser);
            statement.setString(2,toUser);
            statement.setString(3,fromUser);
            statement.setString(4,toUser);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                String status = resultSet.getString("status");
                LocalDate data = resultSet.getDate("data").toLocalDate();

                CererePrietenie cr = new CererePrietenie(fromUser,toUser,status);
                cr.setData(data);

                return cr;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Iterable<CererePrietenie> findReqFromOne(String email){
        String sql = "select * from cereri_prietenie where from_user = (?) and status = (?)";
        Set<CererePrietenie> cereri = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1,email);
            statement.setString(2,"pending");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String fromUser = resultSet.getString("from_user");
                String toUser = resultSet.getString("to_user");
                String status = resultSet.getString("status");
                LocalDate data = resultSet.getDate("data").toLocalDate();

                CererePrietenie cr = new CererePrietenie(fromUser,toUser,status);
                cr.setData(data);
                cereri.add(cr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cereri;
    }

    public Iterable<CererePrietenie> findAll() {
        String sql = "select * from cereri_prietenie";
        Set<CererePrietenie> cereri = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String fromUser = resultSet.getString("from_user");
                String toUser = resultSet.getString("to_user");
                String status = resultSet.getString("status");
                LocalDate data = resultSet.getDate("data").toLocalDate();

                CererePrietenie cr = new CererePrietenie(fromUser,toUser,status);
                cr.setData(data);
                cereri.add(cr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cereri;
    }

    public CererePrietenie save(CererePrietenie entity) {

        String sql = "insert into cereri_prietenie  (from_user, to_user, status, data) values (?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1,entity.getFromUser());
            statement.setString(2,entity.getToUser());
            statement.setString(3,entity.getStatus());
            statement.setDate(4, Date.valueOf(entity.getData()));

            if(statement.executeUpdate() == 0) {
                return entity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CererePrietenie update(CererePrietenie entity) {

        String sql = "update cereri_prietenie set status = (?), data = (?)  where (from_user = (?) and to_user = (?)) or (to_user = (?) and from_user = (?)) ";
        CererePrietenie pr = this.findOne(entity.getFromUser(), entity.getToUser());
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(3,entity.getFromUser());
            statement.setString(4,entity.getToUser());
            statement.setString(5,entity.getFromUser());
            statement.setString(6,entity.getToUser());
            statement.setString(1,entity.getStatus());
            statement.setDate(2, Date.valueOf(LocalDate.now()));

            if(statement.executeUpdate() == 0) {
                return pr;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

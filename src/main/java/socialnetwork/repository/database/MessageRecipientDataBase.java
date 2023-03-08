package socialnetwork.repository.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MessageRecipientDataBase {
    private final String url;
    private final String username;
    private final String password;

    public MessageRecipientDataBase(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Integer save(Integer id, String email) {

        String sql = "insert into message_recipient (id_message, recipient) values (?,?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1,id);
            statement.setString(2,email);


            if(statement.executeUpdate() == 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

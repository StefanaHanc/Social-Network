package socialnetwork.repository.database;

import socialnetwork.domain.Message;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessageDataBase {
        private final String url;
        private final String username;
        private final String password;

        public MessageDataBase(String url, String username, String password) {
                this.url = url;
                this.username = username;
                this.password = password;
        }

        public Integer save(Message entity) {

                String sql = "insert into message  (from,text,data,reply_id) values (?,?,?,?)";
                try (Connection connection = DriverManager.getConnection(url, username, password);
                     PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                ) {
                        statement.setString(1,entity.getFrom());
                        statement.setString(2,entity.getText());
                        statement.setInt(4,entity.getReply());
                        statement.setTimestamp(3, Timestamp.valueOf(entity.getData()));
                        ResultSet rs = statement.getGeneratedKeys();
                        Integer id = rs.getInt(1);
                        if(statement.executeUpdate() == 0) {
                                return id;
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return null;
        }

        public String findOne(Integer replyId) {

                String sql = "select * from  message where (reply_id = (?))";
                try (Connection connection = DriverManager.getConnection(url, username, password);
                     PreparedStatement statement = connection.prepareStatement(sql);
                ) {
                        statement.setInt(1,replyId);
                        ResultSet rs = statement.executeQuery();


                        if(rs.next()) {
                                String email = rs.getString("from");

                                return email;
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return null;
        }

        public Iterable<Message> findChat(String from, String to) {
                String sql = "select * from message inner join message_recipient mr on message.id = mr.id_message where (\"from\")=(?) and recipient = (?) order by data";
                Set<Message> mesaje = new HashSet<>();
                try (Connection connection = DriverManager.getConnection(url, username, password);
                     PreparedStatement statement = connection.prepareStatement(sql);
                ) {
                        statement.setString(1,from);
                        statement.setString(2,to);

                        ResultSet resultSet = statement.executeQuery();

                        while (resultSet.next()) {
                                List<String> toUsers = new ArrayList<>();

                                Integer id = resultSet.getInt("id");
                                String fromUser = resultSet.getString("from");
                                String recipient = resultSet.getString("recipient");
                                toUsers.add(recipient);
                                String text = resultSet.getString("text");
                                LocalDateTime data = resultSet.getTimestamp("data").toLocalDateTime();
                                Integer replyId = resultSet.getInt("replyId");

                                mesaje.add(new Message(id,fromUser,toUsers,text,data,replyId));
                        }

                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return mesaje;
        }


        public Iterable<String> findAll(String email) {
                String sql = "select distinct * from message inner join message_recipient mr on message.id = mr.id_message where (\"from\")=(?) or (recipient) = (?)";
                Set<String> utilizatori = new HashSet<>();
                try (Connection connection = DriverManager.getConnection(url, username, password);
                     PreparedStatement statement = connection.prepareStatement(sql);
                ) {
                        statement.setString(1,email);
                        statement.setString(2,email);

                        ResultSet resultSet = statement.executeQuery();

                        while (resultSet.next()) {

                                String fromUser = resultSet.getString("from");
                                String recipient = resultSet.getString("recipient");
                                if(fromUser.equals(email) && !utilizatori.contains(recipient)){
                                      utilizatori.add(recipient);
                                }
                                if(recipient.equals(email) && !utilizatori.contains(fromUser)){
                                        utilizatori.add(fromUser);
                                }
                        }

                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return utilizatori;
        }
}

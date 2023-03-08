package socialnetwork.domain;

import java.time.LocalDate;

public class CererePrietenie {
    int id;
    String fromUser;
    String toUser;
    String status;
    LocalDate data;

    public CererePrietenie(String fromUser, String toUser, String status) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.status = status;
        this.data = LocalDate.now();
    }

    public String getFromUser() {
        return fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getData() { return data; }

    public void setData(LocalDate data) {this.data = data; }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return  fromUser +
                " | " + toUser +
                " | " + status +
                "\n";
    }


}

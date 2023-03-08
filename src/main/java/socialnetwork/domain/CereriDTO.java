package socialnetwork.domain;

import java.time.LocalDate;
import java.util.List;

public class CereriDTO {
    private String email;
    private String firstName;
    private String lastName;
    String status;
    LocalDate data;

    public CereriDTO(String email, String firstName, String lastName, String status, LocalDate data) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.data = data;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}

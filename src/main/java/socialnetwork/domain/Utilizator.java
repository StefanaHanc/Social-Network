package socialnetwork.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*

dsafds
 */

public class Utilizator extends Entity<String>{
    private String firstName;
    private String lastName;
    private final List<String> friends;
    private String password;

    /**
     *
     * @param mail
     * @param firstName
     * @param lastName
     */
    public Utilizator(String mail, String firstName, String lastName, String password) {
        this.setId(mail);
        this.firstName = firstName;
        this.lastName = lastName;
        this.friends = new ArrayList<>();
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<String> getFriends() {
        return friends;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", friends=" + friends +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        Utilizator that = (Utilizator) o;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                getFriends().equals(that.getFriends()) &&
                getPassword().equals(that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getFriends(), getPassword());
    }
}
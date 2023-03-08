package socialnetwork.domain;

import java.time.LocalDate;


public class Prietenie extends Entity<Tuple<String>> {


    LocalDate date;

    /**
     *
     * @param emails
     * @param date
     */
    public Prietenie(Tuple<String> emails, LocalDate date) {
        setId(emails);
        this.date = date;
    }

    @Override
    public String toString() {
        return "Prietenie{" + this.getId() +
                " data=" + date +
                '}';
    }

    /**
     *
     * @return the date when the friendship was created
     */
    public LocalDate getDate() {
        return date;
    }
}



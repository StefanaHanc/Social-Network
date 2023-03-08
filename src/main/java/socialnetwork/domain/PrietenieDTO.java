package socialnetwork.domain;

import java.time.LocalDate;

public class PrietenieDTO {
    String nume;
    String prenume;
    LocalDate data;

    public PrietenieDTO(String nume, String prenume, LocalDate data) {
        this.nume = nume;
        this.prenume = prenume;
        this.data = data;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public LocalDate getData() {
        return data;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PritenieDTO{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", data=" + data +
                "}\n";
    }
}


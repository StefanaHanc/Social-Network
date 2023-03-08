package socialnetwork.domain;

import java.util.HashSet;
import java.util.List;

public class Page {
    private String email;
    private String nume;
    private String prenume;
    private HashSet<PrietenDTO> prieteni;
    private List<Message> mesaje;
    private HashSet<CereriDTO> cereri;

    public Page(String email, String nume, String prenume, HashSet<PrietenDTO> prieteni, List<Message> mesaje, HashSet<CereriDTO> cereri) {
        this.email = email;
        this.nume = nume;
        this.prenume = prenume;
        this.prieteni = prieteni;
        this.mesaje = mesaje;
        this.cereri = cereri;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public HashSet<PrietenDTO> getPrieteni() {
        return prieteni;
    }

    public void setPrieteni(HashSet<PrietenDTO> prieteni) {
        this.prieteni = prieteni;
    }

    public List<Message> getMesaje() {
        return mesaje;
    }

    public void setMesaje(List<Message> mesaje) {
        this.mesaje = mesaje;
    }

    public HashSet<CereriDTO> getCereri() {
        return cereri;
    }

    public void setCereri(HashSet<CereriDTO> cereri) {
        this.cereri = cereri;
    }
}

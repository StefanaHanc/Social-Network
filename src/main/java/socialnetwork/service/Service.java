package socialnetwork.service;

import socialnetwork.domain.CereriDTO;
import socialnetwork.domain.*;
import socialnetwork.domain.validators.CerereValidator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.database.CereriPrietenieDataBase;
import socialnetwork.repository.database.MessageDataBase;
import socialnetwork.repository.database.MessageRecipientDataBase;
import socialnetwork.repository.database.PrietenieDataBase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class Service {
    private Repository<String, Utilizator> repoUser;
    private Repository<Tuple<String>, Prietenie> repoPrietenie;
    private CereriPrietenieDataBase repoCereri;
    private MessageDataBase repoMessage;
    private MessageRecipientDataBase repoRecipient;
    private final CerereValidator valCerere =  new CerereValidator();

    /**
     *
     * @param repoUser
     * @param repoPrietenie
     */
    public Service(Repository<String, Utilizator> repoUser, Repository<Tuple<String>, Prietenie> repoPrietenie, CereriPrietenieDataBase repoCereri,MessageDataBase repoMessage,MessageRecipientDataBase repoRecipient) {
        this.repoUser = repoUser;
        this.repoPrietenie = repoPrietenie;
        this.repoCereri = repoCereri;
        this.repoMessage = repoMessage;
        this.repoRecipient = repoRecipient;
        repoPrietenie.findAll().forEach(prietenie -> {
            repoUser.findOne(prietenie.getId().getLeft()).getFriends().add(prietenie.getId().getRight());
            repoUser.findOne(prietenie.getId().getRight()).getFriends().add(prietenie.getId().getLeft());
        });
    }



    //========================================================================== USER REPO



    /**
     *
     * @param mail
     * @param nume
     * @param prenume
     */
    public void addUtilizator(String mail, String nume, String prenume, String parola) {
        Utilizator user = new Utilizator(mail, nume, prenume, parola);
        repoUser.save(user);

    }

    /**
     *
     * @param mail
     */
    public void delUtilizator(String mail) {
        Utilizator user =repoUser.delete(mail);
        if (user == null){
            return;
        }
        user.getFriends().forEach(friend->{
            repoPrietenie.delete(new Tuple<> (user.getId(),friend));
            repoUser.findOne(friend).getFriends().remove(user.getId());
        });
    }

    /**
     *
     * @return all entities
     */
    public Iterable<Utilizator> getAllUtilizator(){
        return repoUser.findAll();
    }

    public Utilizator searchUtilizator(String email){
        return repoUser.findOne(email);
    }



    //========================================================================== FRIENDSHIP REPOS



    /**
     *
     * @param mail1
     * @param mail2
     */
    public void addPrietene(String mail1, String mail2){
        Utilizator user1 = repoUser.findOne(mail1);
        Utilizator user2 = repoUser.findOne(mail2);
        if(user1 == null || user2 == null){
            throw new ServiceException("Nu exista user");
        }
        Prietenie prietenie = repoPrietenie.save(new Prietenie(new Tuple<> (mail1, mail2), LocalDate.now()));
        if(prietenie == null){
            user1.getFriends().add(user2.getId());
            user2.getFriends().add(user1.getId());
        }
        else{
            throw new ServiceException("Prietenie exista deja");
        }

    }

    /**
     *
     * @param mail1
     * @param mail2
     */
    public void delPrietenie(String mail1, String mail2){
        Utilizator user1 = repoUser.findOne(mail1);
        Utilizator user2 = repoUser.findOne(mail2);

        repoPrietenie.delete(new Tuple<> (mail1, mail2));
        this.updateCereri(mail1, mail2, "rejected");
    }

    public  Iterable<Prietenie> getAllPrietenie() {
        return repoPrietenie.findAll();
    }

    public Iterable<PrietenieDTO> getAllPrieteni(String email){
        HashSet<PrietenieDTO> rez = new HashSet<>();
        HashSet<Prietenie> prietenii = (HashSet<Prietenie>) repoPrietenie.findAll();
        prietenii.stream().filter(x->
                x.getId().getLeft().equals(email) || x.getId().getRight().equals(email)).
                forEach(y->{
                    HashSet<Utilizator> utilizatori = (HashSet<Utilizator>) repoUser.findAll();
                    utilizatori.stream().filter(a->(a.getId().equals(y.getId().getLeft()) || a.getId().equals(y.getId().getRight())) && !a.getId().equals(email)).
                            forEach(p->{
                                PrietenieDTO usr = new PrietenieDTO(p.getFirstName(),p.getLastName(),y.getDate());
                                rez.add(usr);
                            });
                });
        return rez;
    }

    public Iterable<PrietenDTO> getAllPrieteniUser(String email){
        HashSet<PrietenDTO> rez = new HashSet<>();
        HashSet<Prietenie> prietenii = (HashSet<Prietenie>) repoPrietenie.findAll();
        prietenii.stream().filter(x->
                        x.getId().getLeft().equals(email) || x.getId().getRight().equals(email)).
                forEach(y->{
                    HashSet<Utilizator> utilizatori = (HashSet<Utilizator>) repoUser.findAll();
                    utilizatori.stream().filter(a->(a.getId().equals(y.getId().getLeft()) || a.getId().equals(y.getId().getRight())) && !a.getId().equals(email)).
                            forEach(p->{
                                PrietenDTO usr = new PrietenDTO(p.getId(), p.getFirstName(), p.getLastName());
                                rez.add(usr);
                            });
                });
        return rez;
    }

    public Iterable<PrietenieDTO> getAllPrieteniLuna(String email, int data){
        HashSet<PrietenieDTO> prietenii = (HashSet<PrietenieDTO>) this.getAllPrieteni(email);
        HashSet<PrietenieDTO> rez = new HashSet<>();
        prietenii.stream().filter(x->x.getData().getMonthValue() == data).
                forEach(x->{
                    PrietenieDTO pr = new PrietenieDTO(x.getNume(),x.getPrenume(),x.getData());
                    rez.add(pr);
                });
        return rez;
    }

    public void addCerere(String fromUser, String toUser){
        CererePrietenie cr = new CererePrietenie(fromUser,toUser,"pending");
        if(repoCereri.findOne(fromUser,toUser) != null){
            if(repoCereri.findOne(fromUser,toUser).getStatus().equals("rejected")){
                repoCereri.update(cr);
                return;
            }
            else {
                throw new ServiceException("Exista deja cererea!!!");
            }
        }
        else {
            repoCereri.save(cr);
        }
    }

    public Iterable<CereriDTO> getAllCereri(String email){
        HashSet<CereriDTO> rez = new HashSet<>();
        HashSet<CererePrietenie> cereri = (HashSet<CererePrietenie>) repoCereri.findAll();
        cereri.stream().filter(x-> x.getToUser().equals(email) && x.getStatus().equals("pending")).
                forEach(y->{
                    CereriDTO cr = new CereriDTO(y.getFromUser(), repoUser.findOne(y.getFromUser()).getFirstName(),
                            repoUser.findOne(y.getFromUser()).getLastName(), y.getStatus(), y.getData());

                    rez.add(cr);
                });
        return rez;
    }

    public void updateCereri(String fromUser,String toUser, String status){
        CererePrietenie cr = new CererePrietenie(fromUser,toUser,status);
        valCerere.validate(cr);
        repoCereri.update(cr);
        if(repoCereri.findOne(fromUser,toUser).getStatus().equals("approved")){

            Prietenie pr = new Prietenie(new Tuple<>(fromUser, toUser),LocalDate.now());
            repoPrietenie.save(pr);
        }
    }

    public Iterable<PrietenDTO> getPeople(String forUser){
        HashSet<Utilizator> useri = (HashSet<Utilizator>) getAllUtilizator();
        HashSet<PrietenDTO> rez = new HashSet<>();
        useri.stream().filter(x->repoPrietenie.findOne(new Tuple<String>(forUser, x.getId())) == null && !x.getId().equals(forUser) && (repoCereri.findOne(forUser, x.getId()) == null || !repoCereri.findOne(forUser, x.getId()).getStatus().equals("pending"))).forEach(a ->
        {
            PrietenDTO pr = new PrietenDTO(a.getId(), a.getFirstName(), a.getLastName());
            rez.add(pr);
        });
        return rez;
    }

    public Iterable<PrietenDTO> getCereriTrimise(String fromUser){
        HashSet<CererePrietenie> cereri = (HashSet<CererePrietenie>) repoCereri.findReqFromOne(fromUser);
        HashSet<PrietenDTO> rez = new HashSet<>();
        cereri.forEach(x->{
            Utilizator user = repoUser.findOne(x.getToUser());
            PrietenDTO pr = new PrietenDTO(user.getId(), user.getFirstName(), user.getLastName());
            rez.add(pr);
        });
        return rez;
    }

    //========================================================================== MESSAGE REPOS



    public void replyMessage(String from, List<String> to, String text, Integer replyId){
        Integer id = repoMessage.save(new Message(null, from, null, text, LocalDateTime.now(),  replyId));
        if(replyId == null){
            for (String u : to){
                Set<Message> mesaje = (Set<Message>) repoMessage.findChat(from,u);
                if(mesaje.isEmpty()){
                    repoRecipient.save(id,u);
                }
                else{
                    //repoRecipient.save()
                }

            }
        }
        else
        {
            repoRecipient.save(id, repoMessage.findOne(replyId));
        };
    }

    public Iterable<String> getAllChatRooms(String email){
        return repoMessage.findAll(email);
    }



    //========================================================================== PAGE



    /*
    !!!
    ADAUGA MESAJE CLAN
    !!!
     */
    public Page getPage(String email) {
        Utilizator user = repoUser.findOne(email);
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        HashSet<PrietenDTO> prieteni = (HashSet<PrietenDTO>) getAllPrieteniUser(email);
        List<Message> mesaje = new ArrayList<>();
        HashSet<CereriDTO> cereri = (HashSet<CereriDTO>) getAllCereri(email);

        Page pagina = new Page(email, lastName, firstName, prieteni, mesaje, cereri);
        return pagina;
    }


}

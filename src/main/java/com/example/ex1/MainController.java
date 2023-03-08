package com.example.ex1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import socialnetwork.domain.CereriDTO;
import socialnetwork.domain.Page;
import socialnetwork.domain.PrietenDTO;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class MainController {
    Service srv;
    Page page;
    HashSet<PrietenDTO> people;

    @FXML
    private Label accName;

    @FXML
    private Pane friendPane;

    @FXML
    private Pane msgPane;

    private ObservableList<CereriDTO> modelRequests = FXCollections.observableArrayList();

    @FXML
    private TableView<CereriDTO> reqTable;
    @FXML
    private TableColumn<CereriDTO, String> reqNumeColumn;
    @FXML
    private TableColumn<CereriDTO, String> reqPrenumeColumn;
    @FXML
    private TableColumn<CereriDTO, String> reqStatusColumn;
    @FXML
    private TableColumn<CereriDTO, LocalDate> reqDataColumn;
    @FXML
    private TableColumn<CereriDTO, LocalDate> reqEmailColumn;


    private ObservableList<PrietenDTO> modelFriends = FXCollections.observableArrayList();

    @FXML
    private TableView<PrietenDTO> friendTable;
    @FXML
    private TableColumn<PrietenDTO, String> friendNumeColumn;
    @FXML
    private TableColumn<PrietenDTO, String> friendPrenumeColumn;
    @FXML
    private TableColumn<PrietenDTO, String> friendEmailColumn;

    @FXML
    private TextField searchFriendsBar;

    private ObservableList<PrietenDTO> modelSearch = FXCollections.observableArrayList();

    @FXML
    private TableView<PrietenDTO> searchPeopleTable;
    @FXML
    private TableColumn<PrietenDTO, String> searchNumeColumn;
    @FXML
    private TableColumn<PrietenDTO, String> searchPrenumeColumn;
    @FXML
    private TableColumn<PrietenDTO, String> searchEmailColumn;

    @FXML
    private TextField searchPeopleBar;

    private ObservableList<PrietenDTO> modelSentRequests = FXCollections.observableArrayList();

    @FXML
    private TableView<PrietenDTO> sentRequestsTable;
    @FXML
    private TableColumn<PrietenDTO, String> sentReqNumeColumn;
    @FXML
    private TableColumn<PrietenDTO, String> sentReqPrenumeColumn;
    @FXML
    private TableColumn<PrietenDTO, String> sentReqEmailColumn;

    public void initialize(){
        reqNumeColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        reqPrenumeColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        reqStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        reqDataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        reqEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        reqTable.setItems(modelRequests);

        friendNumeColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        friendPrenumeColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        friendEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        friendTable.setItems(modelFriends);

        searchFriendsBar.textProperty().addListener((observable, oldValue, newValue) -> {
            searchFriends();
        });

        searchNumeColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        searchPrenumeColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        searchEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        searchPeopleBar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()){
                searchPeople();
            }
            else{
                modelSearch.clear();
            }
        });

        searchPeopleTable.setItems(modelSearch);


        sentReqNumeColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        sentReqPrenumeColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        sentReqEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        sentRequestsTable.setItems(modelSentRequests);
    }

    public void setUserService(Service service,String email) {
        this.srv=service;
        page = srv.getPage(email);

        friendPane.setVisible(false);
        msgPane.setVisible(false);

        loadTableRequests();
        loadTableFriends();
        loadTableSentRequests();

        accName.setText(page.getNume() + " " + page.getPrenume());
        people = (HashSet<PrietenDTO>) srv.getPeople(page.getEmail());

    }

    public void loadTableRequests(){
        modelRequests.setAll((Collection<? extends CereriDTO>) srv.getAllCereri(page.getEmail()));
    }

    public void logOut() throws IOException {
        Main main = new Main();
        main.switchToLogIn("login.fxml");
    }

    public void acceptRequest(){
        CereriDTO cerere = reqTable.getSelectionModel().getSelectedItem();

        if (cerere != null) {
            srv.updateCereri(cerere.getEmail(), page.getEmail(), "approved");
            loadTableRequests();
            loadTableFriends();
            page.setPrieteni((HashSet<PrietenDTO>) srv.getAllPrieteniUser(page.getEmail()));
        }
    }



    public void declineRequest(){
        CereriDTO cerere = reqTable.getSelectionModel().getSelectedItem();

        if (cerere != null) {
            srv.updateCereri(cerere.getEmail(), page.getEmail(), "rejected");
            loadTableRequests();
            people = (HashSet<PrietenDTO>) srv.getPeople(page.getEmail());
            searchPeople();
        }
    }

    public void loadTableFriends(){
        modelFriends.setAll(page.getPrieteni());
    }

    public void loadTableSentRequests(){
        modelSentRequests.setAll((Collection<? extends PrietenDTO>) srv.getCereriTrimise(page.getEmail()));
    }


    public void deleteFriend(){
        PrietenDTO prieten = friendTable.getSelectionModel().getSelectedItem();

        if (prieten != null){
            srv.delPrietenie(page.getEmail(), prieten.getEmail());
            page.setPrieteni((HashSet<PrietenDTO>) srv.getAllPrieteniUser(page.getEmail()));
            loadTableFriends();
            people = (HashSet<PrietenDTO>) srv.getPeople(page.getEmail());
            searchPeople();
        }
    }

    public void searchFriends(){
        String substring = searchFriendsBar.getText();

        HashSet<PrietenDTO> prieteni =  page.getPrieteni();
        modelFriends.clear();
        for (PrietenDTO p:prieteni){
            if (((p.getLastName() + " " + p.getFirstName()).toLowerCase(Locale.ROOT).contains(substring.toLowerCase(Locale.ROOT))) ||
                    ((p.getFirstName() + " " + p.getLastName()).toLowerCase(Locale.ROOT).contains(substring.toLowerCase(Locale.ROOT)))){

                modelFriends.add(p);
            }
        }
    }

    public void searchPeople(){
        String substring = searchPeopleBar.getText();
        modelSearch.clear();

        if (!substring.equals("")) {
            for (PrietenDTO p : people) {
                if (((p.getLastName() + " " + p.getFirstName()).toLowerCase(Locale.ROOT).contains(substring.toLowerCase(Locale.ROOT))) ||
                        ((p.getFirstName() + " " + p.getLastName()).toLowerCase(Locale.ROOT).contains(substring.toLowerCase(Locale.ROOT)))) {

                    modelSearch.add(p);
                }
            }
        }
    }

    public void deleteSentRequest(){
        PrietenDTO pr = sentRequestsTable.getSelectionModel().getSelectedItem();

        if (pr != null) {
            srv.updateCereri(page.getEmail(), pr.getEmail(), "rejected");
            loadTableSentRequests();
            people = (HashSet<PrietenDTO>) srv.getPeople(page.getEmail());
            searchPeople();
        }
    }

    public void sendFriendRequest(){
        PrietenDTO pr = searchPeopleTable.getSelectionModel().getSelectedItem();

        if (pr!=null){
            srv.addCerere(page.getEmail(), pr.getEmail());
            people = (HashSet<PrietenDTO>) srv.getPeople(page.getEmail());
            searchPeople();
            loadTableSentRequests();
        }
    }

    public void openFriends() {
        msgPane.setVisible(false);
        friendPane.setVisible(true);
    }

    public void openMsg() {
        friendPane.setVisible(false);
        msgPane.setVisible(true);
    }
}


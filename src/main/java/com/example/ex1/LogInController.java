package com.example.ex1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import socialnetwork.domain.Page;
import socialnetwork.domain.Utilizator;
import socialnetwork.repository.database.*;
import socialnetwork.service.Service;

import java.io.IOException;

public class LogInController {
    Service srv;

    @FXML
    private TextField logInEmail;

    @FXML
    private PasswordField logInPw;

    @FXML
    private Label eroare;

    public void setLogInService(Service service) {
        this.srv=service;
    }

    public void logIn() throws IOException{
        String email = logInEmail.getText();
        String pw = logInPw.getText();

        if (email.isEmpty() || pw.isEmpty()){
            eroare.setText("Date invalide");
            eroare.setVisible(true);
        }
        else if (srv.searchUtilizator(email) == null) {
            eroare.setText("Cont inexistent");
            eroare.setVisible(true);
        }
        else if (!srv.searchUtilizator(email).getPassword().equals(String.valueOf(logInPw.getText().hashCode()))){
            eroare.setText("Parola incorecta");
            eroare.setVisible(true);
        }
        else{
            Main main = new Main();
            main.switchToMain("main.fxml", email);
        }
    }

    public void signUp() throws IOException{
        Main main = new Main();
        main.switchToSignUp("sign_up.fxml");
    }
}
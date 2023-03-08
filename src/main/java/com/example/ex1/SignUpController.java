package com.example.ex1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.service.Service;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpController {
    Service srv;
    UtilizatorValidator val;

    @FXML
    private TextField signUpEmail;
    @FXML
    private TextField signUpNume;
    @FXML
    private TextField signUpPrenume;
    @FXML
    private PasswordField signUpPw;

    @FXML
    private Label eroare;

    public void setSignUpService(Service service) {
        this.srv=service;
        this.val = new UtilizatorValidator();
    }

    public void logIn() throws IOException{
        Main main = new Main();
        main.switchToLogIn("login.fxml");
    }

    public void signUp() throws  IOException{
        String email = signUpEmail.getText();
        String nume = signUpNume.getText();
        String prenume = signUpPrenume.getText();
        String pw = signUpPw.getText();

        Utilizator userTest = new Utilizator(email, prenume, nume, String.valueOf(pw.hashCode()));

        if (signUpEmail.getText().isEmpty() || signUpNume.getText().isEmpty() || signUpPrenume.getText().isEmpty() || signUpPw.getText().isEmpty()) {
            eroare.setText("Date invalide");
            eroare.setVisible(true);
        }
        else if (srv.searchUtilizator(email) != null) {
            eroare.setText("Email existent");
            eroare.setVisible(true);
        }
        else {

            try {
                val.validate(userTest);
                eroare.setVisible(false);
                srv.addUtilizator(email, prenume, nume, String.valueOf(pw.hashCode()));
                Main main = new Main();
                main.switchToMain("main.fxml", email);
            } catch (ValidationException e) {
                eroare.setText("Date invalide");
                eroare.setVisible(true);
            }
        }
    }
}

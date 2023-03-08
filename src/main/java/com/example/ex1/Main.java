package com.example.ex1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import socialnetwork.repository.database.*;
import socialnetwork.service.Service;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 *  1. Parole (functia HashCode, salvam hashcode in bd) ✔
 *  2. Service-uri mai multe ✔✔✔✔✔✔✔✔✔
 *  3. Main window design
 *  4. DATA la cereri prietenie ✔
 *  5. search bar-uri pt prieteni/cereri/evenimente (substring matching)
 */


public class Main extends Application {
    CereriPrietenieDataBase crdb = new CereriPrietenieDataBase("jdbc:postgresql://localhost:5432/academic2","postgres", "postgres");
    UtilizatorDataBase userdb = new UtilizatorDataBase("jdbc:postgresql://localhost:5432/academic2","postgres", "postgres");
    PrietenieDataBase prdb = new PrietenieDataBase("jdbc:postgresql://localhost:5432/academic2","postgres", "postgres");
    MessageDataBase msgdb = new MessageDataBase("jdbc:postgresql://localhost:5432/academic2","postgres", "postgres");
    MessageRecipientDataBase rpdb = new MessageRecipientDataBase("jdbc:postgresql://localhost:5432/retea","postgres", "postgres");

    Service service = new Service(userdb, prdb, crdb, msgdb, rpdb);

    private static Stage currentStage;

    @Override
    public void start(Stage stage) throws IOException {
        currentStage = stage;

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("login.fxml"));
        AnchorPane root=loader.load();
        LogInController controller=loader.getController();
        controller.setLogInService(service);
        currentStage.setScene(new Scene(root, 559, 311));
        currentStage.setTitle("Log in");
        currentStage.show();
    }

    public void switchToMain(String fxml,String email) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
        AnchorPane root=loader.load();
        MainController controller=loader.getController();
        controller.setUserService(service,email);
        currentStage.setScene(new Scene(root, 900, 600));
        currentStage.setTitle("Main page");
        currentStage.show();
    }

    public void switchToLogIn(String fxml) throws IOException{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
        AnchorPane root=loader.load();
        LogInController controller=loader.getController();
        controller.setLogInService(service);
        currentStage.setScene(new Scene(root, 559, 311));
        currentStage.setTitle("Log in");
        currentStage.show();
    }

    public void switchToSignUp(String fxml) throws IOException{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
        AnchorPane root=loader.load();
        SignUpController controller=loader.getController();
        controller.setSignUpService(service);
        currentStage.setScene(new Scene(root, 474, 500));
        currentStage.setTitle("Sign up");
        currentStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
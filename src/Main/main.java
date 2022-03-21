package Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * This was a project for school - a basic inventory management system with no attached database.
 * All information is updated in ram and nothing is saved.
 *
 * FUTURE ENHANCEMENT: My proposed future improvement to this project is to add a way to add new parts while adding
 * or modifying a product. Should a manufacturing company have a specialty product that has bespoke parts, this would limit
 * the number of screens a user has to go through in order to add the new product and associated parts to the inventory management system.
 */

public class main extends Application{
    /**
     * This is the main class that creates the main screen application for the Inventory Management Program.
     * @param primaryStage: The view for the program.
     * @throws Exception from FXMLLoader.
     */



@Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Main.fxml")));
        primaryStage.setTitle("Main Screen - Inventory Management");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * @param args arguments from command line
     */

    public static void main(String[] args){
        launch(args);
    }
}

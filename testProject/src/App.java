import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controllers.Controller;
 
public class App extends Application {
    @Override
    public void start(Stage primaryStage){
        Parent root;
        try{
           // File f = new File("interfaces/Scene.fxml");

           FXMLLoader loader = new FXMLLoader(getClass().getResource("interfaces/Scene.fxml"));
           Parent parent = loader.load(); // Note the loader must be loaded before you can access the controller.
           Controller controller = loader.getController();
            Scene scene = new Scene(parent);
      
            primaryStage.setTitle("Hello new!");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            controller.setStage(primaryStage);
            primaryStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
    

 public static void main(String[] args) {
        launch(args);
    }
}
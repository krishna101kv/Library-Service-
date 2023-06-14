package application;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/** Main application*/
public class Main extends Application {
  public static void main(String[] args) { 
	  launch(args);
  }
  public void start(Stage primaryStage) throws IOException {
	  Parent loginWindow = FXMLLoader.load(getClass().getResource("/views/LoginWindow.fxml"));
      Scene loginWindowScene = new Scene(loginWindow);
      primaryStage.setTitle("LIBRARY SERVICES");
      primaryStage.setScene(loginWindowScene);
      primaryStage.show();
  }
}
package softwareSimilarityChecker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MainUI.fxml"));
        Parent root = loader.load();
        mainController controller = loader.getController();
        Scene parentRoot = new Scene(root);
        controller.initStage(primaryStage);
        primaryStage.setTitle("Code Plagiarism Checker");
        primaryStage.setScene(parentRoot);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

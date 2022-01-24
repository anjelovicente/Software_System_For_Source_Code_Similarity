package softwareSimilarityChecker;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/*
    A controller class for the main menu screen of the project
 */
public class mainController {
    @FXML private Stage stage;
    // Pane
    @FXML private ScrollPane aboutPane;
    // Shapes
    @FXML private Circle circleGIF;
    // Containers
    @FXML private TextField folderTextField;
    // Buttons
    @FXML private Button startButton;
    @FXML private Button aboutButton;
    @FXML private Button exitButton;
    @FXML private Button searchButton;
    @FXML private Button continueButton;
    @FXML private Button backToMainMenuButton;
    @FXML private Button checkerButton;
    @FXML private Button metricsButton;
    @FXML private Button backToSearchMenuButton;
    // Others
    @FXML private File file;

    // Initializes the displayed GIF depicting lines of code as a circular image
    public void initialize() {
        File codingFile = new File("assets/coding.gif");
        Image codingImage = new Image(codingFile.toURI().toString(), false);
        circleGIF.setFill(new ImagePattern(codingImage));
    }

    // Sets the passed stage as the stage to be used for this controller
    public void initStage(Stage stage) {
        this.stage = stage;
    }

    // Displays the contents of the screen when the "START" button is pressed
    public void setStartButton() {
        startButton.setVisible(false);
        aboutButton.setVisible(false);
        exitButton.setVisible(false);

        folderTextField.setVisible(true);
        searchButton.setVisible(true);
        continueButton.setVisible(true);
        continueButton.setDisable(true);
        backToMainMenuButton.setVisible(true);
    }

    // Displays the contents of the screen when the "ABOUT" button is pressed
    public void setAboutButton() {
        startButton.setVisible(false);
        aboutButton.setVisible(false);
        exitButton.setVisible(false);

        aboutPane.setVisible(true);
        backToMainMenuButton.setVisible(true);
    }

    // Exits the program
    public void setExitButton() {
        stage.close();
    }

    // Displays the contents of the screen when the "SEARCH" button is pressed
    public void setSearchButton() {
        // Creates a new stage for the file directory
        Stage resourceStage = new Stage();
        // Allows the user to choose a folder from the file directory
        DirectoryChooser directoryChooser = new DirectoryChooser();
        file = directoryChooser.showDialog(resourceStage);

        // Checks if the user chose a folder
        if (file != null) {
            folderTextField.setText(file.toString());
            continueButton.setDisable(false);
        } else if (!folderTextField.getText().isEmpty()) {
            // Checks if the user chose another file after choosing a while ago
            file = new File(folderTextField.getText());
        }
    }

    // Displays the contents of the screen when the "CONTINUE" button is pressed
    public void setContinueButton() {
        folderTextField.setVisible(false);
        searchButton.setVisible(false);
        continueButton.setVisible(false);
        backToMainMenuButton.setVisible(false);

        checkerButton.setVisible(true);
        metricsButton.setVisible(true);
        backToSearchMenuButton.setVisible(true);
    }

    /*
     Clears the contents of the text field for the folder name before going back to the main menu; done upon
     clicking "BACK" button
     */
    public void setBackToMainMenuButton() {
        folderTextField.clear();

        folderTextField.setVisible(false);
        searchButton.setVisible(false);
        continueButton.setVisible(false);
        backToMainMenuButton.setVisible(false);
        aboutPane.setVisible(false);

        startButton.setVisible(true);
        aboutButton.setVisible(true);
        exitButton.setVisible(true);
    }

    // Displays the contents of the screen when the "SIMILARITY CHECKER" button is pressed
    public void setCheckerButton() throws IOException {
        checkerButton.setVisible(false);
        metricsButton.setVisible(false);
        backToSearchMenuButton.setVisible(false);

        // Closes the stage for the main menu
        stage.close();
        // Instantiates a new stage fo the matrix to be used for similarity checker
        // Patterned to the codes found in Main.java of this project
        Stage matrixStage = new Stage();
        matrixStage.setMaximized(true);
        FXMLLoader matrixLoader = new FXMLLoader();
        matrixLoader.setLocation(getClass().getResource("MatrixUI.fxml"));
        Parent matrixRoot = matrixLoader.load();
        matrixController controller = matrixLoader.getController();
        controller.setData(folderTextField, file, matrixStage);
        Scene parentRoot = new Scene(matrixRoot);
        matrixStage.setTitle("Code Plagiarism Checker");
        matrixStage.setResizable(false);
        matrixStage.setScene(parentRoot);
        matrixStage.show();
    }

    // Displays the contents of the screen when the "SOFTWARE METRICS" button is pressed
    public void setMetricsButton() throws IOException {
        checkerButton.setVisible(false);
        metricsButton.setVisible(false);
        backToSearchMenuButton.setVisible(false);

        // Closes the stage for the main menu
        stage.close();
        // Instantiates a new stage fo the matrix to be used for software metrics
        // Patterned to the codes found in Main.java of this project
        Stage metricsStage = new Stage();
        metricsStage.setMaximized(true);
        FXMLLoader metricsLoader = new FXMLLoader();
        metricsLoader.setLocation(getClass().getResource("MetricsUI.fxml"));
        Parent metricsRoot = metricsLoader.load();
        metricsController controller = metricsLoader.getController();
        controller.setData(folderTextField, file, metricsStage);
        Scene parentRoot = new Scene(metricsRoot);
        metricsStage.setTitle("Code Plagiarism Checker");
        metricsStage.setResizable(false);
        metricsStage.setScene(parentRoot);
        metricsStage.show();
    }

    /*
     Displays again the UI for choosing a file upon clicking the "BACK" button in the pane for choosing
     similarity checker or software metrics
     */
    public void setBackToSearchMenuButton() {
        checkerButton.setVisible(false);
        metricsButton.setVisible(false);
        backToSearchMenuButton.setVisible(false);

        // Makes sure that the content of the folder text field is cleared
        folderTextField.clear();
        folderTextField.setVisible(true);
        searchButton.setVisible(true);
        continueButton.setVisible(true);
        continueButton.setDisable(true);
        backToMainMenuButton.setVisible(true);
    }
}

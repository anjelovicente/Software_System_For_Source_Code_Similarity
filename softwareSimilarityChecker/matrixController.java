package softwareSimilarityChecker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*
    A controller class for the UI of similarity checker component of this project
 */
public class matrixController {
    // For the table displaying the name of the files being compared
    @FXML private TableView<nameOfFiles> filesTableView;
    @FXML private TableColumn<nameOfFiles, String> filesTableColumn;
    // For the table displaying the top 10 comparison with the highest scores
    @FXML private TableView<highestScoresData> highestScoresDataTableView;
    @FXML private TableColumn<highestScoresData, String> highestScoresDataTableColumn;
    // For displaying the absolute path of the chosen folder while on the "Search" or "Continue" screen"
    @FXML private TextField folderTextField;
    // For displaying the absolute path of the chosen folder while on the matrix screen
    @FXML private Label projectTitle;
    // A button for going back to the main menu screen
    // For the scroll pane displaying the GridPane of the matrix
    @FXML private ScrollPane scrollPane;
    // For displaying the matrix
    @FXML private GridPane matrix;
    // A container for the file (folder) chosen
    @FXML private HBox bottomBox;
    @FXML private File file;
    // For the entire stage of the matrix
    @FXML private Stage matrixStage;

    // Container for the highest scores
    private ArrayList<Float> highestScores = new ArrayList<>();
    // Container for the two compared files in relation to the stored highest scores
    private ArrayList<String> namesOfHighestScores = new ArrayList<>();
    // Container for the names of the files inside the chosen folder
    private String[] files;

    // A method for the initializer of the table for the list of names of the files inside the chosen folder
    private void setFilesTableView() {
        filesTableColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        filesTableView.setItems(getFileNames(files));
    }

    // A method for initializing the table displaying the top ten comparisons with the highest scores
    private void setHighestScoresDataTableView() {
        highestScoresDataTableColumn.setCellValueFactory(new PropertyValueFactory<>("nameAndScore"));
        highestScoresDataTableView.setItems(getHighestScoresData(namesOfHighestScores));
        highestScoresDataTableView.setColumnResizePolicy(p -> true);
    }

    // A method for creating the list of names of the files inside the chosen folder
    public ObservableList<nameOfFiles> getFileNames(String[] names) {
        ObservableList<nameOfFiles> fileNames = FXCollections.observableArrayList();

        // Traverses through all the names of the files
        for (int i = 0; i < names.length; i++) {
            fileNames.add(new nameOfFiles(i + 1, names[i]));
        }
        return fileNames;
    }

    // A method for creating the list of the top ten comparisons with the highest scores
    private ObservableList<highestScoresData> getHighestScoresData(ArrayList<String> namesAndScores) {
        ObservableList<highestScoresData> data = FXCollections.observableArrayList();

        // Traverses through all the names of the highest comparisons with the corresponding scores
        for (String namesAndScore : namesAndScores) {
            data.add(new highestScoresData(namesAndScore));
        }
        return data;
    }

    // A method for setting the data to be displayed on the screen based on the similarity checker
    public void setData(TextField folderTextField, File file, Stage matrixStage) {
        // Sets the name of the folder to be displayed on top of the screen
        this.folderTextField = folderTextField;
        projectTitle.setText("Folder Name: " + folderTextField.getText());
        this.file = file;
        this.matrixStage = matrixStage;
        checkOnAction();
        setFilesTableView();
        setHighestScoresDataTableView();
    }

    // A bunch of codes for doing the comparison of files and creating and displaying the matrix on the screen
    private void checkOnAction() {
        projectFileSearcher getF = new projectFileSearcher();
        if (file == null) {
            folderTextField.setText("No File Directory Chosen");
        } else {
            gridColorMaker gcm = new gridColorMaker();
            // Initializes the matrix
            // Stores the names of the files currently handled by "file"
            files = file.list();
            assert files != null;

            matrix = gcm.matrixGridPaneMaker(5, 5, files);

            // Create a loop for reading the files
            ArrayList<File[]> projFiles = getF.searchFiles(file);
            int rows;
            int cols;
            float[][] scores;
            int scoresCounter = 0;
            similarityChecker sc = new similarityChecker();
            getFileInArray gFA = new getFileInArray();
            float simScore, totalSimScore = 0, totalCount = 0;

            // Puts a file on hold and compare it to the rest of the files, also with itself

            // All source codes in one folder
            // No other folders found inside the chosen folder of the user
            if (projFiles.size() == 1) {
                File[] proj1 = projFiles.get(0);
                File[] proj2 = projFiles.get(0);
                rows = proj1.length;
                cols = proj2.length;
                // Utilizes a 2D float-valued array for the score of the similarity checker
                scores = new float[cols][rows];
                for (int x = 0; x < proj1.length; x++) {
                    for (int y = 0; y < proj2.length; y++) {
                        // Reads two files at the same time that will be compared to each other
                        File f1 = gFA.getFiles(proj1, x);
                        File f2 = gFA.getFiles(proj2, y);
                        try {
                            // Passes the two file in the class for determining the similarity score
                            simScore = sc.check(f1, f2);
                            scores[x][y] = simScore;
                            scoresCounter++;
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                    }
                }
                // Instantiates gridColorMaker
                for (int x = 0; x < proj1.length; x++) {
                    for (int y = 0; y < proj2.length; y++) {
                        // Creates a colored rectangle using the checkColor method of gridColorMaker
                        StackPane coloredScore = gcm.matrixCheckColor(scores[x][y]);
                        // Adds the rectangle in a to-be-created cell of the gridPane
                        matrix.add(coloredScore, x + 1, y + 1);
                        // for Top 10 highest scores
                        // Excludes the scenario when a file is compared to itself
                        if (x != y) {
                            if (highestScores.size() == 0) {
                                highestScores.add(scores[x][y]);
                                namesOfHighestScores.add(files[x] + " and " + files[y] + " : " + scores[x][y]);
                            } else if (highestScores.size() < 10) {
                                // Checks where to place the next highest scores in the array not yet size of 10
                                for (int i = 0; i < highestScores.size(); i++) {
                                    if (Math.max(highestScores.get(i), scores[x][y]) == scores[x][y]) {
                                        highestScores.add(i, scores[x][y]);
                                        namesOfHighestScores.add(i, files[x] + " and " + files[y] + " : " + scores[x][y]);
                                        break;
                                    }
                                }
                            } else if (highestScores.size() == 10) {
                                // Limits the contents of the highest scores array to 10
                                // Checks the placement of the file read on the array
                                for (int i = 0; i < highestScores.size(); i++) {
                                    if (Math.max(highestScores.get(i), scores[x][y]) == scores[x][y]) {
                                        highestScores.remove(highestScores.get(i));
                                        highestScores.add(i, scores[x][y]);
                                        namesOfHighestScores.remove(namesOfHighestScores.get(i));
                                        namesOfHighestScores.add(i, files[x] + " and " + files[y] + " : " + scores[x][y]);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //By Folders
            else {
                rows = projFiles.size();
                cols = projFiles.size();
                scores = new float[cols][rows];
                for (int x = 0; x < projFiles.size(); x++) {
                    for (int y = 0; y < projFiles.size(); y++) {
                        File[] proj1 = projFiles.get(x);
                        File[] proj2 = projFiles.get(y);
                        try {
                            for (File f1 : proj1) {
                                for (File f2 : proj2) {
                                    float simScore1 = sc.check(f1, f2);
                                    totalSimScore = totalSimScore +simScore1;
                                    totalCount++;
                                }
                            }
                            float aveSimScore = totalSimScore / totalCount;
                            aveSimScore = sc.check(x,y, aveSimScore);
                            scores[x][y] = aveSimScore;
                            totalSimScore=0;
                            totalCount=0;
                            scoresCounter = scoresCounter + 2;
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                    }
                }
                // Instantiates gridColorMaker
                for (int x = 0; x < projFiles.size(); x++) {
                    for (int y = 0; y < projFiles.size(); y++) {
                        // Creates a colored rectangle using the checkColor method of gridColorMaker
                        StackPane coloredScore = gcm.matrixCheckColor(scores[x][y]);
                        // Adds the rectangle in a to-be-created cell of the gridPane
                        matrix.add(coloredScore, x + 1, y + 1);

                        // for Top 10 highest scores
                        if (x != y) {
                            if (highestScores.size() == 0) {
                                highestScores.add(scores[x][y]);
                                namesOfHighestScores.add(files[x] + " and " + files[y] + " : " + scores[x][y]);
                            } else if (highestScores.size() < 10) {
                                for (int i = 0; i < highestScores.size(); i++) {
                                    if (Math.max(highestScores.get(i), scores[x][y]) == scores[x][y]) {
                                        highestScores.add(i, scores[x][y]);
                                        namesOfHighestScores.add(i, files[x] + " and " + files[y] + " : " + scores[x][y]);
                                        break;
                                    }
                                }
                            } else if (highestScores.size() == 10) {
                                for (int i = 0; i < highestScores.size(); i++) {
                                    if (Math.max(highestScores.get(i), scores[x][y]) == scores[x][y]) {
                                        highestScores.remove(highestScores.get(i));
                                        highestScores.add(i, scores[x][y]);
                                        namesOfHighestScores.remove(namesOfHighestScores.get(i));
                                        namesOfHighestScores.add(i, files[x] + " and " + files[y] + " : " + scores[x][y]);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // Sets up matrix as a content of the scrollPane
            scrollPane.setContent(matrix);
        }

    }

    // A method for bringing back the UI to the main menu
    public void setHomeButton() throws IOException {
        matrixStage.close();
        Stage mainStage = new Stage();
        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("MainUI.fxml"));
        Parent matrixRoot = mainLoader.load();
        mainController controller = mainLoader.getController();
        controller.initStage(mainStage);
        Scene parentRoot = new Scene(matrixRoot);
        mainStage.setTitle("Code Plagiarism Checker");
        mainStage.setResizable(false);
        mainStage.setScene(parentRoot);
        mainStage.show();

    }

    // A method for bringing back the UI to the pane wherein the user can choose again either checker or metrics
    public void setGoToMetricsButton(ActionEvent actionEvent) throws IOException {
        matrixStage.close();
        Stage metricsStage = new Stage();
        FXMLLoader matrixLoader = new FXMLLoader();
        matrixLoader.setLocation(getClass().getResource("MetricsUI.fxml"));
        Parent metricsRoot = matrixLoader.load();
        metricsController controller = matrixLoader.getController();
        metricsStage.setMaximized(true);
        controller.setData(folderTextField, file, metricsStage);
        Scene parentRoot = new Scene(metricsRoot);
        metricsStage.setTitle("Code Plagiarism Checker");
        metricsStage.setResizable(false);
        metricsStage.setScene(parentRoot);
        metricsStage.show();
    }
}

package softwareSimilarityChecker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class metricsController {
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
    @FXML private GridPane metrics;
    @FXML File file;
    @FXML Stage metricsStage;
    @FXML ComboBox<String> metricChooser;
    private String[] files;
    private metricTableData mtd = new metricTableData();
    private ArrayList<String> namesOfHighestScores = new ArrayList<>();

    public metricsController() {
    }

    public void setData(TextField folderTextField, File file, Stage metricsStage) {
        this.folderTextField = folderTextField;
        projectTitle.setText("Folder Name: " + folderTextField.getText());
        this.file = file;
        this.metricsStage = metricsStage;
        metricsOnAction();
        setHighestScoresDataTableView();
    }

    private void setMetricsChooser(ArrayList<String> metrics) {
        // Remove the column title for file name
        metrics.remove(0);
        ObservableList<String> metricsChooserObservableList = FXCollections.observableArrayList();

        metricsChooserObservableList.addAll(metrics);
        metricChooser.setItems(metricsChooserObservableList);
        metricChooser.setValue(metricsChooserObservableList.get(0));
    }

    private void metricsOnAction() {
        projectFileSearcher getF = new projectFileSearcher();
        if (file == null) {
            folderTextField.setText("No File Directory Chosen");
        } else {
            gridColorMaker gcm = new gridColorMaker();
            // Initializes the matrix

            // Stores the names of the files currently handled by "file"
            files = file.list();
            assert files != null;

            metrics = gcm.metricsGridPaneMaker(50, 25, files);

            // Create a loop for reading the files
            ArrayList<File[]> projFiles = getF.searchFiles(file);
            softwareMetrics sm = new softwareMetrics();
            getFileInArray gFA = new getFileInArray();

            int programLength, vocabularySize;
            float programVolume, difficulty, programLevel, effortToImplement, timeToImplement, deliveredBugs;
            if (projFiles.size() == 1) {
                File[] project = projFiles.get(0);
                for (int i = 0; i < project.length; i++) {
                    File projectFile = gFA.getFiles(project, i);
                    sm.getMetrics(projectFile);
                    programLength = sm.getProgramLength();
                    vocabularySize = sm.getVocabularySize();
                    programVolume = sm.getProgramVolume();
                    difficulty = sm.getDifficulty();
                    programLevel = sm.getProgramLevel();
                    effortToImplement = sm.getEffortToImplement();
                    timeToImplement = sm.getTimeToImplement();
                    deliveredBugs = sm.getDeliveredBugs();
                    StackPane programLengthStackPane = gcm.intMetricScoreMaker(programLength, "Program Length");
                    metrics.add(programLengthStackPane, 1, i + 1);
                    StackPane vocabularySizeStackPane = gcm.intMetricScoreMaker(vocabularySize, "Vocabulary Size");
                    metrics.add(vocabularySizeStackPane, 2, i + 1);
                    StackPane programVolumeStackPane = gcm.floatMetricsScoreMaker(programVolume, "Program Volume");
                    metrics.add(programVolumeStackPane, 3, i + 1);
                    StackPane difficultyStackPane = gcm.floatMetricsScoreMaker(difficulty, "Difficulty");
                    metrics.add(difficultyStackPane, 4, i + 1);
                    StackPane programLevelStackPane = gcm.floatMetricsScoreMaker(programLevel, "Program Level");
                    metrics.add(programLevelStackPane, 5, i + 1);
                    StackPane effortToImplementStackPane = gcm.floatMetricsScoreMaker(effortToImplement, "Effort to Implement");
                    metrics.add(effortToImplementStackPane, 6, i + 1);
                    StackPane timeToImplementStackPane = gcm.floatMetricsScoreMaker(timeToImplement, "Time to Implement");
                    metrics.add(timeToImplementStackPane, 7, i + 1);
                    StackPane deliveredBugsStackPane = gcm.floatMetricsScoreMaker(deliveredBugs, "Delivered Bugs");
                    metrics.add(deliveredBugsStackPane, 8, i + 1);

                    mtd.addData(programLength, vocabularySize, programVolume, difficulty, programLevel, effortToImplement, timeToImplement, deliveredBugs);
                }
            } else {
                int totalProgramLength = 0, totalVocabularySize = 0, totalCount = 0;
                float totalProgramVolume = 0, totalDifficulty = 0, totalProgramLevel = 0, totalEffortToImpelement = 0, totalTimeToImplement = 0, totalDeliveredBugs = 0;
                for (int i = 0; i < projFiles.size(); i++) {
                    File[] proj1 = projFiles.get(i);
                    for (File file : proj1) {
                        sm.getMetrics(file);
                        totalProgramLength = sm.getProgramLength();
                        totalVocabularySize = sm.getVocabularySize();
                        totalProgramVolume = sm.getProgramVolume();
                        totalDifficulty = sm.getDifficulty();
                        totalProgramLevel = sm.getProgramLevel();
                        totalEffortToImpelement = sm.getEffortToImplement();
                        totalTimeToImplement = sm.getTimeToImplement();
                        totalDeliveredBugs = sm.getDeliveredBugs();
                        totalCount++;
                    }
                    totalProgramLength = totalProgramLength / totalCount;
                    totalVocabularySize = totalVocabularySize / totalCount;
                    totalProgramVolume = totalProgramVolume / totalCount;
                    totalDifficulty = totalDifficulty / totalCount;
                    totalProgramLevel = totalProgramLevel / totalCount;
                    totalEffortToImpelement = totalEffortToImpelement / totalCount;
                    totalTimeToImplement = totalTimeToImplement / totalCount;
                    totalDeliveredBugs = totalDeliveredBugs / totalCount;

                    StackPane programLengthStackPane = gcm.intMetricScoreMaker(sm.getProgramLength(), "Program Length");
                    metrics.add(programLengthStackPane, 1, i + 1);
                    StackPane vocabularySizeStackPane = gcm.intMetricScoreMaker(sm.getVocabularySize(), "Vocabulary Size");
                    metrics.add(vocabularySizeStackPane, 2, i + 1);
                    StackPane programVolumeStackPane = gcm.floatMetricsScoreMaker(sm.getProgramVolume(), "Program Volume");
                    metrics.add(programVolumeStackPane, 3, i + 1);
                    StackPane difficultyStackPane = gcm.floatMetricsScoreMaker(sm.getDifficulty(), "Difficulty");
                    metrics.add(difficultyStackPane, 4, i + 1);
                    StackPane programLevelStackPane = gcm.floatMetricsScoreMaker(sm.getProgramLevel(), "Program Level");
                    metrics.add(programLevelStackPane, 5, i + 1);
                    StackPane effortToImplementStackPane = gcm.floatMetricsScoreMaker(sm.getEffortToImplement(), "Effort to Implement");
                    metrics.add(effortToImplementStackPane, 6, i + 1);
                    StackPane timeToImplementStackPane = gcm.floatMetricsScoreMaker(sm.getTimeToImplement(), "Time to Implement");
                    metrics.add(timeToImplementStackPane, 7, i + 1);
                    StackPane deliveredBugsStackPane = gcm.floatMetricsScoreMaker(sm.getDeliveredBugs(), "Delivered Bugs");
                    metrics.add(deliveredBugsStackPane, 8, i + 1);

                    mtd.addData(totalProgramLength, totalVocabularySize, totalProgramVolume, totalDifficulty, totalProgramLevel, totalEffortToImpelement, totalTimeToImplement, totalDeliveredBugs);
                }
            }
            scrollPane.setContent(metrics);
            setMetricsChooser(gcm.columnTitles);
            setMetricsTableData();
        }
    }

    public void setHomeButton() throws IOException {
        metricsStage.close();
        Stage mainStage = new Stage();
        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("MainUI.fxml"));
        Parent metricsRoot = mainLoader.load();
        mainController controller = mainLoader.getController();
        controller.initStage(mainStage);
        Scene parentRoot = new Scene(metricsRoot);
        mainStage.setTitle("Code Plagiarism Checker");
        mainStage.setResizable(false);
        mainStage.setScene(parentRoot);
        mainStage.show();
    }

    public void setGoToMatrixButton() throws IOException {
        metricsStage.close();
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

    public void setMetricsTableData() {
        namesOfHighestScores.clear();
        highestScoresDataTableColumn.getColumns().clear();
        String chosenMetric = metricChooser.getValue();
        projectFileSearcher getF = new projectFileSearcher();
        ArrayList<File[]> projFiles = getF.searchFiles(file);
        getFileInArray gFA = new getFileInArray();

        if (projFiles.size() == 1) {
            File[] project = projFiles.get(0);
            for (int i = 0; i < project.length; i++) {
                File projectFile = gFA.getFiles(project, i);
                if (chosenMetric.equals("Program Length") || chosenMetric.equals("Vocabulary Size")) {
                    ArrayList<Integer> data;
                    ArrayList<Integer> highestScores = new ArrayList<>();

                    if (chosenMetric.equals("Program Length")) {
                        data = mtd.programLengthData;
                    } else {
                        data = mtd.vocabularySizeData;
                    }

                    for (int j = 0; j < data.size(); j++) {
                        int score = data.get(j);
                        if (highestScores.size() == 0) {
                            highestScores.add(score);
                            namesOfHighestScores.add(file.getAbsolutePath() + " : " + score);
                        } else if (highestScores.size() < 10) {
                            for (int k = 0; k < highestScores.size(); k++) {
                                if (Math.max(highestScores.get(k), score) == score) {
                                    highestScores.add(k, score);
                                    namesOfHighestScores.add(k,projectFile + " : " + score);
                                    break;
                                }
                            }
                        } else if (highestScores.size() == 10) {
                            for (int k = 0; k < highestScores.size(); k++) {
                                if (Math.max(highestScores.get(k), score) == score) {
                                    highestScores.remove(highestScores.get(k));
                                    highestScores.add(k, score);
                                    namesOfHighestScores.remove(namesOfHighestScores.get(k));
                                    namesOfHighestScores.add(k,projectFile + " : " + score);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    ArrayList<Float> data = new ArrayList<>();
                    ArrayList<Float> highestScores = new ArrayList<>();
                    switch (chosenMetric) {
                        case "Program Volume":
                            data = mtd.programVolumeData;
                            break;
                        case "Difficulty":
                            data = mtd.difficultyData;
                            break;
                        case "Program Level":
                            data = mtd.programLevelData;
                            break;
                        case "Effort to Implement":
                            data = mtd.effortToImplementData;
                            break;
                        case "Time to Implement":
                            data = mtd.timeToImplementData;
                            break;
                        case "No. of Delivered Bugs":
                            data = mtd.deliveredBugsData;
                            break;
                        default:
                            break;
                    }

                    for (float score : data) {
                        if (highestScores.size() == 0) {
                            highestScores.add(score);
                            namesOfHighestScores.add(projectFile + " : " + score);
                        } else if (highestScores.size() < 10) {
                            for (int k = 0; k < highestScores.size(); k++) {
                                if (Math.max(highestScores.get(k), score) == score) {
                                    highestScores.add(k, score);
                                    namesOfHighestScores.add(k, projectFile + " : " + score);
                                    break;
                                }
                            }
                        } else if (highestScores.size() == 10) {
                            for (int k = 0; k < highestScores.size(); k++) {
                                if (Math.max(highestScores.get(k), score) == score) {
                                    highestScores.remove(highestScores.get(k));
                                    highestScores.add(k, score);
                                    namesOfHighestScores.remove(namesOfHighestScores.get(k));
                                    namesOfHighestScores.add(k, projectFile + " : " + score);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < projFiles.size(); i++) {
                File[] proj1 = projFiles.get(i);
                if (chosenMetric.equals("Program Length") || chosenMetric.equals("Vocabulary Size")) {
                    ArrayList<Integer> data;
                    ArrayList<Integer> highestScores = new ArrayList<>();

                    if (chosenMetric.equals("Program Length")) {
                        data = mtd.programLengthData;
                    } else {
                        data = mtd.vocabularySizeData;
                    }

                    for (int j = 0; j < data.size(); j++) {
                        int score = data.get(j);
                        if (highestScores.size() == 0) {
                            highestScores.add(score);
                            namesOfHighestScores.add(Arrays.toString(proj1) + " : " + score);
                        } else if (highestScores.size() < 10) {
                            for (int k = 0; k < highestScores.size(); k++) {
                                if (Math.max(highestScores.get(k), score) == score) {
                                    highestScores.add(k, score);
                                    namesOfHighestScores.add(k,Arrays.toString(proj1) + " : " + score);
                                    break;
                                }
                            }
                        } else if (highestScores.size() == 10) {
                            for (int k = 0; k < highestScores.size(); k++) {
                                if (Math.max(highestScores.get(k), score) == score) {
                                    highestScores.remove(highestScores.get(k));
                                    highestScores.add(k, score);
                                    namesOfHighestScores.remove(namesOfHighestScores.get(k));
                                    namesOfHighestScores.add(k,Arrays.toString(proj1) + " : " + score);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    ArrayList<Float> data = new ArrayList<>();
                    ArrayList<Float> highestScores = new ArrayList<>();
                    switch (chosenMetric) {
                        case "Program Volume":
                            data = mtd.programVolumeData;
                            break;
                        case "Difficulty":
                            data = mtd.difficultyData;
                            break;
                        case "Program Level":
                            data = mtd.programLevelData;
                            break;
                        case "Effort to Implement":
                            data = mtd.effortToImplementData;
                            break;
                        case "Time to Implement":
                            data = mtd.timeToImplementData;
                            break;
                        case "No. of Delivered Bugs":
                            data = mtd.deliveredBugsData;
                            break;
                        default:
                            break;
                    }

                    for (float score : data) {
                        if (highestScores.size() == 0) {
                            highestScores.add(score);
                            namesOfHighestScores.add(Arrays.toString(proj1) + " : " + score);
                        } else if (highestScores.size() < 10) {
                            for (int k = 0; k < highestScores.size(); k++) {
                                if (Math.max(highestScores.get(k), score) == score) {
                                    highestScores.add(k, score);
                                    namesOfHighestScores.add(k, Arrays.toString(proj1) + " : " + score);
                                    break;
                                }
                            }
                        } else if (highestScores.size() == 10) {
                            for (int k = 0; k < highestScores.size(); k++) {
                                if (Math.max(highestScores.get(k), score) == score) {
                                    highestScores.remove(highestScores.get(k));
                                    highestScores.add(k, score);
                                    namesOfHighestScores.remove(namesOfHighestScores.get(k));
                                    namesOfHighestScores.add(k, Arrays.toString(proj1) + " : " + score);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        /*
        if (projFiles.size() == 1) {
            File[] project = projFiles.get(0);
            for (int i = 0; i < project.length; i++) {
                File projectFile = gFA.getFileInArray(project, i);
            }
                if (chosenMetric.equals("Program Length") || chosenMetric.equals("Vocabulary Size")) {
                    ArrayList<Integer> data;
                    ArrayList<Integer> highestScores = new ArrayList<>();

                    if (chosenMetric.equals("Program Length")) {
                        data = mtd.programLengthData;
                    } else {
                        data = mtd.vocabularySizeData;
                    }

                    if (highestScores.size() == 0) {
                        highestScores.add(data.get(i));
                        namesOfHighestScores.add(files[i] + " : " + data.get(i));
                    }
                } else {

                }
            for (int i = 0; i < data.size(); i++) {
                if (highestScores.size() == 0) {

                } else if (highestScores.size() < 10) {
                    for (int j = 0; j < highestScores.size(); j++) {
                        int intData = data.get(i);
                        if (Math.max(highestScores.get(j), intData) == intData) {
                            highestScores.add(j, data.get(i));
                            namesOfHighestScores.add(j, files[i] + " : " + intData);
                            break;
                        }
                    }
                } else if (highestScores.size() == 10) {
                    for (int j = 0; j < highestScores.size(); j++) {
                        int intData = data.get(i);
                        if (Math.max(highestScores.get(j), intData) == intData) {
                            highestScores.remove(highestScores.get(j));
                            highestScores.add(j, intData);
                            namesOfHighestScores.remove(namesOfHighestScores.get(j));
                            namesOfHighestScores.add(j, files[i] + " : " + intData);
                            break;
                        }
                    }
                }
            }

        }
        for (int i = 0; i < namesOfHighestScores.size(); i++) {
            System.out.println(namesOfHighestScores.get(i));
        }
        setHighestScoresDataTableView();

         */
    }

    public void setHighestScoresDataTableView() {
        highestScoresDataTableColumn.setCellValueFactory(new PropertyValueFactory<>("nameAndScore"));
        highestScoresDataTableView.setItems(getHighestScoresData(namesOfHighestScores));
        highestScoresDataTableView.setColumnResizePolicy(p -> true);
    }

    private ObservableList<highestScoresData> getHighestScoresData(ArrayList<String> namesAndScores) {
        ObservableList<highestScoresData> dataObservableList = FXCollections.observableArrayList();

        for (String namesAndScore : namesAndScores) {
            dataObservableList.add(new highestScoresData(namesAndScore));
        }

        return dataObservableList;
    }
}


package softwareSimilarityChecker;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;
/*
    This is a class for creating the grids that will display the details of the similarity checker and software
    metrics.
 */
public class gridColorMaker {
    public ArrayList<String> columnTitles = new ArrayList<>();

    // Initializes the GridPane for the similarity checker
    public GridPane matrixGridPaneMaker(double vGap, double hGap, String[] names) {
        GridPane gridPane = new GridPane();
        // Modifies the vertical and horizontal gap based on the preference of the developer
        gridPane.setVgap(vGap);
        gridPane.setHgap(hGap);

        // Prints on each grid the names of the files to be checked for similarity
        for (int i = 0; i < names.length; i++) {
            Label text = new Label(String.valueOf(i + 1));
            text.setFont(Font.font("Georgia", 12));
            GridPane.setHalignment(text, HPos.CENTER);
            gridPane.add(text, i + 1, 0);
        }

        // Has the same function with the previous for loop
        for (int i = 0; i < names.length; i++) {
            Label text = new Label(String.valueOf(i + 1));
            text.setFont(Font.font("Georgia", 12));
            GridPane.setHalignment(text, HPos.CENTER);
            gridPane.add(text, 0, i + 1);
        }
        // Returns the overall GridPane with names of the files
        return gridPane;

    }

    // Checks the color to be used for a specific score in the similarity checker
    public StackPane matrixCheckColor(float value) {
        // Creates a rectangle with a specified width and height
        Rectangle scoreRectangle = new Rectangle();
        scoreRectangle.setWidth(50);
        scoreRectangle.setHeight(25);

        // Assigns a color for the rectangle based on the specified score
        if (value >= 0 && value < 0.10) {
            scoreRectangle.setFill(Color.WHITE);
        } else if (value >= 0.10 && value < 0.20) {
            scoreRectangle.setFill(Color.rgb(255,255,255));
        } else if (value >= 0.20 && value < 0.30) {
            scoreRectangle.setFill(Color.rgb(252, 216, 208));
        } else if (value >= 0.30 && value < 0.40) {
            scoreRectangle.setFill(Color.rgb(253, 164, 145));
        } else if (value >= 0.40 && value < 0.50) {
            scoreRectangle.setFill(Color.rgb(250, 112, 82));
        } else if (value >= 0.50 && value < 0.60) {
            scoreRectangle.setFill(Color.rgb(254, 63, 22));
        } else if (value >= 0.60 && value < 0.70) {
            scoreRectangle.setFill(Color.rgb(220, 39, 0));
        } else if (value >= 0.70 && value < 0.80) {
            scoreRectangle.setFill(Color.rgb(173, 31, 0));
        } else if (value >= 0.80 && value < 0.90) {
            scoreRectangle.setFill(Color.rgb(120, 25, 0));
        } else {
            scoreRectangle.setFill(Color.rgb(91, 16, 0));
        }


        String scoreString = String.valueOf(value);
        // Truncates the displayed value on each rectangle
        scoreString = scoreString.substring(0, Math.min(scoreString.length(), 4));
        Text scoreText = new Text(scoreString);
        StackPane stackPane = new StackPane();
        // Adds the scoreRectangle and scoreText in a single rectangle
        stackPane.getChildren().addAll(scoreRectangle, scoreText);

        // Returns the stackPane containing the colored rectangle with score
        return stackPane;
    }

    // Initializes the GridPane for the software metrics
    public GridPane metricsGridPaneMaker(double vGap, double hGap, String[] names) {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(vGap);
        gridPane.setHgap(hGap);

        // Adds all the column titles
        columnTitles.add("File Name");
        columnTitles.add("Program Length");
        columnTitles.add("Vocabulary Size");
        columnTitles.add("Program Volume");
        columnTitles.add("Difficulty");
        columnTitles.add("Program Level");
        columnTitles.add("Effort to Implement");
        columnTitles.add("Time to Implement");
        columnTitles.add("No. of Delivered Bugs");

        // Assigns a specific color for each column of the software metrics
        for (int i = 0; i < columnTitles.size(); i++) {
            Label text = new Label();
            text.setText(columnTitles.get(i));
            text.setFont(Font.font("Georgia", 12));
            switch (i) {
                case 1:
                case 8:
                    text.setTextFill(Color.rgb(120, 25, 0));
                    break;
                case 2:
                    text.setTextFill(Color.rgb(80, 0, 72));
                    break;
                case 3:
                    text.setTextFill(Color.rgb(39, 23, 105));
                    break;
                case 4:
                    text.setTextFill(Color.rgb(0, 91, 94));
                    break;
                case 5:
                    text.setTextFill(Color.rgb(0, 101, 24));
                    break;
                case 6:
                    text.setTextFill(Color.rgb(137, 146, 0));
                    break;
                case 7:
                    text.setTextFill(Color.rgb(138, 102, 2));
                default:
                    break;
            }
            // Sets CENTER as the alignment of each column in the GridPane
            GridPane.setHalignment(text, HPos.CENTER);
            gridPane.add(text, i, 0);
        }

        // Displays the names of the files in the first column of the GridPane
        for (int i = 0; i < names.length; i++) {
            Label text = new Label();
            text.setText(names[i]);
            text.setFont(Font.font("Georgia", 12));
            GridPane.setHalignment(text, HPos.CENTER);
            gridPane.add(text, 0, i + 1);
        }

        return gridPane;
    }

    // Determines the color to be assigned on the rectangle of a metric depending on the score; For int valued items
    public StackPane intMetricScoreMaker(int value, String metricScoreName) {
        // Initializes a rectangle with the color to be obtained from the class metricScoreColor
        Rectangle rectangle = new Rectangle();
        metricScoreColor msc = new metricScoreColor();
        rectangle.setFill(msc.setRectangleColor(value, metricScoreName));
        rectangle.setWidth(50);
        rectangle.setHeight(25);

        // Does the same with the similarity checker; truncates the displayed value on the screen
        String intMetricScore = String.valueOf(value);
        intMetricScore = intMetricScore.substring(0, Math.min(intMetricScore.length(), 4));
        Text scoreText = new Text(intMetricScore);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rectangle, scoreText);

        return stackPane;
    }

    // Does the same job of intMetricsScoreMaker, just only for float-valued items
    public StackPane floatMetricsScoreMaker(float value, String metricScoreName) {
        Rectangle rectangle = new Rectangle();
        metricScoreColor msc = new metricScoreColor();
        rectangle.setFill(msc.setRectangleColor(value, metricScoreName));
        rectangle.setWidth(50);
        rectangle.setHeight(25);

        String floatMetricScore = String.valueOf(value);
        floatMetricScore = floatMetricScore.substring(0, Math.min(floatMetricScore.length(), 7));
        Text scoreText = new Text(floatMetricScore);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rectangle, scoreText);

        return stackPane;
    }
}

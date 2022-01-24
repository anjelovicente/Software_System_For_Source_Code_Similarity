package softwareSimilarityChecker;

import javafx.beans.property.SimpleStringProperty;

/*
    A class for transforming the String value so that it could be displayed on the tables of the similarity checker
    and software metrics
 */
public class highestScoresData {
    private SimpleStringProperty nameAndScore;

    public highestScoresData(String nameAndScore) {
        this.nameAndScore = new SimpleStringProperty(nameAndScore);
    }

    public String getNameAndScore() {
        return nameAndScore.get();
    }

    public void setNameAndScore(SimpleStringProperty nameAndScore) {
        this.nameAndScore = nameAndScore;
    }
}

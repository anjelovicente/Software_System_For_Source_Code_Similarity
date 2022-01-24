package softwareSimilarityChecker;

import javafx.scene.paint.Color;

public class metricScoreColor {
    private int intScore;
    private float floatScore;

    public Color setRectangleColor(int intScore, String metricScoreName) {
        this.intScore = intScore;
        switch (metricScoreName) {
            case "Program Length":
                return setProgramLengthColor();
            case "Vocabulary Size":
                return setVocabularySizeColor();
        }
        return Color.WHITE;
    }

    public Color setRectangleColor(float floatScore, String metricScoreName) {
        this.floatScore = floatScore;
        switch (metricScoreName) {
            case "Program Volume":
                return setProgramVolumeColor();
            case "Difficulty":
                return setDifficultyColor();
            case "Program Level":
                return setProgramLevelColor();
            case "Effort to Implement":
                return setEffortToImplementColor();
            case "Time to Implement":
                return setTimeToImplementColor();
            case "Delivered Bugs":
                return setDeliveredBugsColor();
        }
        return Color.WHITE;
    }

    private Color setProgramLengthColor() {
        if (intScore >= 0 && intScore < 1000) {
            return Color.rgb(252, 216, 208);
        } else if (intScore >= 1000 && intScore < 2000) {
            return Color.rgb(253, 164, 145);
        } else if (intScore >= 2000 && intScore < 3000) {
            return Color.rgb(250, 112, 82);
        } else if (intScore >= 3000 && intScore < 4000) {
            return Color.rgb(254, 63, 22);
        } else if (intScore >= 4000 && intScore < 5000) {
            return Color.rgb(220, 39, 0);
        } else {
            return Color.rgb(120, 25, 0);
        }
    }

    private Color setVocabularySizeColor() {
        if (intScore >= 0 && intScore < 100) {
            return Color.rgb(255, 169, 247);
        } else if (intScore >= 100 && intScore < 200) {
            return Color.rgb(255, 119, 242);
        } else if (intScore >= 200 && intScore < 300) {
            return Color.rgb(255, 0, 230);
        } else if (intScore >= 300 && intScore < 400) {
            return Color.rgb(185, 1, 167);
        } else if (intScore >= 400 && intScore < 500) {
            return Color.rgb(125, 0, 113);
        } else {
            return Color.rgb(80, 0, 72);
        }
    }

    private Color setProgramVolumeColor() {
        if (floatScore >= 0 && floatScore < 1000) {
            return Color.rgb(152,127,255);
        } else if (floatScore >= 1000 && floatScore < 2000) {
            return Color.rgb(119, 85, 255);
        } else if (floatScore >= 2000 && floatScore < 3000) {
            return Color.rgb(72, 26, 255);
        } else if (floatScore >= 3000 && floatScore < 4000) {
            return Color.rgb(40, 0, 199);
        } else if (floatScore >= 4000 && floatScore < 5000) {
            return Color.rgb(31, 0, 154);
        } else {
            return Color.rgb(39, 23, 105);
        }
    }

    private Color setDifficultyColor() {
        if (floatScore >= 0 && floatScore < 20) {
            return Color.rgb(168,253,255);
        } else if (floatScore >= 20 && floatScore < 40) {
            return Color.rgb(91,251,255);
        } else if (floatScore >= 40 && floatScore < 60) {
            return Color.rgb(6, 217, 225);
        } else if (floatScore >= 60 && floatScore < 80) {
            return Color.rgb(0, 187, 193);
        } else if (floatScore >= 80 && floatScore < 100) {
            return Color.rgb(0, 147, 152);
        } else {
            return Color.rgb(0, 91, 94);
        }
    }

    private Color setProgramLevelColor() {
        if (floatScore >= 0 && floatScore < 0.20) {
            return Color.rgb(169,255,189);
        } else if (floatScore >= 0.20 && floatScore < 0.40) {
            return Color.rgb(107,255,142);
        } else if (floatScore >= 0.40 && floatScore < 0.60) {
            return Color.rgb(41, 255, 91);
        } else if (floatScore >= 0.60 && floatScore < 0.80) {
            return Color.rgb(0, 206, 48);
        } else if (floatScore >= 0.80 && floatScore < 1.00) {
            return Color.rgb(0, 156, 37);
        } else {
            return Color.rgb(0, 101, 24);
        }
    }

    private Color setEffortToImplementColor() {
        if (floatScore >= 0 && floatScore < 1000) {
            return Color.rgb(251,255,174);
        } else if (floatScore >= 1000 && floatScore < 2000) {
            return Color.rgb(245,255,97);
        } else if (floatScore >= 2000 && floatScore < 3000) {
            return Color.rgb(240, 255, 20);
        } else if (floatScore >= 3000 && floatScore < 4000) {
            return Color.rgb(219, 235, 4);
        } else if (floatScore >= 4000 && floatScore < 5000) {
            return Color.rgb(185, 196, 1);
        } else {
            return Color.rgb(137, 146, 0);
        }
    }

    private Color setTimeToImplementColor() {
        if (floatScore >= 0 && floatScore < 1000) {
            return Color.rgb(255,221,125);
        } else if (floatScore >= 1000 && floatScore < 2000) {
            return Color.rgb(255,207,73);
        } else if (floatScore >= 2000 && floatScore < 3000) {
            return Color.rgb(255, 188, 0);
        } else if (floatScore >= 3000 && floatScore < 4000) {
            return Color.rgb(218, 161, 5);
        } else if (floatScore >= 4000 && floatScore < 5000) {
            return Color.rgb(175, 129, 4);
        } else {
            return Color.rgb(138, 102, 2);
        }
    }

    private Color setDeliveredBugsColor() {
        if (floatScore >= 0 && floatScore < 0.20) {
            return Color.rgb(252, 216, 208);
        } else if (floatScore >= 0.20 && floatScore < 0.40) {
            return Color.rgb(253, 164, 145);
        } else if (floatScore >= 0.40 && floatScore < 0.60) {
            return Color.rgb(250, 112, 82);
        } else if (floatScore >= 0.60 && floatScore < 0.80) {
            return Color.rgb(254, 63, 22);
        } else if (floatScore >= 0.80 && floatScore < 1.00) {
            return Color.rgb(220, 39, 0);
        } else {
            return Color.rgb(120, 25, 0);
        }
    }
}

package softwareSimilarityChecker;

import javafx.beans.property.SimpleStringProperty;
public class nameOfFiles {
    private SimpleStringProperty fileName;

    public nameOfFiles(int i, String fileName) {
        this.fileName = new SimpleStringProperty(i + ". " + fileName);
    }

    public String getFileName() {
        return fileName.get();
    }

    public void setFileName(SimpleStringProperty fileName) {
        this.fileName = fileName;
    }
}

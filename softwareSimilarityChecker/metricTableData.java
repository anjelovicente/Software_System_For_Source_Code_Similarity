package softwareSimilarityChecker;

import java.util.ArrayList;

public class metricTableData {
    public ArrayList<Integer> programLengthData = new ArrayList<>();
    public ArrayList<Integer> vocabularySizeData = new ArrayList<>();
    public ArrayList<Float> programVolumeData = new ArrayList<>();
    public ArrayList<Float> difficultyData = new ArrayList<>();
    public ArrayList<Float> programLevelData = new ArrayList<>();
    public ArrayList<Float> effortToImplementData = new ArrayList<>();
    public ArrayList<Float> timeToImplementData = new ArrayList<>();
    public ArrayList<Float> deliveredBugsData = new ArrayList<>();

    public void addData(int pL, int vS, float pV, float d, float pLE, float e, float t, float dB) {
        programLengthData.add(pL);
        vocabularySizeData.add(vS);
        programVolumeData.add(pV);
        difficultyData.add(d);
        programLevelData.add(pLE);
        effortToImplementData.add(e);
        timeToImplementData.add(t);
        deliveredBugsData.add(dB);
    }

}

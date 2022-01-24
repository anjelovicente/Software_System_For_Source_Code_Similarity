package softwareSimilarityChecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class similarityChecker {
    public float check(File address1, File address2) throws IOException {
        BufferedReader reader1 = new BufferedReader(new FileReader(address1));
        BufferedReader reader2 = new BufferedReader(new FileReader(address2));
        float total, total1=0, total2=0;
        float sim1=0, sim2=0, sim;
        String line1 = reader1.readLine();
        String line2 = reader2.readLine();

        while ((line1 != null)&&(line2 != null)){
            String[] words1 = line1.split("\\s+");
            String[] words2 = line2.split("\\s+");
            for (String item : words1) {
                for (String s : words2) {
                    if (item.equals(s)) {
                        sim1++;
                    }
                }
                total1++;
            }
            for (String s : words2) {
                for (String value : words1) {
                    if (s.equals(value)) {
                        sim2++;
                    }
                }
                total2++;
            }

            line1 = reader1.readLine();
            line2 = reader2.readLine();
        }
        total = (total1+total2)/2;
        sim = (sim1+sim2)/2;
        if(sim > total){
            total = total +(sim - total);
        }
        float perc = (sim/total);
        reader1.close();
        reader2.close();
        return perc;
    }

    public float check(int x, int y, float percent) {
        if(x==y){
            return 1;
        } else {
            return percent;
        }
    }
}

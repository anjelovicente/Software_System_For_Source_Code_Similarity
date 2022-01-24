package softwareSimilarityChecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class SMetric {
    public static ArrayList<String> reorderVariables(ArrayList<String> variables) {
        int[] lengths = new int[variables.size()];
        for(int i=0;i<variables.size();i++)
        {
            lengths[i] = variables.get(i).length();
        }
        for(int i=0;i<lengths.length;i++)
        {
            for(int j=i+1;j<lengths.length;j++)
            {
                if(lengths[i] < lengths[j])
                {
                    int temp = lengths[i];
                    lengths[i] = lengths[j];
                    lengths[j] = temp;

                    String tmp_var = variables.get(i);
                    variables.set(i,variables.get(j));
                    variables.set(j,tmp_var);
                }
            }
        }
        return variables;
    }
    public static ArrayList<String> extractConstants(String line) // extract constants from string
    {
        boolean continueFlag = false;
        ArrayList<String> extracted = new ArrayList<String>();
        String temp = "";
        for(int i=0;i<line.length();i++)
        {
            if(line.charAt(i) >= '0' && line.charAt(i) <= '9')
            {
                if(!continueFlag)
                    continueFlag = !continueFlag;
                temp = temp + line.charAt(i) + "";
            }
            else
            {
                if(continueFlag)
                {
                    extracted.add(temp);
                    temp = "";
                    continueFlag = !continueFlag;
                }
            }
        }
        return extracted;
    }
    public static Map<String,Integer> getUniqueCount(ArrayList<String> list)
    {
        Map<String,Integer> uniqueList = new HashMap<String,Integer>();
        for(int i=0;i<list.size();i++)
        {
            String s = list.get(i);
            if(!uniqueList.containsKey(s))
            {
                int count = 0;
                for(int j=0;j<list.size();j++)
                {
                    if(list.get(j).equals(s))
                        count++;
                }
                uniqueList.put(s, count);
            }
        }
        return uniqueList;
    }
}
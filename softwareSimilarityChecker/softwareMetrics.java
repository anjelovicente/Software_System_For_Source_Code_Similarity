package softwareSimilarityChecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class softwareMetrics {
    private int programLength = 0, vocabularySize = 0;
    private float programVolume = 0, difficulty = 0, programLevel = 0, effortToImplement = 0, timeToImplement = 0, deliveredBugs = 0;

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
    public static ArrayList<String> extractConstants(String line) {
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
    public static Map<String,Integer> getUniqueCount(ArrayList<String> list) {
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
    public void computeMetrics(int N1,int N2,int n1,int n2) {
        programLength = N1 + N2;
        vocabularySize = n1 + n2;
        programVolume = programLength * (float)(Math.log(vocabularySize) / Math.log(2));
        if (n2 == 0) {
            difficulty = 0;
            programLevel = 0;
        } else {
            difficulty = (n1 / 2) * (N2 / n2);
            programLevel = 1 / difficulty;
        }
        effortToImplement = programVolume * difficulty;
        timeToImplement = effortToImplement / 18;
        deliveredBugs = programVolume / 3000;
    }
    public void getMetrics(File address) {
        try
        {
            String[] keywords = { "scanf","printf","main","static" }; 	// data types shouldn't be added to keywords
            String[] dataTypes = { "int","float","double","char"};
            ArrayList<String> operators = new ArrayList<String>();
            ArrayList<String> operands = new ArrayList<String>();
            ArrayList<String> variables = new ArrayList<String>();

            int operatorCount = 0, operandCount = 0;
            boolean skipFlag = false;
            BufferedReader reader = new BufferedReader(new FileReader(new File(String.valueOf(address))));
            String line;

            while((line = reader.readLine()) != null)
            {
                line = line.trim();
                for(String keyword : keywords)
                {
                    if(line.startsWith(keyword))
                    {
                        line = line.substring(0+keyword.length());
                        operators.add(keyword);
                        operatorCount++;
                    }
                }
                for(String dataType : dataTypes)
                {
                    if(line.startsWith(dataType))
                    {
                        operators.add(dataType);
                        operatorCount++;
                        int index = line.indexOf(dataType);
                        line = line.substring(index+dataType.length(),line.length()-1);  // -1 to ignore the semicolon
                        String[] vars = line.split(",");
                        for(String v : vars)
                        {
                            v = v.trim();
                            variables.add(v);
                        }
                    }
                }
                variables = reorderVariables(variables);  // very important !
                skipFlag = false;
                for(int i=0;i<line.length();i++)
                {
                    if(line.charAt(i) >= 'A' && line.charAt(i) <= 'Z' || line.charAt(i) >= 'a' && line.charAt(i) <= 'z' || line.charAt(i) >= '0' && line.charAt(i) <= '9' || line.charAt(i) == ' ' || line.charAt(i) == ',' || line.charAt(i)==';' || line.charAt(i) == '(' || line.charAt(i) == '{')
                    {

                    }
                    else if(line.charAt(i) == ')'  )
                    {
                        if(!skipFlag)
                        {
                            operatorCount++;
                            operators.add("()");
                        }
                    }
                    else if(line.charAt(i) == '}'  )
                    {
                        if(!skipFlag)
                        {
                            operatorCount++;
                            operators.add("{}");
                        }
                    }
                    else if(line.charAt(i) == '"')   // for detecting double quotes
                    {
                        skipFlag = !skipFlag;
                        if(skipFlag)
                            operandCount++;
                        else
                        {
                            int startIndex = line.indexOf("\"");
                            int endIndex = line.lastIndexOf("\"");
                            operands.add(line.substring(startIndex,endIndex+1));
                        }
                    }
                    else
                    {
                        if(!skipFlag)
                        {
                            operators.add(line.charAt(i)+"");
                            operatorCount++;
                        }
                    }
                }
                // removing string literals from line if any
                if(line.contains("\""))
                {
                    int startIndex = line.indexOf("\"");
                    int endIndex = line.lastIndexOf("\"");
                    line = line.substring(0, startIndex) + line.substring(endIndex+1);
                }
                for(String variable : variables)
                {
                    while(line.contains(variable))
                    {
                        int index = line.indexOf(variable);
                        line = line.substring(0, index) + line.substring(index+variable.length(), line.length());
                        operands.add(variable);
                        operandCount++;
                    }
                }
                // checking for constants
                operands.addAll(extractConstants(line));
            }
            Map<String,Integer> uniqueOperators = SMetric.getUniqueCount(operators);
            Map<String,Integer> uniqueOperands = SMetric.getUniqueCount(operands);

            computeMetrics(operators.size(),operands.size(),uniqueOperators.size(),uniqueOperands.size());
            reader.close();
        }
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }
    }
    public int getProgramLength() {
        return programLength;
    }
    public int getVocabularySize() {
        return vocabularySize;
    }
    public float getProgramVolume() {
        return programVolume;
    }
    public float getDifficulty() {
        return difficulty;
    }
    public float getProgramLevel() {
        return programLevel;
    }
    public float getEffortToImplement() {
        return effortToImplement;
    }
    public float getTimeToImplement() {
        return timeToImplement;
    }
    public float getDeliveredBugs() {
        return deliveredBugs;
    }
}

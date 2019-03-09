import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class LanguageModel extends Calculate {
    private Hashtable<String, Double> probability = new Hashtable<>();
    private String languageType;

    public Hashtable<String, Double> getProbability() {
        return probability;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType.replace(".txt", "");
    }

    public String getLanguageType() {
        return languageType;
    }

    // Converts Character Pair occurrence to probability
    public void calculateHeaderProbability(ArrayList<Character> charList){
        // Hash Table to store
        Hashtable<Character, Integer> countingChar = new Hashtable<>();

        // Counts Character Pairs
        for(int i = 0; i < charList.size(); i++){
            if(countingChar.containsKey(charList.get(i)))
                countingChar.put(charList.get(i), countingChar.get(charList.get(i))+1);
            else
                countingChar.put(charList.get(i), 1);
        }

        //--------------------------------------------------------------------------------------------
        // Extracts Values
        Enumeration<Character> keys = countingChar.keys();
        int listSize = charList.size();

        // Converts occurrence count to probability
        while (keys.hasMoreElements()) {
            char key = keys.nextElement();
            int value = countingChar.get(key);

            this.probability.put(String.valueOf(key), (double)value/listSize);
        }
    }

    // Converts String Pair occurrence to probability
    public void calculatePairProbability(ArrayList<String> wordList){
        // Hash Table to store the occurrence of character pairs
        Hashtable<String, Integer> countingPairs = new Hashtable<>();
        // Hash table to store the occurrence of pair headers
        Hashtable<Character, Integer> headerCharProb = new Hashtable<>();

        //--------------------------------------------------------------------------------------------

        for(int i = 0; i < wordList.size(); i++){
            // Counts Character pairs
            if(countingPairs.containsKey(wordList.get(i))) {
                countingPairs.put(wordList.get(i), countingPairs.get(wordList.get(i)) + 1);
            }else {
                countingPairs.put(wordList.get(i), 1);
            }

            // Counts Character header occurrence
            if(headerCharProb.containsKey(wordList.get(i).charAt(0))) {
                headerCharProb.put(wordList.get(i).charAt(0), headerCharProb.get(wordList.get(i).charAt(0)) + 1);
            }else {
                headerCharProb.put(wordList.get(i).charAt(0), 1);
            }
        }

        //--------------------------------------------------------------------------------------------
        // Extracts Values
        Enumeration<String> keys = countingPairs.keys();
        // Converts occurrence count to probability and stores in hashtable
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            int value = countingPairs.get(key);

            // Stores the probability of the Character pairs
            this.probability.put(key, (double)value/headerCharProb.get(key.charAt(0)));
        }
    }

    // Writes List to File
    public void outputToFile(String filePath) {
        try {
            // Creates Writer stream with UTF-16
            Writer writer = new OutputStreamWriter(
                    new FileOutputStream(filePath), StandardCharsets.UTF_16);
            PrintWriter printWriter = new PrintWriter(writer);

            //--------------------------------------------------------------------------------------------
            Enumeration<String> keys = this.probability.keys();
            // Writes contents of Hashtable to file
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                Double value = this.probability.get(key);

                printWriter.write(key + " " + value + "\n");
            }

            //--------------------------------------------------------------------------------------------
            // Close Writer Stream
            writer.close();
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

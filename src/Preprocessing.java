import java.util.*;
import java.io.*;
import java.text.*;
import java.nio.charset.StandardCharsets;
/**
 * This section will contain functions that handles pre-processing such as reading files,
 * extracting words, removing unwanted symbols and extracting pairs of Characters
 *
 * **/


public class Preprocessing{
    // Reads file content in UTF-16 format
    public static String readUnicodeFile(String filePath){
        StringBuilder fileContent = new StringBuilder();
        try {
            // Input FIle Stream
            Reader reader = new InputStreamReader(
                    new FileInputStream(filePath), StandardCharsets.UTF_16);
            BufferedReader bufferedReader = new BufferedReader(reader);

            //--------------------------------------------------------------------------------------------
            // Extracts file content as string
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                fileContent.append(s + "\n");
            }

            //--------------------------------------------------------------------------------------------
            // Close File Stream
            bufferedReader.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileContent.toString();
    }
    public static ArrayList<String> readFile(String filePath){
        ArrayList<String> fileContent = new ArrayList<>();

        try {
            // Input File Stream
            Reader reader = new InputStreamReader(
                    new FileInputStream(filePath), StandardCharsets.UTF_16);
            BufferedReader bufferedReader = new BufferedReader(reader);

            //--------------------------------------------------------------------------------------------
            // Extracts file content as string
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                fileContent.add(s + "\n");
            }

            //--------------------------------------------------------------------------------------------
            // Close File Stream
            bufferedReader.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileContent;
    }

    // Reads File in Locale format
    // and creates an array with each word stored
    public static ArrayList<String> extractWords(String inputText){
        ArrayList<String> wordList = new ArrayList<String>();
        BreakIterator wordIterator = BreakIterator.getWordInstance();

        wordIterator.setText(inputText);
        int start = wordIterator.first();
        int end = wordIterator.next();


        // changes letters to lower case and removes unwanted symbolic characters
        //--------------------------------------------------------------------------------------------
        while (end != BreakIterator.DONE) {
            String word = inputText.substring(start, end);
            word = word.toLowerCase();
            // Removes white spaces and symbolic characters
            if (Character.isLetter(word.charAt(0)) && word.length() > 1) {
                word = word.replaceAll("[^a-z]", "");
                wordList.add(word);
            }

            // moves index to next word
            start = end;
            end = wordIterator.next();


        }
        return wordList;
    }

    // Extract Pairs from each word
    public static ArrayList<String> extractLetterPairs(ArrayList<String> wordList){
        ArrayList<String> output = new ArrayList<String>();

        // Goes through each word in list
        for(String word : wordList){
            // Goes through each pair of characters
            for(int i = 0; i < word.length(); i++){
                try{
                    // Adds a pair of characters to the ArrayList
                    // Placing the character pairs in alphabetical order
                    if(word.charAt(i) < word.charAt(i+1)) {
                        String pair = String.valueOf("" + word.charAt(i) + word.charAt(i + 1));
                        output.add(pair);
                    }else{
                        String pair = String.valueOf("" + word.charAt(i + 1) + word.charAt(i));
                        output.add(pair);
                    }


                }catch(StringIndexOutOfBoundsException e){}
            }
        }
        return output;
    }

    // Gets first occurring letter for each word
    public static ArrayList<Character> extractInitLetters(ArrayList<String> wordList){
        ArrayList<Character> output = new ArrayList<Character>();

        // Gets first index of string
        // Stores it as Char
        for(String word : wordList){
            output.add(word.charAt(0));
        }
        return output;
    }

    // Gets file names from a directory
    public static ArrayList<String> getFileNames(String directory){
        ArrayList<String> results = new ArrayList<String>();

        File[] files = new File(directory).listFiles();
        //If this pathname does not denote a directory, then listFiles() returns null.

        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }

        return results;
    }

    // Creates filename based on language
    // E.g: Chinese.txt -> ChineseModel.txt
    public static String returnOutputName(String language){
        return language.substring(0, language.length()-4)+"Model.txt";
    }

    // Java implementation of argmax from numpy
    // Gets the largest value in an array
    private static double argMax(double[] inputArray){
        double temp = 0;
        for (double x : inputArray){
            if(temp < x){
                temp = x;
            }
        }
        return temp;
    }

    // Outputs the array values as a probability
    public static double[] Softmax(double[] inputArray){
        // Sets decimal place for float value
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(3);

        double[] output = new double[inputArray.length];
        double[] exp_a = new double[inputArray.length];
        double sum_exp_a = 0;
        double c = argMax(inputArray);

        //--------------------------------------------------------------------------------------------
        for(int i = 0; i < inputArray.length; i++){
            exp_a[i] = Math.exp(inputArray[i]-c);
            sum_exp_a += Math.exp(inputArray[i]-c);
        }

        for(int i = 0; i < inputArray.length; i++){
            output[i] = Double.parseDouble(df.format(((exp_a[i]/sum_exp_a)*100)));
        }
        return output;
    }

}
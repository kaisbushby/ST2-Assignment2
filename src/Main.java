import java.util.*;

public class Main {
    // Learns Language specified via directory
    static void learn(String inputFilePath, String outputFilePath){
        // Pre-processing
        String fileContent = Preprocessing.readUnicodeFile(inputFilePath);
        ArrayList<String> wordList = Preprocessing.extractWords(fileContent, Locale.FRENCH);
        ArrayList<Character> initLetterList = Preprocessing.extractInitLetters(wordList);
        ArrayList<String> initPairList = Preprocessing.extractLetterPairs(wordList);

        // Creates weight values on txt file
        Learning.calculateInitProb(initLetterList);
        Learning.calculateTransProb(initPairList);
        Learning.outputToFile(outputFilePath);
    }

    // Creates weight values for the Languages in the "Language Model" Directory
    static void learnAll(){
        // Gets file names at "Language Text" directory
        ArrayList<String> fileNames = Preprocessing.getFileNames("Language Text");

        for(String filename : fileNames){
            String inputFilePath = "Language Text\\" + filename;
            String outputFilePath = "Language Model\\" + Preprocessing.returnOutputName(filename);

            // Pre-processing
            String fileContent = Preprocessing.readUnicodeFile(inputFilePath);
            ArrayList<String> wordList = Preprocessing.extractWords(fileContent, Locale.FRENCH);
            ArrayList<Character> initLetterList = Preprocessing.extractInitLetters(wordList);
            ArrayList<String> initPairList = Preprocessing.extractLetterPairs(wordList);

            // Creates weight values on txt file
            Learning.calculateInitProb(initLetterList);
            Learning.calculateTransProb(initPairList);
            Learning.outputToFile(outputFilePath);

        }

    }

    // Detects all the files in Language_Text folder
    static void run(){
        Calculate.initModels();
        Calculate.detectModel("Language Test\\Unknown1.txt");
        Calculate.detectModel("Language Test\\Unknown2.txt");
        Calculate.detectModel("Language Test\\Unknown3.txt");
        Calculate.detectModel("Language Test\\Unknown4.txt");
        Calculate.detectModel("Language Test\\Unknown5.txt");
    }

    public static void main(String[] args) {
        // Learns specified language and outputs model
        // learn("Language Text\\English.txt", "Language Model\\EnglishModel.txt");

        // Learns all languages in the "Language Text Directory"
        learnAll();

        // Runs the models
        run();
    }
}


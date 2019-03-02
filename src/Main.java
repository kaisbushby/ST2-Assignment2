import java.util.*;
/**
 * Software Technology 2: Assignment 2
 * Language Detection Software using the n-gram Technique
 * Author: Kai Stuart Bushby, u3175374
 * **/

public class Main
{
    public static ArrayList<LanguageModel> langModelList = new ArrayList<>();

    // Learns Language specified via directory
    static void learn(String inputFilePath, String outputFilePath)
    {
        LanguageModel model = new LanguageModel();
        // Pre-processing
        String fileContent = Preprocessing.readUnicodeFile(inputFilePath);
        ArrayList<String> wordList = Preprocessing.extractWords(fileContent);
        ArrayList<Character> initLetterList = Preprocessing.extractInitLetters(wordList);
        ArrayList<String> initPairList = Preprocessing.extractLetterPairs(wordList);

        // Creates weight values on txt file

        model.calculateInitProb(initLetterList);
        model.calculateTransProb(initPairList);
        model.outputToFile(outputFilePath);
    }

    // Creates weight values for the Languages in the "Language Model" Directory
    static void learnAll()
    {
        // Gets file names at "Language Text" directory
        // English.txt
        // French.txt
        // German.txt
        // Italian.txt
        // Spanish.txt
        ArrayList<String> fileNames = Preprocessing.getFileNames("Language Text");


        for(String filename : fileNames){
            LanguageModel model = new LanguageModel();
            model.setLanguageType(filename);
            String inputFilePath = "Language Text/" + filename;
            String outputFilePath = "Language Model/" + Preprocessing.returnOutputName(filename);

            // Pre-processing
            String fileContent = Preprocessing.readUnicodeFile(inputFilePath);
            ArrayList<String> wordList = Preprocessing.extractWords(fileContent);
            ArrayList<Character> initLetterList = Preprocessing.extractInitLetters(wordList);
            ArrayList<String> initPairList = Preprocessing.extractLetterPairs(wordList);

            // Creates weight values on txt file
            model.calculateInitProb(initLetterList);
            model.calculateTransProb(initPairList);
            model.outputToFile(outputFilePath);
            langModelList.add(model);

        }
    }

    // Detects all the files in Language_Text folder
    static void run()
    {
        Calculate.initModels(langModelList);
        Calculate.detectModel("Language Test/Unknown1.txt", langModelList);
        Calculate.detectModel("Language Test/Unknown2.txt", langModelList);
        Calculate.detectModel("Language Test/Unknown3.txt", langModelList);
        Calculate.detectModel("Language Test/Unknown4.txt", langModelList);
        Calculate.detectModel("Language Test/Unknown5.txt", langModelList);
    }

    public static void main(String[] args)
    {
        // Learns specified language and outputs model
        // learn("Language Text\\English.txt", "Language Model\\EnglishModel.txt");

        // Learns all languages in the "Language Text Directory"
        learnAll();

        // Runs the models
        run();
    }
}


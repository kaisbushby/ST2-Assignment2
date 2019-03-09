import java.util.*;
/**
 * Software Technology 2: Assignment 2
 * Language Detection Software using the n-gram Technique
 * Author: Kai Stuart Bushby, u3175374
 * **/

public class Main
{
    public static ArrayList<LanguageModel> langModelList = new ArrayList<>();

    // Creates weight values for the Languages in the "Language Model" Directory
    static void learn()
    {
        // Gets file names at "Learning" directory
        // English.txt
        // French.txt
        // German.txt
        // Italian.txt
        // Spanish.txt
        ArrayList<String> fileNames = Preprocessing.getFileNames("Learning");


        for(String filename : fileNames){
            LanguageModel model = new LanguageModel();
            model.setLanguageType(filename);
            String inputFilePath = "Learning/" + filename;
            String outputFilePath = "Models/" + Preprocessing.returnOutputName(filename);

            // Pre-processing
            String fileContent = Preprocessing.readUnicodeFile(inputFilePath);
            ArrayList<String> wordList = Preprocessing.extractWords(fileContent);
            ArrayList<Character> initLetterList = Preprocessing.extractInitLetters(wordList);
            ArrayList<String> initPairList = Preprocessing.extractLetterPairs(wordList);

            // Creates weight values on txt file
            model.calculateHeaderProbability(initLetterList);
            model.calculatePairProbability(initPairList);
            model.outputToFile(outputFilePath);
            langModelList.add(model);

        }
    }

    // Detects all the files in Language_Text folder
    static void run()
    {
        Calculate.initModels(langModelList);
        Calculate.detectModel("Testing/Unknown1.txt", langModelList);
        Calculate.detectModel("Testing/Unknown2.txt", langModelList);
        Calculate.detectModel("Testing/Unknown3.txt", langModelList);
        Calculate.detectModel("Testing/Unknown4.txt", langModelList);
        Calculate.detectModel("Testing/Unknown5.txt", langModelList);
    }

    public static void main(String[] args)
    {
        // Learns specified language and outputs model
        // learn("Language Text\\English.txt", "Language Model\\EnglishModel.txt");

        // Learns all languages in the "Language Text Directory"
        learn();

        // Runs the models
        run();
    }
}


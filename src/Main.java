import java.util.*;
/**
 * Software Technology 2: Assignment 1
 * Language Detection Software using the n-gram Technique
 * Author: Kai Stuart Bushby, u3175374
 * **/

public class Main
{
    public static ArrayList<LanguageModel> langModelList = new ArrayList<>();

    // Creates weight values for the Languages in the "Language Model" Directory
    static void learn(boolean willLearn) {
        // Boolean flag will determine whether to create new model

        ArrayList<String> fileNames = Preprocessing.getFileNames("Learning");
        // Gets file names at "Learning" directory
        // English.txt
        // French.txt
        // German.txt
        // Italian.txt
        // Spanish.txt

        if(willLearn){
            for(String filename : fileNames) {
                LanguageModel model = new LanguageModel();
                model.setLanguageType(filename);
                String inputFilePath = "Learning/" + filename;
                String outputFilePath = "Models/" + Preprocessing.returnOutputName(filename);

                // Pre-processing
                String fileContent = Preprocessing.readUnicodeFile(inputFilePath);
                ArrayList<String> wordList = Preprocessing.extractWords(fileContent);
                ArrayList<Character> initLetterList = Preprocessing.extractInitLetters(wordList);
                ArrayList<String> initPairList = Preprocessing.extractLetterPairs(wordList);

                // Creates weight values for model file
                model.calculateHeaderProbability(initLetterList);
                model.calculatePairProbability(initPairList);
                model.outputToFile(outputFilePath);
                langModelList.add(model);
            }

        }else {
            for (String filename : fileNames) {
                LanguageModel model = new LanguageModel();
                model.setLanguageType(filename);
                langModelList.add(model);
            }
            Calculate.initializeModels(langModelList);
        }
    }

    // Detects all the files in Language_Text folder
    static void run()
    {
        Calculate.estimateLanguage("Testing/Unknown1.txt", langModelList);
        Calculate.estimateLanguage("Testing/Unknown2.txt", langModelList);
        Calculate.estimateLanguage("Testing/Unknown3.txt", langModelList);
        Calculate.estimateLanguage("Testing/Unknown4.txt", langModelList);
        Calculate.estimateLanguage("Testing/Unknown5.txt", langModelList);
        Calculate.estimateLanguage("Testing/Unknown6.txt", langModelList);
        Calculate.estimateLanguage("Testing/Unknown7.txt", langModelList);
    }

    public static void main(String[] args)
    {
        // Learns all languages in the "Language Text Directory"
        learn(true);

        // Runs the models
        run();
    }
}


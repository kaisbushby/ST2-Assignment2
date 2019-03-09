import java.util.*;
/**
 * This Section will handle the language models
 * It will calculate the probability of a language based on a given text.
 * **/

class Calculate{
    // Reads text data and adds them to the hash table
    private static void readModels(LanguageModel Model, ArrayList<String> inputText){

        for(String str : inputText){
            String tempString[] = str.split(" ");
            Model.getProbability().put(tempString[0], Double.parseDouble(tempString[1]));
        }
    }

    // Initialize hashtable
    static void initModels(ArrayList<LanguageModel> modelList){
        ArrayList<String> importModelList = Preprocessing.getFileNames("Models");
        ArrayList<String> probabilityText;

        // Reads model file and assigns the values onto it's corresponding language model
        int counter = 0;
        for(String model : importModelList){
            probabilityText = Preprocessing.readFile("Models/" + model);
            readModels(modelList.get(counter), probabilityText);

            counter++;
        }
    }

    // Function to detect text language
    static void detectModel(String inputFilePath, ArrayList<LanguageModel> modelList){
        // Processes input data for given file
        // Reads text data and extracts every occurring letter pair and it's probability
        String textData = Preprocessing.readUnicodeFile(inputFilePath);
        ArrayList<String> wordList = Preprocessing.extractWords(textData);
        ArrayList<Character> initLetterList = Preprocessing.extractInitLetters(wordList);
        ArrayList<String> initPairList = Preprocessing.extractLetterPairs(wordList);

        // 0:English    1:French   2:German    3:Italian   4:Spanish

        ArrayList<String> languageName = new ArrayList<>();
        for(LanguageModel languageTable : modelList){
            languageName.add(languageTable.getLanguageType());
        }
        double identificationValue[] = new double[languageName.size()];
        int langIndex = 0;


        // Calculates the probability of the language based of the occurring
        // first letter of each word. e.g "Java" -> "J"
        // try_catch statement in case an unknown value in inputted
        for(LanguageModel languageTable : modelList){
            for(Character value : initLetterList){
                try {
                    identificationValue[langIndex] += languageTable.getProbability().get(String.valueOf(value));
                }catch(NullPointerException e){}
            }
            langIndex++;
        }
        // Same Technique as above but using pairs of occurring letters
        // e.g "Java" -> "Ja", "av", "va"
        langIndex = 0;
        for(LanguageModel languageTable : modelList){
            for(String value : initPairList){
                try {
                    identificationValue[langIndex] += languageTable.getProbability().get(value);
                }catch(NullPointerException e){}
            }
            langIndex++;
        }


        // Prints out language probability for each inputted piece of text
        System.out.println("File name: " + inputFilePath);
        double output[] = Preprocessing.Softmax(identificationValue);
        for(int x = 0; x < identificationValue.length; x++){
            System.out.println(languageName.get(x) + " : " + output[x] + "%");
        }
        System.out.println("The Identified Language is: " + languageName.get(Preprocessing.getLargestValueIndex(output)));
        System.out.println();
    }
}

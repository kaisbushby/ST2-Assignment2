import java.util.*;
/**
 * This Section will handle the language models
 * It will calculate the probability of a language based on a given text.
 * **/

class Calculate{
    // Initialize hashtable
    static void initializeModels(ArrayList<LanguageModel> modelList){
        ArrayList<String> importModelList = Preprocessing.getFileNames("Models");
        ArrayList<String> probabilityText;

        // Reads model file and assigns the values onto it's corresponding language model
        int counter = 0;
        for(String model : importModelList){
            probabilityText = Preprocessing.readFile("Models/" + model);
            // Reads text data and adds them to the hash table
            for(String str : probabilityText){
                String tempString[] = str.split(" ");
                modelList.get(counter).getProbability().put(tempString[0], Double.parseDouble(tempString[1]));
            }
            counter++;
        }
    }

    // Function to detect text language
    static void estimateLanguage(String inputFilePath, ArrayList<LanguageModel> modelList){
        // Processes input data for given file
        // Reads text data and extracts every occurring letter pair and it's probability
        String textData = Preprocessing.readUnicodeFile(inputFilePath);
        ArrayList<String> wordList = Preprocessing.extractWords(textData);
        ArrayList<Character> initLetterList = Preprocessing.extractInitLetters(wordList);
        ArrayList<String> initPairList = Preprocessing.extractLetterPairs(wordList);

        // 0:English    1:French   2:German    3:Italian   4:Spanish
        // Creates index and array for language output
        ArrayList<String> languageName = new ArrayList<>();
        for(LanguageModel languageTable : modelList){
            languageName.add(languageTable.getLanguageType());
        }
        double identificationValue[] = new double[languageName.size()];
        for(int c = 0; c<identificationValue.length; c++)
            identificationValue[c] = 1;

        int langIndex = 0;

        // Calculates the probability of the language based of the occurring
        // first letter of each word. e.g "Java" -> "J"
        // try_catch statement in case the letter doesn't exist in the language
        for(LanguageModel languageTable : modelList){
            for(Character value : initLetterList){
                try {
                    identificationValue[langIndex] *= languageTable.getProbability().get(String.valueOf(value))*10;
                }catch(NullPointerException e){identificationValue[langIndex] *=0.00;}
            }
            // Same Technique as above but using pairs of occurring letters
            // e.g "Java" -> "Ja", "av", "va"
            for(String value : initPairList){
                try {
                    identificationValue[langIndex] *= languageTable.getProbability().get(value)*10;
                }catch(NullPointerException e){identificationValue[langIndex] *= 0.00;}
            }
            langIndex++;
        }


        // Prints out language probability for each inputted piece of text
        System.out.println("File name: " + inputFilePath);
        double output[] = Preprocessing.Softmax(identificationValue);
        for(int x = 0; x < identificationValue.length; x++){
            System.out.println(languageName.get(x) + " : " + output[x] + "%");
        }
        String predictedLanguage = languageName.get(Preprocessing.getLargestValueIndex(output));
        System.out.println("Language Identifies as: " + predictedLanguage);
        System.out.println();
    }
}

import java.util.*;
/**
 * This Section will handle the language models generated from the "Learning" class
 * It will calculate the probability of a language based on a given text.
 * **/

class Calculate{
    // Hash table for each language model
    private static Hashtable<String, Double> ENG_probablity = new Hashtable<>();
    private static Hashtable<String, Double> FRE_probablity = new Hashtable<>();
    private static Hashtable<String, Double> GER_probablity = new Hashtable<>();
    private static Hashtable<String, Double> ITA_probablity = new Hashtable<>();
    private static Hashtable<String, Double> SPA_probablity = new Hashtable<>();
    private static ArrayList<Hashtable<String, Double>> hashModels = new ArrayList<Hashtable<String, Double>>();

    // Creates arraylist of hashtable to store each language probability
    private static ArrayList<Hashtable<String, Double>> createHashArray(){
        ArrayList<Hashtable<String, Double>> list = new ArrayList<>();

        list.add(ENG_probablity);
        list.add(FRE_probablity);
        list.add(GER_probablity);
        list.add(ITA_probablity);
        list.add(SPA_probablity);

        return list;
    }

    // Reads text data and adds them to the hash table
    private static void readModels(Hashtable<String, Double> Model, ArrayList<String> inputText){

        for(String str : inputText){
            String tempString[] = str.split(" ");
            Model.put(tempString[0], Double.parseDouble(tempString[1]));
        }
    }

    // Initialize hashtable
    static void initModels(){
        hashModels = createHashArray();
        ArrayList<String> modelList = Preprocessing.getFileNames("Language Model");
        ArrayList<String> probabilityText;

        int counter = 0;
        for(String model : modelList){
            probabilityText = Preprocessing.readFile("Language Model/" + model);
            readModels(hashModels.get(counter), probabilityText);

            counter++;
        }
    }

    // Function to detect text language
    static void detectModel(String inputFilePath){
        // Processes input data
        // Reads text data and extracts every occurring letter pair and it's probability
        String textData = Preprocessing.readUnicodeFile(inputFilePath);
        ArrayList<String> wordList = Preprocessing.extractWords(textData, Locale.FRENCH);
        ArrayList<Character> initLetterList = Preprocessing.extractInitLetters(wordList);
        ArrayList<String> initPairList = Preprocessing.extractLetterPairs(wordList);

        // 0:English    1:French   2:German    3:Italian   4:Spanish
        double identificationValue[] = new double[5];
        String languageName[] = {"English", "French", "German", "Italian", "Spanish"};
        int langIndex = 0;


        // Calculates the probability of the language based of the occurring
        // first letter of each word. e.g "Java" -> "J"
        // try_catch statement in case an unknown value in inputted
        for(Hashtable<String, Double> languageTable : hashModels){
            for(Character value : initLetterList){
                try {
                    identificationValue[langIndex] += languageTable.get(String.valueOf(value));
                }catch(NullPointerException e){}
            }
            langIndex++;
        }
        // Same Technique as above but using pairs of occurring letters
        // e.g "Java" -> "Ja", "av", "va"
        langIndex = 0;
        for(Hashtable<String, Double> languageTable : hashModels){
            for(String value : initPairList){
                try {
                    identificationValue[langIndex] += languageTable.get(value);
                }catch(NullPointerException e){}
            }
            langIndex++;
        }


        // Prints out language probability for each inputted piece of text
        System.out.println("File name: " + inputFilePath);
        double output[] = Preprocessing.Softmax(identificationValue);
        for(int x = 0; x < identificationValue.length; x++){
            System.out.println(languageName[x] + " : " + output[x] + "%");
        }
        System.out.println();
    }
}

import java.util.*;

class Calculate{
    // Hash table for each language model
    private static Hashtable<String, Double> ENG_probablity = new Hashtable<>();
    private static Hashtable<String, Double> FRE_probablity = new Hashtable<>();
    private static Hashtable<String, Double> GER_probablity = new Hashtable<>();
    private static Hashtable<String, Double> ITA_probablity = new Hashtable<>();
    private static Hashtable<String, Double> SPA_probablity = new Hashtable<>();
    private static ArrayList<Hashtable<String, Double>> hashModels = new ArrayList<Hashtable<String, Double>>();

    // Creates arraylist with hashtable
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
            probabilityText = Preprocessing.readFile("Language Model\\" + model);
            readModels(hashModels.get(counter), probabilityText);

            counter++;
        }
    }

    // Function to detect text language
    static void detectModel(String inputFilePath){
        // Processes input data
        //--------------------------------------------------------------------------------------------
        String textData = Preprocessing.readUnicodeFile(inputFilePath);
        ArrayList<String> wordList = Preprocessing.extractWords(textData, Locale.FRENCH);
        ArrayList<Character> initLetterList = Preprocessing.extractInitLetters(wordList);
        ArrayList<String> initPairList = Preprocessing.extractLetterPairs(wordList);

        // 0:English    1:French   2:German    3:Italian   4:Spanish
        double identificationValue[] = new double[5];
        String languageName[] = {"English", "French", "German", "Italian", "Spanish"};
        int counter = 0;

        //--------------------------------------------------------------------------------------------
        // Adds the key value to the identificationValue on every model
        // try_catch statement in case an unknown value in inputted
        for(Hashtable<String, Double> languageTable : hashModels){
            for(Character value : initLetterList){
                try {
                    identificationValue[counter] += languageTable.get(String.valueOf(value));
                }catch(NullPointerException e){}
            }
            counter++;
        }
        // Same as above but handling character pairs
        counter = 0;
        for(Hashtable<String, Double> languageTable : hashModels){
            for(String value : initPairList){
                try {
                    identificationValue[counter] += languageTable.get(value);
                }catch(NullPointerException e){}
            }
            counter++;
        }

        //--------------------------------------------------------------------------------------------
        // Prints out probability
        System.out.println("File name: " + inputFilePath);
        double output[] = Preprocessing.Softmax(identificationValue);
        for(int x = 0; x < identificationValue.length; x++){
            System.out.println(languageName[x] + " : " + output[x] + "%");
        }
        System.out.println();
    }
}

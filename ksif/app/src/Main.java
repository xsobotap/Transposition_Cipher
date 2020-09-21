import GeneticAlgo.*;
import dictionary.BruteForceAttack;
import dictionary.EnglishDictionary;
import dictionary.NgramStats;
import dictionary.SlovakDictionary;
import helpers.Permutations;
import helpers.TranspositionCipher;
import helpers.TranspositionKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class Main {

    public static void main(String[] args) {

        System.out.println("KSIF_fullka");

        SlovakDictionary slovakDictionary = new SlovakDictionary();
        NgramStats ngramStats = new NgramStats();
        EnglishDictionary englishDictionary = new EnglishDictionary();


        try {
            //TODO Slovniky a nGramy testy
            //System.out.println(slovakDictionary.loadFromFile(true));
            //slovakDictionary.toStandardAlphabet();
            //slovakDictionary.toLowerCase();
            //slovakDictionary.printDirectory();
            //slovakDictionary.writeToFile();
            //System.out.println(slovakDictionary.getSize());

            //ngramStats.computeNgramsFrequenciesFromFile();
            //String ciphertext = "abcdabcdabcd";
            //HashMap<String, Double> mapFromCipherText = NgramStats.readNgram(ciphertext,2,true);
            //System.out.println(mapFromCipherText.toString());

            //TODO sifrovanie a desifrovanie po riadkoch resp stlpcoch testy
            String openText = "JOZEFMARTAJAKUB";
            TranspositionKey transpositionKey = new TranspositionKey(new Integer[]{4,1,5,2,3});
            TranspositionCipher transpositionCipher = new TranspositionCipher();

            String cipherTextRows = transpositionCipher.encryptRows(openText,transpositionKey);
            String cipherTextCols =transpositionCipher.encyptCols(openText,transpositionKey);
            System.out.println("cipher rows: "+cipherTextRows);
            System.out.println("cipher cols: "+cipherTextCols);

            String decryptedTextRows = transpositionCipher.decryptRows(cipherTextRows, transpositionKey);
            String decryptedTextCols = transpositionCipher.decryptCols(cipherTextCols , transpositionKey);
            System.out.println("decipher rows: "+decryptedTextRows);
            System.out.println("decipher cols: "+decryptedTextCols);

            //TODO testy permutacia zo slova
            Permutations.printPerm(Permutations.wordPerm("aazzzbaacdefghijklmnopzyx"));

            //TODO IDEALNY PRIPAD
            //dlhy nezasifrovany eng text, idealne mod 10 = 0
            //vyrobit statistiku bigramov a trigramov
            //zasifrovat po riadkoch heslom dlzky 10
            //statistika bigramov a trigramov zasifrovaneho textu
            //snaha dostat sa na referencne hodnoty statistiky
            //brute force na heslo



            //TODO sifrovanie desifrovanie testy
            Integer[] rndPermutation = new Integer[]{4,1,5,2,3,7,6};
            //Permutations.rndPerm(rndPermutation);
            TranspositionKey testKey = new TranspositionKey(rndPermutation);
            String OT_Eng = "The earliest forms of secret writing required little more than writing implements since most people could not read. More literacy, or literate opponents, required actual cryptography. The main classical cipher types are transposition ciphers, which rearrange the order of letters in a message (e.g., 'hello world' becomes 'ehlol owrdl' in a trivially simple rearrangement scheme), and substitution ciphers, which systematically replace letters or groups of letters with other letters or groups of letters (e.g., 'fly at once' becomes 'gmz bu podf' by replacing each letter with the one following it in the Latin alphabet). Simple versions of either have never offered much confidentiality from enterprising opponents. An early substitution cipher was the Caesar cipher, in which each letter in the plaintext was replaced by a letter some fixed number of positions further down the alphabet. Suetonius reports that Julius Caesar used it with a shift of three to communicate with his generals. Atbash is an example of an early Hebrew cipher. The earliest known use of cryptography is some carved ciphertext on stone in Egypt (ca 1900 BCE), but this may have been done for the amusement of literate observers rather than as a way of concealing information.The Greeks of Classical times are said to have known of ciphers (e.g., the scytale transposition cipher claimed to have been used by the Spartan military).[16] Steganography (i.e., hiding even the existence of a message so as to keep it confidential) was also first developed in ancient times. An early example, from Herodotus, was a message tattooed on a slave's shaved head and concealed under the regrown hair.[10] More modern examples of steganography include the use of invisible ink, microdots, and digital watermarks to conceal information.";
            String editedOT_Eng = OT_Eng.toLowerCase().replaceAll("\\W","");
            System.out.println(editedOT_Eng);

            String encryptedEditedOT_Eng = transpositionCipher.encryptRows(editedOT_Eng, testKey);
            String decryptedEditedOT_Eng = transpositionCipher.decryptRows(encryptedEditedOT_Eng, testKey);
            System.out.println(decryptedEditedOT_Eng);

            //TODO vytvarenie statistik ngramov.. vytvorenie arraylistu hashmap, kvoli iteroavniu v nGramFitness
            HashMap<String, Double> lettersDec;
            HashMap<String, Double> bigramsDec = NgramStats.readNgram(decryptedEditedOT_Eng,2, true);
            HashMap<String, Double> trigramsDec= NgramStats.readNgram(decryptedEditedOT_Eng,3, true);
            HashMap<String, Double> quadgramsDec= NgramStats.readNgram(decryptedEditedOT_Eng,4, true);
            ArrayList<HashMap<String, Double>> listOfNgramsDec = new ArrayList<>();
            listOfNgramsDec.add(null);
            listOfNgramsDec.add(bigramsDec);
            listOfNgramsDec.add(trigramsDec);
            listOfNgramsDec.add(quadgramsDec);


            HashMap<String, Double> lettersEnc;
            HashMap<String, Double> bigramsEnc = NgramStats.readNgram(encryptedEditedOT_Eng,2, true);
            HashMap<String, Double> trigramsEnc= NgramStats.readNgram(encryptedEditedOT_Eng,3, true);
            HashMap<String, Double> quadgramsEnc= NgramStats.readNgram(encryptedEditedOT_Eng,4, true);
            ArrayList<HashMap<String, Double>> listOfNgramsEnc= new ArrayList<>();
            listOfNgramsEnc.add(null);
            listOfNgramsEnc.add(bigramsEnc);
            listOfNgramsEnc.add(trigramsEnc);
            listOfNgramsEnc.add(quadgramsEnc);



            //TODO FITNESS TESTY + IDEALNY PRIPAD
            // text je zasifrovany permutaciou 4 1 5 2 3 ... bruteforcom a nGramfitness funkciou som dostal spravny kluc
            // aj pri pouziti idealnych hodnot povodneho textu a aj pomocou slovnikovych hodnot 
            //rndPerm 4 1 5 2 3
            Double score1 = NgramStats.allnGramsDistance(encryptedEditedOT_Eng, listOfNgramsDec, 4 );
            Double score2 = NgramStats.allnGramsDistance(listOfNgramsEnc,listOfNgramsDec, 4);
            System.out.println(score1);
            System.out.println(score2);

            Double score3 = NgramStats.fitnessNgrams(testKey,encryptedEditedOT_Eng, listOfNgramsDec,4);
            System.out.println(score3);

            //TranspositionKey result = BruteForceAttack.tryAllPermN(encryptedEditedOT_Eng,listOfNgramsDec, 5); //funguje
           // TranspositionKey result = BruteForceAttack.tryAllPermN(encryptedEditedOT_Eng,englishDictionary.getListOfNgrams(), transpositionKey.getEncPerm().length); //funguje
            //System.out.println(result.getScore());
            //System.out.println(Arrays.toString(result.getEncPerm()));


            Genetic genetic = new Genetic(encryptedEditedOT_Eng,listOfNgramsDec,7);

            Integer[] geneticresult = genetic.genetic();

            System.out.println("  ------------------- genetic result --------------------------");
            for(int m = 0; m < 7;m++){
                System.out.print(geneticresult[m]);
            }
            System.out.println();

//genetic.removeDuplicates();


        }


        catch(Exception e){
            e.printStackTrace();
        }

    }
}
package dictionary;

import helpers.Permutations;
import helpers.TranspositionKey;

import java.util.ArrayList;
import java.util.HashMap;



public class BruteForceAttack {
    /*
    * Inputs : ciphertext - ciphertext
    *          n - len of permutations
    *          Hashmap of reference Values of nGrams
    *          //directory - directory with reference values of nGrams frequencies
    * Description:
    *           -function start with generating all possible permutations of length n, which are applied to cipherText
    *           -for each permutation is created TranspositionKey which use generated permutation as encryption key
    *           -to each TranspositionKey is calculated and set score, as result of comparision of language nGram frequencies and real nGram frequencies
    *           -TranspositionKey with best score is returned (best : converging to zero)
    */
    public static TranspositionKey tryAllPermN(String cipherText, ArrayList<HashMap<String, Double>> listOfReferenceNgrams, int n){
        Integer[] initialPerm = new Integer[n];
        for(int i =0; i < n; i++){
            initialPerm[i] = i+1;
        }
        ArrayList<Integer[]> allPermutations =(ArrayList)Permutations.allPerm(initialPerm);
        TranspositionKey currentKey=null;
        TranspositionKey bestKey=null;
        Double worstScore = new Double(99999); // TODO TODO TODO
        Double score = null;

        for(int i = 0; i < allPermutations.size(); i++){
            currentKey = new TranspositionKey(allPermutations.get(i));
            score = NgramStats.fitnessNgrams(currentKey,cipherText,listOfReferenceNgrams, 4);
            if(score<worstScore){
                bestKey = currentKey;
                worstScore = score;
            }
        }

        return bestKey;
    }
}

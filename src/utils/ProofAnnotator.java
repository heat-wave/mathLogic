package utils;

import expression.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by heat_wave on 10/5/15.
 */
public class ProofAnnotator {
     public static ArrayList<String> annotateProof(ArrayList<Expression> proof, HashSet<Expression> assumptions) throws ParseException {
         HashMap<Expression, Integer> mapProof = new HashMap<>();
         HashMap<Expression, Integer> neededAlpha = new HashMap<>();
         HashMap<Expression, Pair> resultsMP = new HashMap<>();
         ArrayList<String> result = new ArrayList<>();
         for (int i = 0; i < proof.size(); i++) {
             Expression curExp = proof.get(i);
             int a = curExp.isAxiom();
             if (a > 0) {
                 result.add("(" + (i + 1) + ") " + curExp.toString() + " axiom " + a);
             }
             else {
                 if (assumptions.contains(curExp)) {
                     result.add("(" + (i + 1) + ") " + curExp.toString() + " assumption");
                 }
                 else if (resultsMP.containsKey(curExp)) {
                     result.add("(" + (i + 1) + ") " + curExp.toString() + " MP " + (resultsMP.get(curExp).fst + 1) + ", " + (resultsMP.get(curExp).snd + 1));
                 }
                 else {
                     result.add("(" + (i + 1) + ") " + curExp.toString() + "\nProof is incorrect from statement " + (i + 1));
                     return result;
                 }
             }
             if (curExp instanceof Implication) {
                 Expression alpha = ((Implication) curExp).left;
                 Expression beta = ((Implication) curExp).right;
                 if (mapProof.containsKey(alpha)) {
                     resultsMP.put(beta, new Pair(mapProof.get(alpha), i));
                 }
                 else {
                     neededAlpha.put(alpha, i);
                 }
             }
             if (neededAlpha.containsKey(curExp)) {
                 resultsMP.put(((Implication)proof.get(neededAlpha.get(curExp))).right, new Pair (i, neededAlpha.get(curExp)));
                 neededAlpha.remove(curExp);
             }
             mapProof.put(curExp, i);
         }
         return result;
     }
}

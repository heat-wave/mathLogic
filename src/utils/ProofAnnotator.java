package utils;

import expression.Expression;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by heat_wave on 10/5/15.
 */
public class ProofAnnotator {
     static ArrayList<String> annotateProof(ArrayList<Expression> proof, HashSet<Expression> assumptions) throws ParseException {
         HashMap<Expression, Integer> mapProof = new HashMap<>();
         HashSet<Expression> neededAlpha = new HashSet<>();
         HashMap<Expression, Pair> resultsMP = new HashMap<>();
         ArrayList<String> result = new ArrayList<>();
         for (int i = 0; i < proof.size(); i++) {
             Expression curExp = proof.get(i);
             int a = curExp.isAxiom();
             if (a > 0) {
                 result.add(curExp.toString() + " axiom " + a);
             }
             else {
                 if (assumptions.contains(curExp)) {
                     result.add(curExp.toString() + " assumption");
                 }
                 else if (resultsMP.containsKey(curExp)) {
                     result.add(curExp.toString() + " MP " + resultsMP.get(curExp).fst + ", " + resultsMP.get(curExp).snd);
                 }
                 else {
                     result.add(curExp.toString() + "\n Proof is incorrect from statement " + (i + 1));
                     return result;
                 }
             }

         }
         return result;
     }
    class Pair {
        int fst, snd;

        public Pair(int fst, int snd) {
            this.fst = fst;
            this.snd = snd;
        }
    }
}

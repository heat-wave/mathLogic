package utils;

import expression.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by heat_wave on 10/5/15.
 */
public class Deductor {
    public static ArrayList<Expression> completeProof (Expression alpha, HashSet<Expression> assumptions, ArrayList<Expression> proof) throws ParseException {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<Expression, Integer> mapProof = new HashMap<>();
        HashMap<Expression, Integer> neededAlpha = new HashMap<>();
        HashMap<Expression, Pair> resultsMP = new HashMap<>();
        for (int i = 0; i < proof.size(); i++) {
            Expression curExp = proof.get(i);
            int a = curExp.isAxiom();
            if (a > 0 || assumptions.contains(curExp)) {
                result.add(curExp);
                result.add(Axioms.axiom1(curExp, alpha));
                result.add(new Implication(alpha, curExp));
            }
            else {
                if (curExp.equals(alpha)) {
                    result.addAll(Proofs.getSelfCons(alpha));
                }
                else if (resultsMP.containsKey(curExp)) {
                    Expression deltaJ = proof.get(resultsMP.get(curExp).fst);
                    Expression deltaK = proof.get(resultsMP.get(curExp).snd);
                    result.add(Axioms.axiom2(alpha, deltaJ, curExp));
                    result.add(new Implication(new Implication(alpha, deltaK), new Implication(alpha, curExp)));
                    result.add(new Implication(alpha, curExp));
                }
                else {
                    result.add(curExp);
                }
            }
            if (curExp instanceof Implication) {
                Expression alphaCur = ((Implication) curExp).left;
                Expression betaCur = ((Implication) curExp).right;
                if (mapProof.containsKey(alphaCur)) {
                    resultsMP.put(betaCur, new Pair(mapProof.get(alphaCur), i));
                }
                else {
                    neededAlpha.put(alphaCur, i);
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

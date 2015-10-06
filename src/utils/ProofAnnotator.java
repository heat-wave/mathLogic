package utils;

import annotated_expression.*;
import expression.Expression;
import expression.Implication;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by heat_wave on 10/5/15.
 */
public class ProofAnnotator {
    static boolean annotatedOnce = false;
    private static ArrayList<AnnotatedExpression> annotateProof(ArrayList<Expression> proof, HashSet<Expression> assumptions) throws ParseException {
        HashMap<Expression, Integer> mapProof = new HashMap<>();
        HashMap<Expression, HashSet<Integer>> neededAlpha = new HashMap<>();

        // resultsMP: key - beta, value - (index of alpha, index of alpha->beta)
        HashMap<Expression, Pair> resultsMP = new HashMap<>();
        ArrayList<AnnotatedExpression> result = new ArrayList<>();
        for (int i = 0; i < proof.size(); i++) {
            Expression curExp = proof.get(i);
            int a = curExp.isAxiom();
            if (a > 0) {
                result.add(new AnnotatedAxiom(curExp, a));
            } else {
                if (assumptions.contains(curExp)) {
                    result.add(new AnnotatedAssumption(curExp));
                } else if (resultsMP.containsKey(curExp)) {
                    result.add(new AnnotatedMP(curExp, resultsMP.get(curExp).fst, resultsMP.get(curExp).snd));
                } else {
                    result.add(new AnnotatedWrong(curExp));
                    break;
                }
            }
            if (curExp instanceof Implication) {
                Expression alpha = ((Implication) curExp).left;
                Expression beta = ((Implication) curExp).right;
                if (mapProof.containsKey(alpha)) {
                    resultsMP.put(beta, new Pair(mapProof.get(alpha), i));
                } else {
                    if (!neededAlpha.containsKey(alpha)) {
                        neededAlpha.put(alpha, new HashSet<>());
                    }
                    neededAlpha.get(alpha).add(i);
                }
            }
            if (neededAlpha.containsKey(curExp)) {
                for (int k : neededAlpha.get(curExp)) {
                    resultsMP.put(((Implication) proof.get(k)).right, new Pair(i, k));
                }
                neededAlpha.remove(curExp);
            }
            mapProof.put(curExp, i);
        }
        if (result.get(result.size() - 1) instanceof AnnotatedAssumption || result.get(result.size() - 1) instanceof AnnotatedAxiom) {
            AnnotatedExpression exp = result.get(result.size() - 1);
            result.clear();
            result.add(exp);
            return result;
        } else {
            return removeRedundantStatements(result);
        }

    }

    private static ArrayList<AnnotatedExpression> removeRedundantStatements(ArrayList<AnnotatedExpression> expressions) throws ParseException {
        if (annotatedOnce) {
            return expressions;
        }
        used = new boolean[expressions.size()];
        markImportantStatements(expressions.size() - 1, expressions);
        ArrayList<Expression> resultAux = new ArrayList<>();
        for (int i = 0; i < expressions.size(); i++) {
            if (used[i]) {
                resultAux.add(expressions.get(i).expression);
            }
        }
        annotatedOnce = true;
        return annotateProof(resultAux, new HashSet<>());
    }

    private static boolean[] used;
    private static void markImportantStatements(int current, ArrayList<AnnotatedExpression> expressions) {
        used[current] = true;
        if (expressions.get(current) instanceof AnnotatedMP) {
            markImportantStatements(((AnnotatedMP) expressions.get(current)).alpha, expressions);
            markImportantStatements(((AnnotatedMP) expressions.get(current)).alphaBeta, expressions);
        }
    }

    public static ArrayList<String> getAnnotatedProof(ArrayList<Expression> proof, HashSet<Expression> assumptions) throws ParseException {
        ArrayList<AnnotatedExpression> result = annotateProof(proof, assumptions);
        ArrayList<String> ans = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            ans.add("(" + (i + 1) + ") " + result.get(i).toString());
            if (result.get(i) instanceof AnnotatedWrong) {
                ans.add("Proof is incorrect from statement " + (i + 1));
            }
        }
        return ans;
    }


}

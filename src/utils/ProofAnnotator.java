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
    // variable, that is needed to check, how much times was current proof annotated, because I annotate it again after removing redundant statements (we don't need infinite loops, do we?)
    static boolean annotatedOnce = false;
    private static ArrayList<AnnotatedExpression> annotateProof(ArrayList<Expression> proof, HashSet<Expression> assumptions) throws ParseException {
        // mapProof: key - expression, value - its index in proof; so we can easily get expression's index
        HashMap<Expression, Integer> mapProof = new HashMap<>();

        // neededAlpha: key - expression alpha, value - set of indices, that represent expressions like alpha->beta, these expressions need to get alpha to add all of those betas to resultsMP
        HashMap<Expression, HashSet<Integer>> neededAlpha = new HashMap<>();

        // resultsMP: key - beta, value - (index of alpha, index of alpha->beta)
        HashMap<Expression, Pair> resultsMP = new HashMap<>();

        // result: fully annotated proof (not final product)
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
                    // if current expression doesn't represent axiom, assumption or result of MP, it's completely wrong, so we don't need to proceed annotating
                    result.add(new AnnotatedWrong(curExp));
                    break;
                }
            }
            // how we can use current expression after
            // if curExp = alpha->beta, we need to save beta as result of MP (if alpha has appeared in proof before) or be ready to add it if we meet alpha after
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
            // if curExp is alpha of some alpha->beta, we can add all such betas to resultMP
            if (neededAlpha.containsKey(curExp)) {
                for (int k : neededAlpha.get(curExp)) {
                    resultsMP.put(((Implication) proof.get(k)).right, new Pair(i, k));
                }
                // and remove this alpha from neededAlpha, because it's already appeared in proof
                neededAlpha.remove(curExp);
            }
            mapProof.put(curExp, i);
        }
        // if proven statement is assumption or axiom, we don't really need to prove it
        if (result.get(result.size() - 1) instanceof AnnotatedAssumption || result.get(result.size() - 1) instanceof AnnotatedAxiom) {
            AnnotatedExpression exp = result.get(result.size() - 1);
            result.clear();
            result.add(exp);
            return result;
        } else {
            // there can be some similar statements in proof, or statements which aren't really needed for result, so we can drop them
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
        // we need only used expressions
        for (int i = 0; i < expressions.size(); i++) {
            if (used[i]) {
                resultAux.add(expressions.get(i).expression);
            }
        }
        annotatedOnce = true;
        // we need to annotate it one more time, because indices have changed
        return annotateProof(resultAux, new HashSet<>());
    }

    // just marking statements as used with dfs
    private static boolean[] used;
    private static void markImportantStatements(int current, ArrayList<AnnotatedExpression> expressions) {
        used[current] = true;
        if (expressions.get(current) instanceof AnnotatedMP) {
            markImportantStatements(((AnnotatedMP) expressions.get(current)).alpha, expressions);
            markImportantStatements(((AnnotatedMP) expressions.get(current)).alphaBeta, expressions);
        }
    }

    public static ArrayList<String> getAnnotatedProof(ArrayList<Expression> proof, HashSet<Expression> assumptions) throws ParseException {
        // annotate given proof
        ArrayList<AnnotatedExpression> result = annotateProof(proof, assumptions);
        // and be ready to print it
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

package utils;

import expression.*;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by heat_wave on 10/5/15.
 */
public class Prover {
    // evaluate works in quite obvious way
    // so it's just goes down in expression-tree, and when it meets variable, it returns its current estimate
    // bitmask stores current variables' estimation in a way: V={A,B,C}, let A=T, B=F, C=F, so bitmask=001
    public static boolean evaluate(Expression expr, int bitmask) {
        if (expr instanceof Implication) {
            return !evaluate(((Implication) expr).left, bitmask) || evaluate(((Implication) expr).right, bitmask);
        }
        if (expr instanceof Or) {
            return evaluate(((Or) expr).left, bitmask) || evaluate(((Or) expr).right, bitmask);
        }
        if (expr instanceof And) {
            return evaluate(((And) expr).left, bitmask) && evaluate(((And) expr).right, bitmask);
        }
        if (expr instanceof Not) {
            return !evaluate(((Not) expr).subExpr, bitmask);
        }
        else {
            String binary = String.format("%32s", Integer.toBinaryString(bitmask)).replace(' ', '0');
            return (binary.charAt(binary.length() - expr.getRealVariables().get(((Variable) expr).var) - 1) == '1');
        }
    }

    public static ArrayList<Expression> prove(Expression expression) throws ParseException {
        // we need to prove our statement with all 2^(|V|) variables' estimations
        int varCount = expression.getRealVariables().size();
        int range = 1 << (varCount);
        ArrayList<Expression>[] proofs = new ArrayList[range];
        for (int bitmask = 0; bitmask < range; bitmask++) {
            proofs[bitmask] = proveWithEstimate(expression, bitmask);
            if (proofs[bitmask] == null) {
                String binary = String.format("%32s", Integer.toBinaryString(bitmask)).replace(' ', '0');
                System.out.print("Statement " + expression.toString() + " is wrong with ");
                for (Map.Entry<String, Integer> entry : expression.getRealVariables().entrySet()) {
                    System.out.print(entry.getKey() + "=" + (binary.charAt(binary.length() - entry.getValue() - 1) == '1' ? "T " : "F "));
                }
                return null;
            }
        }
        // so we got all 2^(|V|) proofs, now it's time to merge them using Lemma of exclusion assumption
        // it says: if Gamma, alpha |- beta and Gamma, !alpha |- beta => Gamma |- beta
        String[] variables = new String[varCount];
        // we get array of variables, so we can easily get variable by index
        for (Map.Entry<String, Integer> entry : expression.getRealVariables().entrySet()) {
            variables[entry.getValue()] = entry.getKey();
        }
        // we have bitmasks of proofs like these: (Let V = {A,B,C})
        // 000, 001, 010, 011, 100, 101, 110, 111
        // so at first we merge pairs of proofs, then we merge fours of them ...
        // so every time we merge proofs[j] and proofs[j + step / 2] and store result in proofs[j]
        int step = 2;
        for (int i = 0; i < varCount; i++) {
            // each variable becomes subject to lemma
            Expression alpha = new Variable(variables[i]);
            for (int j = 0; j < range; j += step) {
                HashSet<Expression> assumptions = new HashSet<>();
                String binary = String.format("%32s", Integer.toBinaryString(j)).replace(' ', '0');
                for (int k = i + 1; k < varCount; k++) {
                    assumptions.add(binary.charAt(binary.length() - expression.getRealVariables().get(variables[k]) - 1) == '1' ? new Variable(variables[k]) : new Not(new Variable(variables[k])));
                }
                // according to lemma of exclusion:
                // we need to apply deduction to Gamma, alpha |- beta and Gamma, !alpha |- beta
                // so we get Gamma |- alpha->beta
                ArrayList<Expression> proof1 = Deductor.completeProof(new Not(alpha), assumptions, proofs[j]);
                // and Gamma |- !alpha->beta
                ArrayList<Expression> proof2 = Deductor.completeProof(alpha, assumptions, proofs[j + step / 2]);
                // then we use lemma that proves alpha | !alpha (there are no more options)
                ArrayList<Expression> tertiumNonDatum = Proofs.getTertiumNonDatum(alpha);
                // and after that we are ready to absolutely exclude alpha from our context
                ArrayList<Expression> exclusion = Proofs.getExclusion(expression, alpha);
                proofs[j].clear();
                proofs[j].addAll(proof1);
                proofs[j].addAll(proof2);
                proofs[j].addAll(tertiumNonDatum);
                proofs[j].addAll(exclusion);
            }
            step *= 2;
        }
        // obviously, final result will be stored in proofs[0]
        // now we need just minimize it and annotate
        return proofs[0];
    }

    private static ArrayList<Expression> proveWithEstimate(Expression expression, int bitmask) {
        // if here is at least one estimation, that gives false statement, there is no sense to prove it further
        if (!evaluate(expression, bitmask)) {
            return null;
        }
        // for example if V={A,B,C}, current bitmask = 100, current context will be: {A,!B,!C}
        HashSet<Expression> context = new HashSet<>();
        String binary = String.format("%32s", Integer.toBinaryString(bitmask)).replace(' ', '0');
        context.addAll(expression.getRealVariables().entrySet().stream().map(entry -> (binary.charAt(binary.length() - entry.getValue() - 1) == '1' ? (new Variable(entry.getKey())) : (new Not(new Variable(entry.getKey()))))).collect(Collectors.toList()));
        // so we are ready to recursively prove our statement
        return proveRecursive(expression, context, bitmask);
    }

    private static ArrayList<Expression> proveRecursive(Expression expression, HashSet<Expression> context, int bitmask) {
        // the main idea of this method is lemma of truth tables, so each line of such tables gives us provable statement
        // for example:
        // A | B | A&B
        // 0 | 0 |  0   !A,!B |- !(A&B)
        // 0 | 1 |  0   !A, B |- !(A&B)
        // 1 | 0 |  0   A, !B |- !(A&B)
        // 1 | 1 |  0   A, B  |- A&B
        // all of these proofs stay in directory resources/proofs and are called correspondingly NoNoNotAnd, ... YesYesAnd
        ArrayList<Expression> result = new ArrayList<>();
        if (expression instanceof Implication) {
            boolean leftEst = evaluate(((Implication) expression).left, bitmask);
            boolean rightEst = evaluate(((Implication) expression).right, bitmask);
            if (leftEst && rightEst) {
                result.addAll(proveRecursive(((Implication) expression).left, context, bitmask));
                result.addAll(proveRecursive(((Implication) expression).right, context, bitmask));

                result.addAll(Proofs.getYesYesImpl(((Implication) expression).left, ((Implication) expression).right));

            }
            if (!leftEst && rightEst) {
                result.addAll(proveRecursive(new Not(((Implication) expression).left), context, bitmask));
                result.addAll(proveRecursive(((Implication) expression).right, context, bitmask));
                result.addAll(Proofs.getNoYesImpl(((Implication) expression).left, ((Implication) expression).right));
            }
            if (!leftEst && !rightEst) {
                result.addAll(proveRecursive(new Not(((Implication) expression).left), context, bitmask));
                result.addAll(proveRecursive(new Not(((Implication) expression).right), context, bitmask));
                result.addAll(Proofs.getNoNoImpl(((Implication) expression).left, ((Implication) expression).right));
            }

        }
        if (expression instanceof And) {
            boolean leftEst = evaluate(((And) expression).left, bitmask);
            boolean rightEst = evaluate(((And) expression).right, bitmask);
            if (leftEst && rightEst) {
                result.addAll(proveRecursive(((And) expression).left, context, bitmask));
                result.addAll(proveRecursive(((And) expression).right, context, bitmask));
                result.addAll(Proofs.getYesYesAnd(((And) expression).left, ((And) expression).right));
            }
        }
        if (expression instanceof Or) {
            boolean leftEst = evaluate(((Or) expression).left, bitmask);
            boolean rightEst = evaluate(((Or) expression).right, bitmask);

            if (leftEst) {
                result.addAll(proveRecursive(((Or) expression).left, context, bitmask));
                result.addAll(Proofs.getYesNoOr(((Or) expression).left, ((Or) expression).right));
            }
            else if (rightEst) {
                result.addAll(proveRecursive(((Or) expression).right, context, bitmask));
                result.addAll(Proofs.getNoYesOr(((Or) expression).left, ((Or) expression).right));
            }
        }
        // if current expression is Not we have two different options depending on kind of expression inside Not
        if (expression instanceof Not) {
            Expression aux = ((Not) expression).subExpr;
            // if subExpression is Not too, so current expression looks like !!alpha, we can at first give prove of alpha, and after that add teo signs of Not using template proof AddDoubleNot
            if (aux instanceof Not) {
                result.addAll(proveRecursive(((Not) aux).subExpr, context, bitmask));
                result.addAll(Proofs.getAddDoubleNot(((Not) aux).subExpr));
            }
            // in other cases: we do the same thing than before but for opposite lines of table
            if (aux instanceof Implication) {
                boolean leftEst = evaluate(((Implication) aux).left, bitmask);
                boolean rightEst = evaluate(((Implication) aux).right, bitmask);
                if (leftEst && !rightEst) {
                    result.addAll(proveRecursive(((Implication) aux).left, context, bitmask));
                    result.addAll(proveRecursive(new Not(((Implication) aux).right), context, bitmask));
                    result.addAll(Proofs.getYesNoNotImpl(((Implication) aux).left, ((Implication) aux).right));
                }
            }
            if (aux instanceof And) {
                boolean leftEst = evaluate(((And) aux).left, bitmask);
                boolean rightEst = evaluate(((And) aux).right, bitmask);
                if (!leftEst && !rightEst) {
                    result.addAll(proveRecursive(new Not(((And) aux).left), context, bitmask));
                    result.addAll(proveRecursive(new Not(((And) aux).right), context, bitmask));
                    result.addAll(Proofs.getNoNoNotAnd(((And) aux).left, ((And) aux).right));
                }
                if (!leftEst && rightEst) {
                    result.addAll(proveRecursive(new Not(((And) aux).left), context, bitmask));
                    result.addAll(proveRecursive(((And) aux).right, context, bitmask));
                    result.addAll(Proofs.getNoYesNotAnd(((And) aux).left, ((And) aux).right));
                }
                if (leftEst && !rightEst) {
                    result.addAll(proveRecursive(((And) aux).left, context, bitmask));
                    result.addAll(proveRecursive(new Not(((And) aux).right), context, bitmask));
                    result.addAll(Proofs.getYesNoNotAnd(((And) aux).left, ((And) aux).right));
                }
            }
            if (aux instanceof Or) {
                boolean leftEst = evaluate(((Or) aux).left, bitmask);
                boolean rightEst = evaluate(((Or) aux).right, bitmask);
                if (!leftEst && !rightEst) {
                    result.addAll(proveRecursive(new Not(((Or) aux).left), context, bitmask));
                    result.addAll(proveRecursive(new Not(((Or) aux).right), context, bitmask));
                    result.addAll(Proofs.getNoNoNotOr(((Or) aux).left, ((Or) aux).right));
                }
            }
        }
        // if current expression is in context, it doesn't need to be proven, so we just add it to result
        if (context.contains(expression)) {
            result.add(expression);
        }
        return result;
    }


}

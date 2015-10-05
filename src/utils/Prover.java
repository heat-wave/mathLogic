package utils;

import expression.*;

import java.util.HashMap;

/**
 * Created by heat_wave on 10/5/15.
 */
public class Prover {
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
            String binary = Integer.toBinaryString(bitmask + (2 << 30));
            return (binary.charAt(binary.length() - expr.getRealVariables().get(((Variable) expr).var)) - 1 == '1');
        }
    }

    public static void prove(Expression expression) {

    }


}

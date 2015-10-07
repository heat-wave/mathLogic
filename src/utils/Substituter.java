package utils;

import expression.*;
import java.util.HashMap;
/**
 * Created by heat_wave on 10/5/15.
 */
public class Substituter {
    // in axioms and template proofs we often need to substitute variables with custom expressions
    // so who can do it better that substituter
    public static Expression substitute(Expression expression, HashMap<String, Expression> variables) {
        if (expression instanceof Implication) {
            return new Implication(substitute(((Implication) expression).left, variables), substitute(((Implication) expression).right, variables));
        }
        if (expression instanceof Or) {
            return new Or(substitute(((Or) expression).left, variables), substitute(((Or) expression).right, variables));
        }
        if (expression instanceof And) {
            return new And(substitute(((And) expression).left, variables), substitute(((And) expression).right, variables));
        }
        if (expression instanceof Not) {
            return new Not(substitute(((Not) expression).subExpr, variables));
        }
        if (expression instanceof Variable) {
            return variables.get(((Variable) expression).var);
        }
        return null;
    }
}

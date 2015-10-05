package utils;

import expression.*;

/**
 * Created by heat_wave on 10/5/15.
 */
public class Axioms {
    public static Expression axiom1 (Expression alpha, Expression beta) {
        return new Implication(alpha, new Implication(beta, alpha));
    }
    public static Expression axiom2 (Expression alpha, Expression beta, Expression gamma) {
        return new Implication(new Implication(alpha, beta), new Implication(new Implication(alpha, new Implication(beta, gamma)), new Implication(alpha, gamma)));
    }
}

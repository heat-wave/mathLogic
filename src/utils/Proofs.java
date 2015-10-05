package utils;

import expression.Expression;
import expression.Implication;

import java.util.ArrayList;

/**
 * Created by baba_beda on 10/5/15.
 */
public class Proofs {
    public static ArrayList<Expression> selfImpl(Expression a) {
        ArrayList<Expression> result = new ArrayList<>();
        result.add(Axioms.axiom1(a, a));
        Expression aux = new Implication(a, a);
        result.add(Axioms.axiom1(a, aux));
        result.add(Axioms.axiom2(a, aux, a));
        result.add(new Implication(result.get(1), aux));
        result.add(aux);
        return result;
    }
}

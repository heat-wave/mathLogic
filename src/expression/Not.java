package expression;

/**
 * Created by baba_beda on 10/4/15.
 */
public class Not extends Expression {
    public Expression subExpr;

    public Not(Expression subExpr) {
        this.subExpr = subExpr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Not not = (Not) o;

        if (!subExpr.equals(not.subExpr)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return subExpr.hashCode();
    }
}

package expression;

/**
 * Created by heat_wave on 10/4/15.
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

        return subExpr.equals(not.subExpr);

    }

    @Override
    public int hashCode() {
        return subExpr.hashCode();
    }

    @Override
    public String toString() {
        return "!" + "(" + subExpr.toString() + ")";
    }
}

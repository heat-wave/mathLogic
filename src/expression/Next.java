package expression;

/**
 * Created by heat_wave on 10/7/15.
 */
public class Next extends Expression {
    Expression subExpr;

    public Next(Expression subExpr) {
        this.subExpr = subExpr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Next next = (Next) o;

        return subExpr.equals(next.subExpr);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + subExpr.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "(" + subExpr.toString() + ")" + "'";
    }
}

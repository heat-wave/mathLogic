package expression;

/**
 * Created by baba_beda on 10/4/15.
 */
public class Or  extends Expression {
    public Expression left, right;
    public Or (Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Or or = (Or) o;

        if (!left.equals(or.left)) return false;
        if (!right.equals(or.right)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = left.hashCode();
        result = 31 * result + right.hashCode();
        return result;
    }
}

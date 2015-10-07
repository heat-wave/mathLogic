package expression;

/**
 * Created by heat_wave on 10/7/15.
 */
public class EqualityPredicate extends Expression {
    Expression leftTerm, rightTerm;

    public EqualityPredicate(Expression leftTerm, Expression rightTerm) {
        this.leftTerm = leftTerm;
        this.rightTerm = rightTerm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        EqualityPredicate that = (EqualityPredicate) o;

        return leftTerm.equals(that.leftTerm) && rightTerm.equals(that.rightTerm);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + leftTerm.hashCode();
        result = 31 * result + rightTerm.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return leftTerm.toString() + "=" + rightTerm.toString();
    }
}

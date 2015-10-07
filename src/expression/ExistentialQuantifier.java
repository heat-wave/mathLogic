package expression;

/**
 * Created by heat_wave on 10/7/15.
 */
public class ExistentialQuantifier extends Expression {
    Variable var;
    Expression function;

    public ExistentialQuantifier(Variable var, Expression function) {
        this.var = var;
        this.function = function;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ExistentialQuantifier that = (ExistentialQuantifier) o;

        return var.equals(that.var) && function.equals(that.function);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + var.hashCode();
        result = 31 * result + function.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "?" + var.toString() + "(" + function.toString() + ")";
    }
}

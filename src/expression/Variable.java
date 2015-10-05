package expression;

/**
 * Created by baba_beda on 10/4/15.
 */
public class Variable extends Expression {
    public String var;
    public Variable(String var) {
        this.var = var;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;

        if (var != null ? !var.equals(variable.var) : variable.var != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return var != null ? var.hashCode() : 0;
    }
}

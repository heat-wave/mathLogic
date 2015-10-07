package expression;

import java.util.ArrayList;

/**
 * Created by heat_wave on 10/7/15.
 */
public class Predicate extends Expression {
    String name;
    ArrayList<Expression> terms;

    public Predicate(String name, ArrayList<Expression> terms) {
        this.name = name;
        this.terms = terms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Predicate predicate = (Predicate) o;

        return name.equals(predicate.name) && terms.equals(predicate.terms);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + terms.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String result = name + "(";
        for (int i = 0; i < terms.size() - 1; i++) {
            result += terms.get(i) + ", ";
        }
        result += terms.get(terms.size() - 1);
        return result;
    }
}

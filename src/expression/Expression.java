package expression;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by heat_wave on 10/3/15.
 */
public class Expression {
    private HashSet<String> variables = new HashSet<>();
    private HashMap<String, Integer> variablesAux = new HashMap<>();

    @Override
    public boolean equals(Object other) {
        if (this instanceof Implication && other instanceof Implication) {
            return this.equals(other);
        }
        if (this instanceof And && other instanceof And) {
            return this.equals(other);
        }
        if (this instanceof Or && other instanceof Or) {
            return this.equals(other);
        }
        if (this instanceof Not && other instanceof Not) {
            return this.equals(other);
        }
        return this instanceof Variable && other instanceof Variable && this.equals(other);
    }

    @Override
    public int hashCode() {
        if (this instanceof Implication) {
            return this.hashCode();
        }
        if (this instanceof And) {
            return this.hashCode();
        }
        if (this instanceof Or) {
            return this.hashCode();
        }
        if (this instanceof Not) {
            return this.hashCode();
        }  
        if (this instanceof Variable) {
            return this.hashCode();
        }
        return 0;
    }
}

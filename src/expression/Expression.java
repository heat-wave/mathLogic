package expression;

import com.sun.istack.internal.Pool;

import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by heat_wave on 10/3/15.
 */
public class Expression {
    private HashSet<String> variables = new HashSet<>();
    public HashMap<String, Integer> realVariables;
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

    public int isAxiom() throws ParseException {
        for (int i = 1; i <= 10; i++) {
            if (isAxiom(i)) {
                return i;
            }
        }
        return 0;
    }

    private void setRealVariables(HashMap<String, Integer> realVariables) {
        this.realVariables = realVariables;
        if (this instanceof Implication) {
            ((Implication) this).left.setRealVariables(this.realVariables);
            ((Implication) this).right.setRealVariables(this.realVariables);
        }
        if (this instanceof Or) {
            ((Or) this).left.setRealVariables(this.realVariables);
            ((Or) this).right.setRealVariables(this.realVariables);
        }
        if (this instanceof And) {
            ((And) this).left.setRealVariables(this.realVariables);
            ((And) this).right.setRealVariables(this.realVariables);
        }
        if (this instanceof Not) {
            ((Not) this).subExpr.setRealVariables(this.realVariables);
        }
    }

    public HashMap<String, Integer> getRealVariables() {
        if (realVariables == null) {
            getVariables();
            int i = 0;
            realVariables = new HashMap<>();
            for (String s : variables) {
                realVariables.put(s, i++);
            }
            this.setRealVariables(realVariables);
        }
        return realVariables;
    }

    private HashSet<String> getVariables() {
        if (this instanceof Implication) {
            variables.addAll(((Implication) this).left.getVariables());
            variables.addAll(((Implication) this).right.getVariables());
        }
        else if (this instanceof Or) {
            variables.addAll(((Or) this).left.getVariables());
            variables.addAll(((Or) this).right.getVariables());
        }
        else if (this instanceof And) {
            variables.addAll(((And) this).left.getVariables());
            variables.addAll(((And) this).right.getVariables());
        }
        else if (this instanceof Not) {
            variables.addAll(((Not) this).subExpr.getVariables());
        }
        else {
            variables.add(((Variable) this).var);
        }
        return variables;
    }
    private boolean isAxiom(int number) throws ParseException {
        switch(number) {
            case 1:
                return (this instanceof Implication && ((Implication) this).right instanceof Implication && ((Implication) this).left.equals(((Implication) ((Implication) this).right).right));

            case 2:
                if (!(this instanceof Implication) || !(((Implication) this).left instanceof  Implication) || !(((Implication) this).right instanceof Implication)
                        || !(((Implication) ((Implication) this).right).left instanceof Implication) || !(((Implication) ((Implication) this).right).right instanceof  Implication)
                        || !(((Implication) ((Implication) ((Implication) this).right).left).right instanceof Implication)) {
                    break;
                }
                return (((Implication) ((Implication) this).left).left.equals(((Implication) ((Implication) ((Implication) this).right).left).left) && ((Implication) ((Implication) this).left).right.equals(((Implication) ((Implication) ((Implication) ((Implication) this).right).left).right).left)
                        && ((Implication) ((Implication) this).left).left.equals(((Implication) ((Implication) ((Implication) this).right).right).left)
                        && ((Implication) ((Implication) ((Implication) ((Implication) this).right).left).right).right.equals(((Implication) ((Implication) ((Implication) this).right).right).right));

            case 3:
                return (this instanceof Implication && ((Implication) this).left instanceof And && ((Implication) this).right.equals(((And) ((Implication) this).left).left));

            case 4:
                return (this instanceof Implication && ((Implication) this).left instanceof And && ((Implication) this).right.equals(((And) ((Implication) this).left).right));

            case 5:
                return (this instanceof Implication && ((Implication) this).right instanceof Implication && ((Implication) ((Implication) this).right).right instanceof And
                        && ((Implication) this).left.equals(((And) ((Implication) ((Implication) this).right).right).left) && ((Implication) ((Implication) this).right).left.equals(((And) ((Implication) ((Implication) this).right).right).right));

            case 6:
                return (this instanceof Implication && ((Implication) this).right instanceof Or && ((Implication) this).left.equals(((Or) ((Implication) this).right).left));

            case 7:
                return (this instanceof Implication && ((Implication) this).right instanceof Or && ((Implication) this).left.equals(((Or) ((Implication) this).right).right));

            case 8:
                if (!(this instanceof Implication) || !(((Implication) this).left instanceof Implication) || !(((Implication) this).right instanceof Implication) || !(((Implication) ((Implication) this).right).left instanceof Implication)
                        || !(((Implication) ((Implication) this).right).right instanceof Implication) || !(((Implication) ((Implication) ((Implication) this).right).right).left instanceof Or)) {
                    break;
                }
                return (((Implication) ((Implication) this).left).left.equals(((Or) ((Implication) ((Implication) ((Implication) this).right).right).left).left)
                        && ((Implication) ((Implication) ((Implication) this).right).left).left.equals(((Or) ((Implication) ((Implication) ((Implication) this).right).right).left).right)
                        && ((Implication) ((Implication) this).left).right.equals(((Implication) ((Implication) ((Implication) this).right).left).right) && ((Implication) ((Implication) this).left).right.equals(((Implication) ((Implication) ((Implication) this).right).right).right));

            case 9:
                if (!(this instanceof Implication) || !(((Implication) this).right instanceof  Implication) || !(((Implication) this).left instanceof Implication) || !(((Implication) ((Implication) this).right).right instanceof Not)
                        || !(((Implication) ((Implication) this).right).left instanceof Implication) || !(((Implication) ((Implication) ((Implication) this).right).left).right instanceof Not)) {
                    break;
                }
                return (((Implication) ((Implication) this).left).left.equals(((Implication) ((Implication) ((Implication) this).right).left).left) && ((Implication) ((Implication) this).left).left.equals(((Not) ((Implication) ((Implication) this).right).right).subExpr)
                        && ((Implication) ((Implication) this).left).right.equals(((Not) ((Implication) ((Implication) ((Implication) this).right).left).right).subExpr));

            case 10:
                return (this instanceof Implication && ((Implication) this).left instanceof Not && ((Not) ((Implication) this).left).subExpr instanceof Not && ((Implication) this).right.equals(((Not) ((Not) ((Implication) this).left).subExpr).subExpr));
        }
        return false;
    }

    @Override
    public String toString() {
        if (this instanceof Implication) {
            return this.toString();
        }
        if (this instanceof Or) {
            return this.toString();
        }
        if (this instanceof And) {
            return this.toString();
        }
        if (this instanceof Not) {
            return this.toString();
        }
        if (this instanceof Variable) {
            return this.toString();
        }
        return "";
    }
}

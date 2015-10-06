package annotated_expression;

import expression.Expression;

/**
 * Created by heat_wave on 10/6/15.
 */
public class AnnotatedAssumption extends AnnotatedExpression {
    public AnnotatedAssumption(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return expression.toString() + " assumption";
    }
}

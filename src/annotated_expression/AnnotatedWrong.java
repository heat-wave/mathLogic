package annotated_expression;

import expression.Expression;

/**
 * Created by heat_wave on 10/6/15.
 */
public class AnnotatedWrong extends AnnotatedExpression {
    public AnnotatedWrong(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return expression.toString();
    }
}

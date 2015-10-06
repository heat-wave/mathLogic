package annotated_expression;

import expression.Expression;

/**
 * Created by heat_wave on 10/6/15.
 */
public class AnnotatedMP extends AnnotatedExpression {
    public int alpha, alphaBeta;
    public AnnotatedMP(Expression expression, int alpha, int alphaBeta) {
        this.expression = expression;
        this.alpha = alpha;
        this.alphaBeta = alphaBeta;
    }

    @Override
    public String toString() {
        return expression.toString() + " MP " + (alpha + 1) + ", " + (alphaBeta + 1);
    }
}

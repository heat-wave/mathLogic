package utils;

import expression.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created by heat_wave on 10/3/15.
 */
public class Parser {
    // available predicates
    private static final String PREDICATES = "@?=";
    
    // available ariphmetic operators
    private static final String ARIPHMETIC_OPERATORS = "+*'";
    
    // available logic operators
    private static final String LOGIC_OPERATORS = ">|&!";

    // available words in propositional logic/predicats
    private static final String WORDS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    // available variables in predicats
    private static final String VARIABLES = "abcdefghijklmnopqrstuvwxyz0123456789";

    // temporary stack, that holds operators and brackets
    private static Stack<String> stackOperations = new Stack<>();

    // stack for holding expressions converted to reversed polish notation (holds current parsed expression)
    private static Stack<String> stackRPN = new Stack<>();

    // expression that represents expression-tree of current statement
    static Expression expr;


    private static boolean isWord(String token) {
        return WORDS.contains(token);
    }
    private static boolean isPredicateVariable(String token) {
        return VARIABLES.contains(token);
    }

    private static boolean isOpenBracket(String token) {
        return token.equals("(");
    }

    private static boolean isCloseBracket(String token) {
        return token.equals(")");
    }

    private static boolean isPredicate(String token) {
        return PREDICATES.contains(token);
    }

    private static boolean isAriphmeticOperator(String token) {
        return ARIPHMETIC_OPERATORS.contains(token);
    }
    private static boolean isLogicOperator(String token) {
        return LOGIC_OPERATORS.contains(token);
    }

    private static byte getPrecedence(String token) {
        return (byte) LOGIC_OPERATORS.indexOf(token);
    }

    private static boolean isLeftAssoc(String token) {
        return (token.equals("&") || token.equals("|")) || token.equals("+") || token.equals("*");
    }

    private static boolean isBinary(String token) {
        return (!token.equals("!")&&!token.equals("'")&&!token.equals("@")&&!token.equals("?"));
    }

    public static Expression parse(String expression) throws ParseException {
        // cleaning stacks
        stackOperations.clear();
        stackRPN.clear();

        StringTokenizer stringTokenizer = new StringTokenizer(expression, LOGIC_OPERATORS + "()" + WORDS, true);

        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            if (isOpenBracket(token)) {
                stackOperations.push(token);
            }
            else if (isCloseBracket(token)) {
                while (!stackOperations.isEmpty() && !isOpenBracket(stackOperations.lastElement())) {
                    stackRPN.push(stackOperations.pop());
                }
                stackOperations.pop();
            }
            else if (isWord(token)) {
                if (WORDS.indexOf(token) >= WORDS.indexOf("0")) {
                    stackRPN.push(stackRPN.pop() + token);
                }
                else {
                    stackRPN.push(token);
                }
            }
            else if (isLogicOperator(token)) {
                while (!stackOperations.isEmpty() && isLogicOperator(stackOperations.lastElement()) &&
                        ((isLeftAssoc(token) && getPrecedence(token) <= getPrecedence(stackOperations.lastElement())) ||
                                (!isLeftAssoc(token) && getPrecedence(token) < getPrecedence(stackOperations.lastElement())))) {
                    stackRPN.push(stackOperations.pop());
                }
                stackOperations.push(token);
            }
        }
        while(!stackOperations.isEmpty()) {
            stackRPN.push(stackOperations.pop());
        }
        expr = parseExpression();
        return expr;
    }

    private static Expression parseExpression() {
        String token = stackRPN.pop();
        if (token.equals(">")) {
            Expression right = parseExpression();
            Expression left = parseExpression();
            return new Implication(left, right);
        }
        if (token.equals("|")) {
            Expression right = parseExpression();
            Expression left = parseExpression();
            return new Or(left, right);
        }
        if (token.equals("&")) {
            Expression right = parseExpression();
            Expression left = parseExpression();
            return new And(left, right);
        }
        if (token.equals("!")) {
            Expression subExpr = parseExpression();
            return new Not(subExpr);
        }
        return new Variable(token);
    }

    public static ArrayList<Expression> parseAlpha(String assumption) throws ParseException {
        ArrayList<Expression> alpha = new ArrayList<>();
        String curAss = "";
        int position = assumption.indexOf("|-");
        for (int i = 0; i < position; i++) {
            char token = assumption.charAt(i);
            if (token == ',') {
                alpha.add(parse(curAss));
                curAss = "";
            }
            else {
                curAss += token;
            }
        }
        alpha.add(parse(curAss));
        return alpha;
    }
}

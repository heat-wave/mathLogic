package utils;

import expression.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Scanner;


/**
 * Created by heat_wave on 10/5/15.
 */
public class Axioms {
    static Expression[] axioms = new Expression[10];
    public static void parseAxioms() {
        try (Scanner in = new Scanner(new File("resources" + File.separator + "axioms.txt"))) {
            for (int i = 0; i < 10; i++) {
                axioms[i] = Parser.parse(in.next().replace("->", ">"));
            }
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }
    public static Expression axiom1(Expression alpha, Expression beta) {
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", alpha);
        variables.put("B", beta);
        return Substituter.substitute(axioms[0], variables);
    }
    public static Expression axiom2(Expression alpha, Expression beta, Expression gamma) {
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", alpha);
        variables.put("B", beta);
        variables.put("C", gamma);
        return Substituter.substitute(axioms[1], variables);
    }
    public static Expression axiom3(Expression alpha, Expression beta) {
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", alpha);
        variables.put("B", beta);
        return Substituter.substitute(axioms[2], variables);
    }
    public static Expression axiom4(Expression alpha, Expression beta) {
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", alpha);
        variables.put("B", beta);
        return Substituter.substitute(axioms[3], variables);
    }
    public static Expression axiom5(Expression alpha, Expression beta) {
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", alpha);
        variables.put("B", beta);
        return Substituter.substitute(axioms[4], variables);
    }
    public static Expression axiom6(Expression alpha, Expression beta) {
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", alpha);
        variables.put("B", beta);
        return Substituter.substitute(axioms[5], variables);
    }
    public static Expression axiom7(Expression alpha, Expression beta) {
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", alpha);
        variables.put("B", beta);
        return Substituter.substitute(axioms[6], variables);
    }
    public static Expression axiom8(Expression alpha, Expression beta, Expression gamma) {
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", alpha);
        variables.put("B", beta);
        variables.put("C", gamma);
        return Substituter.substitute(axioms[7], variables);
    }
    public static Expression axiom9(Expression alpha, Expression beta) {
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", alpha);
        variables.put("B", beta);
        return Substituter.substitute(axioms[8], variables);
    }
    public static Expression axiom10(Expression alpha) {
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", alpha);
        return Substituter.substitute(axioms[9], variables);
    }
}

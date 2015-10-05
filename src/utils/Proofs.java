package utils;

import expression.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by heat_wave on 10/5/15.
 */
public class Proofs {
    static ArrayList<Expression> addDoubleNot;
    static ArrayList<Expression> contraposition;
    static ArrayList<Expression> selfCons;
    static ArrayList<Expression> exclusion;
    static ArrayList<Expression> prependAnd;
    static ArrayList<Expression> removeAnd;
    static ArrayList<Expression> rotateImpl;
    static ArrayList<Expression> tertiumNonDatum;
    static ArrayList<Expression> noNoImpl;
    static ArrayList<Expression> noYesImpl;
    static ArrayList<Expression> yesNoNotImpl;
    static ArrayList<Expression> yesYesImpl;
    static ArrayList<Expression> noNoNotAnd;
    static ArrayList<Expression> noYesNotAnd;
    static ArrayList<Expression> yesNoNotAnd;
    static ArrayList<Expression> yesYesAnd;
    static ArrayList<Expression> noNoNotOr;
    static ArrayList<Expression> noYesOr;
    static ArrayList<Expression> yesNoOr;
    static ArrayList<Expression> yesYesOr;

    public static void parseProofs() {
        addDoubleNot = parseFile("AddDoubleNot");
        contraposition = parseFile("Contraposition");
        selfCons = parseFile("SelfCons");
        exclusion = parseFile("Exclusion");
        prependAnd = parseFile("PrependAnd");
        removeAnd = parseFile("RemoveAnd");
        rotateImpl = parseFile("RotateImpl");
        tertiumNonDatum = parseFile("TertiumNonDatum");
        noNoImpl = parseFile("NoNoImpl");
        noYesImpl = parseFile("NoYesImpl");
        yesNoNotImpl = parseFile("YesNoNotImpl");
        yesYesImpl = parseFile("YesYesImpl");
        noNoNotAnd = parseFile("NoNoNotAnd");
        noYesNotAnd = parseFile("NoYesNotAnd");
        yesNoNotAnd = parseFile("YesNoNotAnd");
        yesYesAnd = parseFile("YesYesAnd");
        noNoNotOr = parseFile("NoNoNotOr");
        noYesOr = parseFile("NoYesOr");
        yesNoOr = parseFile("YesNoOr");
        yesYesOr = parseFile("YesYesOr");
    }
    private static ArrayList<Expression> parseFile(String fileName) {
        ArrayList<Expression> list = new ArrayList<>();
        try (Scanner in = new Scanner(new File("resources/proofs/" + fileName + ".txt"))) {
            while (in.hasNext()) {
                list.add(Parser.parse(in.next().replace("->", ">")));
            }
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Expression> getAddDoubleNot(Expression a) {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", a);
        result.addAll(addDoubleNot.stream().map(e -> Substituter.substitute(e, variables)).collect(Collectors.toList()));
        return result;
    }

    public static ArrayList<Expression> getContraposition(Expression a, Expression b) {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", a);
        variables.put("B", b);
        result.addAll(contraposition.stream().map(e -> Substituter.substitute(e, variables)).collect(Collectors.toList()));
        return result;
    }

    public static ArrayList<Expression> getSelfCons(Expression a) {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", a);
        result.addAll(selfCons.stream().map(e -> Substituter.substitute(e, variables)).collect(Collectors.toList()));
        return result;
    }

    public static ArrayList<Expression> getExclusion(Expression a, Expression b) {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", a);
        variables.put("B", b);
        result.addAll(exclusion.stream().map(e -> Substituter.substitute(e, variables)).collect(Collectors.toList()));
        return result;
    }

    public static ArrayList<Expression> getPrependAnd(Expression a, Expression b, Expression c) {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", a);
        variables.put("B", b);
        variables.put("C", c);
        result.addAll(prependAnd.stream().map(e -> Substituter.substitute(e, variables)).collect(Collectors.toList()));
        return result;
    }

    public static ArrayList<Expression> getRemoveAnd(Expression a, Expression b, Expression c) {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", a);
        variables.put("B", b);
        variables.put("C", c);
        result.addAll(removeAnd.stream().map(e -> Substituter.substitute(e, variables)).collect(Collectors.toList()));
        return result;
    }

    public static ArrayList<Expression> getRotateImpl(Expression a, Expression b, Expression c) {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", a);
        variables.put("B", b);
        variables.put("C", c);
        result.addAll(rotateImpl.stream().map(e -> Substituter.substitute(e, variables)).collect(Collectors.toList()));
        return result;
    }

    public static ArrayList<Expression> getTertiumNonDatum(Expression a) {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", a);
        result.addAll(tertiumNonDatum.stream().map(e -> Substituter.substitute(e, variables)).collect(Collectors.toList()));
        return result;
    }

    public static ArrayList<Expression> getNoNoImpl(Expression a, Expression b) {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", a);
        variables.put("B", b);
        result.addAll(noNoImpl.stream().map(e -> Substituter.substitute(e, variables)).collect(Collectors.toList()));
        return result;
    }

    public static ArrayList<Expression> getNoYesImpl(Expression a, Expression b) {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", a);
        variables.put("B", b);
        result.addAll(noYesImpl.stream().map(e -> Substituter.substitute(e, variables)).collect(Collectors.toList()));
        return result;
    }

    public static ArrayList<Expression> getYesNoNotImpl(Expression a, Expression b) {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", a);
        variables.put("B", b);
        result.addAll(yesNoNotImpl.stream().map(e -> Substituter.substitute(e, variables)).collect(Collectors.toList()));
        return result;
    }

    public static ArrayList<Expression> getYesYesImpl(Expression a, Expression b) {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", a);
        variables.put("B", b);
        result.addAll(yesYesImpl.stream().map(e -> Substituter.substitute(e, variables)).collect(Collectors.toList()));
        return result;
    }

    public static ArrayList<Expression> getNoNoNotAnd(Expression a, Expression b) {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", a);
        variables.put("B", b);
        result.addAll(noNoNotAnd.stream().map(e -> Substituter.substitute(e, variables)).collect(Collectors.toList()));
        return result;
    }

    public static ArrayList<Expression> getNoYesNotAnd(Expression a, Expression b) {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", a);
        variables.put("B", b);
        result.addAll(noYesNotAnd.stream().map(e -> Substituter.substitute(e, variables)).collect(Collectors.toList()));
        return result;
    }

    public static ArrayList<Expression> getYesNoNotAnd(Expression a, Expression b) {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", a);
        variables.put("B", b);
        result.addAll(yesNoNotAnd.stream().map(e -> Substituter.substitute(e, variables)).collect(Collectors.toList()));
        return result;
    }

    public static ArrayList<Expression> getYesYesAnd(Expression a, Expression b) {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", a);
        variables.put("B", b);
        result.addAll(yesYesAnd.stream().map(e -> Substituter.substitute(e, variables)).collect(Collectors.toList()));
        return result;
    }

    public static ArrayList<Expression> getNoNoNotOr(Expression a, Expression b) {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", a);
        variables.put("B", b);
        result.addAll(noNoNotOr.stream().map(e -> Substituter.substitute(e, variables)).collect(Collectors.toList()));
        return result;
    }

    public static ArrayList<Expression> getNoYesOr(Expression a, Expression b) {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", a);
        variables.put("B", b);
        result.addAll(noYesOr.stream().map(e -> Substituter.substitute(e, variables)).collect(Collectors.toList()));
        return result;
    }

    public static ArrayList<Expression> getYesNoOr(Expression a, Expression b) {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", a);
        variables.put("B", b);
        result.addAll(yesNoOr.stream().map(e -> Substituter.substitute(e, variables)).collect(Collectors.toList()));
        return result;
    }

    public static ArrayList<Expression> getYesYesOr(Expression a, Expression b) {
        ArrayList<Expression> result = new ArrayList<>();
        HashMap<String, Expression> variables = new HashMap<>();
        variables.put("A", a);
        variables.put("B", b);
        result.addAll(yesYesOr.stream().map(e -> Substituter.substitute(e, variables)).collect(Collectors.toList()));
        return result;
    }
}

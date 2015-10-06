import expression.*;
import utils.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by heat_wave on 10/6/15.
 */
public class Task3 {
    public static void main(String[] args) {
        Proofs.parseProofs();
        Axioms.parseAxioms();
        new Task3().solve();
    }
    public void solve() {
        try (Scanner in = new Scanner(new File("task3.in"))) {
            Expression expression = Parser.parse(in.next().replace("->", ">"));
            ArrayList<Expression> proof = Prover.prove(expression);
            if (proof != null) {
                ProofAnnotator.getAnnotatedProof(proof, new HashSet<>()).forEach(System.out::println);
            }
        } catch (ParseException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

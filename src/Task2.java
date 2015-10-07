import expression.Expression;
import expression.Implication;
import utils.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by heat_wave on 10/5/15.
 */
public class Task2 {
    public static void main(String[] args) {
        Axioms.parseAxioms();
        Proofs.parseProofs();
        new Task2().solve();
    }
    public void solve() {
        try (Scanner in = new Scanner(new File("task2.in"))) {
            ArrayList<Expression> proof = new ArrayList<>();
            // firstly we need to get and parse context
            String assumption = in.next();
            ArrayList<Expression> assumptionsAux = Parser.parseAlpha(assumption);
            // alpha - expression, which we're going to "deduct"
            Expression alpha = assumptionsAux.get(assumptionsAux.size() - 1);
            // assumptions - whole left context
            HashSet<Expression> assumptions = new HashSet<>();
            for (int i = 0; i < assumptionsAux.size() - 1; i++) {
                assumptions.add(assumptionsAux.get(i));
                if (i > 0) {
                    System.out.print(", ");
                }
                System.out.print(assumptionsAux.get(i).toString());
            }
            // then, we are ready to read proof, that we need to complete
            while (in.hasNext()) {
                String statement = in.next();
                proof.add(Parser.parse(statement.replace("->", ">")));
            }
            if (!assumptions.isEmpty()) {
                System.out.print(" ");
            }
            System.out.println("|- " + (new Implication(alpha, proof.get(proof.size() - 1))).toString());
            // let's do deduction and print annotated proof
            ProofAnnotator.getAnnotatedProof(Deductor.completeProof(alpha, assumptions, proof), assumptions).forEach(System.out::println);
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }
}

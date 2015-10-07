import expression.Expression;
import utils.Parser;
import utils.ProofAnnotator;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by heat_wave on 10/5/15.
 */
public class Task1 {
    public static void main(String[] args) {
        new Task1().solve();
    }
    public void solve() {
        try (Scanner in = new Scanner(new File("task1.in"))) {
            ArrayList<Expression> proof = new ArrayList<>();
            // read and parse all incoming statements to list
            while (in.hasNext()) {
                String statement = in.next();
                proof.add(Parser.parse(statement.replace("->", ">")));
            }
            // send input proof to annotator, that will check and annotate it
            ProofAnnotator.getAnnotatedProof(proof, new HashSet<>()).forEach(System.out::println);
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }
}
